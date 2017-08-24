package com.chrissen.zhitian.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chrissen.zhitian.R;
import com.chrissen.zhitian.model.bean.entity.Index;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.IndexViewHolder> {

    private List<Index> indexList;

    public IndexAdapter(List<Index> indexList){
        this.indexList = indexList;
    }

    @Override
    public IndexViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_weather,parent,false);
        return new IndexViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IndexViewHolder holder, int position) {
        Index index = indexList.get(position);
        if(index != null){
            switch (index.getName()){
                case "空调指数":
                    holder.indexImageIv.setImageResource(R.drawable.index_air_conditioning);
                    break;
                case "运动指数":
                    holder.indexImageIv.setImageResource(R.drawable.index_sport);
                    break;
                case "紫外线指数":
                    holder.indexImageIv.setImageResource(R.drawable.index_uv);
                    break;
                case "感冒指数":
                    holder.indexImageIv.setImageResource(R.drawable.index_virus);
                    break;
                case "洗车指数":
                    holder.indexImageIv.setImageResource(R.drawable.index_washing);
                    break;
                case "空气污染扩散指数":
                    holder.indexImageIv.setImageResource(R.drawable.index_air_pollution);
                    break;
                case "穿衣指数":
                    holder.indexImageIv.setImageResource(R.drawable.index_clothes);
                    break;
            }
            if(index.getValue() != null){
                holder.valueTv.setText(index.getValue());
            }else {
                holder.valueTv.setText("无");
            }
            if(index.getDetail() != null){
                holder.detailTv.setText(index.getDetail());
            }else {
                holder.detailTv.setText("没有信息");
            }
        }

    }

    @Override
    public int getItemCount() {
        return indexList.size();
    }

    class IndexViewHolder extends ViewHolder{
        private TextView valueTv , detailTv;
        private ImageView indexImageIv;
        public IndexViewHolder(View itemView) {
            super(itemView);
            indexImageIv = (ImageView) itemView.findViewById(R.id.item_index_image_iv);
            valueTv = (TextView) itemView.findViewById(R.id.item_index_value_tv);
            detailTv = (TextView) itemView.findViewById(R.id.item_index_detail_tv);
        }
    }

}
