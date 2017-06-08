package com.eternal.express.mvp.companies;

import com.eternal.express.data.bean.Company;
import com.eternal.express.mvp.BasePresenter;
import com.eternal.express.mvp.BaseView;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/5/3 21:50
 * @desc ${TODO}
 */

public interface CompaniesContract {
    interface View extends BaseView<Presenter> {

        void showGetCompaniesError();

        void showCompanies(List<Company> value);

    }

    interface Presenter extends BasePresenter {

    }
}
