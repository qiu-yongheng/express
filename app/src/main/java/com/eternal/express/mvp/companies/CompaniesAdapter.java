package com.eternal.express.mvp.companies;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eternal.express.R;
import com.eternal.express.component.FastScrollRecyclerView;
import com.eternal.express.data.bean.Company;
import com.eternal.express.interfaze.OnRecyclerViewItemClickListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author 邱永恒
 * @time 2017/6/8 21:02
 * @desc ${TODO}
 */

public class CompaniesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements FastScrollRecyclerView.SectionedAdapter{
    private final Context context;
    private final List<Company> list;
    private final LayoutInflater inflater;
    private OnRecyclerViewItemClickListener listener;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_WITH_HEADER = 1;

    public CompaniesAdapter(@NonNull Context context, @NonNull List<Company> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            return new NormalViewHolder(inflater.inflate(R.layout.item_company, parent, false), listener);
        }
        return new WithHeaderViewHolder(inflater.inflate(R.layout.item_company_with_header, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Company company = list.get(position);
        if (holder instanceof NormalViewHolder) {
            NormalViewHolder cvh = (NormalViewHolder) holder;
            cvh.textViewAvatar.setText(company.getName().substring(0, 1).toUpperCase());
            cvh.textViewCompanyTel.setText(company.getTel());
            cvh.textViewCompanyName.setText(company.getName());
            cvh.avatar.setColorFilter(Color.parseColor(company.getAvatarColor()));
        } else if (holder instanceof WithHeaderViewHolder) {
            WithHeaderViewHolder wh = (WithHeaderViewHolder) holder;
            wh.textViewAvatar.setText(company.getName().substring(0, 1).toUpperCase());
            wh.textViewCompanyTel.setText(company.getTel());
            wh.textViewCompanyName.setText(company.getName());
            wh.stickyHeaderText.setText(getSectionName(position));
            wh.avatar.setColorFilter(Color.parseColor(company.getAvatarColor()));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 根据类型设置头item
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0 || list.get(position).getAlphabet().charAt(0) != list.get(position - 1).getAlphabet().charAt(0)) {
            return TYPE_WITH_HEADER;
        }
        return TYPE_NORMAL;
    }

    /**
     * 获取头item的文字描述
     * @param position
     * @return
     */
    @NonNull
    @Override
    public String getSectionName(int position) {
        if (list.isEmpty()) {
            return "";
        }
        char c = list.get(position).getAlphabet().charAt(0);
        // 判断是否是数字
        if (Character.isDigit(c)) {
            return "#";
        } else {
            // 大写
            return Character.toString(Character.toUpperCase(c));
        }
    }

    /**
     * 普通控件
     */
    public class NormalViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        CircleImageView avatar;
        AppCompatTextView textViewCompanyName;
        AppCompatTextView textViewAvatar;
        AppCompatTextView textViewCompanyTel;

        private OnRecyclerViewItemClickListener listener;

        public NormalViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView);

            avatar = (CircleImageView) itemView.findViewById(R.id.imageViewAvatar); // 图标
            textViewAvatar = (AppCompatTextView) itemView.findViewById(R.id.textViewAvatar); // 图标描述
            textViewCompanyName = (AppCompatTextView) itemView.findViewById(R.id.textViewCompanyName); // 快递公司名
            textViewCompanyTel = (AppCompatTextView) itemView.findViewById(R.id.textViewCompanyTel); // 快递电话

            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.OnItemClick(view, getLayoutPosition());
            }
        }
    }

    /**
     * 头控件
     */
    public class WithHeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView avatar;
        AppCompatTextView textViewCompanyName;
        AppCompatTextView textViewAvatar;
        AppCompatTextView textViewCompanyTel;
        AppCompatTextView stickyHeaderText;

        private OnRecyclerViewItemClickListener listener;

        public WithHeaderViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView);
            avatar = (CircleImageView) itemView.findViewById(R.id.imageViewAvatar);
            textViewAvatar = (AppCompatTextView) itemView.findViewById(R.id.textViewAvatar);
            textViewCompanyName = (AppCompatTextView) itemView.findViewById(R.id.textViewCompanyName);
            textViewCompanyTel = (AppCompatTextView) itemView.findViewById(R.id.textViewCompanyTel);
            stickyHeaderText = (AppCompatTextView) itemView.findViewById(R.id.headerText); // header描述

            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.OnItemClick(v, getLayoutPosition());
            }
        }
    }
}
