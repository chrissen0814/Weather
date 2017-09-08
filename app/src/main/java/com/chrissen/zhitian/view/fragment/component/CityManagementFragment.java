package com.chrissen.zhitian.view.fragment.component;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.chrissen.zhitian.R;
import com.chrissen.zhitian.adapter.CityManagementAdapter;
import com.chrissen.zhitian.adapter.SearchCityAdapter;
import com.chrissen.zhitian.model.bean.City;
import com.chrissen.zhitian.model.bean.SavedCity;
import com.chrissen.zhitian.view.fragment.component.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Administrator on 2017/8/22 0022.
 */

public class CityManagementFragment extends BaseFragment {

    private RecyclerView cityRv;
    private RelativeLayout cityManagementRl;
    private CityManagementAdapter adapter;
    private List<SavedCity> savedCityList;
    private Toolbar searchCityToolbar;
    private SearchView searchCityView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_city_management;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        cityManagementRl = (RelativeLayout) view.findViewById(R.id.city_management_rl);
        searchCityToolbar = (Toolbar) view.findViewById(R.id.search_city_toolbar);
        searchCityView = (SearchView) view.findViewById(R.id.search_city_sv);
        final PopupWindow popupWindow = new PopupWindow(getActivity());
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        searchCityView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    String queriedCity = query + "%";
                    final List<City> foundCityList = DataSupport.where("cityname like ?",queriedCity).find(City.class);
                    if(foundCityList.size() == 0){
                        Snackbar.make(cityManagementRl,"无该城市信息",Snackbar.LENGTH_LONG)
                                .show();
                    }else {
                        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.popup_window_search_city,null);
                        popupWindow.setContentView(layout);
                        popupWindow.showAsDropDown(searchCityToolbar);
                        RecyclerView searchCityRv = (RecyclerView) layout.findViewById(R.id.search_city_rv);
                        SearchCityAdapter searchCityAdapter = new SearchCityAdapter(foundCityList);
                        searchCityRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                        searchCityRv.setAdapter(searchCityAdapter);
                        searchCityAdapter.setOnItemClickListener(new SearchCityAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                City city = foundCityList.get(position);
                                if(!city.getCityCode().isEmpty()){
                                    saveCity(city);
                                }else {
                                    Snackbar.make(cityManagementRl,"该城市没有天气代码，不能添加",Snackbar.LENGTH_LONG)
                                            .show();
                                }
                                foundCityList.clear();
                                adapter.notifyDataSetChanged();
                                popupWindow.dismiss();
                            }
                        });
                    }
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                popupWindow.dismiss();
                return true;
            }
        });
        searchCityView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                popupWindow.dismiss();
                return true;
            }
        });
        cityRv = (RecyclerView) view.findViewById(R.id.city_management_rv);
        adapter = new CityManagementAdapter(this,savedCityList);
        cityRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        cityRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new CityManagementAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ((DrawerLayout)getActivity().findViewById(R.id.main_drawer_layout))
                        .closeDrawer(Gravity.RIGHT);
                SavedCity savedCity = savedCityList.get(position);
                EventBus.getDefault().post(savedCity);
            }
        });
        adapter.setOnItemLongClickListener(new CityManagementAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                showPopupMenu(view,position);
            }
        });
    }

    private void saveCity(City city) {
        if (city != null) {
            SavedCity savedCity = new SavedCity(city.getCityId(),city.getParentId(),city.getCityCode(),city.getCityName());
            if(!compareTwoCities(savedCity)){
                savedCity.save();
                Snackbar.make(cityManagementRl,"添加成功",Snackbar.LENGTH_LONG)
                        .show();
            }else {
                Snackbar.make(cityManagementRl,"该城市已经存在",Snackbar.LENGTH_LONG)
                        .show();
            }
        }
    }

    private boolean compareTwoCities(SavedCity city){
        List<SavedCity> savedCityList = DataSupport.findAll(SavedCity.class);
        for(SavedCity savedCity : savedCityList){
            if(savedCity.getCityId().equals(city.getCityId())){
                return true;
            }
        }
        return false;
    }


    @Override
    protected void initData() {
        savedCityList = DataSupport.findAll(SavedCity.class);
    }

    private void showPopupMenu(View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(getActivity(),view);
        popupMenu.getMenuInflater().inflate(R.menu.city_management_popup_menu,popupMenu.getMenu());
        try {
            Field field = popupMenu.getClass().getDeclaredField("mPopup");
            field.setAccessible(true);
            MenuPopupHelper helper = (MenuPopupHelper) field.get(popupMenu);
            helper.setForceShowIcon(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.city_management_popup_menu_delete:
                        savedCityList.get(position).delete();
                        savedCityList.remove(position);
                        adapter.notifyItemRemoved(position);
                        Snackbar.make(cityManagementRl,"已删除",Snackbar.LENGTH_SHORT)
                                .show();
                        break;
                }
                return true;
            }
        });
    }


}
