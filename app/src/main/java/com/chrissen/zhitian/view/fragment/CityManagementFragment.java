package com.chrissen.zhitian.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chrissen.zhitian.R;
import com.chrissen.zhitian.adapter.CityManagementAdapter;
import com.chrissen.zhitian.model.bean.SavedCity;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Administrator on 2017/8/22 0022.
 */

public class CityManagementFragment extends Fragment {

    private RecyclerView cityRv;
    private RelativeLayout cityManagementRl;
    private CityManagementAdapter adapter;
    private List<SavedCity> savedCityList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedCityList = DataSupport.findAll(SavedCity.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_management,container,false);
        cityManagementRl = (RelativeLayout) view.findViewById(R.id.city_management_rl);
        cityRv = (RecyclerView) view.findViewById(R.id.city_management_rv);
        adapter = new CityManagementAdapter(savedCityList);
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
                if(position == 0){
                    Snackbar.make(cityManagementRl,"默认城市无法删除",Snackbar.LENGTH_LONG)
                            .show();
                }else {
                    showPopupMenu(view,position);
                }
            }
        });
        return view;
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
