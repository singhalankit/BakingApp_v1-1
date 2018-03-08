package com.example.ankit_pc.bakingappudacity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import butterknife.BindView;

/**
 * Created by ANKIT_PC on 08-03-2018.
 */

public class IngredientsList extends AppCompatActivity {
    Context mContext;
    private Recipe recipe;

    @BindView(R.id.widget_ingredients_RecyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_list);

        Intent intent = getIntent();
        if (intent.hasExtra("recipe")) {
            recipe = intent.getParcelableExtra("recipe");
            setupRecyclerView(recyclerView);

        }

    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        Log.v("Entered in setRecycler",Integer.toString(recipe.getSteps().length));
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(new WidgetIngredientsAdapter(recipe));
    }

}