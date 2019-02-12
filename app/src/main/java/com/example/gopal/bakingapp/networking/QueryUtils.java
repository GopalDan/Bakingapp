package com.example.gopal.bakingapp.networking;

/**
 * Created by Gopal on 2/12/2019.
 */

import android.graphics.Movie;
import android.text.TextUtils;
import android.util.Log;

import com.example.gopal.bakingapp.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class with methods to help perform the HTTP request and
 * parse the response.
 */
public final class QueryUtils {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();


    /**
     * Query TMDb database and return a list of  {@link Movie} .
     */
    public static Recipe fetchRecipe(String requestUrl, int selectedId) {
        Log.e(LOG_TAG, "Fetching is called: ");
      /*  try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create a list of movie
        Recipe recipe = extractFeatureFromJson(jsonResponse, selectedId);

        // Return the movie list
        return recipe;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                // Toast.makeText(,"Http Networking Problem " + urlConnection.getResponseCode(),Toast.LENGTH_SHORT).show();
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of  {@link Movie}  by parsing out json.
     */
    private static Recipe extractFeatureFromJson(String jsonString, int selectedId) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            JSONArray root = new JSONArray(jsonString);
            for (int i = 0; i < root.length(); i++) {
                JSONObject singleRecipe = root.getJSONObject(i);
                int recipeId = singleRecipe.optInt("id");

                if (recipeId == selectedId) {
                    List<List<String>> ingredientsList = new ArrayList<>();
                    List<List<String>> instructionsList = new ArrayList<>();

                    String recipeName = singleRecipe.optString("name");
                    JSONArray ingredientsArray = singleRecipe.getJSONArray("ingredients");
                    JSONArray instructionsArray = singleRecipe.getJSONArray("steps");

                    // Ingredints list
                    for (int j = 0; j < ingredientsArray.length(); j++) {
                        // single ingredient list should be here as each time a list should be created to store the single ingredient
                        List<String> singleIngredientList = new ArrayList<>();

                        JSONObject singleIngredient = ingredientsArray.getJSONObject(j);
                        String quantity = singleIngredient.optString("quantity");
                        String measure = singleIngredient.optString("measure");
                        String ingredient = singleIngredient.optString("ingredient");
                        singleIngredientList.add(quantity);
                        singleIngredientList.add(measure);
                        singleIngredientList.add(ingredient);
                        ingredientsList.add(singleIngredientList);
                    }
                    // Instructions list
                    for (int k = 0; k < instructionsArray.length(); k++) {
                        // single instruction list should be here as each time a list should be created to store the single ingredient
                        List<String> singleInstructionList = new ArrayList<>();

                        JSONObject singleInstruction = instructionsArray.getJSONObject(k);
                        String id = singleInstruction.optString("id");
                        String shortDescription = singleInstruction.optString("shortDescription");
                        String description = singleInstruction.optString("description");
                        String videURL = singleInstruction.optString("videoURL");
                        String thumbnailURL = singleInstruction.optString("thumbnailURL");
                        singleInstructionList.add(id);
                        singleInstructionList.add(shortDescription);
                        singleInstructionList.add(description);
                        singleInstructionList.add(videURL);
                        singleInstructionList.add(thumbnailURL);
                        instructionsList.add(singleInstructionList);
                    }


                    return new Recipe(recipeId, recipeName, ingredientsList, instructionsList);
                }
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return null;
    }
}
