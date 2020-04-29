package com.example.android_resapi.ui;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android_resapi.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DetailItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private ArrayList<DetailItemData> itemList;

    private OnLoadMoreListener onLoadMoreListener;
    private LinearLayoutManager mLinearLayoutManager;

    private boolean isMoreLoading = false;
    private int visibleThreshold = 1;
    int firstVisibleItem, visibleItemCount, totalItemCount, lastVisibleItem;

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public DetailItemAdapter(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener=onLoadMoreListener;
        itemList =new ArrayList<>();
    }

    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager){
        this.mLinearLayoutManager=linearLayoutManager;
    }

    public void setRecyclerView(RecyclerView mView){
        mView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLinearLayoutManager.getItemCount();
                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();

                if (!isMoreLoading && (totalItemCount - visibleItemCount)<= (firstVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isMoreLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            return new StudentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_sleep_recyclerview, parent, false));
        } else {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false));
        }

    }

    public void addAll(List<DetailItemData> lst){
        itemList.clear();
        itemList.addAll(lst);
        notifyDataSetChanged();
    }

    public void addItemMore(List<DetailItemData> lst){
        itemList.addAll(lst);
        notifyItemRangeChanged(0,itemList.size());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StudentViewHolder) {
            DetailItemData did = (DetailItemData) itemList.get(position);
           ((StudentViewHolder) holder).Date.setText(did.getDate());
           ((StudentViewHolder) holder).WakeUp.setText(did.getWakeUpTime());
           ((StudentViewHolder) holder).goBed.setText(did.getGoBedTime());
        }
    }

    public void setMoreLoading(boolean isMoreLoading) {
        this.isMoreLoading=isMoreLoading;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setProgressMore(final boolean isProgress) {
        if (isProgress) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    itemList.add(null);
                    notifyItemInserted(itemList.size() - 1);
                }
            });
        } else {
            itemList.remove(itemList.size() - 1);
            notifyItemRemoved(itemList.size());
        }
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView Date;
        public TextView WakeUp;
        public TextView goBed;

        public StudentViewHolder(View v) {
            super(v);
            Date = (TextView) v.findViewById(R.id.detail_sleep_date_textview_id);
            WakeUp = (TextView) v.findViewById(R.id.detail_sleep_wakeup_textview_id);
            goBed = (TextView) v.findViewById(R.id.detail_sleep_gobed_textview_id);
        }
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar pBar;
        public ProgressViewHolder(View v) {
            super(v);
            pBar = (ProgressBar) v.findViewById(R.id.pBar);
        }
    }

}
