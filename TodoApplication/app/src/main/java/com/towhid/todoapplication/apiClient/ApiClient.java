package com.towhid.todoapplication.apiClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://675c0de4fe09df667f6271b8.mockapi.io/api/v6/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(){

        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;

    }

}
