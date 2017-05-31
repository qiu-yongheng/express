package com.eternal.express.rxjava;

import com.google.gson.annotations.SerializedName;

/**
 * @author qiuyongheng
 * @time 2017/5/31  10:18
 * @desc 统一错误信息的格式
 */

public class BaseResponse {
    public static final int CODE_SUCCESS = 0;

    public String msg;
    public int code;
    @SerializedName("error_response")
    public ErrorResponse errorResponse;

    public static final class ErrorResponse {
        public String msg;
        public int code;
    }
}
