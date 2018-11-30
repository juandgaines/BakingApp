
package com.mytechideas.bakingapp.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable {

    public static  final String PARCELABLE="parcelable";

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("videoURL")
    @Expose
    private String videoURL;
    @SerializedName("thumbnailURL")
    @Expose
    private String thumbnailURL;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(id);
        parcel.writeString(shortDescription);
        parcel.writeString(description);
        parcel.writeString(videoURL);
        parcel.writeString(thumbnailURL);

    }

    public Step(Parcel parcel){
        //read and set saved values from parcel
        id=parcel.readInt();
        shortDescription= parcel.readString();
        description=parcel.readString();
        videoURL=parcel.readString();
        thumbnailURL=parcel.readString();


    }


    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>(){

        @Override
        public Step  createFromParcel(Parcel parcel) {
            return new Step(parcel);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[0];
        }
    };

    @Override
    public String toString() {
        StringBuilder ingredientString=new StringBuilder("");
        ingredientString
                .append("Id:"+id+"\n")
                .append("shortDescription: "+shortDescription+"\n")
                .append("description: "+description+"\n")
                .append("video: "+videoURL+"\n")
                .append("thumbail: "+thumbnailURL+"\n");


        return ingredientString.toString();
    }
}
