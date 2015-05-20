package com.insthub.ecmobile.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.insthub.ecmobile.fragment.ItemFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页滑动导航栏.
 *
 * @since Apr. 09, 2015
 */
public class MsgPageAdapter extends FragmentPagerAdapter {
	private List<ItemFragment> mFragmentList = new ArrayList<>();

	public MsgPageAdapter(FragmentManager manager, List<ItemFragment> fragments) {
		super(manager);

		mFragmentList.addAll(fragments);
		manager.executePendingTransactions();
	}

	@Override
	public int getCount() {
		return mFragmentList.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public ItemFragment getItem(int position) {
		return mFragmentList.get(position);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mFragmentList.get(position).getTitle();
	}

//	public int getPageIconRes(int position){return mFragmentList.get(position).getIcon();}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		return super.instantiateItem(container, position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
	}

	public void updateList(List<ItemFragment> fragmentList) {
		mFragmentList.clear();
		mFragmentList.addAll(fragmentList);
		notifyDataSetChanged();
	}

	/*
	* icon id
	* */
//	@Override
//	public int getPageIconResId(int position) {
//		return R.drawable.accsessory_arrow_down;
//	}
}
