package com.efd.striketectablet.api;

import com.efd.striketectablet.App;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingletonCSV {

    public static CSVRest CSV_REST;

    private static App application;
    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder ongoing = chain.request().newBuilder();
                    ongoing.addHeader("Accept", "text/plain,application/json;versions=1");

                    return chain.proceed(ongoing.build());
                }
            })
            .build();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(RestUrl.CSV_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }

    public static void setApplication(App application) {
        RetrofitSingletonCSV.application = application;
        CSV_REST = createService(CSVRest.class);
    }

}
