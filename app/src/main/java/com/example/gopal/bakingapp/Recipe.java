package com.example.gopal.bakingapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * Created by Gopal on 2/11/2019.
 */

/**
 * class represent a single recipe
 */
public class Recipe {
    private int id;
    private String name;
    private List<List<String>> ingredients;
    private List<List<String>> steps;
   private static final String JSON_FILE = "F:\\AndroidStudioProjects\\AdvAndApps\\6 - MediaPlaybacks\\Bakingapp\\app\\src\\main\\assets\\recipe.txt";

    public Recipe(int id, String name, List<List<String>> ingredients, List<List<String>> steps) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    /**
     * Gets a single recipe by its ID.
     * @param context The application context.
     * @param recipeID The sample ID.
     * @return The single recipe object
     */
    static Recipe getRecipeByID(Context context, int recipeID) {
        JsonReader reader;
        try {
            reader = readJSONFile(context);
            reader.beginArray();
            while (reader.hasNext()) {
                Recipe currentRecipe = readRecipe(reader);
                if (currentRecipe.getId() == recipeID) {
                    Log.e("Recipe", "Getting recipe");
                    reader.close();
                    return currentRecipe;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("Recipe", "Not getting recipe");
        return null;
    }

    /**
     * Method used for obtaining a single reipe from the JSON file.
     * @param reader The JSON reader object pointing a single sample JSON object.
     * @return The Recipe the JsonReader is pointing to.
     */
    private static Recipe readRecipe(JsonReader reader) {
        int id = -1;
        String name = null;
        List<List<String>> ingredients = null;
        List<List<String>> steps = null;

        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String string = reader.nextName();
                switch (string) {
                    case "id":
                        id = reader.nextInt();
                        break;
                    case "name":
                        name = reader.nextString();
                        break;
                    case "ingredients":
                        ingredients = readIngredientsArray(reader);
                        break;
                    case "steps":
                        steps = readInstructionsArray(reader);
                        break;
                    default:
                        break;
                }
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Recipe(id,name ,ingredients, steps);
    }

    // Reading ingredients array
    public static List<List<String>> readIngredientsArray(JsonReader reader) throws IOException{
        List<List<String>> ingredients = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()){
            ingredients.add(readSingleIngredient(reader));
        }
        reader.endArray();
        return ingredients;
    }

    // Reading single ingredient
    public static List<String> readSingleIngredient(JsonReader reader) throws IOException{
        List<String> singleIngredient = new ArrayList<>();
        String quantity = null;
        String measure = null;
        String ingredient = null;
        reader.beginObject();
        while (reader.hasNext()){
            String name = reader.nextName();
            switch (name){
                case "quantity":
                    quantity = reader.nextString();
                    singleIngredient.add(quantity);break;
                case "measure":
                    measure = reader.nextString();
                    singleIngredient.add(measure);break;
                case "ingredient":
                   ingredient  =reader.nextString();
                    singleIngredient.add(ingredient);
                    break;
                default: reader.skipValue();
            }
        }
        reader.endObject();
        return singleIngredient;
    }
    // Reading instructions array
    public static List<List<String>> readInstructionsArray(JsonReader reader) throws IOException{
        List<List<String>> instructions = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()){
            instructions.add(readSingleInstruction(reader));
        }
        reader.endArray();
        return instructions;
    }
    // Reading single instruction
    public static List<String> readSingleInstruction(JsonReader reader) throws IOException{
        List<String> singleInstruction = new ArrayList<>();
        String id = null;
        String shortDescription = null;
        String description = null;
        String videoUrl = null;
        String thumbnailUrl = null;
        reader.beginObject();
        while (reader.hasNext()){
            String name = reader.nextName();
            switch (name){
                case "id":
                    id = reader.nextString();
                    singleInstruction.add(id);break;
                case "shortDescription":
                    shortDescription = reader.nextString();
                    singleInstruction.add(shortDescription);break;
                case "description":
                    description  =reader.nextString();
                    singleInstruction.add(description);
                    break;
                case "videoURL":
                    videoUrl = reader.nextString();
                    singleInstruction.add(videoUrl);break;

                case "thumbnailURL":
                    thumbnailUrl = reader.nextString();
                    singleInstruction.add(thumbnailUrl);break;
                default: reader.skipValue();
            }
        }
        reader.endObject();
        return singleInstruction;
    }

    /**
     * Method for creating a JsonReader object that points to the JSON array of recipes.
     * @param context The application context.
     * @return The JsonReader object pointing to the JSON array of recipes.
     * @throws IOException Exception thrown if the sample file can't be found.
     */
    private static JsonReader readJSONFile(Context context) throws IOException {
        InputStream is = new FileInputStream(JSON_FILE);
        JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));

        return reader;
       /*
       AssetManager assetManager = context.getAssets();
        String uri = null;

        try {
            for (String asset : assetManager.list("")) {
                if (asset.endsWith(".exolist.json")) {
                    uri = "asset:///" + asset;
                }
            }
        } catch (IOException e) {
            Toast.makeText(context, R.string.sample_list_load_error, Toast.LENGTH_LONG)
                    .show();
        }

        String userAgent = Util.getUserAgent(context, "ClassicalMusicQuiz");
        DataSource dataSource = new DefaultDataSource(context, null, userAgent, false);
        DataSpec dataSpec = new DataSpec(Uri.parse(uri));
        InputStream inputStream = new DataSourceInputStream(dataSource, dataSpec);

        JsonReader reader = null;
        try {
            reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
        } finally {
            Util.closeQuietly(dataSource);
        }
        */

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<List<String>> getIngredients() {
        return ingredients;
    }

    public List<List<String>> getSteps() {
        return steps;
    }

}
