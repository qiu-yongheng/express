package com.eternal.express.mvp.packages;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.eternal.express.MainActivity;
import com.eternal.express.R;
import com.eternal.express.data.bean.Package;
import com.eternal.express.interfaze.OnRecyclerViewItemClickListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author qiuyongheng
 * @time 2017/5/10  10:13
 * @desc
 */

public class PackagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    private final List<Package> list;
    private final LayoutInflater inflater;
    private final String[] packageStatus;
    private OnRecyclerViewItemClickListener listener;

    public PackagesAdapter(@NonNull Context context, @NonNull List<Package> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        // 快递的状态
        packageStatus = context.getResources().getStringArray(R.array.package_status);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PackageViewHolder(inflater.inflate(R.layout.item_package, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    /**
     * viewHolder
     */
    public class PackageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        private final OnRecyclerViewItemClickListener listener;
        AppCompatTextView textViewPackageName;
        AppCompatTextView textViewTime;
        AppCompatTextView textViewStatus;
        AppCompatTextView textViewAvatar;
        AppCompatTextView textViewRemove;
        ImageView imageViewRemove;
        CircleImageView circleImageViewAvatar;
        LinearLayout layoutMain;
        View wrapperView;
        public PackageViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView);
            // 初始化view
            textViewPackageName = (AppCompatTextView) itemView.findViewById(R.id.textViewPackageName);
            textViewStatus = (AppCompatTextView) itemView.findViewById(R.id.textViewStatus);
            textViewTime = (AppCompatTextView) itemView.findViewById(R.id.textViewTime);
            textViewAvatar = (AppCompatTextView) itemView.findViewById(R.id.textViewAvatar);
            textViewRemove = (AppCompatTextView) itemView.findViewById(R.id.textViewRemove);
            imageViewRemove = (ImageView) itemView.findViewById(R.id.imageViewRemove);
            circleImageViewAvatar = (CircleImageView) itemView.findViewById(R.id.circleImageView);
            layoutMain = (LinearLayout) itemView.findViewById(R.id.layoutPackageItemMain);
            wrapperView = itemView.findViewById(R.id.layoutPackageItem);

            this.listener = listener;
            // 设置点击事件
            itemView.setOnClickListener(this);
            // 给item设置点击后打开menu菜单的点击事件
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {

        }

        /**
         * 点击后创建menu菜单
         * @param contextMenu
         * @param view
         * @param contextMenuInfo
         */
        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            if (contextMenu != null) {
                // 向外传递当前点击的item的运单号
                ((MainActivity)context).setSelectedPackageId(list.get(getLayoutPosition()).getNumber());
                // 获取当前position数据
                Package pack = list.get(getLayoutPosition());
                // 设置menu头标题
                contextMenu.setHeaderTitle(pack.getName());
                // 根据信息是否读过, 设置不同的menu item
                if (pack.isReadable()) {
                    contextMenu.add(Menu.NONE, R.id.action_set_readable, 0, R.string.set_read);
                } else {
                    contextMenu.add(Menu.NONE, R.id.action_set_readable, 0, R.string.set_unread);
                }
                contextMenu.add(Menu.NONE, R.id.action_copy_code, 0, R.string.copy_code);
                contextMenu.add(Menu.NONE, R.id.action_share, 0, R.string.share);
            }
        }
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
