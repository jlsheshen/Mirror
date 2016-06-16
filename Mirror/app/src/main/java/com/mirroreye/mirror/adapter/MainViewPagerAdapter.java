package com.mirroreye.mirror.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mirroreye.mirror.ui.main.AllFragment;

import java.util.List;

/**
 * Created by liangduo on 16/6/14.
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // instanceof : 二元操作符
        // 判断其左边对象是否为其右边类的实例，返回boolean类型的数据。
        // 可以用来判断继承中的子类的实例是否为父类的实现
        if (fragments.get(position) instanceof AllFragment)
            return AllFragment.newInstance(position);
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
