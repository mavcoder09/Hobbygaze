package com.hobbygaze.maverick.hobbygaze.old;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.hobbygaze.maverick.hobbygaze.R;

//public class com.hobbygaze.maverick.hobbygaze.oldfiles.MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener{
public class MainActivity extends Activity {

    public static GoogleAnalytics analytics;
    public static Tracker tracker;
    SharedPreferences someData;
    public static String filename="MySharedString";



   //SharedPreferences preferences = getPreferences(MODE_PRIVATE);
    //SharedPreferences.Editor editor = preferences.edit();  // Put the values from the UI
    String PauseWebUrl=null;


    String URL="http://www.hobbygaze.com/";

    WebView ourBrow;
    //private SwipeRefreshLayout swipeLayout;
    //String URL="http://52.76.53.63/";


    int count=0;
    private static final String TAG = "old.MainActivity";


    public class ourViewClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView v,String url){

            int l=url.length();

            int first_pos=0;
            int last_pos=l;
            String str=null;
            String str1=null;

            if (url.startsWith("whatsapp://send?"))
            {
                for(int i=0;i<l;i++) {
                    char c = url.charAt(i);
                    if (c == '=') {
                        first_pos=i+1;
                    }
                }


                        str=url.substring(first_pos,last_pos);
                // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
               /* Log.v(com.hobbygaze.maverick.hobbygaze.oldfiles.TAG, " Whatsapp share URL=" + url);
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/*");
                share.putExtra(Intent.EXTRA_STREAM, Uri.parse(Environment.getExternalStorageState()));
                startActivity(Intent.createChooser(share, "Share image using"));
                return false;
                */
                try {
                    str1=URLDecoder.decode(str,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Log.v(TAG, " Whatsapp share URL=" + str1);
                Uri uri = Uri.parse(str1);


                /*Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "" + uri);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);

                */

                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "" + uri);
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Whatsapp not installed", Toast.LENGTH_LONG);
                    toast.show();
                }
                Log.v(TAG, " Returned from Whatsapp1");
                return true;

            }
            Log.v(TAG, "Returned from Whatsapp2");
            Log.v(TAG, " in url =" + url);
            v.loadUrl(url);
            return true;
        }
    }


    public class GeoWebChromeClient extends WebChromeClient {

        public void statusCheck()
        {
            final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

            if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                buildAlertMessageNoGps();

            }


        }
        private void buildAlertMessageNoGps() {
           /* final AlertDialog.Builder builder = new AlertDialog.Builder(com.hobbygaze.maverick.hobbygaze.oldfiles.MainActivity.this);
            builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog,  final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();

            */

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
       /* @Override
        public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {

            final boolean remember = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(com.hobbygaze.maverick.hobbygaze.oldfiles.MainActivity.this);
            builder.setTitle("Locations");
            builder.setMessage("Would like to use your Current Location ")
                    .setCancelable(true).setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    statusCheck();
                    callback.invoke(origin, true, remember);
                }
            }).setNegativeButton("Don't Allow", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    callback.invoke(origin, false, remember);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
*/
       @Override
       public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {

           statusCheck();
           callback.invoke(origin, true, false);
       }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);

        tracker = analytics.newTracker("UA-46463624-3"); // Replace with actual tracker/property Id
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
/*
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

*/
        long latestVersionCode = 1;

// get currently running app version code
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        long versionCode = pInfo.versionCode;


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
        ourBrow = (WebView) findViewById(R.id.wvBrowser);
        ourBrow.getSettings().setJavaScriptEnabled(true);
        ourBrow.getSettings().setLoadWithOverviewMode(true);
        ourBrow.getSettings().setUseWideViewPort(true);
        ourBrow.getSettings().setBuiltInZoomControls(true);
        ourBrow.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);

        ourBrow.getSettings().setGeolocationEnabled(true);
        ourBrow.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        ourBrow.getSettings().setDatabaseEnabled(true);
        ourBrow.getSettings().setDomStorageEnabled(true);


        ourBrow.setWebChromeClient(new GeoWebChromeClient());
        //ourBrow.getSettings().setGeolocationDatabasePath(context.getFilesDir().getPath());

        if(isNetworkStatusAvialable (getApplicationContext())) {

            //ourBrow.clearCache(true);
            //Toast.makeText(getApplicationContext(), "internet available", Toast.LENGTH_SHORT).show();
        } else {
            /*Dialog d1 = new Dialog(this);

            d1.setTitle("Failed to connect");
            TextView tv = new TextView(this);
            d1.setContentView(tv);
            d1.show();
            */

            AlertDialog alertDialog = new AlertDialog.Builder(
                    MainActivity.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("No internet ");

            // Setting Dialog Message
            alertDialog.setMessage("Please check your internet connectivity");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.warning);

            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                   // Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                }
            });

            // Showing Alert Message
            alertDialog.show();
           // Toast.makeText(getApplicationContext(), "internet is not available", Toast.LENGTH_SHORT).show();

        }



        //ourBrow.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        //viewer.getSettings().setAppCachePath("/data/data/com.your.package.appname/cache"??);
        //ourBrow.getSettings().setAppCacheEnabled(true);
        //ourBrow.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);


        ourBrow.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        ourBrow.setWebViewClient(new ourViewClient());
        try {
            ourBrow.loadUrl(URL);
            PauseWebUrl = ourBrow.getUrl();
            Log.v(TAG, " CurrentURL=" + PauseWebUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

/*
    @Override public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                swipeLayout.setRefreshing(false);
            }
        }, 10000);

        ourBrow.loadUrl(URL);

    }
*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ourBrow.goBack();
        //ourBrow.loadUrl("http://ec2-52-74-10-234.ap-southeast-1.compute.amazonaws.com/");
       // this.finish();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        String webUrl = ourBrow.getUrl();
        Log.v(TAG, " BackpageURL=" + webUrl);
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode)
            {
                case KeyEvent.KEYCODE_BACK:
                    if(ourBrow.canGoBack()){
                        ourBrow.goBack();
                    }
                  // else if (webUrl.equals(URL + "?v=c86ee0d9d7ed"))
                    else if (webUrl.equals(URL))
                    {
                        count ++;
                        if(count==1)
                        {
                            Toast toast = Toast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_LONG);
                            toast.show();
                        }

                        else if(count==2)
                        this.finish();
                    }
                    else
                    {
                        ourBrow.loadUrl(URL);
                        //finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
       /* String pauseURLforResume =  ourBrow.getUrl();
        Log.v(com.hobbygaze.maverick.hobbygaze.oldfiles.TAG, " onPauseURL=" + pauseURLforResume);
        editor.putString(ResumeURL, pauseURLforResume);
        editor.commit();
*/
        String stringData= ourBrow.getUrl();

        SharedPreferences.Editor editor=someData.edit();


        CharSequence cs1 = "whatsapp";

        // string contains the specified sequence of char values
        boolean retval = stringData.contains(cs1);
        String str=null;
        int l=stringData.length();
        int pos=l;

        if (retval)
        {


            for(int i=0;i<stringData.length();i++)
            {
                char c = stringData.charAt(i);
                if(c=='?'){
                    pos=i;
                    Log.v(TAG, " Inforloop url is =" + stringData);
                    break;
                }


            }
            str=stringData.substring(0, pos);
            editor.putString("sharedString",str);
            editor.commit();
            Log.v(TAG, " onPauseURLHome=" + str);
        }

        else {
            editor.putString("sharedString", stringData);
            editor.commit();
            Log.v(TAG, " onPauseURL=" + stringData);
        }

    }

    @Override
    public void onResume()
    {
        super.onResume();
        //String dataReturned=preferences.getString(ResumeURL, URL);
        //Log.v(com.hobbygaze.maverick.hobbygaze.oldfiles.TAG, "onResumeURL=" + dataReturned);

        //    ourBrow.loadUrl(URL);

        someData=getSharedPreferences(filename,0);
        String dataReturned=someData.getString("sharedString", URL);//Default value couldnt load if key is not present
        ourBrow.loadUrl(dataReturned);
        Log.v(TAG, "onResumeURL=" + dataReturned);



    }
   /* @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences settings = this.getSharedPreferences("filename", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(filename);
        editor.clear();
        editor.commit();
        finish();
    }
*/
    public static boolean isNetworkStatusAvialable (Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivityManager != null)
    {
        NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
        if(netInfos != null)
            if(netInfos.isConnected())
                return true;
    }
    return false;
}

}