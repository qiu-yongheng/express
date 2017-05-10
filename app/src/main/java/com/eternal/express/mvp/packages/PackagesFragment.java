package com.eternal.express.mvp.packages;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eternal.express.R;
import com.eternal.express.data.bean.Package;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 邱永恒
 * @time 2017/5/3 21:50
 * @desc ${TODO}
 */

public class PackagesFragment extends Fragment implements PackagesContract.View {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.emptyView)
    LinearLayout mEmptyView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView mBottomNavigationView;
    private PackagesContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /* 1. 获取要显示的view */
        View view = inflater.inflate(R.layout.fragment_packages, container, false);
        ButterKnife.bind(this, view);

        /* 2. 初始化view */
        initViews(view);

        /* 3. 初始化监听 */
        initListener();

        /* 4. 加载数据 */

        /* 5. 设置加载menu */
        setHasOptionsMenu(true);


        return view;
    }


    private void initListener() {
        // 1. 导航栏点击切换fragment
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_all:
                        presenter.setFiltering(PackageFilterType.ALL_PACKAGES);
                        break;
                    case R.id.nav_on_the_way:
                        presenter.setFiltering(PackageFilterType.ON_THE_WAY_PACKAGES);
                        break;
                    case R.id.nav_delivered:
                        presenter.setFiltering(PackageFilterType.DELIVERED_PACKAGES);
                        break;
                }

                // 每次切换后, 更新数据
                presenter.loadPackages();
                return true;
            }
        });

        // 2. fab点击添加订单
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // 3. 下拉刷新
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshPackages();
            }
        });
    }

    @Override
    public void initViews(View view) {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public static PackagesFragment newInstance() {
        return new PackagesFragment();
    }

    @Override
    public void setPresenter(PackagesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showEmptyView(boolean toShow) {

    }

    @Override
    public void showPackages(@NonNull List<Package> list) {

    }

    @Override
    public void shareTo(@NonNull Package pack) {

    }


    @Override
    public void showPackageRemovedMsg(String packageName) {

    }

    @Override
    public void copyPackageNumber() {

    }

    @Override
    public void showNetworkError() {

    }

}
