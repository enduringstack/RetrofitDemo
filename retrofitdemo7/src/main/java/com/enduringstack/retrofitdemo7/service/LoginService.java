package com.enduringstack.retrofitdemo7.service;

import com.enduringstack.retrofitdemo7.model.AuthScope;
import com.enduringstack.retrofitdemo7.model.Authorization;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by sebastianmazur on 18.12.15.
 */
public interface LoginService {
    String SERVICE_ENDPOINT = "https://api.github.com/";

    @POST("authorizations")
    Call<Authorization> authStandard(@Header("Authorization") String authorizationHeaderValue, @Body AuthScope scope);

    @POST("authorizations")
    Call<Authorization> authTwoFactor(@Header("Authorization") String authorizationHeaderValue, @Header("X-GitHub-OTP") String twoFactorHeaderValue, @Body AuthScope scope);
}
