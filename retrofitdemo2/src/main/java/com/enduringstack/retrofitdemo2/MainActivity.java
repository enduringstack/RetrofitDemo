package com.enduringstack.retrofitdemo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnRequest = (Button) findViewById(R.id.btnRequest);
        final TextView tv = (TextView) findViewById(R.id.tv);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("");

                SearchBookService service = RetrofitWrapper.getInstance().create(SearchBookService.class);

                String bookName = "追风筝的人";

                String tagName = "风筝";

                String tag = null;
                String book = null;

                try {
                    book = URLEncoder.encode(bookName, "UTF-8");
                    tag = URLEncoder.encode(tagName, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Call<BookInfo> call = service.getBookByName(book, tag);

                call.enqueue(new Callback<BookInfo>() {
                    @Override
                    public void onResponse(Call<BookInfo> call, Response<BookInfo> response) {

                        String message = response.message();

                        Request request = call.request();

                        String s = request.url().toString();

                        Log.e(TAG, "onResponse: " + response.body().toString());
                        tv.setText(response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<BookInfo> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }
}
