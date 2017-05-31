package com.eternal.express.rxjava;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import retrofit2.Response;


/**
 * @author qiuyongheng
 * @time 2017/5/31  10:50
 * @desc 转换过程其实就是一个错误处理的过程
 */

public class ErrorCheckerTransformer<T extends Response<R>, R extends BaseResponse> implements ObservableTransformer<T, R>{

    public static final String DEFAULT_ERROR_MESSAGE = "Oh, no";

    private Context mContext;

    public ErrorCheckerTransformer(final Context context) {
        mContext = context;
    }

    @Override
    public ObservableSource<R> apply(Observable<T> upstream) {
        return upstream
                .map(new Function<T, R>() {
                    @Override
                    public R apply(T t) throws Exception {
                        String msg = null;
                        if (!t.isSuccessful() || t.body() == null) {
                            msg = DEFAULT_ERROR_MESSAGE;
                        } else if (t.body().errorResponse != null) {
                            msg = t.body().errorResponse.msg;
                            if (msg == null) {
                                msg = DEFAULT_ERROR_MESSAGE;
                            }
                        } else if (t.body().code != BaseResponse.CODE_SUCCESS) {
                            msg = t.body().msg;
                            if (msg == null) {
                                msg = DEFAULT_ERROR_MESSAGE;
                            }
                        }

                        if (msg != null) {
                            try {
                                throw new ErrorResponseException(msg);
                            } catch (ErrorResponseException e) {
                                throw Exceptions.propagate(e);
                            }
                        }
                        return t.body();
                    }
                });
    }
}
