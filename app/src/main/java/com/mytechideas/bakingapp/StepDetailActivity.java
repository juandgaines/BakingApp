package com.mytechideas.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.mytechideas.bakingapp.retrofit.Recipe;
import com.mytechideas.bakingapp.retrofit.Step;
import com.mytechideas.bakingapp.retrofit.StepRecipeContainerClass;

import java.util.List;

public class StepDetailActivity extends AppCompatActivity implements StepDetailFragment.OnNextButtonClickListener {

    private static final String LOG_TAG = StepDetailActivity.class.getSimpleName();
    private static final String RECIPE_STATE ="recipe";

    private StepRecipeContainerClass mContainer;
    private Step pStep;
    private Recipe mRecipe;
    private boolean flag = false;
    private int mStepId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);


        if (savedInstanceState == null) {


            StepDetailFragment mDetail = new StepDetailFragment();

            Intent intent = getIntent();
            mContainer = intent.getParcelableExtra(StepRecipeContainerClass.PARCELABLE);
            mRecipe = mContainer.getmRecipe();
            pStep = mContainer.getmStep();


            mStepId = pStep.getId();
            mDetail.setStep(pStep);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.step_container, mDetail)
                    .commit();

        }
        else {
            mRecipe=savedInstanceState.getParcelable(RECIPE_STATE);
        }

    }

    @Override
    public void onNextSelected(int position) {
        StepDetailFragment mDetail = new StepDetailFragment();

        for (Step d : mRecipe.getSteps()) {
            if (d.getId() != null && d.getId().equals(position)) {
                mDetail.setStep(d);
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.step_container, mDetail)
                        .commit();
            }

        }

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        int display_mode = getResources().getConfiguration().orientation;

        if (hasFocus && display_mode == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI();
            flag = false;
        }

    }


    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                //View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int display_mode = getResources().getConfiguration().orientation;



            int action = event.getActionMasked();


            switch (action) {

                case (MotionEvent.ACTION_DOWN):
                    if (display_mode == Configuration.ORIENTATION_LANDSCAPE) {
                        if (!flag) {
                            hideSystemUI();

                            flag = true;

                        } else {
                            flag = false;
                            showSystemUI();
                        }
                    }
                    return true;

                default:
                    return super.onTouchEvent(event);
            }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(RECIPE_STATE,mRecipe);
    }
}
