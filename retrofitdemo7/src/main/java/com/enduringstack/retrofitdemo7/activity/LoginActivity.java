package com.enduringstack.retrofitdemo7.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.enduringstack.retrofitdemo7.R;
import com.enduringstack.retrofitdemo7.Utils.RequestUtils;
import com.enduringstack.retrofitdemo7.model.AuthScope;
import com.enduringstack.retrofitdemo7.model.Authorization;
import com.enduringstack.retrofitdemo7.service.LoginService;
import com.enduringstack.retrofitdemo7.service.ServiceFactory;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private EditText loginET;
    private EditText passwordET;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (!prefs.getString("TOKEN", "").isEmpty()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            finish();
        } else {
            setContentView(R.layout.activity_login);

            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.logging_in));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            loginET = (EditText) findViewById(R.id.loginET);

            passwordET = (EditText) findViewById(R.id.passwordET);
            passwordET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == R.id.login || id == EditorInfo.IME_NULL) {
                        attemptLogin();
                        return true;
                    }
                    return false;
                }
            });

            Button loginButton = (Button) findViewById(R.id.loginButton);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    attemptLogin();
                }
            });
        }
    }

    private void attemptLogin() {
        if (checkFields()) {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                final String login = loginET.getText().toString();
                final String password = passwordET.getText().toString();

                progressDialog.show();

                LoginService loginService = ServiceFactory.createRetrofitService(LoginService.class, LoginService.SERVICE_ENDPOINT);
                loginService.authStandard(RequestUtils.getAuthorizationHeader(login, password), new AuthScope(new String[]{"repo", "public_repo"}, "MaterialHub"))
                        .enqueue(new Callback<Authorization>() {
                            @Override
                            public void onResponse(Response<Authorization> response, Retrofit retrofit) {
                                progressDialog.dismiss();

                                String tfHeader = response.headers().get("X-GitHub-OTP");

                                if (tfHeader != null) {
                                    handleTFA(login, password);
                                } else {
                                    if (response.code() == 201) {
                                        loggedIn(login, response.body().getToken());
                                    } else {
                                        processRequestError(response);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, R.string.unknown_error, Toast.LENGTH_SHORT).show();
                            }
                        });


            }

        }
    }

    private void handleTFA(final String login, final String password) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.authentication_code);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String code = input.getText().toString().trim();
                if (code.isEmpty()) {
                    Toast.makeText(LoginActivity.this, R.string.field_cant_be_empty, Toast.LENGTH_SHORT).show();
                    input.requestFocus();
                } else {
                    progressDialog.show();

                    LoginService loginService = ServiceFactory.createRetrofitService(LoginService.class, LoginService.SERVICE_ENDPOINT);

                    loginService.authTwoFactor(RequestUtils.getAuthorizationHeader(login, password), code, new AuthScope(new String[]{"repo", "public_repo"}, "MaterialHub"))
                            .enqueue(new Callback<Authorization>() {
                                @Override
                                public void onResponse(Response<Authorization> response, Retrofit retrofit) {
                                    progressDialog.dismiss();

                                    if (response.code() == 201) {
                                        loggedIn(login, response.body().getToken());
                                    } else {
                                        processRequestError(response);
                                    }
                                }

                                @Override
                                public void onFailure(Throwable t) {
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, R.string.unknown_error, Toast.LENGTH_SHORT).show();
                                }
                            });

                    dialog.cancel();
                }
            }
        });

        builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                progressDialog.dismiss();
            }
        });

        builder.show();
    }

    private boolean checkFields() {
        boolean retVal = true;
        loginET.setError(null);
        passwordET.setError(null);

        if (loginET.getText().toString().trim().isEmpty()) {
            retVal = false;
            loginET.setError(getString(R.string.field_required));
            loginET.requestFocus();
        } else if (passwordET.getText().toString().trim().isEmpty()) {
            retVal = false;
            passwordET.setError(getString(R.string.field_required));
            passwordET.requestFocus();
        }

        return retVal;
    }

    private void processRequestError(Response<Authorization> response) {
        if (response.code() == 401) {
            Toast.makeText(LoginActivity.this, R.string.wrong_login, Toast.LENGTH_SHORT).show();
        } else if (response.code() == 422) {
            Toast.makeText(LoginActivity.this, R.string.already_registered, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(LoginActivity.this, R.string.unknown_error, Toast.LENGTH_SHORT).show();
        }
    }

    public void loggedIn(String login, String token) {
        SharedPreferences.Editor prefs = PreferenceManager.getDefaultSharedPreferences(this).edit();
        prefs.putString("LOGIN", login);
        prefs.putString("TOKEN", token);
        prefs.commit();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        finish();
    }
}
