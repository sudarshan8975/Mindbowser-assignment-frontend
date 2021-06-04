package com.example.list.assigmnent.service;

import com.example.list.assigmnent.utility.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceFactory {

    public static Services makeApiServiceService() {
        return makeApiService();
    }

    private static Services makeApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(Services.class);
    }




}