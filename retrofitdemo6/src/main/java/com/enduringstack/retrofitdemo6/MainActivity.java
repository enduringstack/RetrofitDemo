package com.enduringstack.retrofitdemo6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private Button btnWrapper;

    private Button btnNormal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnWrapper = (Button) findViewById(R.id.btnWrapper);

        btnNormal = (Button) findViewById(R.id.btnNormal);

        tv = (TextView) findViewById(R.id.tv);

        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.github.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                LoadRepo loadRepo = retrofit.create(LoadRepo.class);

                Call<List<Repo>> call = loadRepo.loadRepo("enduringstack");

                call.enqueue(new Callback<List<Repo>>() {
                    @Override
                    public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                        tv.setText("");
                        tv.setText(response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<List<Repo>> call, Throwable t) {
                        tv.setText("");
                        tv.setText("请求失败");
                    }
                });
            }
        });

        btnWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.github.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                SearchUsersService service = retrofit.create(SearchUsersService.class);

                Call<List<UserInfo>> call = service.listUsers(2016);

                call.enqueue(new Callback<List<UserInfo>>() {
                    @Override
                    public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {
                        tv.setText("");
                        tv.setText(response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<List<UserInfo>> call, Throwable t) {
                        tv.setText("");
                        tv.setText("请求失败");
                    }
                });
            }
        });

    }
}
