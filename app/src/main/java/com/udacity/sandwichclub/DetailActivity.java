package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //connect the java code with the layout:
        setContentView(R.layout.activity_detail);

        // make a java object for the image view to handle it:
        ImageView ingredientsIv = findViewById(R.id.image_iv);


        // intent is like an a way to move form main to here.
        // in this case we see if the intent has a value or not:
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        // intent also can carry an information..
        //in this case it carried the index position of the sandwich:
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent

            closeOnError();
            return;
        }

        //get the sandwiches json text from "res/values/string" as an array:
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);

        //take the info of one sandwich as a String:
        String json = sandwiches[position];

        //we take the json String and parse it in JsonUtils, and then we make an object from the class Sandwich:
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {

        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //make a java text view Object to handle the layout:
        TextView mainNameView  = findViewById(R.id.main_name_tv);
        TextView alsoKnownAsView = findViewById(R.id.also_known_tv);
        TextView originView = findViewById(R.id.origin_tv);
        TextView descriptionView = findViewById(R.id.description_tv);
        TextView ingredientsView = findViewById(R.id.ingredients_tv);

        //set the text for the String text views:
        mainNameView.setText(sandwich.getMainName());
        originView.setText(sandwich.getPlaceOfOrigin());
        descriptionView.setText(sandwich.getDescription());


        //we make a string lists for 'also known as' and 'ingredients', and assign the values to them using the object sandwich:
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        List<String> ingredientsList = sandwich.getIngredients();

        //check if the list is empty or not, if yes, then set the text to nothing:
        if(alsoKnownAsList.size()==0){
            alsoKnownAsView.setText(" ");
        }else{
            //here we use the class StringBuilder to handle the string that comes from the list:
            StringBuilder AKAS = new StringBuilder();

            //here we use the AKAS object to store the values of the list inside the string:
            for (String AKA : alsoKnownAsList) {
                AKAS.append("  -").append(AKA).append("\n");
            }

            //we delete the last new line to make the string looks nicer:
            AKAS.deleteCharAt(AKAS.lastIndexOf("\n"));


            //set the text to the text views:
            alsoKnownAsView.setText(AKAS);
        }


        //same as the previous if condition, but for the ingredients:
        if(ingredientsList.size()==0){
            ingredientsView.setText(" ");
        }else{
            StringBuilder ingrds = new StringBuilder();
            for (String ingrd : ingredientsList) {
                ingrds.append("  -").append(ingrd).append("\n");
            }
            ingrds.deleteCharAt(ingrds.lastIndexOf("\n"));

            ingredientsView.setText(ingrds);
        }





    }
}
