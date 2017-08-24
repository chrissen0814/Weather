package com.chrissen.zhitian.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chrissen.zhitian.R;
import com.chrissen.zhitian.model.bean.City;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2017/8/22 0022.
 */

public class SearchCityAdapter extends RecyclerView.Adapter<SearchCityAdapter.SearchViewHolder> {

    private OnItemClickListener listener;

    private List<City> cityList;

    public SearchCityAdapter(List<City> cityList){
        this.cityList = cityList;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_city,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SearchViewHolder holder, int position) {
        final City city = cityList.get(position);
        List<City> parentCityList = DataSupport.where("cityid = ?" , city.getParentId()).limit(1).find(City.class);
        if(!parentCityList.isEmpty()){
            String cityInfo = parentCityList.get(0).getCityName() + " - " + city.getCityName();
            holder.cityInfoTv.setText(cityInfo);
        }else {
            holder.cityInfoTv.setText(city.getCityName());
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v,holder.getAdapterPosition());
            }
        });
    }



    @Override
    public int getItemCount() {
        return cityList.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView cityInfoTv;
        public SearchViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            cityInfoTv = (TextView) itemView.findViewById(R.id.search_city_tv);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view , int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
