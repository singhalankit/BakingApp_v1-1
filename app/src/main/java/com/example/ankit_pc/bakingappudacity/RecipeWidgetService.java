package com.example.ankit_pc.bakingappudacity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ANKIT_PC on 08-03-2018.
 */

public class RecipeWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoteViewsFactory(this.getApplicationContext());
    }
}


class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    private Context mContext;
    ArrayList<Recipe> mRecipes = new ArrayList<Recipe>();
    RecipeRemoteViewsFactory(Context context){
        this.mContext = context;
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        FetchWidgetRecipeData recipeData = new FetchWidgetRecipeData();
        recipeData.execute();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mRecipes != null)
            return mRecipes.size();
        else
            return 0;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_list);
        Recipe recipe = mRecipes.get(i);
        rv.setTextViewText(R.id.recipe_TextView,recipe.getName());

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }



    public class FetchWidgetRecipeData extends AsyncTask<Void,Void,ArrayList<Recipe>>
    {


        @Override

        protected ArrayList<Recipe> doInBackground(Void... voids) {
            ArrayList<Recipe> RecipeList = new ArrayList<Recipe>();
            try {
                String json = NetworkUtils.getResponseFromHttpUrl();
                RecipeList = RecipeJsonUtils.getRecipesFromJson(json);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }

            return RecipeList;
        }


        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {
            super.onPostExecute(recipes);
            mRecipes=recipes;
        }
    }




}
