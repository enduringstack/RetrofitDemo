package com.enduringstack.retrofitdemo6;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by chenfuduo on 17-3-31.
 */

public interface SearchUsersService {

    @GET("users")
    @Headers("Cache-Control: max-age=640000")
    Call<List<UserInfo>> listUsers(@Query("since") int since);
}
