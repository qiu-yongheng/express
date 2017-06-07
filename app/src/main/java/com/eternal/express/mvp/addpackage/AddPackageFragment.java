package com.eternal.express.mvp.addpackage;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * @author 邱永恒
 * @time 2017/6/7 20:44
 * @desc ${TODO}
 */

public class AddPackageFragment extends Fragment implements AddPackageContract.View {


    /**
     * 暴露给外界创建对象的方法
     * @return
     */
    public static AddPackageFragment getInstance() {
        return new AddPackageFragment();
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void setPresenter(AddPackageContract.Presenter presenter) {

    }

}
