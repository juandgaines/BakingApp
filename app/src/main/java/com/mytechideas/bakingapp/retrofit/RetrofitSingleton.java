package com.mytechideas.bakingapp.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {

    private static final Object LOCK =new Object();
    private static Retrofit sInstance;



    private static final String BASE_URL_RETROFIT="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/";

    private static Retrofit getRetroInstance(){
        if(sInstance==null){
            synchronized (LOCK){
                Gson gson= new GsonBuilder().create();
                sInstance=new Retrofit.Builder()
                        .baseUrl(BASE_URL_RETROFIT)
                        .addConverterFactory(GsonConverterFactory.create(gson)).build();
            }
        }
        return sInstance;
    }

    public static RecipeService getRecipeService(){
        return getRetroInstance().create(RecipeService.class);
    }


}
