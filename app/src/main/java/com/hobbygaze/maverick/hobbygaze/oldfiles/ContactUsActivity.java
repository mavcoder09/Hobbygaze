package com.hobbygaze.maverick.hobbygaze.oldfiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.hobbygaze.maverick.hobbygaze.R;

/**
 * Created by abhishek on 12/1/15.
 */
public class ContactUsActivity extends AppCompatActivity {
Button b;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_us);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        getSupportActionBar().setTitle(" ");


        b=(Button)findViewById(R.id.btn_getlisted);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Option1 clicked");
                Intent intent = new Intent(ContactUsActivity.this,
                        GetListedActivity.class);
                startActivity(intent);
                finish();


            }
        });


    }


}




