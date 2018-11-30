package com.mytechideas.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mytechideas.bakingapp.retrofit.Recipe;
import com.mytechideas.bakingapp.retrofit.Step;
import com.mytechideas.bakingapp.retrofit.StepRecipeContainerClass;

import java.util.List;

public class StepDetailActivity extends AppCompatActivity implements StepDetailFragment.OnNextButtonClickListener {

    private static final String LOG_TAG=StepDetailActivity.class.getSimpleName();

    private StepRecipeContainerClass mContainer;
    private Step pStep;
    private Recipe mRecipe;
    private int mStepId=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);


        if(savedInstanceState == null) {

            // Retrieve list index values that were sent through an intent; use them to display the desired Android-Me body part image
            // Use setListindex(int index) to set the list index for all BodyPartFragments

            // Create a new head BodyPartFragment
            StepDetailFragment mDetail = new StepDetailFragment();

            Intent  intent=getIntent();
            mContainer=intent.getParcelableExtra(StepRecipeContainerClass.PARCELABLE);
            mRecipe= mContainer.getmRecipe();
            pStep= mContainer.getmStep();


            mStepId= pStep.getId();
            mDetail.setStep(pStep);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.step_container, mDetail)
                    .commit();

        }

    }

    @Override
    public void onNextSelected(int position) {
        StepDetailFragment mDetail = new StepDetailFragment();

        for(Step d : mRecipe.getSteps()){
            if(d.getId() != null && d.getId().equals(position)){
                mDetail.setStep(d);
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.step_container, mDetail)
                        .commit();
            }


            //something here
        }






    }
}
