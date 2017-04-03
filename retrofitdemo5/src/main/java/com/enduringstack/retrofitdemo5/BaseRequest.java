package com.enduringstack.retrofitdemo5;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chenfuduo on 17-3-26.
 */

public class BaseRequest<T> {

    private Context mContext;
    private Call<T> mCall;

    public BaseRequest(Context context, Call<T> call) {
        mContext = context;
        mCall = call;
    }

    public void handleRequest(final ResponseCallBack<T> callBack) {
        callBack.onRequest();
        mCall.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                callBack.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                callBack.onFailure(call, t);
            }
        });
    }
}
