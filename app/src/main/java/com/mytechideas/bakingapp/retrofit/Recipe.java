
package com.mytechideas.bakingapp.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Recipe  implements Parcelable {
    public static  final String PARCELABLE="parcelable";

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients = null;
    @SerializedName("steps")
    @Expose
    private List<Step> steps = null;
    @SerializedName("servings")
    @Expose
    private Integer servings;
    @SerializedName("image")
    @Expose
    private String image;

    public Recipe(Integer mid, String mname, List<Ingredient> mingredients, List<Step> msteps,Integer mservings, String mimage){

        id=mid;
        name=mname;
        ingredients=mingredients;
        steps=msteps;
        servings=mservings;
        image=mimage;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeList(ingredients);
        parcel.writeList(steps);
        parcel.writeInt(servings);
        parcel.writeString(image);

    }

    public Recipe(Parcel parcel){
        //read and set saved values from parcel
        id=parcel.readInt();
        name= parcel.readString();
        ingredients=parcel.readArrayList(Ingredient.class.getClassLoader());
        steps=parcel.readArrayList(Step.class.getClassLoader());
        servings=parcel.readInt();
        image=parcel.readString();

    }


    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>(){

        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[0];
        }
    };

    @Override
    public String toString() {
        StringBuilder recipeString=new StringBuilder("");
        recipeString
                .append("Id:"+id+"\n")
                .append("Name: "+name+"\n")
                .append("Ingredients: "+ingredients.size()+"\n")
                .append("Steps: "+steps.size()+"\n")
                .append("Servings: "+servings+"\n")
                .append("image: "+image+"\n");

        return recipeString.toString();
    }
}
