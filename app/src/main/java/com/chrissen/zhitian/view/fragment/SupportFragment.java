package com.chrissen.zhitian.view.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chrissen.zhitian.R;

/**
 * Created by Administrator on 2017/9/3 0003.
 */

public class SupportFragment extends Fragment {

    private TextView alipayTv , coolapkTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_support,container,false);
        initLayout(view);
        return view;
    }

    private void initLayout(View view) {
        coolapkTv = (TextView) view.findViewById(R.id.support_coolapk_tv);
        coolapkTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.coolapk.com/apk/136267";
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        alipayTv = (TextView) view.findViewById(R.id.support_alipay_tv);
        alipayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData cd = ClipData.newPlainText("alipay_account","chrissen0814@gmail.com");
                cm.setPrimaryClip(cd);
                Toast.makeText(getActivity(), "账户信息已经复制，请打开支付宝来完成捐赠！", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
