package com.mytechideas.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mytechideas.bakingapp.retrofit.Ingredient;
import com.mytechideas.bakingapp.retrofit.Recipe;
import com.mytechideas.bakingapp.retrofit.Step;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String LOG_TAG= RecipeDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);


        Intent intent =getIntent();

        if(intent!=null){

            final Recipe recipeData = intent.getParcelableExtra(Recipe.PARCELABLE);
            final int id=recipeData.getId();
            final String mNameStr =recipeData.getName();
            final List<Ingredient> mIngredientsList=recipeData.getIngredients();
            final List<Step> mStepsList=recipeData.getSteps();
            final Integer mServings= recipeData.getServings() ;
            final String mImage=recipeData.getImage();

            Log.d(LOG_TAG,"Name:" +mNameStr+ " and ID: "+id);

        }
    }
}
