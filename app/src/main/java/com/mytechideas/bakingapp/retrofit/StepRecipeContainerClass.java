package com.mytechideas.bakingapp.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

public class StepRecipeContainerClass  implements Parcelable {

    public static  final String PARCELABLE="parcelable";

    private Recipe mRecipe;
    private Step mStep;

    public StepRecipeContainerClass(Recipe r, Step s){
        mRecipe=r;
        mStep=s;


    }

    public void setmRecipe(Recipe mRecipe) {
        this.mRecipe = mRecipe;
    }

    public void setmStep(Step mStep) {
        this.mStep = mStep;
    }

    public Recipe getmRecipe() {
        return mRecipe;
    }

    public Step getmStep() {
        return mStep;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeParcelable(mRecipe,i);
        parcel.writeParcelable(mStep,i);

    }

    public StepRecipeContainerClass(Parcel parcel){
        //read and set saved values from parcel
        mRecipe=parcel.readParcelable(Recipe.class.getClassLoader());
        mStep= parcel.readParcelable(Step.class.getClassLoader());


    }


    public static final Parcelable.Creator<StepRecipeContainerClass> CREATOR = new Parcelable.Creator<StepRecipeContainerClass>(){

        @Override
        public StepRecipeContainerClass createFromParcel(Parcel parcel) {
            return new StepRecipeContainerClass(parcel);
        }

        @Override
        public StepRecipeContainerClass[] newArray(int size) {
            return new StepRecipeContainerClass[0];
        }
    };

    @Override
    public String toString() {
        StringBuilder recipeString=new StringBuilder("");
        recipeString
                .append("step:"+mStep.toString()+"\n")
                .append("recipe: "+mRecipe.toString()+"\n");

        return recipeString.toString();
    }
}
