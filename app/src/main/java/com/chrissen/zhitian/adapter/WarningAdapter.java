package com.chrissen.zhitian.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chrissen.zhitian.R;
import com.chrissen.zhitian.util.HttpUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/14.
 */

public class WarningAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_BODY = 0;
    private static final int TYPE_FOOTER = 1;

    private Activity activity;
    private List<String> warningList;

    public WarningAdapter(Activity activity){
        this.activity = activity;
        warningList = new ArrayList<>();
        warningList.add("高温预警");
        warningList.add("暴雨预警");
        warningList.add("沙尘暴预警");
        warningList.add("强对流天气预警");
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_BODY){
            View view = LayoutInflater.from(parent.getContext()).inflate(com.chrissen.zhitian.R.layout.item_warning,parent,false);
            return new ContentViewHolder(view);
        }else {
            View footerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itema_warning_footer,parent,false);
            return new FooterViewHolder(footerView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof ContentViewHolder){
            String warningTitle = warningList.get(position);
            ((ContentViewHolder)holder).titleTv.setText(warningTitle);
            ((ContentViewHolder)holder).titleLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((ContentViewHolder)holder).contentLl.getVisibility() == View.GONE){
                        ((ContentViewHolder)holder).animateOpen(((ContentViewHolder)holder).contentLl);
                        if(position == 0){
                            String tempUrl = "http://www.nmc.cn/publish/country/warning/megatemperature.html";
                            getWarningContent(tempUrl , ((ContentViewHolder)holder));
                        }else if(position == 1){
                            String rainUrl = "http://www.nmc.cn/publish/country/warning/downpour.html";
                            getWarningContent(rainUrl,((ContentViewHolder)holder));
                        }else if(position == 2){
                            String sandUrl = "http://www.nmc.cn/publish/country/warning/dust.html";
                            getWarningContent(sandUrl,((ContentViewHolder)holder));
                        }else if(position == 3){
                            String duiliuUrl = "http://www.nmc.cn/publish/country/warning/strong_convection.html";
                            getWarningContent(duiliuUrl,((ContentViewHolder)holder));
                        }
                    }else if(((ContentViewHolder)holder).contentLl.getVisibility() == View.VISIBLE){
                        ((ContentViewHolder)holder).animateClose(((ContentViewHolder)holder).contentLl);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == warningList.size()){
            return TYPE_FOOTER;
        }else {
            return TYPE_BODY;
        }
    }

    @Override
    public int getItemCount() {
        return warningList.size() + 1;
    }

    class ContentViewHolder extends RecyclerView.ViewHolder{
        TextView titleTv , contentTv;
        LinearLayout contentLl , titleLl;
        private int hiddenLayoutHeight;

        public ContentViewHolder(View itemView) {
            super(itemView);
            titleLl = (LinearLayout) itemView.findViewById(R.id.item_warning_title_ll);
            contentLl = (LinearLayout) itemView.findViewById(R.id.item_warning_content_ll);
            titleTv = (TextView) itemView.findViewById(R.id.item_warning_title);
            contentTv = (TextView) itemView.findViewById(R.id.item_warning_content_tv);
            hiddenLayoutHeight = contentLl.getLayoutParams().height;
        }

        private void animateOpen(View view){
            view.setVisibility(View.VISIBLE);
            ValueAnimator animator = createDropAnimator(view,0,hiddenLayoutHeight);
            animator.start();
        }

        private void animateClose(final View view){
            int origHeight = view.getHeight();
            ValueAnimator animator = createDropAnimator(view,origHeight,0);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(View.GONE);
                }
            });
            animator.start();
        }


        private ValueAnimator createDropAnimator(final View view , int start , int end){
            ValueAnimator animator = ValueAnimator.ofInt(start,end);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (Integer) animation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.height = value;
                    view.setLayoutParams(layoutParams);
                }
            });
            return animator;
        }

    }


    class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView footerTv;
        public FooterViewHolder(View itemView) {
            super(itemView);
            footerTv = (TextView) itemView.findViewById(R.id.item_warning_footer_tv);
        }

    }


    private void getWarningContent(String url , final ContentViewHolder holder){

        HttpUtil.doGetAsyn(url, new HttpUtil.CallBack() {
            @Override
            public void onRequestComplete(String result) {
                if (result == null) {
                    return;
                }
                Document document = Jsoup.parse(result);
                Element textElement = document.getElementById("text");
                Element writingElement = textElement.getElementsByClass("writing").first();
                Elements ps = writingElement.getElementsByTag("p");
                final String title = ps.get(0).text();
                final String content = ps.get(1).text();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        holder.contentTv.setText("");
                        holder.contentTv.setText(title + content);
                    }
                });
            }
        });
    }

}
