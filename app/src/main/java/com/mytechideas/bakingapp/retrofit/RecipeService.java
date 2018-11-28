package com.mytechideas.bakingapp.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeService {

    @GET("59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();

}
