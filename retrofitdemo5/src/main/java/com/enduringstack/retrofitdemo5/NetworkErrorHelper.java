package com.enduringstack.retrofitdemo5;

import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;

/**
 * Created by chenfuduo on 17-3-26.
 */

public class NetworkErrorHelper {
    /**
     * 对应HTTP的状态码
     */
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static String getMessage(Throwable e) {
        if (e instanceof ConnectException) {
            return "网络连接异常";
        } else if (e instanceof HttpException) {
            HttpException exception = (HttpException) e;
            return exception.message();
        } else if (e instanceof JSONException) {
            return "数据解析异常";
        }
        return "未知异常";
    }
}
