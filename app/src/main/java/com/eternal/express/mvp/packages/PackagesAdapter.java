package com.eternal.express.mvp.packages;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.eternal.express.data.bean.Package;
import com.eternal.express.interfaze.OnRecyclerViewItemClickListener;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/5/10  10:13
 * @desc
 */

public class PackagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    private final List<Package> list;
    private final LayoutInflater inflater;
    private OnRecyclerViewItemClickListener listener;

    public PackagesAdapter(@NonNull Context context, @NonNull List<Package> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * 获取监听器
     * @param listener
     */
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 更新数据, 保持数据最新
     * @param list The data.
     */
    public void updateData(@NonNull List<Package> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}
