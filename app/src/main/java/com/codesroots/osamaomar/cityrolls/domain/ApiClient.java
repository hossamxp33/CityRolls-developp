package com.codesroots.osamaomar.cityrolls.domain;

import androidx.annotation.NonNull;

import com.codesroots.osamaomar.cityrolls.helper.MyApplication;
import com.codesroots.osamaomar.cityrolls.helper.PreferenceHelper;
import com.codesroots.osamaomar.cityrolls.helper.ResourceUtil;

import java.util.concurrent.TimeUnit;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    public static String BASE_URL ;
    //  https://system.city-rolls.com/api/items/homepage.json
    private static final int TIMEOUT = 30;
    public static Retrofit retrofit = null;

    @NonNull
    private static OkHttpClient getOkHttpClient() {
// set your desired log level
// add your other interceptors …
// add logging as last interceptor
        return new OkHttpClient()
                .newBuilder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .cache(null)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request.Builder builder = originalRequest.newBuilder();

                    builder.addHeader("Accept", "application/json");
                    builder.addHeader("Content-Type", "application/json");
                    builder.addHeader("lang", ResourceUtil.getCurrentLanguage(MyApplication.getInstance()));
                    builder.addHeader(
                            "Authorization",
                            "Bearer "+ PreferenceHelper.getToken()
                    )  ;
                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                })
                .build();
    }

    public static Retrofit getClient() {
        //   if (retrofit == null) {
        retrofit = new Retrofit.Builder()
                .baseUrl(PreferenceHelper.getURL_Base())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory( RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(getOkHttpClient())
                .build();
//        }else {
//
//            retrofit.baseUrl().url() = "sd";
//        }
        return retrofit;
    }
    public static Retrofit getClientPayment() {
        //   if (retrofit == null) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://accept.paymob.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory( RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(getOkHttpClientPayment())
                .build();
//        }else {
//
//            retrofit.baseUrl().url() = "sd";
//        }
        return retrofit;
    }

    @NonNull
    private static OkHttpClient getOkHttpClientPayment() {
// set your desired log level
// add your other interceptors …
// add logging as last interceptor
        return new OkHttpClient()
                .newBuilder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .cache(null)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request.Builder builder = originalRequest.newBuilder();

                    builder.addHeader("Accept", "application/json");
                    builder.addHeader("Content-Type", "application/json");

                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                })
                .build();
    }
}