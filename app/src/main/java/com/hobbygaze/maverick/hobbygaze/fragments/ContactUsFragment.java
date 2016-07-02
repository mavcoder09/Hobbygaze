package com.hobbygaze.maverick.hobbygaze.fragments;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hobbygaze.maverick.hobbygaze.FragmentUtils;
import com.hobbygaze.maverick.hobbygaze.R;
import com.hobbygaze.maverick.hobbygaze.oldfiles.GetListedActivity;

/**
 * Created by abhishek on 12/7/15.
 */
public class ContactUsFragment  extends Fragment {
    //Button b;
    TextView call;
    Context context;
    ImageButton ib;
    ImageButton email;
    public static ContactUsFragment newInstance() {

        return new ContactUsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_contact_us, container, false);
        context = rootView.getContext();
       // b=(Button)rootView.findViewById(R.id.btn_getlisted);
        call=(TextView)rootView.findViewById(R.id.cal);
        ib=(ImageButton)rootView.findViewById(R.id.bcal);
        email=(ImageButton)rootView.findViewById(R.id.btnemail);
        /*
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Option1 clicked");
                Intent intent = new Intent(context,
                        GetListedActivity.class);
                startActivity(intent);
                //finish();


            }
        });
*/
        final String number = "tel:" +call.getText().toString().trim();

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("call button clicked");
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                startActivity(callIntent);


            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("email button clicked");
                FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                        SendFeedBackFragment.newInstance(), FragmentTransaction.TRANSIT_FRAGMENT_FADE);


            }
        });



        return rootView;
    }




}
