package com.eternal.express.rxjava;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import retrofit2.Response;

/**
 * @author qiuyongheng
 * @time 2017/5/31  11:36
 * @desc ${TODD}
 */

public class DefaultTransformer<T extends Response<R>, R extends BaseResponse> implements ObservableTransformer<T, R>{
    private Context mContext;

    public DefaultTransformer(final Context context) {
        mContext = context;
    }

    @Override
    public ObservableSource<R> apply(Observable<T> upstream) {
        return upstream
                .compose(new SchedulerTransformer<T>())
                .compose(new ErrorCheckerTransformer<T, R>(mContext));
    }
}
