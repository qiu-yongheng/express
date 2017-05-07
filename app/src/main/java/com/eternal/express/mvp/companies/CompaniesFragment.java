package com.eternal.express.mvp.companies;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * @author 邱永恒
 * @time 2017/5/3 21:52
 * @desc ${TODO}
 */

public class CompaniesFragment extends Fragment implements CompaniesContract.View{
    public static CompaniesFragment newInstance() {
        return new CompaniesFragment();
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
