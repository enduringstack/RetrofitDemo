package com.enduringstack.retrofitdemo5;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by chenfuduo on 17-3-26.
 */

public interface UserApi {
    @FormUrlEncoded
    @POST("login")
    Call<JSONObject> login(@Field("username") String username, @Field("pwd") String pwd,
                           @Field("remember_me") boolean rember);
    @GET("buyer/total")
    Call<JSONObject> getUser();

    /**
    @FormUrlEncoded
    @POST("login")
    Observable<JSONObject> login(@Field("username") String username, @Field("pwd") String pwd, @Field("remember_me") boolean rember);

    @GET("buyer/total")
    Observable<JSONObject> getUser();*/
}
