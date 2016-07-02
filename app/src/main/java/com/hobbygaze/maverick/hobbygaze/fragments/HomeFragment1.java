package com.hobbygaze.maverick.hobbygaze.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.hobbygaze.maverick.hobbygaze.FragmentUtils;
import com.hobbygaze.maverick.hobbygaze.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import xdroid.toaster.Toaster;

/**
 * Created by abhishek on 4/13/16.
 */
public class HomeFragment1 extends Fragment {
    double latitude=0;
    double longitude=0;
    GPSTracker gps;
    GPSTracker gps1;
    Context context;
    String result;
    String result1;
    keyWordClicked1 mCallback;
    String loc_manual=null;
    int REQUEST_LOC=1;

    public interface keyWordClicked1{
        public void sendText(String keywords,double lat,double longit);
    }


    public class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    result1 = bundle.getString("address");
                    break;
                default:
                    result1 = null;
            }
            // replace by what you need to do
            System.out.println(result1);


        }
    }

    public static HomeFragment1 newInstance() {

        return new HomeFragment1();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (keyWordClicked1) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement keyWordClicked");
        }


    }
    public void passData(String result,double latitude,double longitude) {
        mCallback.sendText(result, latitude,longitude);
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.grid_main, container, false);
        context = rootView.getContext();
        GridView gridView = (GridView)rootView.findViewById(R.id.gridview);

        //FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
           checkPermissions();
        }
        else{
            getloc();
        }

        final com.getbase.floatingactionbutton.FloatingActionButton actionA = (com.getbase.floatingactionbutton.FloatingActionButton) rootView.findViewById(R.id.action_a);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);

                alert.setTitle("Enter any location or use GPS");
                alert.setMessage("Press cancel to use GPS");

                PopupWindow popWindow = new PopupWindow(context);
                final EditText input = new EditText(context);
                popWindow.setFocusable(true);
                popWindow.update();

                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        loc_manual=input.getText().toString();
                        System.out.println(input.getText().toString());
                        Toaster.toastLong("Location set to "+loc_manual);
                        result=input.getText().toString();
                        System.out.println("Result in home="+result);
                        passData(result,latitude,longitude);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }
        });

        final com.getbase.floatingactionbutton.FloatingActionButton actionB = (com.getbase.floatingactionbutton.FloatingActionButton) rootView.findViewById(R.id.action_b);
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable(context)) {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        checkPermissions();
                    }
                    if(result1!=null)
                    {
                        Toaster.toastLong("Location set to "+result1);
                        System.out.println("Result in home1="+result1);
                        //  Snackbar.make(view, "Location set to " + result, Snackbar.LENGTH_LONG)
                        //        .setAction("Action", null).show();
                    }

                    else {
                        gps1 = new GPSTracker(context);
                        if (gps1.canGetLocation()) {

                            latitude = gps1.getLatitude();
                            longitude = gps1.getLongitude();
                            System.out.println(latitude);
                            System.out.println(longitude);
                            //passData("keyword",latitude,longitude);
                            getAddressFromLocation(context, latitude, longitude, new GeocoderHandler());
                            if(result1!=null){
                                Toaster.toastLong("Location set to "+result1);
                                passData(result1,latitude,longitude);

                            }

                            else
                                Toaster.toastLong("Please wait while we fetch location...");

                        } else {
                            gps1.showSettingsAlert();
                        }



                    }
                }
                else
                {
                    Toaster.toastLong("Check You internet connection");
                }




            }
        });







        gridView.setAdapter(new MyAdapter(context));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                if (isNetworkAvailable(context)) {
                    if (position == 0) {
                        FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                                SearchResultsFragment.newInstance("Games", "bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    } else if (position == 1) {
                        FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                                SearchResultsFragment.newInstance("Yoga", "bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    } else if (position == 2) {
                        FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                                SearchResultsFragment.newInstance("Bowling", "bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    } else if (position == 3) {
                        FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                                SearchResultsFragment.newInstance("Dance", "bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    } else if (position == 4) {
                        FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                                SearchResultsFragment.newInstance("Sports", "bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    } else if (position == 5) {
                        FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                                SearchResultsFragment.newInstance("Relax", "bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    } else if (position == 6) {
                        FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                                SearchResultsFragment.newInstance("Arts", "bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    } else if (position == 7) {
                        FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                                SearchResultsFragment.newInstance("Learning", "bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    } else if (position == 8) {
                        FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                                SearchResultsFragment.newInstance("Music", "bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    } else if (position == 9) {
                        FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                                SearchResultsFragment.newInstance("Just For Fun", "bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    } else if (position == 10) {
                        FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                                SearchResultsFragment.newInstance("Dayouts", "bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    } else {
                        FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                                SearchResultsFragment.newInstance("Archery", "bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    }
                }

                else {
                    Toaster.toastLong("Check You internet connection");
                }
            }
        });

        return rootView;
    }

    private class MyAdapter extends BaseAdapter
    {
        private List<Item> items = new ArrayList<Item>();
        private LayoutInflater inflater;

        public MyAdapter(Context context)
        {
            inflater = LayoutInflater.from(context);

            items.add(new Item("Games", R.drawable.games));
            items.add(new Item("Yoga", R.drawable.yoga));
            items.add(new Item("Bowling", R.drawable.bowling));
            items.add(new Item("Dance", R.drawable.dance));
            items.add(new Item("Sports", R.drawable.sports));
            items.add(new Item("Relax", R.drawable.relax));
            items.add(new Item("Arts", R.drawable.arts));
            items.add(new Item("Learning", R.drawable.learning));
            items.add(new Item("Music", R.drawable.music));
            items.add(new Item("Just For Fun", R.drawable.just_for_fun));
            items.add(new Item("Dayouts", R.drawable.dayouts));
            items.add(new Item("Archery", R.drawable.archery));
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i)
        {
            return items.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return items.get(i).drawableId;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            View v = view;
            ImageView picture;
            TextView name;

            if(v == null)
            {
                v = inflater.inflate(R.layout.gridview_item, viewGroup, false);
                v.setTag(R.id.picture, v.findViewById(R.id.picture));
                v.setTag(R.id.text, v.findViewById(R.id.text));
            }

            picture = (ImageView)v.getTag(R.id.picture);
            name = (TextView)v.getTag(R.id.text);

            Item item = (Item)getItem(i);

            picture.setImageResource(item.drawableId);
            name.setText(item.name);

            return v;
        }

        private class Item
        {
            final String name;
            final int drawableId;

            Item(String name, int drawableId)
            {
                this.name = name;
                this.drawableId = drawableId;
            }
        }
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

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    public void getloc(){


        gps = new GPSTracker(context);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            System.out.println(latitude);
            System.out.println(longitude);

            getAddressFromLocation(context, latitude, longitude, new GeocoderHandler());
            System.out.println("lat and long are "+latitude+" "+longitude);

            passData(result1,latitude,longitude);


        } else {
            gps.showSettingsAlert();
        }
    }

    private void checkPermissions() {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                System.out.println("Show dialog box");
                displayPermissionsRequiredDialog();
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_LOC);
                getloc();
               // System.out.println("test 2");

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else
        {
            getloc();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

            if(requestCode== REQUEST_LOC)
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            getloc();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    displayPermissionsRequiredDialog();

                    //Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    //Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                    //intent.setData(uri);
                   // startActivityForResult(intent,REQUEST_LOC);
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request

    }

    private void displayPermissionsRequiredDialog() {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle("Permissions Required");
        builder.setMessage("Hobbygaze requires your location to provide you calculated distances wherever you want to go");
        builder.setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_LOC);
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                Toaster.toastLong("Please set the GPS on to view distances");
            }
        });

        builder.create().show();


    }


}
