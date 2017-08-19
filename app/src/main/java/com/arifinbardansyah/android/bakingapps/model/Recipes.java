package com.arifinbardansyah.android.bakingapps.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by arifinbardansyah on 8/8/17.
 */

public class Recipes implements Parcelable {
    private int id;
    private String name;
    private int servings;
    private String image;
    private ArrayList<Ingredients> ingredients;
    private ArrayList<Steps> steps;

    public Recipes() {
    }

    protected Recipes(Parcel in) {
        id = in.readInt();
        name = in.readString();
        servings = in.readInt();
        image = in.readString();
        if (ingredients == null){
            ingredients = new ArrayList<>();
        }
        in.readTypedList(ingredients,Ingredients.CREATOR);
        if (steps == null){
            steps = new ArrayList<>();
        }
        in.readTypedList(steps,Steps.CREATOR);
    }

    public static final Creator<Recipes> CREATOR = new Creator<Recipes>() {
        @Override
        public Recipes createFromParcel(Parcel in) {
            return new Recipes(in);
        }

        @Override
        public Recipes[] newArray(int size) {
            return new Recipes[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<Ingredients> getIngredients() {
        return ingredients;
    }

    public ArrayList<Steps> getSteps() {
        return steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(servings);
        dest.writeString(image);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
    }

}
