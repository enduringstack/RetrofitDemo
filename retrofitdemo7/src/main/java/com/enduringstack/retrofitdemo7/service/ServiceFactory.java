package com.enduringstack.retrofitdemo7.service;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by sebastianmazur on 18.12.15.
 */
public class ServiceFactory {

    public static <T> T createRetrofitService(final Class<T> clazz, final String endPoint) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        T service = retrofit.create(clazz);

        return service;
    }
}
