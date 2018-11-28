package com.mytechideas.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mytechideas.bakingapp.retrofit.Ingredient;
import com.mytechideas.bakingapp.retrofit.Recipe;
import com.mytechideas.bakingapp.retrofit.Step;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements MasterListRecipe.OnStepClickListener{

    public static final String LOG_TAG= RecipeDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        if(savedInstanceState == null) {

            // Retrieve list index values that were sent through an intent; use them to display the desired Android-Me body part image
            // Use setListindex(int index) to set the list index for all BodyPartFragments

            // Create a new head BodyPartFragment
            MasterListRecipe mMaster = new MasterListRecipe();

            Recipe pRecipe= getIntent().getParcelableExtra(Recipe.PARCELABLE);
            mMaster.setRecipe(pRecipe);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.recipe_container, mMaster)
                    .commit();
        }
    }

    @Override
    public void onStepSelected(int position) {


    }
}
