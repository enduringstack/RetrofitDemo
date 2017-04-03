package com.enduringstack.retrofitdemo7.service;

import com.enduringstack.retrofitdemo7.model.Subscription;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by sebastianmazur on 19.12.15.
 */
public interface RepositoryWatchingService {
    String SERVICE_ENDPOINT = "https://api.github.com/";

    @GET("repos/{owner}/{repo}/subscription")
    Call<Void> isWatched(@Path("owner") String owner, @Path("repo") String repo, @Header("Authorization") String authorizationHeaderValue);

    @PUT("repos/{owner}/{repo}/subscription")
    Call<Void> watchRepo(@Path("owner") String owner, @Path("repo") String repo, @Header("Authorization") String authorizationHeaderValue, @Body Subscription subscriptionParams);

    @DELETE("repos/{owner}/{repo}/subscription")
    Call<Void> unwatchRepo(@Path("owner") String owner, @Path("repo") String repo, @Header("Authorization") String authorizationHeaderValue);
}