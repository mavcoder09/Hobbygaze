package com.hobbygaze.maverick.hobbygaze.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hobbygaze.maverick.hobbygaze.R;

import xdroid.toaster.Toaster;
import android.support.v4.app.FragmentManager;
/**
 * Created by abhishek on 12/7/15.
 */
public class SendFeedBackFragment  extends Fragment {
    TextView email;
    EditText sub;
    EditText msg;
    Button mail;
    public static SendFeedBackFragment newInstance() {

        return new SendFeedBackFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_feedback, container, false);
       // email=(TextView)rootView.findViewById(R.id.useremail);
        sub=(EditText)rootView.findViewById(R.id.subject);
        msg=(EditText)rootView.findViewById(R.id.body);
        mail=(Button)rootView.findViewById(R.id.send_email);

//        email.setError(null);
        sub.setError(null);
        msg.setError(null);


        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            //    String emailid = email.getText().toString().trim();
                String subject = sub.getText().toString().trim();
                String message = msg.getText().toString().trim();
                if (!subject.isEmpty() && !message.isEmpty()) {


                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{"hello@hobbygaze.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, subject);
                    i.putExtra(Intent.EXTRA_TEXT, message);
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));

                        //FragmentManager fm = getActivity().getSupportFragmentManager();
                        //fm.popBackStack();
                       // finish();
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }

                } else
                    Toaster.toastLong("Please enter all fields");
            }
        });



        return rootView;
    }

    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }



}
