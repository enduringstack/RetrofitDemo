package com.enduringstack.retrofitdemo5;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chenfuduo on 17-3-26.
 */

public abstract class ResponseCallBack<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

    }

    /**
     * 初始化的时候调用
     * 一般用于处理加载进度
     */
    public void onRequest() {

    }
}