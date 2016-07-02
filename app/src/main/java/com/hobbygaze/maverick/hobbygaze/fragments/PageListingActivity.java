package com.hobbygaze.maverick.hobbygaze.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.hobbygaze.maverick.hobbygaze.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import xdroid.toaster.Toaster;

/**
 * Created by abhishek on 11/25/15.
 */
public class PageListingActivity extends AppCompatActivity {
    private static final String TAG_CONTENT = "content";
    private static final String TAG_HOURS_OF_OP = "hours_of_operation";
    private static final String TAG_PRICE = "Pricing";

    String post_id=null;
    private ProgressDialog pDialog;
    private static final String TAG_ID = "id";
    String url="http://www.hobbygaze.com/api/searchlisting/get_posts_metadata/?post_id=";
    String copy = new String(url);
    TextView mon;
    TextView tue;
    TextView wed;
    TextView thu;
    TextView fri;
    TextView sat;
    TextView sun;
    String Mon;
    String Tue;
    String Wed;
    String Thu;
    String Fri;
    String Sat;
    String Sun;
    String content;
String price=null;

    JSONArray result = null;
    WebView ourBrow;
    public class ourViewClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView v, String url) {
            v.loadUrl(url);
            return true;

        }

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_places);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        getSupportActionBar().setTitle(" ");


        ourBrow = (WebView) findViewById(R.id.web);


       // this.ourBrow.getSettings().setLoadWithOverviewMode(true);
       // this.ourBrow.getSettings().setUseWideViewPort(true);

        this.ourBrow.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        this.ourBrow.setScrollbarFadingEnabled(false);
        this.ourBrow.setVerticalScrollBarEnabled(true);
        this.ourBrow.setHorizontalScrollBarEnabled(true);

      //  this.ourBrow.getSettings().setSupportZoom(true);
        this.ourBrow.getSettings().setLightTouchEnabled(true);
       // mon=(TextView)findViewById(R.id.mon);
      //  tue=(TextView)findViewById(R.id.tue);
       // wed=(TextView)findViewById(R.id.wed);
      //  thu=(TextView)findViewById(R.id.thu);
       // fri=(TextView)findViewById(R.id.fri);
       // sat=(TextView)findViewById(R.id.sat);
      //  sun=(TextView)findViewById(R.id.sun);
        //ourBrow.getSettings().setJavaScriptEnabled(true);
        //ourBrow.getSettings().setLoadWithOverviewMode(true);
       // ourBrow.getSettings().setUseWideViewPort(true);
        // getting intent data
        Intent in = getIntent();

        // Get JSON values from previous intent
        content = in.getStringExtra(TAG_CONTENT);
        post_id=in.getStringExtra(TAG_ID);
        price=in.getStringExtra(TAG_PRICE);

        System.out.println("pricing= "+price);
        copy=copy+post_id;
        new GetResults2().execute();

       // mon.setText(Mon);
        //tue.setText(Tue);
       // wed.setText(Wed);
        //thu.setText(Thu);
        //fri.setText(Fri);
        //sat.setText(Sat);
       // sun.setText(Sun);
        System.out.println("Monday timing"+Mon);




    }

    private class GetResults2 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(PageListingActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {


            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            String company_email = null;
            String jsonStr = null;
            try {
                jsonStr = sh.run(copy);
                //  System.out.println("json string "+ jsonStr);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();

            }


            if (jsonStr != null) {
                try {

                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String attachment_url = null;
                    // Getting JSON Array node


                    result = jsonObj.getJSONArray(TAG_HOURS_OF_OP);





                    for (int i = 0; i < result.length(); i++) {
                        JSONObject c = result.getJSONObject(i);


                        try {
                            Mon = c.getString("0");
                            Tue = c.getString("1");
                            Wed = c.getString("2");
                           Thu = c.getString("3");
                            Fri = c.getString("4");
                           Sat = c.getString("5");
                            Sun = c.getString("6");
                            System.out.println("Monday "+Mon);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }












                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            //System.out.println(response);

            //Log.d("Response: ", "> " + response);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            System.out.println("Monday timing"+Mon);
            Mon = Mon.replace("{", "").replace("}", "");
            Tue = Tue.replace("{", "").replace("}", "");
            Wed = Wed.replace("{", "").replace("}", "");
            Thu = Thu.replace("{", "").replace("}", "");
            Fri = Fri.replace("{", "").replace("}", "");
            Sat = Sat.replace("{", "").replace("}", "");
            Sun = Sun.replace("{", "").replace("}", "");

//String[] day0=Mon.split("\"start\":\"");
            Mon=Mon.replaceAll("\"start\":\"", "");
            Mon=Mon.replaceAll("\"end\":\"", " - ");
            Mon=Mon.replaceAll("\",","");
            Mon=Mon.replaceAll("\"","");
            Tue=Tue.replaceAll("\"start\":\"","");
            Tue=Tue.replaceAll("\"end\":\""," - ");
            Tue=Tue.replaceAll("\",","");
            Tue=Tue.replaceAll("\"","");
            Wed=Wed.replaceAll("\"start\":\"","");
            Wed=Wed.replaceAll("\"end\":\""," - ");
            Wed=Wed.replaceAll("\",","");
            Wed=Wed.replaceAll("\"","");
            Thu=Thu.replaceAll("\"start\":\"","");
            Thu=Thu.replaceAll("\"end\":\""," - ");
            Thu=Thu.replaceAll("\",","");
            Thu=Thu.replaceAll("\"","");
            Fri=Fri.replaceAll("\"start\":\"","");
            Fri=Fri.replaceAll("\"end\":\""," - ");
            Fri=Fri.replaceAll("\",","");
            Fri=Fri.replaceAll("\"","");
            Sat=Sat.replaceAll("\"start\":\"","");
            Sat=Sat.replaceAll("\"end\":\""," - ");
            Sat=Sat.replaceAll("\",","");
            Sat=Sat.replaceAll("\"","");
            Sun=Sun.replaceAll("\"start\":\"","");
            Sun=Sun.replaceAll("\"end\":\""," - ");
            Sun=Sun.replaceAll("\",","");
            Sun=Sun.replaceAll("\"","");


            content=content.replaceAll("Highlights","<span style=\"font-weight: 700;color: #00C3FE;\">Highlights</span>");
            //String hrs=Mon+Tue+Wed+Thu+Fri+Sat+Sun;
            content=content.replaceAll("Other Facilities","<span style=\"font-weight: 700;color: #00C3FE;\">Other Facilities</span>");

            String[] split = content.split("Pricing");
            content = split[0] + "</span></td></tr></tbody></table>";
            String[] splitto=content.split("<map style");
            content=splitto[0]+"</td></tr></tbody></table>";
            System.out.println("split1"+" " +split[0]);
            System.out.println("split2"+" " +splitto[0]);



            String table="<table  style=\"border: none\">\n" +
                    "  <tr>\n" +
                    "    <td style=\"border: none\">Monday</td>\n" +
                    "    <td style=\"border: none\">"+Mon+"</td> \n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td style=\"border: none\">Tuesday</td>\n" +
                    "    <td style=\"border: none\">"+Tue+"</td> \n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td style=\"border: none\">Wednesday</td>\n" +
                    "    <td style=\"border: none\">"+Wed+"</td> \n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td style=\"border: none\">Thursday</td>\n" +
                    "    <td style=\"border: none\">"+Thu+"</td> \n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td style=\"border: none\">Friday</td>\n" +
                    "    <td style=\"border: none\">"+Fri+"</td> \n" +
                    "  <tr>\n" +
                    "    <td style=\"border: none\">Saturday</td>\n" +
                    "    <td style=\"border: none\">"+Sat+"</td> \n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td style=\"border: none\">Sunday</td>\n" +
                    "    <td style=\"border: none\">"+Sun+"</td> \n" +
                    "  </tr>\n" +
                    "  </tr>\n" +
                    "</table>";


            String pricing1="<span style=\"font-weight: 700;color: #00C3FE;\">Pricing</span>";
            try {

                ourBrow.loadData("<html><body style=\"margin:0px; padding:0px; width:100%; height:100%\">"+ content + "<span style=\"font-weight: 700;color: #00C3FE; padding-bottom:10px\">Hours of Operation</span>"+"<br>"+"&nbsp;</br>"+"\n"+ table+ "</body></html>",
                        "text/html; charset=UTF-8", null);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


}
