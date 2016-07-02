package com.hobbygaze.maverick.hobbygaze.fragments;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hobbygaze.maverick.hobbygaze.FragmentUtils;
import com.hobbygaze.maverick.hobbygaze.R;
import com.hobbygaze.maverick.hobbygaze.oldfiles.PopularPlacesActivity;
import com.hobbygaze.maverick.hobbygaze.oldfiles.PopularPlacesWebActivity;

/**
 * Created by abhishek on 12/7/15.
 */
public class PopularPlacesFragment  extends Fragment {
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
    Context context;
    static double lat1=0;
    static double long1=0;


    public static PopularPlacesFragment newInstance(double latitude,double longitude) {
        getDataFromBaseActivity(latitude,longitude);
        return new PopularPlacesFragment();
    }

    public static void getDataFromBaseActivity(double lat,double longit){

        lat1=lat;
        long1=longit;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_popular_places, container, false);

        t1=(Button)rootView.findViewById(R.id.b1);
        t2=(Button)rootView.findViewById(R.id.b2);
        t3=(Button)rootView.findViewById(R.id.b3);
        t4=(Button)rootView.findViewById(R.id.b4);
        t5=(Button)rootView.findViewById(R.id.b5);
        t6=(Button)rootView.findViewById(R.id.b6);
        t7=(Button)rootView.findViewById(R.id.b7);
        t8=(Button)rootView.findViewById(R.id.b8);
        t9=(Button)rootView.findViewById(R.id.b9);
        t10=(Button)rootView.findViewById(R.id.b10);
        t11=(Button)rootView.findViewById(R.id.b11);


        context = rootView.getContext();
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                        SearchResultsFragment.newInstance("Yoga", "Kormangala", lat1, long1), FragmentTransaction.TRANSIT_FRAGMENT_FADE);



            }
        });


        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                        SearchResultsFragment.newInstance("Music", "Malleshwaram", lat1, long1), FragmentTransaction.TRANSIT_FRAGMENT_FADE);



            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                        SearchResultsFragment.newInstance("Fitness", "Kormangala", lat1, long1), FragmentTransaction.TRANSIT_FRAGMENT_FADE);



            }
        });

        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                        SearchResultsFragment.newInstance("Dance", "Jayanagar", lat1, long1), FragmentTransaction.TRANSIT_FRAGMENT_FADE);



            }
        });

        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                        SearchResultsFragment.newInstance("Bowling", "Marathalli", lat1, long1), FragmentTransaction.TRANSIT_FRAGMENT_FADE);



            }
        });

        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                        SearchResultsFragment.newInstance("Sports", "Whitefield", lat1, long1), FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            }
        });

        t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                        SearchResultsFragment.newInstance("Tennis", "Whitefield", lat1, long1), FragmentTransaction.TRANSIT_FRAGMENT_FADE);



            }
        });

        t8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                        SearchResultsFragment.newInstance("Bowling", "Bellandur", lat1, long1), FragmentTransaction.TRANSIT_FRAGMENT_FADE);


            }
        });

        t9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                        SearchResultsFragment.newInstance("Gyms", "Kumaraswamy", lat1, long1), FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            }
        });

        t10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                        SearchResultsFragment.newInstance("Yoga", "Indiranagar", lat1, long1), FragmentTransaction.TRANSIT_FRAGMENT_FADE);



            }
        });

        t11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                        SearchResultsFragment.newInstance("Laser", "Jayanagar", lat1, long1), FragmentTransaction.TRANSIT_FRAGMENT_FADE);


            }
        });






        return rootView;
    }




}
