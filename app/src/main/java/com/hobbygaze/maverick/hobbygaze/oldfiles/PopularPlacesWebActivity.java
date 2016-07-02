package com.hobbygaze.maverick.hobbygaze.oldfiles;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hobbygaze.maverick.hobbygaze.R;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by abhishek on 11/25/15.
 */
public class PopularPlacesWebActivity   extends Activity {
    private static final String TAG_URL = "url";
    WebView ourBrow;
    private static final String TAG = "PopularPlaces";
    private ProgressBar progress;
    String content=null;
    public class ourViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            // pDialog = new ProgressDialog(com.hobbygaze.maverick.hobbygaze.oldfiles.PageListingActivity.this);
            //  pDialog.setMessage("Please wait...");
            //pDialog.setCancelable(false);
            //pDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //if (pDialog.isShowing())
            //  pDialog.dismiss();
        }


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
                    str1= URLDecoder.decode(str, "UTF-8");
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
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            PopularPlacesWebActivity.this.setValue(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //  getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_popular_places_web);


        // getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);

        //  pDialog = new ProgressDialog(this);
        //  pDialog.setCancelable(false);


        ourBrow = (WebView) findViewById(R.id.wBrowser);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setMax(100);
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
        Intent in = getIntent();

        // Get JSON values from previous intent
        content = in.getStringExtra("url");
        System.out.println("URL received ="+ content);
        System.out.println(content);


        ourBrow.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        ourBrow.setWebViewClient(new ourViewClient());

        try {

            ourBrow.loadUrl(content);
            PopularPlacesWebActivity.this.progress.setProgress(0);

        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
      /*  String webUrl = ourBrow.getUrl();
        Log.v(com.hobbygaze.maverick.hobbygaze.oldfiles.TAG, " BackpageURL=" + webUrl);

        ourBrow.loadUrl(content);
        if(ourBrow.canGoBack()){
            ourBrow.goBack();
        }


        return super.onKeyDown(keyCode, event);
        */

        String webUrl = ourBrow.getUrl();
        Log.v(TAG, " BackpageURL=" + webUrl);
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode)
            {
                case KeyEvent.KEYCODE_BACK:
                    if(ourBrow.canGoBack()){
                        System.out.println("case 1");
                        ourBrow.goBack();
                    }
                    // else if (webUrl.equals(URL + "?v=c86ee0d9d7ed"))
                    else if (webUrl.equals(content))
                    {
                        System.out.println("case2");
                        this.finish();
                    }
                    else
                    {
                        System.out.println("case3");
                        ourBrow.loadUrl(webUrl);
                        ourBrow=null;
                        this.finish();
                        //finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);




    }

    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }




}
