package com.mytechideas.bakingapp;

import android.content.Intent;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mytechideas.bakingapp.retrofit.Ingredient;
import com.mytechideas.bakingapp.retrofit.Recipe;
import com.mytechideas.bakingapp.retrofit.RecipeService;
import com.mytechideas.bakingapp.retrofit.RetrofitSingleton;
import com.mytechideas.bakingapp.retrofit.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler{

    public static final String LOG_TAG=MainActivity.class.getSimpleName();
    public static final String KEY_RECYCLERVIEW_1="position1";
    private Parcelable mRecyclerViewState1;

    @BindView(R.id.recipe_listview) RecyclerView mRecyclerView;


    private RecipeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mAdapter=new RecipeAdapter(MainActivity.this, null);
        mRecyclerView.setAdapter(mAdapter);

        RecipeService movieService= RetrofitSingleton.getRecipeService();
        final int columns = getResources().getInteger(R.integer.activity_columns_recyclerview);
        mLayoutManager = new GridLayoutManager(this,columns);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (mRecyclerViewState1 != null) {
            mLayoutManager.onRestoreInstanceState(mRecyclerViewState1);
        }

        mRecyclerView.setHasFixedSize(true);


        movieService.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                List<Recipe> mRecipies= new ArrayList<Recipe>();

                for(Recipe recipe: response.body()) {
                    Integer mId=recipe.getId();
                    String mName=recipe.getName();
                    List<Ingredient> mListIngredients=recipe.getIngredients();
                    List<Step> mListSteps= recipe.getSteps();
                    Integer mServings= recipe.getServings();
                    String mImage=recipe.getImage();
                    Recipe mRecipe=new Recipe(mId,mName,mListIngredients,mListSteps,mServings,mImage);
                    mRecipies.add(mRecipe);

                    Log.d(LOG_TAG,mId + mName);
                }

                mAdapter.setRecipeData(mRecipies);

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

                Log.e(LOG_TAG, t.getMessage());
            }
        });

    }

    @Override
    public void onClick(Recipe recipeData) {

        Bundle b = new Bundle();
        b.putParcelable(Recipe.PARCELABLE,recipeData);
        Intent intent =new Intent(this,RecipeDetailsActivity.class);
        intent.putExtras(b);
        startActivity(intent);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable(KEY_RECYCLERVIEW_1, mRecyclerView.getLayoutManager().onSaveInstanceState());


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState!=null){
            mRecyclerViewState1 = savedInstanceState.getParcelable(KEY_RECYCLERVIEW_1);

        }
    }

}
