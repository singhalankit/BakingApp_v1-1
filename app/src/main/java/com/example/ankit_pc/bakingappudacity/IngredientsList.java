package com.example.ankit_pc.bakingappudacity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by ANKIT_PC on 08-03-2018.
 */

public class IngredientsList extends AppCompatActivity {
    Context mContext;
    private ArrayList<Recipe> recipes;
    int position;

    @BindView(R.id.widget_ingredients_RecyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_list);

        Intent intent = getIntent();
        if (intent.hasExtra("recipes")) {
            recipes = intent.getParcelableExtra("recipes");
            position = intent.getIntExtra("position",position);
            setupRecyclerView(recyclerView);

        }

    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        Log.v("Entered in setRecycler",Integer.toString(recipes.get(position).getSteps().length));
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(new WidgetIngredientsAdapter(recipes.get(position)));
    }

}