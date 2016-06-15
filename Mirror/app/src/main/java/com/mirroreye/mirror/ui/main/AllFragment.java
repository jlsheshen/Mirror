package com.mirroreye.mirror.ui.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.adapter.HorizontalRecyclerViewAdapter;
import com.mirroreye.mirror.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangduo on 16/6/14.
 */
public class AllFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private HorizontalRecyclerViewAdapter horizontalRecyclerViewAdapter;

    @Override
    public int setLayout() {
        return R.layout.fragment_all;
    }

    @Override
    protected void initView(View view) {
        recyclerView= bindView(R.id.all_fragment_rv);
    }

    @Override
    protected void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        horizontalRecyclerViewAdapter = new HorizontalRecyclerViewAdapter(getContext());

        List<String> data = new ArrayList<>();
        for (int i = 0 ;i< 10 ;i++){
            data.add(i+"巴拉拉能量~~~~");
        }
        horizontalRecyclerViewAdapter.setData(data);
        recyclerView.setAdapter(horizontalRecyclerViewAdapter);

    }
}
