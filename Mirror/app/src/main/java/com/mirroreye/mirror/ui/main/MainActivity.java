package com.mirroreye.mirror.ui.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.adapter.MainViewPagerAdapter;
import com.mirroreye.mirror.adapter.PPWAdapter;
import com.mirroreye.mirror.base.BaseActivity;
import com.mirroreye.mirror.base.view.VerticalViewPager;
import com.mirroreye.mirror.interfaces.PPWToFragment;
import com.mirroreye.mirror.ui.login.LoginActivty;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener, PPWToFragment {
    private VerticalViewPager verticalViewPager;
    private MainViewPagerAdapter mainViewPagerAdapter;
    private List<Fragment> fragmentList;
    private PPWAdapter ppwAdapter;
    private TextView login;


    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        verticalViewPager = bindView(R.id.main_vertical_vp);
        login = bindView(R.id.login);
    }

    @Override
    protected void initData() {
        ppwAdapter = new PPWAdapter(this);

        initFragment();
        mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.setFragments(fragmentList);
        verticalViewPager.setAdapter(mainViewPagerAdapter);
        verticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ppwAdapter.setType(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        login.setOnClickListener(this);


    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            fragmentList.add(new AllFragment());
        }
        fragmentList.add(new CartFragment());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                Intent intent = new Intent(this, LoginActivty.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void getPosition(int position) {
        verticalViewPager.setCurrentItem(position);
    }
}
