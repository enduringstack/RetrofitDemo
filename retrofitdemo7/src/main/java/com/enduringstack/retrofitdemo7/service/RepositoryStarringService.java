package com.enduringstack.retrofitdemo7.service;

import retrofit.Call;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by sebastianmazur on 19.12.15.
 */
public interface RepositoryStarringService {
    String SERVICE_ENDPOINT = "https://api.github.com/";

    @GET("user/starred/{owner}/{repo}")
    Call<Void> isStarred(@Path("owner") String owner, @Path("repo") String repo, @Header("Authorization") String authorizationHeaderValue);

    @Headers("Content-Length: 0")
    @PUT("user/starred/{owner}/{repo}")
    Call<Void> starRepo(@Path("owner") String owner, @Path("repo") String repo, @Header("Authorization") String authorizationHeaderValue);

    @DELETE("user/starred/{owner}/{repo}")
    Call<Void> unstarRepo(@Path("owner") String owner, @Path("repo") String repo, @Header("Authorization") String authorizationHeaderValue);
}