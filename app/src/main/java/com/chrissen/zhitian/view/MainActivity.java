package com.chrissen.zhitian.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chrissen.zhitian.R;
import com.chrissen.zhitian.presenter.LocationPresenter;
import com.chrissen.zhitian.presenter.LocationPresenterImpl;
import com.chrissen.zhitian.util.PreferencesLoader;
import com.chrissen.zhitian.view.fragment.CityManagementFragment;
import com.chrissen.zhitian.view.fragment.SearchCityFragment;
import com.chrissen.zhitian.view.fragment.WeatherFragment;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    
    private boolean isFirst;
    private LocationPresenter presenter;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startSplashActivity();
        setContentView(R.layout.activity_main);
        initLayout();
        presenter = new LocationPresenterImpl();
        presenter.loadLocation(this);
        startWeatherFragment();
    }

    private void initLayout() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_right_container_fl,new CityManagementFragment())
                        .commit();
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_left_container_fl,new SearchCityFragment())
                .commit();
    }


    private void startWeatherFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container_fl,new WeatherFragment())
                .commit();
    }

    private void startSplashActivity() {
        isFirst = PreferencesLoader.getBoolean(PreferencesLoader.IMPORT_DATA,true);
        if (isFirst) {
            Intent splashIntent = new Intent(this,SplashActivity.class);
            startActivity(splashIntent);
            finish();
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
