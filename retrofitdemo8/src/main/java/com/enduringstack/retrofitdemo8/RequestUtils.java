package com.enduringstack.retrofitdemo8;

import android.util.Base64;

/**
 * Created by chenfuduo on 17-4-1.
 */

public class RequestUtils {

    public static String getAuthorizationHeader(String login, String password) {
        String headerValue = Base64.encodeToString(String.format("%s:%s", login, password).getBytes(),
                Base64.DEFAULT).replace("\n","");

        String header = "Basic " + headerValue;

        return header;
    }
}
