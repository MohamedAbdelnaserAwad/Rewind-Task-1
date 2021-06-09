package com.example.rewindtask1;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit getRetrofit(){

        HttpLoggingInterceptor httpLoggingInerceptor = new HttpLoggingInterceptor();
        httpLoggingInerceptor.setLevel(httpLoggingInerceptor.getLevel());

        OkHttpClient okHttpClient =new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInerceptor)
                .build();


        Retrofit retrofit   =new Retrofit.Builder()
                .baseUrl("https://api.nytimes.com/svc/search/v2/articlesearch.json?q=election&api-key=NSLGHeOObgwr2Smw5FbNnk7YNRYufHFM")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return  retrofit;
    }

    public static ApiInterface getInterface(){
        ApiInterface apiInterface = getRetrofit().create(ApiInterface.class);
        return apiInterface;
    }


}
