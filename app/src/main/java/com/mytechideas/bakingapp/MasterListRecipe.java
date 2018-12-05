package com.mytechideas.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mytechideas.bakingapp.retrofit.Ingredient;
import com.mytechideas.bakingapp.retrofit.Recipe;
import com.mytechideas.bakingapp.retrofit.Step;

import java.util.List;

public class MasterListRecipe extends Fragment implements  StepsAdapter.StepsAdapterOnClickHandler{

    public static final String LOG_TAG=MasterListRecipe.class.getSimpleName();
    public static  final String RECYCLERVIEW_STATE="recyclerview";
    private static final String SAVED_RECIPE = "recipe_saved";
    private static final String KEY_RECYCLERVIEW = "recyclerview_state";
    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    OnStepClickListener mCallback;
    private RecyclerView recyclerView;
    private int mPosition;
    private Recipe mRecipe;

    private List<Ingredient> mImgredients;
    private List<Step> mSteps;

    @Override
    public void onClick(Step stepData) {

        mCallback.onStepSelected(stepData);

    }


    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnStepClickListener {
        void onStepSelected(Step position);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    public MasterListRecipe() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);



            recyclerView = rootView.findViewById(R.id.steps_listview);
            TextView textView = rootView.findViewById(R.id.ingredients_content);
            TextView textTitle = rootView.findViewById(R.id.textView);



            if(savedInstanceState!=null){
                mRecipe=savedInstanceState.getParcelable(SAVED_RECIPE);
                //Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(RECYCLERVIEW_STATE);
                mImgredients=mRecipe.getIngredients();
                mSteps=mRecipe.getSteps();
                //recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
            }

            String totalIngredients="";
            for (Ingredient ingredient:mImgredients){

                String ingre= ingredient.getIngredient();
                String measure= ingredient.getMeasure();
                Double  quantity =ingredient.getQuantity();

                totalIngredients+= "*"+quantity+" "+measure+ " "+ingre+"\n";
            }

            String mTitle=mRecipe.getName();
            textView.setText(totalIngredients);
            textTitle.setText(mTitle);
            int id=0;
            StepsAdapter mStepsAdapter= new StepsAdapter(this,mSteps);

            recyclerView.setAdapter(mStepsAdapter);


            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);


            recyclerView.setHasFixedSize(true);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getContext());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getContext(), RecipeWidgetProvider.class));

            if(mTitle.equals("Nutella Pie")){
                id=R.drawable.ic_nutella_pie_2;
            }
            else  if(mTitle.equals("Brownies")){
                id=R.drawable.ic_brownie_2;
            }
            else  if(mTitle.equals("Yellow Cake")){
                id=R.drawable.ic_yellowcake;
            }
            else  if(mTitle.equals("Cheesecake")){
                id=R.drawable.ic_cheescake;
            }
            RecipeWidgetProvider.updateRecipeWidgets(getContext(), appWidgetManager, appWidgetIds, mRecipe.getName(),totalIngredients,id);
            return rootView;


    }

    public void setRecipe(Recipe recipe) {
        mRecipe=recipe;
        mImgredients = recipe.getIngredients();
        mSteps=recipe.getSteps();

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState != null)
        {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(RECYCLERVIEW_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
            mRecipe=savedInstanceState.getParcelable(SAVED_RECIPE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(SAVED_RECIPE, mRecipe);
        outState.putParcelable(KEY_RECYCLERVIEW, recyclerView.getLayoutManager().onSaveInstanceState());
    }

}
