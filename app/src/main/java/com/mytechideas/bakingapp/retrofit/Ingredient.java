
package com.mytechideas.bakingapp.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable {

    @SerializedName("quantity")
    @Expose
    private double quantity;
    @SerializedName("measure")
    @Expose
    private String measure;
    @SerializedName("ingredient")
    @Expose
    private String ingredient;

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeDouble(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);

    }

    public Ingredient(Parcel parcel){
        //read and set saved values from parcel
        quantity=parcel.readDouble();
        measure= parcel.readString();
        ingredient=parcel.readString();

    }


    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>(){

        @Override
        public Ingredient createFromParcel(Parcel parcel) {
            return new Ingredient(parcel);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[0];
        }
    };

    @Override
    public String toString() {
        StringBuilder ingredientString=new StringBuilder("");
        ingredientString
                .append("Quantity:"+quantity+"\n")
                .append("Measure: "+measure+"\n")
                .append("Ingredient: "+ingredient+"\n");


        return ingredientString.toString();
    }
}
