package com.enduringstack.retrofitdemo8;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by chenfuduo on 17-3-31.
 */

public interface LoginService {

    String SERVICE_ENDPOINT = "https://api.github.com/";

    @POST("authorizations")
    Call<Authorization> authStandard(@Header("Authorization") String authorizationHeaderValue, @Body AuthScope scope);
}
