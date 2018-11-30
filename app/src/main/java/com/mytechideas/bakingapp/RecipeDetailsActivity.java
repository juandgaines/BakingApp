package com.mytechideas.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mytechideas.bakingapp.retrofit.Ingredient;
import com.mytechideas.bakingapp.retrofit.Recipe;
import com.mytechideas.bakingapp.retrofit.Step;
import com.mytechideas.bakingapp.retrofit.StepRecipeContainerClass;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements MasterListRecipe.OnStepClickListener{

    public static final String LOG_TAG= RecipeDetailsActivity.class.getSimpleName();
    private static final String MODE_STATE = "state";
    private static final String TABLE_MODE = "mode";
    private boolean twoPanel;


    private StepDetailFragment mDetail;



    private Recipe pRecipe;

    public boolean getTwoPanelState(){

        return twoPanel;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        if(savedInstanceState==null) {

            pRecipe = getIntent().getParcelableExtra(Recipe.PARCELABLE);

            MasterListRecipe mMaster = new MasterListRecipe();
            mMaster.setRecipe(pRecipe);


            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.recipe_container, mMaster)
                    .commit();


            if (findViewById(R.id.recipe_details_linear_layout) != null) {

                twoPanel = true;

                mDetail = new StepDetailFragment();


                Step pStep = pRecipe.getSteps().get(0);
                mDetail.setStep(pStep);

                mDetail.setMode(twoPanel);

                fragmentManager.beginTransaction()
                        .add(R.id.step_container, mDetail)
                        .commit();


            }
        }


    }

    @Override
    public void onStepSelected(Step position) {

        if(twoPanel){

            mDetail = new StepDetailFragment();

            mDetail.setStep(position);
            mDetail.setMode(twoPanel);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_container,mDetail)
                    .commit();

        }

        else{

            Bundle b = new Bundle();
            StepRecipeContainerClass mTesting= new StepRecipeContainerClass(pRecipe,position);


            b.putParcelable(StepRecipeContainerClass.PARCELABLE, mTesting);

            // Attach the Bundle to an intent
            final Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtras(b);

            startActivity(intent);

        }


    }


}
