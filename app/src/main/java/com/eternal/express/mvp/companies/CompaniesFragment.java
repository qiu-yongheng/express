package com.eternal.express.mvp.companies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eternal.express.R;

/**
 * @author 邱永恒
 * @time 2017/5/3 21:52
 * @desc ${TODO}
 */

public class CompaniesFragment extends Fragment implements CompaniesContract.View{
    public CompaniesFragment() {}

    /**
     * 外界可以清楚知道创建对象需要什么参数
     * @return
     */
    public static CompaniesFragment newInstance() {
        return new CompaniesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflater.inflate(R.layout.fragment_companies_list, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void setPresenter(CompaniesContract.Presenter presenter) {

    }

    @Override
    public void showGetCompaniesError() {

    }

    @Override
    public void showCompanies() {

    }


}
