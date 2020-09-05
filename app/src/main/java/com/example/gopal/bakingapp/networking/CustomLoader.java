package com.example.gopal.bakingapp.networking;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.Movie;
import android.util.Log;

import com.example.gopal.bakingapp.Recipe;

import java.util.List;

/**
 * Created by Gopal on 2/12/2019.
 */


public class CustomLoader extends AsyncTaskLoader<Recipe> {
    String LOG_TAG = CustomLoader.class.getSimpleName();
    private String mUrl;
    private int mSelectedId;
    List<Movie> movies;
    public CustomLoader(Context context, String url, int selectedId) {
        super(context);
        mUrl = url;
        mSelectedId = selectedId;
    }

    @Override
    protected void onStartLoading() {
       /* //  Log.e(LOG_TAG,"onStartLoading is called: ");
        if (movies != null) {
            // Use cached data
            deliverResult(movies);
        } else {
            // We have no data, so kick off loading it
            forceLoad();
        }*/
        forceLoad();
    }

    @Override
    public Recipe loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        Log.e(LOG_TAG,"loadInBackground is called: ");
        // Perform the network request, parse the response, and extract a list of movies.
        Log.v("CustomLoader", "URL for data fetching- " + mUrl);
        return QueryUtils.fetchRecipe(mUrl, mSelectedId);
    }

    /*@Override
    public void deliverResult(List<Movie> data) {
        // We’ll save the data for later retrieval
        Log.v(LOG_TAG, "Caching  data is used- " + mUrl);

        movies = data;
        // We can do any pre-processing we want here
        // Just remember this is on the UI thread so nothing lengthy!
        super.deliverResult(data);
    }*/
}
