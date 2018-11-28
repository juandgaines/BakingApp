package com.mytechideas.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mytechideas.bakingapp.retrofit.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    public final static String LOG_TAG= RecipeAdapter.class.getName().toString();
    private Context context;
    private List<Recipe> mRecipeData;
    private final RecipeAdapterOnClickHandler mClickHandler;

    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler, List<Recipe> recipeFetchedData) {
        mClickHandler =  clickHandler;
        setRecipeData(recipeFetchedData);

    }

    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe movieData);
    }


    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_item_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new RecipeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder recipeViewHolder, int position ) {
        String mTitle =mRecipeData.get(position).getName();

        TypedValue outValue = new TypedValue();

        //context.getResources().getValue(R.dimen.picasso_image_divisor,outValue,true);

        float divisor= outValue.getFloat();


        recipeViewHolder.mRecipePosterView.setImageDrawable(context.getDrawable(R.drawable.ic_cake_1));
        recipeViewHolder.mTitleRecipe.setText(mTitle);


    }

    @Override
    public int getItemCount() {
        if (null == mRecipeData) return 0;
        return mRecipeData.size();
    }

    public void setRecipeData(List<Recipe> movieData) {
        mRecipeData = movieData;
        notifyDataSetChanged();
    }

    public void restartData(){
        mRecipeData.clear();
        notifyDataSetChanged();
    }



    public class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final ImageView mRecipePosterView;
        public final TextView mTitleRecipe;


        public RecipeHolder(@NonNull View itemView) {
            super(itemView);

            mRecipePosterView= (ImageView) itemView.findViewById(R.id.imageView);
            mTitleRecipe=(TextView) itemView.findViewById(R.id.recipe_name) ;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition=getAdapterPosition();
            Recipe mRecipe = mRecipeData.get(adapterPosition);
            mClickHandler.onClick(mRecipe);
        }


    }


}
