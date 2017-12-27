package com.example.xinghuacheng.howrelax.switchActivity;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xinghuacheng.howrelax.R;

import java.util.List;

/**
 * Created by Xinghua Cheng on 04/08/2017.
 */

public class ItemAdapter extends ArrayAdapter<secondItem> {

    // 畫面資源編號
    private int resource;
    // 包裝的記事資料
    private List<secondItem> items;

    public ItemAdapter(Context context, int resource, List<secondItem> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout itemView;
        // 讀取目前位置的記事物件
        final secondItem item = getItem(position);

        if (convertView == null) {
            // 建立項目畫面元件
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater)
                    getContext().getSystemService(inflater);
            li.inflate(resource, itemView, true);
        }
        else {
            itemView = (LinearLayout) convertView;
        }

        // 讀取記事顏色、已選擇、標題與日期時間元件
        RelativeLayout typeColor = (RelativeLayout) itemView.findViewById(R.id.type_color);
        ImageView selectedItem = (ImageView) itemView.findViewById(R.id.selected_item);
        TextView titleView = (TextView) itemView.findViewById(R.id.actTitle_text);
        TextView dateView = (TextView) itemView.findViewById(R.id.actDate_text);
        ImageView imageView = (ImageView)itemView.findViewById(R.id.activity_thumb);

        // 設定記事顏色
        GradientDrawable background = (GradientDrawable)typeColor.getBackground();
        background.setColor(item.getColor().parseColor());


        // 設定標題與日期時間
        titleView.setText(item.getActTitle());
        dateView.setText(item.getActTime());
        imageView.setImageBitmap(item.getBitmap());
        imageView.setVisibility(View.VISIBLE);


        // 設定是否已選擇
//        selectedItem.setVisibility(item.isSelected() ? View.VISIBLE : View.INVISIBLE);

        return itemView;
    }

    // 設定指定編號的記事資料
    public void set(int index, secondItem item) {
        if (index >= 0 && index < items.size()) {
            items.set(index, item);
            notifyDataSetChanged();
        }
    }

    // 讀取指定編號的記事資料
    public secondItem get(int index) {
        return items.get(index);
    }

}