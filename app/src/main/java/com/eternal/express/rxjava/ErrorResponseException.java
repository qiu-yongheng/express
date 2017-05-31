package com.eternal.express.rxjava;

import java.io.IOException;

/**
 * @author qiuyongheng
 * @time 2017/5/31  10:19
 * @desc 统一请求错误和返回错误
 */

public class ErrorResponseException extends IOException{
    public ErrorResponseException() {
        super();
    }

    public ErrorResponseException(String detailMessage) {
        super(detailMessage);
    }
}
