package com.enduringstack.retrofitdemo7.service;

import com.enduringstack.retrofitdemo7.model.Repository;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Created by sebastianmazur on 18.12.15.
 */
public interface RepositoryService {
    String SERVICE_ENDPOINT = "https://api.github.com/";

    @GET("user/repos")
    Call<Repository[]> getRepos(@Header("Authorization") String authorizationHeaderValue);
}