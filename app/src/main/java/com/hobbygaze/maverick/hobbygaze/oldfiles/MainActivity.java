package com.hobbygaze.maverick.hobbygaze.oldfiles;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.hobbygaze.maverick.hobbygaze.BaseActivity;
import com.hobbygaze.maverick.hobbygaze.R;
import com.hobbygaze.maverick.hobbygaze.fragments.HomeFragment;
import com.hobbygaze.maverick.hobbygaze.fragments.SearchResultsFragment;
import com.hobbygaze.maverick.hobbygaze.helper.SQLiteHandler;
import com.hobbygaze.maverick.hobbygaze.helper.SessionManager;
import com.hobbygaze.maverick.hobbygaze.lractivity.LoginActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
//import com.winsontan520.wversionmanager.library.OnReceiveListener;
//import com.winsontan520.wversionmanager.library.WVersionManager;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //private SimpleLocation location;





    public static GoogleAnalytics analytics;
    public static Tracker tracker;

    private SQLiteHandler db;
    GPSTracker gps;
double latitude;
    double longitude;
    private SessionManager session;
String result;
    EditText loc;
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
    private static final String EMAIL = "email";
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");

        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);

        tracker = analytics.newTracker("UA-46463624-3"); // Replace with actual tracker/property Id
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);

        long latestVersionCode = 1;

// get currently running app version code
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        long versionCode = pInfo.versionCode;
        System.out.println("app version="+versionCode);

        if(versionCode < latestVersionCode) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);


            alertDialog.setTitle("Old version");


            alertDialog.setMessage("Your app is out of date.Update?");


            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {



                    Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse("https://play.google.com/store/apps/details?id=com.cs.maverick.hanumanchalisa"));
                    startActivity(intent);
                }
            });


            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                }
            });


            alertDialog.show();
        }
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
        loc=(EditText) findViewById(R.id.location);
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
                        gps = new GPSTracker(MainActivity.this);

                        // check if GPS enabled
                        if (gps.canGetLocation()) {

                            latitude = gps.getLatitude();
                            longitude = gps.getLongitude();
                            System.out.println(latitude);
                            System.out.println(longitude);
                            getAddressFromLocation(MainActivity.this, latitude, longitude, new GeocoderHandler());

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
String unit="K";

        //double distance=calculateDistance(latitude,longitude,12.914541,77.564722,unit);

//System.out.println("Distance="+ distance);


        final EditText keyword=(EditText)findViewById(R.id.keyword);
        keyword.setError(null);
        Button b = (Button) findViewById(R.id.search);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                String keywords = keyword.getText().toString().trim();
                if (!keywords.isEmpty()) {
                  //  Intent ii = new Intent(MainActivity.this, SearchResultsActivity.class);
                    //ii.putExtra("keyword", keywords);
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





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       View header = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
      //  View header = navigationView.inflateHeaderView(R.layout.nav_header_main);
        navigationView.addHeaderView(header);
        TextView email=(TextView)header.findViewById(R.id.emailid);
        TextView name=(TextView)header.findViewById(R.id.display_name);
        Button logout=(Button)header.findViewById(R.id.logout);
        Intent in = getIntent();

        // Get JSON values from previous intent
        String cookie = in.getStringExtra("cookie");
        String username = in.getStringExtra("username");
        String displayname = in.getStringExtra("displayname");

        System.out.println("Main activity"+cookie);
        System.out.println("Main activity"+username);
        System.out.println("Main activity" + displayname);
      //  email.setText(username);
       // name.setText(displayname);

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        String user_name = user.get("name");
        String user_email = user.get("email");
        email.setText(user_email);
        name.setText(user_name);

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });


       // if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //    getWindow().setNavigationBarColor(getResources().getColor(R.color.btn_color));
       // }
//TextView email=(TextView)findViewById(R.id.emailid);
    //    Intent in = getIntent();

        // Get JSON values from previous intent
      //  String emailid = in.getStringExtra(EMAIL);
        //email.setText(emailid);


    }

    private void checkVersionCode() {
/*
        long latestVersionCode = 2;

// get currently running app version code
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        long versionCode = pInfo.versionCode;

        System.out.println("Version code =" + versionCode);
        if(versionCode < latestVersionCode) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);


            alertDialog.setTitle("Old version");


            alertDialog.setMessage("Your app is out of date.Update?");


            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {



                    Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse("https://play.google.com/store/apps/details?id=com.cs.maverick.hanumanchalisa"));
                    startActivity(intent);
                }
            });


            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                }
            });


            alertDialog.show();
        }
        */
    }

    private void showPermissionDialogGPS() {



        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is off");

        // Setting Dialog Message
        alertDialog.setMessage("Do you want to turn GPS on ");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.gps);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                // Write your code here to invoke YES event
                //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      //  int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        }
        else if (id == R.id.nav_places) {
            Intent i = new Intent(MainActivity.this, PopularPlacesActivity.class);
            startActivity(i);
        }




       else if (id == R.id.nav_share) {


            Intent i = new Intent(MainActivity.this, ShareActivity.class);
            startActivity(i);



        } else if (id == R.id.nav_send) {

            Intent i = new Intent(MainActivity.this, ContactUsActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_blog) {
            Intent i = new Intent(MainActivity.this, BaseActivity.class);
            startActivity(i);
        }



        else if (id == R.id.nav_feedback) {

            Intent i = new Intent(MainActivity.this, SendFeedbackActivity.class);
            startActivity(i);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2, String unit)
    {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "M") {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double deg2rad(double deg)
    {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double rad2deg(double rad)
    {
        return (rad * 180.0 / Math.PI);
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



}
