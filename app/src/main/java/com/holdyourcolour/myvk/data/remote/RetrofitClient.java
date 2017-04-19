package com.holdyourcolour.myvk.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HoldYourColour on 17.04.2017.
 */

public class RetrofitClient {
    private static Retrofit sRetrofit = null;

    public static Retrofit getClient(String baseURL){
        if (sRetrofit == null){
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }
}
