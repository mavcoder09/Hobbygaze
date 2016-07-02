package com.hobbygaze.maverick.hobbygaze;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.answers.SearchEvent;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.hobbygaze.maverick.hobbygaze.fragments.BlogFragment;
import com.hobbygaze.maverick.hobbygaze.fragments.ContactUsFragment;
import com.hobbygaze.maverick.hobbygaze.fragments.HomeFragment;
import com.hobbygaze.maverick.hobbygaze.fragments.HomeFragment1;
import com.hobbygaze.maverick.hobbygaze.fragments.MySuggestionProvider;
import com.hobbygaze.maverick.hobbygaze.fragments.PopularPlacesFragment;
import com.hobbygaze.maverick.hobbygaze.fragments.SearchResultsFragment;
import com.hobbygaze.maverick.hobbygaze.fragments.SendFeedBackFragment;
import com.hobbygaze.maverick.hobbygaze.fragments.ShareFragment;
import com.hobbygaze.maverick.hobbygaze.helper.SQLiteHandler;
import com.hobbygaze.maverick.hobbygaze.helper.SessionManager;
import com.hobbygaze.maverick.hobbygaze.lractivity.LoginActivity;

import java.util.HashMap;

import io.fabric.sdk.android.Fabric;
import xdroid.toaster.Toaster;

/**
 * Created by abhishek on 12/7/15.
 */
public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,HomeFragment1.keyWordClicked1

//public class BaseActivity extends AppCompatActivity
  //      implements NavigationView.OnNavigationItemSelectedListener
{
    double latitude=0;
    double longitude=0;
    String result=null;
    public static GoogleAnalytics analytics;
    public static Tracker tracker;

    private SQLiteHandler db;
    private SessionManager session;
    boolean home=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Fabric.with(this, new Crashlytics());
        Fabric.with(this, new Answers());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        handleIntent(getIntent());

        if (isNetworkAvailable(this)) {
            analytics = GoogleAnalytics.getInstance(this);
            analytics.setLocalDispatchPeriod(1800);

            tracker = analytics.newTracker("UA-46463624-3"); // Replace with actual tracker/property Id
            tracker.enableExceptionReporting(true);
            tracker.enableAdvertisingIdCollection(true);
            tracker.enableAutoActivityTracking(true);
            System.out.println("Google analytics connected");
        }




        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }


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
        Answers.getInstance().logCustom(new CustomEvent("Userdetails")
                .putCustomAttribute("Username", user_name)
                .putCustomAttribute("Email id ", user_email));

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        FragmentUtils.addFragment(getSupportFragmentManager(), R.id.mainContainer,
                HomeFragment1.newInstance(), FragmentTransaction.TRANSIT_FRAGMENT_FADE);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
System.out.println("total fragment count="+getFragmentManager().getBackStackEntryCount());
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.mainContainer);
        if (currentFragment instanceof HomeFragment1) {
            home=true;
            System.out.println("Found home fragment");

        }
       // if (getFragmentManager().getBackStackEntryCount() == 0) {
          //  System.out.println("fragment popped 1");
       // }
       //
      // else {
        //    System.out.println("fragment popped 2");
          //  getFragmentManager().popBackStack();
       // }

        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            System.out.println("fragment popped 1");
            //getFragmentManager().popBackStack();

        }
        else {
            System.out.println("fragment popped 2");
            super.onBackPressed();
        }



    }


    @Override
    protected void onNewIntent(Intent intent) {

        // System.out.println("hello2");
        setIntent(intent);
        handleIntent(intent);
    }



    private void handleIntent(Intent intent) {
        //System.out.println("hello");
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
           this.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            );
            //System.out.println("searched 1");
            if (isNetworkAvailable(this)) {
                String query = intent.getStringExtra(SearchManager.QUERY);

                Answers.getInstance().logSearch(new SearchEvent()
                        .putQuery(query));
                System.out.println(query);
                // Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                System.out.println("Result ="+result);
                if(result==null)
                result="bangalore";
                System.out.println("latitude for search="+latitude);
                System.out.println("longitude for search="+longitude);
                FragmentUtils.changeFragment(getSupportFragmentManager(), R.id.mainContainer,
                        SearchResultsFragment.newInstance(query, "bangalore", latitude, longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);


                //SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                 //       MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
               // suggestions.saveRecentQuery(query, null);
            }

            else
            {
                Toaster.toastLong("Check You internet connection");
            }
            //use the query to search your data somehow
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }






    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            FragmentUtils.changeFragment(getSupportFragmentManager(), R.id.mainContainer,
                    HomeFragment1.newInstance(), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
System.out.println("Home fragment");



        }
        else if (id == R.id.nav_places) {


            FragmentUtils.changeFragment(getSupportFragmentManager(), R.id.mainContainer,
                    PopularPlacesFragment.newInstance(latitude,longitude), FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            System.out.println("Popular places fragment");
        }




        else if (id == R.id.nav_share) {

            FragmentUtils.changeFragment(getSupportFragmentManager(), R.id.mainContainer,
                    ShareFragment.newInstance(), FragmentTransaction.TRANSIT_FRAGMENT_FADE);


            System.out.println("share fragment");

        } else if (id == R.id.nav_send) {
            FragmentUtils.changeFragment(getSupportFragmentManager(), R.id.mainContainer,
                    ContactUsFragment.newInstance(), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            System.out.println("Contact Us fragment");
        }
        else if (id == R.id.nav_blog) {

            FragmentUtils.changeFragment(getSupportFragmentManager(), R.id.mainContainer,
                    BlogFragment.newInstance(), FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            System.out.println("Blog fragment");
        }



        else if (id == R.id.nav_feedback) {
            FragmentUtils.changeFragment(getSupportFragmentManager(), R.id.mainContainer,
                    SendFeedBackFragment.newInstance(), FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            System.out.println("send feedback fragment");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



    @Override
    public void sendText(String result1,double lat,double longit){
        latitude=lat;
        longitude=longit;
        if(result==null)
        {
            result="bangalore";
        }
        else{
            if(result1!=null)
            result=result1.toString();
        }


        // Get Fragment B

        SearchResultsFragment frag = new SearchResultsFragment();
        frag.updateText(result1, lat, longit);
    }


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


    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

}