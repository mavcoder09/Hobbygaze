package com.hobbygaze.maverick.hobbygaze.oldfiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hobbygaze.maverick.hobbygaze.R;

import xdroid.toaster.Toaster;

/**
 * Created by abhishek on 12/1/15.
 */
public class SendFeedbackActivity extends AppCompatActivity {
    TextView email;
    EditText sub;
    EditText msg;
    Button mail;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        getSupportActionBar().setTitle(" ");
       email=(TextView)findViewById(R.id.useremail);
        sub=(EditText)findViewById(R.id.subject);
        msg=(EditText)findViewById(R.id.body);
        mail=(Button)findViewById(R.id.send_email);
//email.setText("hello@hobbygaze.com");
        email.setError(null);
        sub.setError(null);
        msg.setError(null);




        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String emailid = email.getText().toString().trim();
                String subject=sub.getText().toString().trim();
                String message=msg.getText().toString().trim();
                if (!subject.isEmpty()&& !message.isEmpty()){


                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"hello@hobbygaze.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, subject);
                i.putExtra(Intent.EXTRA_TEXT   , message);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                    finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SendFeedbackActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

                }



                else
                    Toaster.toastLong("Please enter all fields");
            }
        });




    }

}
