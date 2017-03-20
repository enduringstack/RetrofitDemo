package com.enduringstack.retrofitdemo2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chenfuduo on 17-3-20.
 */

public interface SearchBookService {
    @GET("v2/book/search")
    Call<BookInfo> getBookByName(@Query("name")String name, @Query("tag") String tag);
}
