package com.example.xinghuacheng.howrelax.Activity;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

	List<Fragment> fragments;

	/**
	 * Fragmentʵ��ֻ��Zero��One��Two������
	 */
	public FragmentAdapter(FragmentManager fm) {
		super(fm);
		fragments = new ArrayList<Fragment>();
		fragments.add(new FragmentTwo());
		fragments.add(new FragmentZero());
		fragments.add(new FragmentOne());
		fragments.add(new FragmentTwo());
		fragments.add(new FragmentZero());
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}
}