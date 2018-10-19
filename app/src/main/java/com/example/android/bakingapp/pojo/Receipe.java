package com.example.android.bakingapp.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Receipe implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredients> ingredients = null;
    @SerializedName("steps")
    @Expose
    private List<ReceipeSteps> steps = null;
    @SerializedName("servings")
    @Expose
    private Integer servings;
    @SerializedName("image")
    @Expose
    private String image;

    public Receipe(int i, String name, int servings,String image) {
        this.setId(i);
        this.setName(name);
        this.setIngredients(null);
        this.setSteps(null);
        this.setServings(servings);
        this.setImage(image);

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

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public List<ReceipeSteps> getSteps() {
        return steps;
    }

    public void setSteps(List<ReceipeSteps> steps) {
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

    public Receipe mockdata() {
        this.setId(1);
        this.setName("Nutella Pie");
        this.setIngredients(null);
        this.setSteps(null);
        this.setServings(8);
        this.setImage("");
        return this;
    }
/*

    public Receipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        servings = in.readInt();
        image = in.readString();
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        in.readTypedList(this.ingredients, Ingredients.CREATOR); // Should work now
        in.readTypedList(this.steps, ReceipeSteps.CREATOR); // Should work now

    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeTypedList(ingredients);
        parcel.writeTypedList(steps);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }

    // This is to de-serialize the object
    public static final Parcelable.Creator<Receipe> CREATOR = new Parcelable.Creator<Receipe>() {
        public Receipe createFromParcel(Parcel in) {
            return new Receipe(in);
        }
        public Receipe[] newArray(int size) {
            return new Receipe[size];
        }
    };*/

}
