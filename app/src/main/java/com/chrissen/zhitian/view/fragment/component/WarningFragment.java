package com.chrissen.zhitian.view.fragment.component;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chrissen.zhitian.R;
import com.chrissen.zhitian.adapter.WarningAdapter;

/**
 * Created by Administrator on 2017/9/8 0008.
 */

public class WarningFragment extends Fragment {
    private WarningAdapter mWarningAdapter;
    private RecyclerView mWarningRv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_warning,container,false);
        mWarningRv = (RecyclerView) view.findViewById(R.id.warning_rv);
        mWarningAdapter = new WarningAdapter(getActivity());
        mWarningRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mWarningRv.setAdapter(mWarningAdapter);
        return view;
    }



}
