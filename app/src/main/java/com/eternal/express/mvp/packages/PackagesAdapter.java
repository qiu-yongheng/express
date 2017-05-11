package com.eternal.express.mvp.packages;

import android.content.Context;
import android.graphics.Typeface;
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
        // 获取数据
        Package item = list.get(position);
        PackageViewHolder pvh = (PackageViewHolder) holder;

        if (item.getData() != null && item.getData().size() > 0) {
            // 1. 获取快递当前状态
            int state = Integer.parseInt(item.getState());
            pvh.textViewStatus.setText(String.valueOf(packageStatus[state]) + " - " + item.getData().get(0).getContext());
            pvh.textViewTime.setText(item.getData().get(0).getTime());
        } else {
            pvh.textViewTime.setText("");
            pvh.textViewStatus.setText(R.string.get_status_error);
        }

        // 2. 判断是否已读, 未读, 设置粗体, 已读, 设置正常
        if (item.isReadable()) {
            pvh.textViewPackageName.setTypeface(null, Typeface.BOLD);
            pvh.textViewTime.setTypeface(null, Typeface.BOLD);
            pvh.textViewStatus.setTypeface(null, Typeface.BOLD);
        } else {
            pvh.textViewPackageName.setTypeface(null, Typeface.NORMAL);
            pvh.textViewTime.setTypeface(null, Typeface.NORMAL);
            pvh.textViewStatus.setTypeface(null, Typeface.NORMAL);
        }
        // 3. 设置标题
        pvh.textViewPackageName.setText(item.getName());
        // 4. 设置圆形图标里的文字
        pvh.textViewAvatar.setText(item.getName().substring(0,1));
        // 5. 设置图标填充色
        pvh.circleImageViewAvatar.setImageResource(item.getColorAvatar());
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

        /**
         *
         * @param view
         */
        @Override
        public void onClick(View view) {
            if (this.listener != null) {
                listener.OnItemClick(view, getLayoutPosition());
            }
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
                // 根据信息是否读过, 添加不同的menu item
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
        return list.size();
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
