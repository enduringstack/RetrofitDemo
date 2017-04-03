package com.enduringstack.retrofitdemo5.converter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by chenfuduo on 17-3-26.
 */

public class JsonConverterFactory extends Converter.Factory {
    public static JsonConverterFactory create() {
        return new JsonConverterFactory();
    }

    private JsonConverterFactory() {
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == JSONObject.class)
            return JsonResponseBodyConverter.JSONObjectResponseBodyConverter.INSTANCE;
        if (type == JSONArray.class)
            return JsonResponseBodyConverter.JSONArrayResponseBodyConverter.INSTANCE;
        return null;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if (type == JSONObject.class || type == JSONArray.class)
            return JsonRequestBodyConverter.INSTANCE;
        return null;
    }
}