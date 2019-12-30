package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String SandwichJsonString) {
        //we make Sandwich object before the try, because if an error happened,
        //the the parseSandwichJson method can return the object as null:
        Sandwich parsedSandwich = null;
        try{
            //First, Initialize JSON Object from the Sandwich Json String:
            JSONObject SandwichJSONObject = new JSONObject(SandwichJsonString);

            //Then, we create a json object also but for name:
            JSONObject name = SandwichJSONObject.getJSONObject("name");

            //now we get a the string mainName from the json object 'name':
            String mainName = name.getString("mainName");

            //here we get the json array from the json object 'name':
            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");

            //this time we get the string values from the base Sandwich json object directly:
            String placeOfOrigin = SandwichJSONObject.getString("placeOfOrigin");
            String description = SandwichJSONObject.getString("description");
            String image = SandwichJSONObject.getString("image");

            //again, we're getting a json array:
            JSONArray ingredients = SandwichJSONObject.getJSONArray("ingredients");

            //we want to make an object from the class Sandwich directly, but the probelm is we have 2 arrays,
            // and the class only take a list as a parameter, so let's convert the arrays to a list:
            List<String> alsoKnownAsList = new ArrayList<>();
            List<String> ingredientsList = new ArrayList<>();

            //a variable to limit the loop:
            int x = alsoKnownAs.length();
            int y = ingredients.length();


            //here in the loop, we convert the array to a list manually (for alsoKnownAs):
            for (int i = 0; i < x; i++) {
                String AKA = alsoKnownAs.getString(i);
                alsoKnownAsList.add(AKA);
            }

            //we do the loop again with the ingredients:
            for (int i = 0; i < y; i++) {
                String ingrd = ingredients.getString(i);
                ingredientsList.add(ingrd);
            }


            //we make an object from the class Sandwich, because we will use it in the DetailActivity.java
            parsedSandwich = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);

        }catch (JSONException e) {

            Log.e("JsonUtils", "Problem parsing the Sandwich JSON Object", e);
        }
        return parsedSandwich;
    }
}
