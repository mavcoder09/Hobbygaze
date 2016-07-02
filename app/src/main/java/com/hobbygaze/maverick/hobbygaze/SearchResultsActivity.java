package com.hobbygaze.maverick.hobbygaze;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;

import com.hobbygaze.maverick.hobbygaze.fragments.SearchResultsFragment;

/**
 * Created by maverick on 11/5/2015.
 */
public class SearchResultsActivity  extends AppCompatActivity {

    Button btn_sol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        getSupportActionBar().setTitle(" ");

       // FragmentUtils.addFragment(getSupportFragmentManager(), R.id.mainContainer,
         //       SearchResultsFragment.newInstance(), FragmentTransaction.TRANSIT_FRAGMENT_FADE);





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}