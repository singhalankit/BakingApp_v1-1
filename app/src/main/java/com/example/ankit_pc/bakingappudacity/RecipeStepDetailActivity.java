package com.example.ankit_pc.bakingappudacity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ANKIT_PC on 07-03-2018.
 */

public class RecipeStepDetailActivity extends AppCompatActivity {

    private Recipe recipe;
    private RecipeStep step;
    private RecipeStep prevStep;
    private RecipeStep nextStep;
    private ArrayList<Recipe> recipes;
    private RecipeStepDetailFragment fragment;

    @BindView(R.id.previousButton)
    Button prevButton;
    @BindView(R.id.nextButton) Button nextButton;
    Long currentPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_step_detail_activity);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (savedInstanceState == null) {
            if (intent.hasExtra(RecipeStepListActivity.TAG_RECIPE))
                recipe = intent.getParcelableExtra(RecipeStepListActivity.TAG_RECIPE);
            if (intent.hasExtra(RecipesMainFragment.TAG_RECIPES))
                recipes = intent.getParcelableArrayListExtra(RecipesMainFragment.TAG_RECIPES);
            if (intent.hasExtra(RecipeStepDetailFragment.ARG_STEP))
                step = getIntent().getParcelableExtra(RecipeStepDetailFragment.ARG_STEP);
        } else {
            step = savedInstanceState.getParcelable(RecipeStepDetailFragment.ARG_STEP);
            recipes = savedInstanceState.getParcelableArrayList(RecipesMainFragment.TAG_RECIPES);
            recipe = savedInstanceState.getParcelable(RecipeStepListActivity.TAG_RECIPE);
        }

        checkPrevNext();
    }

    private void startFullscreen(){
        Intent intent = new Intent(this, FullscreenPlayerActivity.class);
        intent.putExtra(RecipeStepListActivity.TAG_RECIPE, recipe);
        intent.putExtra(RecipeStepDetailFragment.ARG_STEP, step);
        intent.putExtra("current",currentPosition);
        intent.putParcelableArrayListExtra(RecipesMainFragment.TAG_RECIPES, recipes);
        startActivity(intent);
    }

    private void changeFragment(){
        Bundle arguments = new Bundle();
        arguments.putParcelable(RecipeStepDetailFragment.ARG_STEP, step);
        fragment = new RecipeStepDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_step_detail_container, fragment)
                .commit();
    }

    private void checkPrevNext(){
        if(recipe != null) {
            RecipeStep[] steps = recipe.getSteps();
            int length = steps.length;

            prevButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);

            if (step.getId().equals(steps[0].getId())) {
                prevButton.setVisibility(View.GONE);
                nextStep = steps[1];
            } else if (step.getId().equals(steps[length - 1].getId())) {
                nextButton.setVisibility(View.GONE);
                prevStep = steps[length - 2];
            } else {
                for (int i = 1; i < (length - 1); i++) {
                    if (step.getId().equals(steps[i].getId())) {
                        prevStep = steps[i - 1];
                        nextStep = steps[i + 1];
                    }
                }
            }
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !step.getVideoURL().isEmpty()){
            startFullscreen();
        } else {
            changeFragment();
        }
    }

    @OnClick(R.id.previousButton)
    void previousStep(View view){
        step = prevStep;
        ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
        checkPrevNext();
    }

    @OnClick(R.id.nextButton)
    void nextStep(View view){
        step = nextStep;
        ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
        checkPrevNext();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        currentPosition = ExoPlayerVideoHandler.getInstance().getPlayer().getCurrentPosition();
        outState.putParcelable(RecipeStepListActivity.TAG_RECIPE, recipe);
        outState.putParcelableArrayList(RecipesMainFragment.TAG_RECIPES, recipes);
        outState.putParcelable(RecipeStepDetailFragment.ARG_STEP, step);
        outState.putLong("current",currentPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        step = savedInstanceState.getParcelable(RecipeStepDetailFragment.ARG_STEP);
        recipes = savedInstanceState.getParcelableArrayList(RecipesMainFragment.TAG_RECIPES);
        recipe = savedInstanceState.getParcelable(RecipeStepListActivity.TAG_RECIPE);
        currentPosition = savedInstanceState.getLong("current");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.putExtra(RecipeStepListActivity.TAG_RECIPE, recipe);
                upIntent.putParcelableArrayListExtra(RecipesMainFragment.TAG_RECIPES, recipes);
                NavUtils.navigateUpTo(this, upIntent);
                ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }




}
