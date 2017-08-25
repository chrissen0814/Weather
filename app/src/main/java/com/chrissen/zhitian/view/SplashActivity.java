package com.chrissen.zhitian.view;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chrissen.zhitian.R;
import com.chrissen.zhitian.presenter.CityPresenter;
import com.chrissen.zhitian.presenter.CityPresenterImpl;
import com.chrissen.zhitian.util.PreferencesLoader;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private static final int PERMISSION = 1;
    private ImageView loadingIv;
    private TextView loadingTextTv;
    private RelativeLayout containerRl;
    private CityPresenter cityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initLayout();
    }

    private void initLayout() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        containerRl = (RelativeLayout) findViewById(R.id.splash_container_rl);
        loadingIv = (ImageView) findViewById(R.id.splash_loading_iv);
        loadingTextTv = (TextView) findViewById(R.id.splash_loading_text_tv);
        ObjectAnimator rotationAnim = (ObjectAnimator) AnimatorInflater.loadAnimator(this,R.animator.loading_rotation);
        ObjectAnimator alphaAnim = (ObjectAnimator) AnimatorInflater.loadAnimator(this,R.animator.loading_alpha);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(rotationAnim).with(alphaAnim);
        animatorSet.setTarget(loadingIv);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                loadingIv.setVisibility(View.INVISIBLE);
                loadingTextTv.setVisibility(View.INVISIBLE);
                Snackbar.make(containerRl,"导入成功",Snackbar.LENGTH_SHORT)
                        .show();
                showTips();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                cityPresenter = new CityPresenterImpl();
                cityPresenter.saveCityList();
                loadingTextTv.setVisibility(View.VISIBLE);
            }
        });
        animatorSet.start();
    }

    private void showTips() {
        new AlertDialog.Builder(this)
                .setTitle("权限说明")
                .setMessage("定位权限用于获取所在地点的城市信息，存储权限用于百度定位写入离线定位数据,通话权限获取用户设备的IMEI，通过IMEI和mac来唯一的标识用户，以便进行数据统计。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestPermission();
                    }
                }).create()
                .show();
    }

    private void requestPermission() {
        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this,permissions,PERMISSION);
        }
    }

    private void startHomeActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PreferencesLoader.putBoolean(PreferencesLoader.IMPORT_DATA,false);
                finish();
                Intent homeIntent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(homeIntent);
            }
        },1000);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION:
                if(grantResults.length>0){
                    for(int result : grantResults){
                        if(result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this, "必须同意所有权限才能使用本应用程序！", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    startHomeActivity();
                }else {
                    Toast.makeText(this,"发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }


}
