package com.mytechideas.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mytechideas.bakingapp.retrofit.Recipe;
import com.mytechideas.bakingapp.retrofit.Step;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepHolder> {



    public final static String LOG_TAG= RecipeAdapter.class.getName().toString();
    private Context context;
    private List<Step> mStepData;
    private final StepsAdapter.StepsAdapterOnClickHandler mClickHandler;

    public StepsAdapter(StepsAdapter.StepsAdapterOnClickHandler clickHandler, List<Step> stepsFetchedData) {
        mClickHandler =  clickHandler;
        setRecipeData(stepsFetchedData);

    }

    public interface StepsAdapterOnClickHandler {
        void onClick(Step stepData);
    }


    @NonNull
    @Override
    public StepsAdapter.StepHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.step_item_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new StepsAdapter.StepHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.StepHolder stepViewHolder, int position ) {
        String mTitle =mStepData.get(position).getDescription();

        stepViewHolder.mRecipePosterView.setImageDrawable(context.getDrawable(R.drawable.ic_cake_2));
        stepViewHolder.mTitleStep.setText(mTitle);


    }

    @Override
    public int getItemCount() {
        if (null == mStepData) return 0;
        return mStepData.size();
    }

    public void setRecipeData(List<Step> movieData) {
        mStepData = movieData;
        notifyDataSetChanged();
    }

    public void restartData(){
        mStepData.clear();
        notifyDataSetChanged();
    }



    public class StepHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final ImageView mRecipePosterView;
        public final TextView mTitleStep;


        public StepHolder(@NonNull View itemView) {
            super(itemView);

            mRecipePosterView= (ImageView) itemView.findViewById(R.id.imageView);
            mTitleStep=(TextView) itemView.findViewById(R.id.step_name) ;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition=getAdapterPosition();
            Step mStep = mStepData.get(adapterPosition);
            mClickHandler.onClick(mStep);
        }


    }


}

