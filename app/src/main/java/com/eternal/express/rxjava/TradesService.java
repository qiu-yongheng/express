package com.eternal.express.rxjava;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

/**
 * @author qiuyongheng
 * @time 2017/5/31  10:25
 * @desc retrofit请求接口
 */

public interface TradesService {
    @GET("api.tradecategories/1.0.0/get")
    Observable<Response<CategoryResponse>> tradeCategories();

    @FormUrlEncoded
    @GET("api.trade/1.0.0/get")
    Observable<Response<TradeItemResponse>> tradeDetail(@Field("tid") String tid);
}
