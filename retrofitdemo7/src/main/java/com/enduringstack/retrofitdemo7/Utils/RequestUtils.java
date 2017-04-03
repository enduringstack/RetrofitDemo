package com.enduringstack.retrofitdemo7.Utils;

import android.content.SharedPreferences;
import android.util.Base64;

/**
 * Created by sebastianmazur on 18.12.15.
 */
public class RequestUtils {

    /**
     * @param login    user login
     * @param password user password
     * @return Basic authentication header value
     */
    public static String getAuthorizationHeader(String login, String password) {
        String headerValue = Base64.encodeToString(String.format("%s:%s", login, password).getBytes(), Base64.DEFAULT).replace("\n", "");
        String header = "Basic " + headerValue;

        return header;
    }

    /**
     * Create Basic authentication header based in LOGIN and TOKEN values saved in application preferences
     *
     * @param prefs
     * @return Basic authentication header value
     */
    public static String getAuthorizationHeader(SharedPreferences prefs) {
        String token = prefs.getString("TOKEN", "");
        String login = prefs.getString("LOGIN", "");
        String headerValue = "";

        if (!login.isEmpty() && !token.isEmpty()) {
            String value = Base64.encodeToString(String.format("%s/token:%s", login, token).getBytes(), Base64.DEFAULT).replace("\n", "");
            headerValue = String.format("Basic %s", value);
        }

        return headerValue;
    }
}
