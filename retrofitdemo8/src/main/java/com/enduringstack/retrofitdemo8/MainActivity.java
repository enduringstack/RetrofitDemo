package com.enduringstack.retrofitdemo8;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText etUserName;

    private EditText etPassword;

    private Button btnLogin;

    private TextView tvHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserName = (EditText) findViewById(R.id.et_userName);

        etPassword = (EditText) findViewById(R.id.et_password);

        btnLogin = (Button) findViewById(R.id.btn_login);

        tvHint = (TextView) findViewById(R.id.tv_hint);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName  = etUserName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                LoginService service = ServiceFactory.createRetrofitService(LoginService.class, LoginService.SERVICE_ENDPOINT);

                final String header = RequestUtils.getAuthorizationHeader(userName, password);

                String[] scops = new String[]{"public_repo", "repo"};

                String note = "RetrofitDemo11";

                AuthScope authScope = new AuthScope(scops, note);

                Call<Authorization> call = service.authStandard(header, authScope);

                call.enqueue(new Callback<Authorization>() {
                    @Override
                    public void onResponse(Call<Authorization> call, Response<Authorization> response) {
                        tvHint.setText("");
                        Request request = call.request();

                        HttpUrl url = request.url();

                        Headers headers = request.headers();

                        String s1 = headers.toString();

                        String s = url.toString();

                        Log.e(TAG, "onResponse: " + s);
                        Log.e(TAG, "onResponse: " + s1);
                        tvHint.setText(response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<Authorization> call, Throwable t) {
                        tvHint.setText("");
                        tvHint.setText("失败");
                    }
                });
            }
        });
    }
}
