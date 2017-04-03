package com.enduringstack.retrofitdemo7.service;

import com.enduringstack.retrofitdemo7.model.SearchReposResponse;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by sebastianmazur on 19.12.15.
 */
public interface SearchReposService {
    String SERVICE_ENDPOINT = "https://api.github.com/";

    @GET("search/repositories")
    Call<SearchReposResponse> getQuery(@Query("q") String query, @Header("Authorization") String authorizationHeaderValue);
}

