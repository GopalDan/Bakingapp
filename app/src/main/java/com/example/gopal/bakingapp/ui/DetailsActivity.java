package com.example.gopal.bakingapp.ui;

/**
 * Created by Gopal on 2/12/2019.
 */


import android.app.LoaderManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gopal.bakingapp.R;
import com.example.gopal.bakingapp.Recipe;
import com.example.gopal.bakingapp.RecipeAdapter;
import com.example.gopal.bakingapp.Step;
import com.example.gopal.bakingapp.networking.CustomLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Recipe> {
    private final String TAG = DetailsActivity.class.getSimpleName();
    private List<List<String>> ingredients;
    private List<List<String>> steps;
    private List<Step> instructions;
    private int mSelectedId;
    private RecipeAdapter mAdapter;
    private final String mUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private final int LOADER_ID = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mSelectedId = getIntent().getIntExtra(MainActivity.CARD_VIEW_ID, 1);
        instructions = new ArrayList<>();

        mAdapter = new RecipeAdapter(this,new ArrayList<Step>());
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(mAdapter);
        // when a list item is clicked go to InstructionActivity for showing video & brief description
        // Using Serializable to pass the list of custom object i.e. List<Step>
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(DetailsActivity.this, InstructionActivity.class);
                intent.putExtra("list",(Serializable) instructions);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });

        // start the loader
        getLoaderManager().initLoader(LOADER_ID,null,this);
    }

    /**
     * Showing ingredients & steps of selected Recipe in MainActivity
     * @param singleRecipe The Recipe that is selected
     */
    public void showIngredient(Recipe singleRecipe) {
        //Log.e(TAG, "ID is" + singleRecipe.getId());
        if (singleRecipe != null) {
            String recipeName = singleRecipe.getName();
            ingredients = singleRecipe.getIngredients();
            steps = singleRecipe.getSteps();

            // setting title in action bar
            setTitle(recipeName);
            for(int j=0;j<steps.size();j++){
                List<String> singleInstruction = steps.get(j);
                String stepNumber = singleInstruction.get(0);
                String stepInfo = singleInstruction.get(1);
                String description = singleInstruction.get(2);
                String videoUrl = singleInstruction.get(3);
                String thumbnailUrl = singleInstruction.get(4);

                instructions.add(new Step(stepNumber,stepInfo,description, videoUrl, thumbnailUrl));
            }

            TextView txtView = findViewById(R.id.ingredient_tv);
            txtView.setText("");
            int length = ingredients.size();
            Log.e(TAG, "Number of ingredients are: " + length);

            for (int i = 0; i <length; i++) {
                List<String> singleIngredient = ingredients.get(i);
                String quantity = singleIngredient.get(0);
                String measure = singleIngredient.get(1);
                String ingredient = singleIngredient.get(2);
                String mixUp = getString(R.string.filled_bullet) + ingredient +"(" + quantity + " " +  measure + ")";
                txtView.append(mixUp);
                txtView.append("\n");
            }
        }
    }

    @Override
    public android.content.Loader<Recipe> onCreateLoader(int i, Bundle bundle) {
        return new CustomLoader(this, mUrl, mSelectedId);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Recipe> loader, Recipe recipe) {
        mAdapter.clear();
        showIngredient(recipe);
        mAdapter.addAll(instructions);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Recipe> loader) {

    }
}
