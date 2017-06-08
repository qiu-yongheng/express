package com.eternal.express.mvp.companies;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.eternal.express.R;
import com.eternal.express.component.FastScrollRecyclerView;
import com.eternal.express.data.bean.Company;
import com.eternal.express.mvp.search.SearchActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2017/5/3 21:52
 * @desc ${TODO}
 */

public class CompaniesFragment extends Fragment implements CompaniesContract.View {
    @BindView(R.id.recyclerViewCompaniesList)
    FastScrollRecyclerView recyclerViewCompaniesList;
    Unbinder unbinder;
    private CompaniesContract.Presenter presenter;

    public CompaniesFragment() {

    }

    /**
     * 外界可以清楚知道创建对象需要什么参数
     *
     * @return
     */
    public static CompaniesFragment newInstance() {
        return new CompaniesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_companies_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        setHasOptionsMenu(true);
        return view;
    }

    /**
     * 创建menu
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.companies_list, menu);
    }

    /**
     * 设置menu的点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            startActivity(new Intent(getContext(), SearchActivity.class),
                    ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
        }
        return true;
    }

    @Override
    public void initViews(View view) {
        recyclerViewCompaniesList.setHasFixedSize(true);
        recyclerViewCompaniesList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void setPresenter(CompaniesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showGetCompaniesError() {
        Snackbar.make(recyclerViewCompaniesList, "获取失败", Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 创建adapter
     * 设置数据
     * recyclerview绑定数据
     * @param value
     */
    @Override
    public void showCompanies(List<Company> value) {

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
