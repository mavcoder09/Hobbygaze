package com.hobbygaze.maverick.hobbygaze.fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.hobbygaze.maverick.hobbygaze.FragmentUtils;
import com.hobbygaze.maverick.hobbygaze.R;
import com.hobbygaze.maverick.hobbygaze.SearchResultsActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by abhishek on 12/7/15.
 */
public class HomeFragment  extends Fragment {
    GPSTracker gps;
    double latitude=0;
    double longitude=0;
    Context context;
    String result;
    EditText loc;
    Button btnArchery;
    Button btnBowling;
    Button btnDance;
    Button btnFitness;
    Button btnJustForfun;
    Button btnMusic;
    Button btnYoga;
    Button btnsports;
    Button btnlearning;
   keyWordClicked mCallback;

    public interface keyWordClicked{
        public void sendText(String keywords,double lat,double longit);
    }




    public class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    result = bundle.getString("address");
                    break;
                default:
                    result = null;
            }
            // replace by what you need to do
            System.out.println(result);
            loc.setText(result);

        }
    }
        public static HomeFragment newInstance() {

        return new HomeFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (keyWordClicked) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement keyWordClicked");
        }


    }


    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }

    public void sendtoSearchFragment(String keywords,double lat,double longit){
        mCallback.sendText(keywords, lat, longit);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        final EditText keyword=(EditText)rootView.findViewById(R.id.keyword);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
       // InputMethodManager imm = (InputMethodManager)this.getActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(keyword.getWindowToken(), 0);
        context = rootView.getContext();
        loc=(EditText)rootView.findViewById(R.id.location);
        loc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                // System.out.println("test1");
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (loc.getRight() - loc.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        //loc.setText(result);
                        gps = new GPSTracker(context);

                        // check if GPS enabled
                        if (gps.canGetLocation()) {

                            latitude = gps.getLatitude();
                            longitude = gps.getLongitude();
                            System.out.println(latitude);
                            System.out.println(longitude);
                            getAddressFromLocation(context, latitude, longitude, new GeocoderHandler());

                            //   float[] results = new float[3];
                            // Location l = new Location("Point A");
                            //l.distanceBetween(latitude, longitude, 12.914541, 77.564722, results);
                            //float a=results[0]/1000;
                            //double newKB = Math.round(a*100.0)/100.0;
                            //System.out.println("Distance=" + newKB);


                            // \n is for new line
                            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                        } else {
                            // can't get location
                            // GPS or Network is not enabled
                            // Ask user to enable GPS/network in settings
                            gps.showSettingsAlert();
                        }


                        //    System.out.println("test2");
                        return true;
                    }
                }
                return false;
            }
        });



        keyword.setError(null);

        Button b = (Button) rootView.findViewById(R.id.search);


        btnArchery =(Button)rootView.findViewById(R.id.btnArchery);
        btnBowling=(Button)rootView.findViewById(R.id.btnBowling);
        btnDance=(Button)rootView.findViewById(R.id.btnDance);
        btnFitness=(Button)rootView.findViewById(R.id.btnFitness);
        btnJustForfun=(Button)rootView.findViewById(R.id.btnJustForfun);
        btnMusic=(Button)rootView.findViewById(R.id.btnMusic);
        btnYoga=(Button)rootView.findViewById(R.id.btnYoga);
        btnsports=(Button)rootView.findViewById(R.id.btnsports);
        btnlearning=(Button)rootView.findViewById(R.id.btnlearning);


        btnArchery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                        SearchResultsFragment.newInstance("Archery","bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            }
        });

        btnBowling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                        SearchResultsFragment.newInstance("Bowling","bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            }
        });

        btnDance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                        SearchResultsFragment.newInstance("Dance","bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            }
        });

        btnFitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                        SearchResultsFragment.newInstance("Fitness","bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            }
        });

        btnJustForfun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                        SearchResultsFragment.newInstance("Just For Fun","bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            }
        });

        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                        SearchResultsFragment.newInstance("Music","bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            }
        });

        btnYoga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                        SearchResultsFragment.newInstance("Yoga","bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            }
        });


        btnsports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                        SearchResultsFragment.newInstance("Sports","bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            }
        });

        btnlearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                        SearchResultsFragment.newInstance("Learning","bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            }
        });


        b.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                hide_keyboard(getActivity());
                String keywords = keyword.getText().toString().trim();
                String location=loc.getText().toString().trim();
                if (!keywords.isEmpty()) {
                    // Intent ii = new Intent(context, SearchResultsActivity.class);
                    //ii.putExtra("keyword", keywords);
                    //ii.putExtra("lat", String.valueOf(latitude));
                    //ii.putExtra("long", String.valueOf(longitude));
                    //startActivity(ii);
                    //sendtoSearchFragment(keywords,latitude,longitude);

                    FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                            SearchResultsFragment.newInstance(keywords,location, latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    // Intent ii = new Intent(MainActivity.this, SearchResultsActivity.class);
                    // ii.putExtra("keyword", keywords);
                    //ii.putExtra("lat", String.valueOf(latitude));
                    //ii.putExtra("long", String.valueOf(longitude));
                    //startActivity(ii);
                } else {
                    // Prompt user to enter credentials
                    keyword.setError("Please enter keyword to continue");
                }

                //finish();
            }
        });



        return rootView;
    }

    public static void getAddressFromLocation(final Context context,final double latitude,final double longitude, final Handler handler) {
        System.out.println("location called function");
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> list = geocoder.getFromLocation(
                            latitude, longitude, 1);
                    if (list != null && list.size() > 0) {
                        Address address = list.get(0);
                        // sending back first address line and locality
                        result = address.getAddressLine(0) + ", " + address.getSubLocality() + ", " +address.getLocality() ;
                    }
                } catch (IOException e) {
                    Log.e("Maps", "Impossible to connect to Geocoder", e);
                } finally {
                    Message msg = Message.obtain();
                    msg.setTarget(handler);
                    if (result != null) {
                        msg.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", result);
                        msg.setData(bundle);
                    } else
                        msg.what = 0;
                    msg.sendToTarget();
                }
            }
        };
        thread.start();
    }



  /*  public void sendHomeFragData(String keywords,double lat,double longit)
    {
        SearchResultsFragment srp = new SearchResultsFragment();


        Bundle args = new Bundle();
        args.putString("Keywords",keywords);
        args.putDouble("latitude", lat);
        args.putDouble("longitude", longit);
        //args.putInt("month", calender.get(Calendar.MONTH));
        //args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        srp.setArguments(args);

        // date.setCallBack(onDateChange);
        //date.show(getFragmentManager(), "Date Picker");
    }
*/


    public static void hide_keyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if(view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
