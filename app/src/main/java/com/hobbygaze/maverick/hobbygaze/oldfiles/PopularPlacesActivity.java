package com.hobbygaze.maverick.hobbygaze.oldfiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.hobbygaze.maverick.hobbygaze.R;

/**
 * Created by abhishek on 11/30/15.
 */
public class PopularPlacesActivity extends AppCompatActivity {
    Button t1;
    Button t2;
    Button t3;
    Button t4;
    Button t5;
    Button t6;
    Button t7;
    Button t8;
    Button t9;
    Button t10;
    Button t11;
    Button t12;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_places);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        getSupportActionBar().setTitle(" ");

        t1=(Button)findViewById(R.id.b1);
        t2=(Button)findViewById(R.id.b2);
        t3=(Button)findViewById(R.id.b3);
        t4=(Button)findViewById(R.id.b4);
        t5=(Button)findViewById(R.id.b5);
        t6=(Button)findViewById(R.id.b6);
        t7=(Button)findViewById(R.id.b7);
        t8=(Button)findViewById(R.id.b8);
        t9=(Button)findViewById(R.id.b9);
        t10=(Button)findViewById(R.id.b10);
        t11=(Button)findViewById(R.id.b11);
        t12=(Button)findViewById(R.id.b12);


       t1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            System.out.println("Option1 clicked");
               Intent intent = new Intent(PopularPlacesActivity.this,
                       PopularPlacesWebActivity.class);
               intent.putExtra("url", "http://www.hobbygaze.com/listings/?search_keywords=Yoga&search_location=Koramangala%2C+Bengaluru%2C+Karnataka%2C+India&search_categories%5B%5D=&use_search_radius=on&search_radius=5&search_lat=12.9279232&search_lng=77.62710779999998&search_region=&search_context=20&v=269a118bb62e");
               startActivity(intent);
                //finish();


           }
       });


        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PopularPlacesActivity.this,
                        PopularPlacesWebActivity.class);
                intent.putExtra("url", "http://www.hobbygaze.com/listings/?search_keywords=Music+&search_location=Malleshwara%2C+Bengaluru%2C+Karnataka&search_categories%5B%5D=&use_search_radius=on&search_radius=5&search_lat=0&search_lng=0&search_region=&search_context=20&v=269a118bb62e");
                startActivity(intent);


            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PopularPlacesActivity.this,
                        PopularPlacesWebActivity.class);
                intent.putExtra("url", "http://www.hobbygaze.com/listings/?search_keywords=Fitness+studio&search_location=Koramangala%2C+Bengaluru%2C+Karnataka%2C+India&search_categories%5B%5D=&use_search_radius=on&search_radius=5&search_lat=12.9279232&search_lng=77.62710779999998&search_region=&search_context=20&v=269a118bb62e");
                startActivity(intent);


            }
        });

        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PopularPlacesActivity.this,
                        PopularPlacesWebActivity.class);
                intent.putExtra("url", "http://www.hobbygaze.com/listings/?search_keywords=Dance&search_location=Jayanagar%2C+Bengaluru%2C+Karnataka&search_categories%5B%5D=&use_search_radius=on&search_radius=5&search_lat=0&search_lng=0&search_region=&search_context=20&v=269a118bb62e");
                startActivity(intent);


            }
        });

        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PopularPlacesActivity.this,
                        PopularPlacesWebActivity.class);
                intent.putExtra("url", "http://www.hobbygaze.com/listings/?search_keywords=Bowling&search_location=Marathahalli%2C+Bengaluru%2C+Karnataka&search_categories%5B%5D=&use_search_radius=on&search_radius=5&search_lat=0&search_lng=0&search_region=&search_context=20&v=269a118bb62e");
                startActivity(intent);


            }
        });

        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PopularPlacesActivity.this,
                        PopularPlacesWebActivity.class);
                intent.putExtra("url", "http://www.hobbygaze.com/listings/?search_keywords=Sports+&search_location=Whitefield%2C+Bengaluru%2C+Karnataka&search_categories%5B%5D=&use_search_radius=on&search_radius=5&search_lat=0&search_lng=0&search_region=&search_context=20&v=269a118bb62e");

                startActivity(intent);

            }
        });

        t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PopularPlacesActivity.this,
                        PopularPlacesWebActivity.class);
                intent.putExtra("url", "http://www.hobbygaze.com/listings/?search_keywords=Tennis&search_location=Marathahalli%2C+Bengaluru%2C+Karnataka%2C+India&search_categories%5B%5D=&use_search_radius=on&search_radius=5&search_lat=12.9591722&search_lng=77.69741899999997&search_region=&search_context=20&v=269a118bb62e");
                startActivity(intent);


            }
        });

        t8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PopularPlacesActivity.this,
                        PopularPlacesWebActivity.class);
                intent.putExtra("url", "http://www.hobbygaze.com/listings/?search_keywords=Bowling&search_location=Bellandur%2C+Bengaluru%2C+Karnataka&search_categories%5B%5D=&use_search_radius=on&search_radius=5&search_lat=0&search_lng=0&search_region=&search_context=20&v=269a118bb62e");

                startActivity(intent);

            }
        });

        t9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PopularPlacesActivity.this,
                        PopularPlacesWebActivity.class);
                intent.putExtra("url", "http://www.hobbygaze.com/listings/?search_keywords=Gym&search_location=Kumaraswamy+Layout%2C+Bengaluru%2C+Karnataka&search_categories%5B%5D=&use_search_radius=on&search_radius=5&search_lat=0&search_lng=0&search_region=&search_context=20&v=269a118bb62e");

                startActivity(intent);
            }
        });

        t10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PopularPlacesActivity.this,
                        PopularPlacesWebActivity.class);
                intent.putExtra("url", "http://www.hobbygaze.com/listings/?search_keywords=Yoga+&search_location=Indiranagar%2C+Bengaluru%2C+Karnataka&search_categories%5B%5D=&use_search_radius=on&search_radius=5&search_lat=0&search_lng=0&search_region=&search_context=20&v=269a118bb62e");
                startActivity(intent);


            }
        });

        t11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PopularPlacesActivity.this,
                        PopularPlacesWebActivity.class);
                intent.putExtra("url", "http://www.hobbygaze.com/listings/?search_keywords=Laser+Tagging&search_location=Jayanagar%2C+Bengaluru%2C+Karnataka&search_categories%5B%5D=&use_search_radius=on&search_radius=5&search_lat=0&search_lng=0&search_region=&search_context=20&v=269a118bb62e");

                startActivity(intent);

            }
        });

        t12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PopularPlacesActivity.this,
                        PopularPlacesWebActivity.class);
                intent.putExtra("url", "http://www.hobbygaze.com/listing-category/Amusement-Park/?v=269a118bb62e");

                startActivity(intent);

            }
        });

















    }






}