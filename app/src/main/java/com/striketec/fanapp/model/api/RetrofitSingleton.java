package com.striketec.fanapp.model.api;

import com.striketec.fanapp.utils.constants.ApiConstants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sukhbirs on 16-11-2017.
 * This is Retrofit Singleton class that generates single instance of type RestInterface.
 */

public class RetrofitSingleton {

    // private constructor
    private RetrofitSingleton() {
    }

    public static RestInterface getRestInterface() {
        return RetrofitSingletonHelper.sRestInterface;
    }

    private static class RetrofitSingletonHelper {
        private static OkHttpClient sHttpClient = new OkHttpClient.Builder()
                .readTimeout(ApiConstants.READ_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(ApiConstants.CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(ApiConstants.WRITE_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder ongoing = chain.request().newBuilder();
                        ongoing.addHeader("Accept", "text/plain,application/json;versions=1");

                        return chain.proceed(ongoing.build());
                    }
                })
                .build();

        private static Retrofit.Builder sRetrofitBuilder =
                new Retrofit.Builder()
                        .baseUrl(RestUrl.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());

        private static RestInterface sRestInterface = sRetrofitBuilder.client(sHttpClient).build().create(RestInterface.class);
    }
}
