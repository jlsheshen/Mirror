package com.mirroreye.mirror.ui.main;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.adapter.MainViewPagerAdapter;
import com.mirroreye.mirror.base.BaseActivity;
import com.mirroreye.mirror.base.view.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private VerticalViewPager verticalViewPager;
    private MainViewPagerAdapter mainViewPagerAdapter;
    private List<Fragment> fragmentList;


    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        verticalViewPager = bindView(R.id.main_vertical_vp);
    }

    @Override
    protected void initData() {
        initFragment();
        mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.setFragments(fragmentList);
        verticalViewPager.setAdapter(mainViewPagerAdapter);

    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new AllFragment());
        fragmentList.add(new GogglesFragment());
        fragmentList.add(new SunglassesFragment());
        fragmentList.add(new ShareFragment());
        fragmentList.add(new CartFragment());
    }
}
