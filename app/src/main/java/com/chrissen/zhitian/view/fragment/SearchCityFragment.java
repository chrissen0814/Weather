package com.chrissen.zhitian.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chrissen.zhitian.R;
import com.chrissen.zhitian.adapter.SearchCityAdapter;
import com.chrissen.zhitian.model.bean.City;
import com.chrissen.zhitian.model.bean.SavedCity;
import com.chrissen.zhitian.util.PreferencesLoader;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/22 0022.
 */

public class SearchCityFragment extends Fragment {

    private int color;
    private Toolbar searchToolbar;
    private SearchCityAdapter adapter;
    private List<City> cityList;
    private SearchView searchView;
    private RecyclerView searchCityRv;
    private RelativeLayout searchCityRl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cityList = new ArrayList<>();
        color = PreferencesLoader.getInt(PreferencesLoader.WEATHER_COLOR,R.color.colorAccent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_city,container,false);
        initLayout(view);
        return view;
    }

    private void initLayout(View view) {
        searchToolbar = (Toolbar) view.findViewById(R.id.search_city_toolbar);
        searchToolbar.setBackgroundColor(color);
        searchCityRl = (RelativeLayout) view.findViewById(R.id.search_city_rl);
        searchView = (SearchView) view.findViewById(R.id.search_city_sv);
        adapter = new SearchCityAdapter(cityList);
        searchCityRv = (RecyclerView) view.findViewById(R.id.search_city_rv);
        searchCityRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchCityRv.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    String queriedCity = query + "%";
                    final List<City> foundCityList = DataSupport.where("cityname like ?",queriedCity).find(City.class);
                    if(foundCityList.size() == 0){
                        Snackbar.make(searchCityRl,"无该城市信息",Snackbar.LENGTH_LONG)
                                .show();
                    }else {
                        cityList.clear();
                        cityList.addAll(foundCityList);
                        adapter.notifyDataSetChanged();
                        adapter.setOnItemClickListener(new SearchCityAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                City city = foundCityList.get(position);
                                if(!city.getCityCode().isEmpty()){
                                    saveCity(city);
                                    Snackbar.make(searchCityRl,"添加成功",Snackbar.LENGTH_LONG)
                                            .show();
                                }else {
                                    Snackbar.make(searchCityRl,"该城市没有天气代码，不能添加",Snackbar.LENGTH_LONG)
                                            .show();
                                }
                                foundCityList.clear();
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                cityList.clear();
                adapter.notifyDataSetChanged();
                return true;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                cityList.clear();
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void saveCity(City city) {
        if (city != null) {
            SavedCity savedCity = new SavedCity(city.getCityId(),city.getParentId(),city.getCityCode(),city.getCityName());
            if(!compareTwoCities(savedCity)){
                savedCity.save();

                Toast.makeText(getActivity(), "已保存", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), "该城市已经存在", Toast.LENGTH_SHORT).show();
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

}
