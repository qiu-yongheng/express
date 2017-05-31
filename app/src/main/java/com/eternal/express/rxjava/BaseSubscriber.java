package com.eternal.express.rxjava;

import android.content.Context;

import org.reactivestreams.Subscriber;

/**
 * @author qiuyongheng
 * @time 2017/5/31  11:48
 * @desc ${TODD}
 */

public abstract class BaseSubscriber<T> implements Subscriber<T> {
    private Context mContext;

    public BaseSubscriber(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }
}
