using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using ESRI.ArcGIS.Geometry;
using ESRI.ArcGIS.Carto;
using ESRI.ArcGIS.Controls;
using ESRI.ArcGIS.Display;
using ESRI.ArcGIS.esriSystem;
using ESRI.ArcGIS.GlobeCore;
using ESRI.ArcGIS.Output;
using ESRI.ArcGIS.SystemUI;
using ESRI.ArcGIS.Analyst3D;
using ESRI.ArcGIS.DataSourcesFile;
using ESRI.ArcGIS.Geodatabase;
using ESRI.ArcGIS.GeoAnalyst;
using ESRI.ArcGIS.DataManagementTools;
using ESRI.ArcGIS.Geoprocessor;
using ESRI.ArcGIS.Geoprocessing;
using ESRI.ArcGIS.DataSourcesGDB;
namespace checkMoutainShape
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        //定义工作空间
        IWorkspaceFactory pFactory;

        IWorkspace pWorkspace;

        IFeatureWorkspace pFeatureWorkspace;

        //判断曲线是否闭合
        public bool CheckLine(List<IPoint> contourPoint)
        {

            //判断起点和终点是否是同一个点
            int pointCount = contourPoint.Count;

            if (contourPoint[0].X!=contourPoint[pointCount-1].X||contourPoint[0].Y!=contourPoint[pointCount-1].Y)
            {
                return false;
            }
            return true;   //默认返回曲线是闭合的
        }


        //删除不是闭合的曲线
        public IFeatureLayer deleteLine(IFeatureLayer contourLayer)
        {

            IFeatureClass conLineclass = contourLayer.FeatureClass;

            IFeatureCursor pCursor = conLineclass.Search(null, false);

            IFeature pFeature = null;

            while ((pFeature = pCursor.NextFeature()) != null)
            {
                //获取等高线
                IGeometry geometry = pFeature.Shape;
                IPolyline polyline = (IPolyline)geometry;

                //定义列表存储构成等高线的点的坐标、高程
                List<IPoint> contourPoint = new List<IPoint>();

                contourPoint = getPointByContourLine(polyline);

                if (!CheckLine(contourPoint))
                {
                    //删除该等高线
                    pFeature.Delete();

                }

            }


            return contourLayer;
        }


        //在指定文件目录下新建并获取要素类
        public IFeatureClass getFeatureClass(string shpPath)
        {

            //确定存放shapefile的文件路径是否存在



            string strShapeFolder = shpPath;

            string strShapeFile = "contourPolygon.shp";


            FileInfo fFile = new FileInfo(strShapeFolder + @"\" + strShapeFile);
                 
            //是否重命名
            if (fFile.Exists)
            {

                DialogResult r2 = MessageBox.Show("此文件名已经被覆盖","返回值是0否1",MessageBoxButtons.YesNo,MessageBoxIcon.Question);

                //取消关闭
                if (r2 == System.Windows.Forms.DialogResult.Yes)
                {
                    DirectoryInfo fold = new DirectoryInfo(strShapeFolder);

                    FileInfo[] files = fold.GetFiles("contourPolygon" + ".*");

                    foreach (FileInfo f in files)
                    {
                        f.Delete();
                    }

                }

            }
            string sFileFullName = strShapeFolder + strShapeFile;

            IWorkspaceFactory pWorkspaceFactory = new ShapefileWorkspaceFactory();

            IFeatureWorkspace pFeatureWorkspace = (IFeatureWorkspace)pWorkspaceFactory.OpenFromFile(strShapeFolder,0);

            IFeatureClass pFeatureClass = null;

            if (File.Exists(sFileFullName))
            {

                pFeatureClass = pFeatureWorkspace.OpenFeatureClass(strShapeFile);

                IDataset pDataset = (IDataset)pFeatureClass;

                pDataset.Delete();
                
            }
            //创建字段
            IFields pFields = new FieldsClass();

            IFieldsEdit pFieldsEdit = (IFieldsEdit)pFields;

            IField pField = new FieldClass();

            IFieldEdit pFieldEdit = (IFieldEdit)pField;

            ISpatialReferenceFactory spatialReferenceFac = new SpatialReferenceEnvironment();

            IGeographicCoordinateSystem pGCS ;

            //pGCS = spatialReferenceFac.CreateGeographicCoordinateSystem();

            pFieldEdit.Name_2 = "SHAPE";

            pFieldEdit.Type_2 = esriFieldType.esriFieldTypeGeometry;

            IGeometryDefEdit pGeoDef = new GeometryDefClass();

            IGeometryDefEdit pGeoDefEdit = (IGeometryDefEdit)pGeoDef;

            pGeoDefEdit.GeometryType_2 = esriGeometryType.esriGeometryPolygon;

            pGeoDefEdit.SpatialReference_2 = new UnknownCoordinateSystemClass();

            pFieldEdit.GeometryDef_2 = pGeoDef;

            pFieldsEdit.AddField(pField);

            //创建shp
      pFeatureClass = pFeatureWorkspace.CreateFeatureClass(strShapeFile,pFields,null,null,esriFeatureType.esriFTSimple,"SHAPE","");


            return pFeatureClass;

        }

        //获取生成的等高线的最大的高程、最小的高程
        public int getMax(IFeatureLayer contourLayer)
        {

            IFeatureClass conLineclass = contourLayer.FeatureClass;

            IFeatureCursor pCursor = conLineclass.Search(null,false);

            IFeature pFeat = null;

            int contourIndex = conLineclass.Fields.FindField("Contour");

            int maxContour = 0;
            int minContour = 0;
            int maxConIndex = 0;

            int index = 0;

            while ((pFeat = pCursor.NextFeature()) != null)
            {
                Int16 contour = Convert.ToInt16(pFeat.get_Value(contourIndex));
               
                if (maxContour<=contour)
                {
                    maxContour = contour;
                    
                    maxConIndex = index;   //获取最大记录
                }
                if (minContour>=contour)
                {
                    minContour = contour;
                }
                index++;
            }

            return maxContour;
        }

        public IFeatureClass IfeatureBuffer(List<IPoint> contourPoint, IFeatureClass featureclass, String contour)
        {
            if (featureclass.ShapeType!=ESRI.ArcGIS.Geometry.esriGeometryType.esriGeometryPolygon)
            {
                return null;
            }

            //根据数据集得到工作空间
            IDataset dataset = (IDataset)featureclass;

            IWorkspace workspace = dataset.Workspace;

            IWorkspaceEdit workspaceEdit = (IWorkspaceEdit)workspace;

            // 开始数据编辑和数据操作

            workspaceEdit.StartEditing(true);

            workspaceEdit.StartEditOperation();


            //创建Feature Buffer
            IFeatureBuffer featureBuffer = featureclass.CreateFeatureBuffer();


            // 创建插入游标并设置buffering为true
            IFeatureCursor featurecursor = featureclass.Insert(true);

            object featureOID;


            //构造面，并将构成等高线的点赋予面

            IPoint pPoint = new PointClass();

            IPointCollection pointCollection = new PolygonClass();

            object missing = Type.Missing;

            double x, y;

            for (int i = 0; i < contourPoint.Count; i++)
            {

                x = contourPoint[i].X;

                y = contourPoint[i].Y;

                pPoint = new PointClass();

                pPoint.PutCoords(x, y);

                pointCollection.AddPoint(pPoint, ref missing, ref missing);

            }



            IPolygon pPolygon = new PolygonClass();

            //生成面
            pPolygon = (IPolygon)pointCollection;

            featureBuffer.Shape = pPolygon;

            featureOID = featurecursor.InsertFeature(featureBuffer);


            //对featurebuffer插入字段

            //featureBuffer.set_Value(featureBuffer.Fields.FindField("Contour"), contour);

           

            //停止编辑
            workspaceEdit.StopEditOperation();
            workspaceEdit.StopEditing(true);
           

            //释放游标
            System.Runtime.InteropServices.Marshal.ReleaseComObject(featurecursor);

            //返回要素类
            return featureclass;
        }

        private void button1_Click(object sender, EventArgs e)
        {

            //得到dem、分成多个区域







            //获取等高线
            ILayer tLayer = this.axMapControl1.get_Layer(0);

            IRasterLayer tRasterLayer = (IRasterLayer)tLayer;

            IFeatureClass tFeatureClass = null;

            IGeoDataset tGeodataset = null;

            //使用接口参数（Raster,等高线间距,基值）
            ISurfaceOp tSurfaceop = new RasterSurfaceOpClass();

             object obj = 0;

             tGeodataset = tSurfaceop.Contour((IGeoDataset)tRasterLayer.Raster, 5, ref obj); //等高线间距为10米

             IFeatureLayer tFeatureLayer = new FeatureLayerClass();

            //判断是否生成等高线
            if (tGeodataset!=null)
            {
                tFeatureClass = (IFeatureClass)tGeodataset;
               
                if (tFeatureClass!=null)
                {
                    tFeatureLayer.FeatureClass = tFeatureClass;

                    //this.axMapControl1.AddLayer((ILayer)tFeatureLayer);

                    //this.axMapControl1.Refresh();
                }

            }

            tFeatureLayer = deleteLine(tFeatureLayer);

            this.axMapControl1.AddLayer((ILayer)tFeatureLayer);

            this.axMapControl1.Refresh();

            /*选取特定高程的等高线*/
           
            //得到Feature的指针
            IQueryFilter queryFilter = new SpatialFilterClass();

            int maxContour = getMax(tFeatureLayer);

            int queryContour = maxContour - 10; //选择具体某条等高线

            queryFilter.WhereClause = "Contour = " + queryContour;

            IFeatureCursor featureCursor = tFeatureClass.Search(queryFilter,false);
            IFeature feature = featureCursor.NextFeature();

            //获取表的长度
            //int attributeTableLength = tFeatureClass.Fields.FindField("id");
            //string lenStr = attributeTableLength.ToString();
            //MessageBox.Show(lenStr);
            ////
            //IGeometry geometry;
            //IPolyline polyline;
            /* 筛选符合要求的等高线*/
            //while (feature!=null)
            //{
            //    IFields ptFields  =  feature.Fields;
            //    geometry =  feature.Shape;
            //    polyline =  (IPolyline)geometry;
            //    IField field = ptFields.get_Field(0);
            //    feature.get_Value();
            //    feature = featureCursor.NextFeature(); //指针移动到下一行。
            //}




            
            //释放游标
            System.Runtime.InteropServices.Marshal.ReleaseComObject(featureCursor);


            //获取选中的等高线
            IGeometry geometry = feature.Shape;
            IPolyline polyline = (IPolyline)geometry;

            //定义列表存储构成等高线的点的坐标、高程
            List<IPoint> contourPoint = new List<IPoint>(); 

            contourPoint = getPointByContourLine(polyline);

            /*将线转成面,并获取面的面积*/
           
            ////初始化Geoprocessor工具
            //Geoprocessor processor = new Geoprocessor();

            //FeatureToPolygon fPolygon = new FeatureToPolygon();

            //fPolygon.in_features = feature;

            //fPolygon.out_feature_class = "D:\\testData\\conPolygon.shp";
            
            //processor.Execute(fPolygon,null);
               

            //构造面，并将构成等高线的点赋予面

            //IPoint pPoint = new PointClass();

            //IPointCollection pointCollection = new PolygonClass();

            //object missing = Type.Missing;

            //double x, y;

            //for (int i = 0; i < contourPoint.Count; i++)
            //{

            //    x = contourPoint[i].X;

            //    y = contourPoint[i].Y;

            //    pPoint = new PointClass();

            //    pPoint.PutCoords(x, y);

            //    pointCollection.AddPoint(pPoint, ref missing, ref missing);

            //}

            //IPolygon pPolygon = new PolygonClass();

            ////生成面
            //pPolygon = (IPolygon)pointCollection;

            

            //获取指定数据库的要素类

            string path = "D:\\testPolygon";

            IFeatureClass featureclass = getFeatureClass(path);

            IFeatureLayer polyFeatureLayer = new FeatureLayerClass();

            IFeatureClass polyFeatclas = IfeatureBuffer(contourPoint, featureclass, maxContour.ToString());


            /*给由等高线生成的面添加新字段Contour(表示等高线高程值是多少)*/
             //定义新字段
            IField pField = new FieldClass();

            //字段编辑
            IFieldEdit pFieldEdit = pField as IFieldEdit;

            //新建字段名
            pFieldEdit.Name_2 = "Contour";

            //获取属性表
            IClass pTable = polyFeatclas as IClass;

            pTable.AddField(pFieldEdit);

            IFeature pFea = polyFeatclas.GetFeature(0);

            pFea.set_Value(pFea.Fields.FindField("Contour"), maxContour.ToString());

            //保存
            pFea.Store();

            polyFeatureLayer.FeatureClass = polyFeatclas;

            //将生成的面加入到当前地图控件中
            this.axMapControl1.AddLayer(polyFeatureLayer);

            this.axMapControl1.Refresh();

            IFeature polyFeature, lineFeature;

            polyFeature = polyFeatclas.GetFeature(0);

            lineFeature = feature;

            double judResult = calculate(polyFeature, lineFeature);

            double radius = fitting(contourPoint);

            RadiTBox.Text = radius.ToString();

            MessageBox.Show(judResult.ToString(), "面积/周长");
        }

        //获取构成等高线的点
        public static List<IPoint> getPointByContourLine(IPolyline contourLine) 
        {
            List<IPoint> pointList = new List<IPoint>();

            IPointCollection pointCollection = (IPointCollection)contourLine;

            IPoint point;

            int pointCount = pointCollection.PointCount; //获取点的个数

            for (int i = 0; i < pointCount; i++)
            {
                point = pointCollection.get_Point(i);

                pointList.Add(point);
            }

            if (pointList.Count>0)
            {
                return pointList;
            }
            else
	        {
                return null;
	        }

        }

        //计算等高线的长度、等高线构成的面的面积
        public  double calculate(IFeature polyFeature,IFeature lineFeature)
        {  

            double Area = 0;
            double Perimeter = 0;
            if (polyFeature.Shape.GeometryType == esriGeometryType.esriGeometryPolygon)
            {

                IArea pArea = polyFeature.Shape as IArea;

                Area += pArea.Area;
            }
            if (lineFeature.Shape.GeometryType== esriGeometryType.esriGeometryPolyline)
            {

                IGeometry geometry = lineFeature.Shape;

                IPolyline polyline = (IPolyline)geometry;

                Perimeter += polyline.Length;
            }

            Area = Math.Abs(Area);

            AreaTBox.Text = Area.ToString();

            PeriTBox.Text = Perimeter.ToString();

            //返回面积与周长的比值
            return Area / Perimeter;
        }


        //根据点拟合出圆的半径和圆心
        public static double fitting(List<IPoint> pointDataSet)
        {

            double centerX = 0.0f,centerY = 0.0f;

            double radius = 0.0f;

            if (pointDataSet.Count<3)
            {
                return 0;
                MessageBox.Show("点的个数少于3");
            }
            double sum_x = 0.0f, sum_y = 0.0f;
            double sum_x2 = 0.0f, sum_y2 = 0.0f;
            double sum_x3 = 0.0f, sum_y3 = 0.0f;
            double sum_xy = 0.0f, sum_x1y2 = 0.0f, sum_x2y1 = 0.0f;

            int pointCount = pointDataSet.Count; //获取点的个数

            for (int i = 0; i < pointCount; i++)
            {
                double x = pointDataSet[i].X;
                double y = pointDataSet[i].Y;
                double x2 = x * x;
                double y2 = y * y;
                sum_x += x;
                sum_y += y;
                sum_x2 += x2;
                sum_y2 += y2;
                sum_x3 += x2 * x;
                sum_y3 += y2 * y;
                sum_xy += x * y;
                sum_x1y2 += x * y2;
                sum_x2y1 += x2 * y;
            }

            double C, D, E, G, H;
            double a, b, c;

            C = pointCount * sum_x2 - sum_x * sum_x;
            D = pointCount * sum_xy - sum_x * sum_y;
            E = pointCount * sum_x3 + pointCount * sum_x1y2 - (sum_x2 + sum_y2) * sum_x;
            G = pointCount * sum_y2 - sum_y * sum_y;
            H = pointCount * sum_x2y1 + pointCount * sum_y3 - (sum_x2 + sum_y2) * sum_y;
            a = (H * D - E * G) / (C * G - D * D);
            b = (H * C - E * D) / (D * D - G * C);
            c = -(a * sum_x + b * sum_y + sum_x2 + sum_y2) / pointCount;

            centerX = a / (-2);

            centerY = b / (-2);

            radius = Math.Sqrt(a * a + b * b - 4 * c) / 2;

            return radius;
        }
        
        /*计算等高线围成的区域的面积、周长*/
        public static double calculateArea_perimeter(List<IPoint> pointDataSet)
        {

            int pointCount = pointDataSet.Count; //获取点的个数

            double area = 0;

            double perimeter = 0;

            int j = pointCount - 1;

            //使用梯形算法计算多边形面积|、周长
            for (int i = 0; i < pointCount; i++)
            { 
                area += (pointDataSet[j].X + pointDataSet[i].X) * (pointDataSet[j].Y - pointDataSet[i].Y);
                perimeter += Math.Sqrt((pointDataSet[j].X - pointDataSet[i].X) * (pointDataSet[j].X - pointDataSet[i].X) + (pointDataSet[j].Y - pointDataSet[i].Y) * (pointDataSet[j].Y - pointDataSet[i].Y));
                j = i;
            }

            area/= 2;

            //避免点的次序不是逆时针而导致计算结果为负数
            area = Math.Abs(area);

            //计算面积/周长(其值是否近似等于半径的一半)
            double ratio = perimeter/area;

            //显示比值
            MessageBox.Show(ratio.ToString()); 
            return 0;
        }
    }
}
