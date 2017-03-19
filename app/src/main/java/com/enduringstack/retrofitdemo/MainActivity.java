package com.enduringstack.retrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.enduringstack.retrofitdemo.bean.ThemeInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    Button btnNormal;
    Button btnWrapper;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNormal = (Button) findViewById(R.id.btnNormal);
        btnWrapper = (Button) findViewById(R.id.btnWrapper);

        tv = (TextView) findViewById(R.id.tv);

        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataNormal();
            }
        });

        btnWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataWrapper();
            }
        });
    }

    private void initDataWrapper() {
        tv.setText("");

        ThemeService service = RetrofitWrapper.getInstance().create(ThemeService.class);
        Call<ThemeInfo> call = service.getThemeInfo("themes");

        call.enqueue(new Callback<ThemeInfo>() {
            @Override
            public void onResponse(Call<ThemeInfo> call, Response<ThemeInfo> response) {
                Log.e(TAG, "onResponse: " + response.body().toString());
                tv.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<ThemeInfo> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void initDataNormal() {
        tv.setText("");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://news-at.zhihu.com/api/4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ThemeService themeService = retrofit.create(ThemeService.class);

        Call<ThemeInfo> call = themeService.getThemeInfo("themes");

        call.enqueue(new Callback<ThemeInfo>() {
            @Override
            public void onResponse(Call<ThemeInfo> call, Response<ThemeInfo> response) {
                Log.e(TAG, "onResponse: " + response.body().toString());
                tv.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<ThemeInfo> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
