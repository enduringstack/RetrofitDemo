package com.enduringstack.retrofitdemo;

import com.enduringstack.retrofitdemo.bean.ThemeInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by chenfuduo on 17-3-19.
 */

public interface ThemeService {
    @GET("{category}")
    Call<ThemeInfo> getThemeInfo(@Path("category") String category);
}
