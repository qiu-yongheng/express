package com.eternal.express.rxjava;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author qiuyongheng
 * @time 2017/5/31  11:17
 * @desc ${TODD}
 */

public class ServiceFactory {
    public static final String OLD_BASE_URL = "https://liangfeizc.com/gw/oauthentry/";
    public static final String NEW_BASE_URL = "https://liangfei.me/api/oauthentry/";

    private static final OkHttpClient sClient = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    HttpUrl url = request
                            .url()
                            .newBuilder()
                            .addQueryParameter("access_token", "")
                            .build();
                    request = request.newBuilder().url(url).build();
                    return chain.proceed(request);
                }
            })
            .build();

    public static <T> T createOldService(Class<T> serviceClazz) {
        return createOauthService(OLD_BASE_URL, serviceClazz);
    }

    public static <T> T createNewService(Class<T> serviceClazz) {
        return createOauthService(NEW_BASE_URL, serviceClazz);
    }

    public static <T> T createOauthService(String baseUrl, Class<T> serviceClazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(sClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(serviceClazz);
    }
}
