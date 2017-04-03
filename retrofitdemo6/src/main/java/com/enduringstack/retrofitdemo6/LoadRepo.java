package com.enduringstack.retrofitdemo6;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by chenfuduo on 17-3-31.
 */

public interface LoadRepo {
    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })
    @GET("users/{user}/repos")
    Call<List<Repo>> loadRepo(@Path("user") String user);
}
