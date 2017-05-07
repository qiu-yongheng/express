package com.eternal.express.mvp.companies;

import com.eternal.express.mvp.BasePresenter;
import com.eternal.express.mvp.BaseView;

/**
 * @author 邱永恒
 * @time 2017/5/3 21:50
 * @desc ${TODO}
 */

public interface CompaniesContract {
    interface View extends BaseView<Presenter> {

        void showGetCompaniesError();

        void showCompanies();

    }

    interface Presenter extends BasePresenter {

    }
}
