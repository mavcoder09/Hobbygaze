package com.hobbygaze.maverick.hobbygaze.oldfiles;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RatingBar;

import com.hobbygaze.maverick.hobbygaze.R;

//import com.ms.square.android.expandabletextview.ExpandableTextView;

/**
 * Created by maverick on 10/31/2015.
 */
public class ListingPlaceActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_places);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

      //  ExpandableTextView expTv1 = (ExpandableTextView)findViewById(R.id.expand_text_view);
        //expTv1.setText(getString(R.string.dummy_text1));

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.rgb(135, 206, 250), PorterDuff.Mode.SRC_ATOP);


    }




}
