package com.chrissen.zhitian.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chrissen.zhitian.R;
import com.chrissen.zhitian.adapter.IndexAdapter;
import com.chrissen.zhitian.model.bean.Weather;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class IndexFragment extends Fragment {

    private RecyclerView indexRv;
    private IndexAdapter indexAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_index_weather,container,false);
        indexRv = (RecyclerView) view.findViewById(R.id.weather_index_rv);
        return view;
    }

    @Subscribe
    public void onEvent(Weather weather){
        indexRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        indexAdapter = new IndexAdapter(weather.getInfo().getIndexList());
        indexRv.setAdapter(indexAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
