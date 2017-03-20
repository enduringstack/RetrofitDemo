package com.enduringstack.retrofitdemo2;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chenfuduo on 17-3-19.
 */

public class RetrofitWrapper
{
    private static RetrofitWrapper instance;

    private Retrofit retrofit;

    private RetrofitWrapper()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl(Contant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitWrapper getInstance()
    {
        if (null == instance)
        {
            synchronized (RetrofitWrapper.class)
            {
                if (null == instance)
                {
                    instance = new RetrofitWrapper();
                }
            }
        }

        return instance;
    }

    public <T> T create(Class<T> service)
    {
        return retrofit.create(service);
    }
}
