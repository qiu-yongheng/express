package com.eternal.express.mvp;

import android.view.View;

/**
 * @author 邱永恒
 * @time 2017/5/3 21:43
 * @desc ${TODO}
 */

public interface BaseView<T> {
    void initViews(View view);

    void setPresenter(T presenter);
}
