package com.hobbygaze.maverick.hobbygaze.oldfiles;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hobbygaze.maverick.hobbygaze.R;
import com.hobbygaze.maverick.hobbygaze.fragments.PageListingActivity;
import com.hobbygaze.maverick.hobbygaze.fragments.SearchResults;
import com.hobbygaze.maverick.hobbygaze.fragments.SearchResultsAdapter;
import com.hobbygaze.maverick.hobbygaze.oldfiles.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import xdroid.toaster.Toaster;

/**
 * Created by abhishek on 11/22/15.
 */
public class Search extends android.support.v4.app.ListFragment {

    public Search() {
    }

    private ProgressDialog pDialog;
    Context context;
    // URL to get contacts JSON
    private static String url = "http://www.hobbygaze.com/api/get_search_results/?search=";
    private static String url1 = null;
    static String copy = new String(url);
    // static View rootView=null;
    // private static final String com.hobbygaze.maverick.hobbygaze.oldfiles.TAG = "com.hobbygaze.maverick.hobbygaze.oldfiles.BlogActivity";
    // private List<com.hobbygaze.maverick.hobbygaze.oldfiles.POST> posts;

    SearchResults[] SearchResultsdata;
    // JSON Node names
    private static final String TAG_POSTS = "posts";
    private static final String TAG_TITLE = "title";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_ATTACHMENTS = "attachments";
    private static final String TAG_ATTACHMENT_URL = "url";
    private static final String TAG_CUSTOM_FIELD = "custom_fields";
    private static final String TAG_EMAIL = "company_email";
    private static final String TAG_URL = "url";


    private static final String TAG_LAT = "geolocation_lat";
    private static final String TAG_LONG = "geolocation_long";

    String geoDistance = null;

    String latitude = null;
    String longitude = null;
    static String current_lat = null;
    static String current_long = null;
    // contacts JSONArray
    JSONArray result = null;
    String content = null;
    String title = null;
    String listing_page_url = null;
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> resultList;
    ArrayList<String> ar = new ArrayList<String>();
    ListView list;

    public static Search newInstance() {
        //  getDataFromHome(keywords,lat,longit);

        return new Search();
    }


    public static void getDataFromHome(String keywords, double lat, double longit) {
        // copy=url+keywords;
        //  current_lat=String.valueOf(lat);
        //  current_long=String.valueOf(longit);

    }

  /*  public void updateText(String keywords, double lat, double longit) {
        copy = url + keywords;
        current_lat = String.valueOf(lat);
        current_long = String.valueOf(longit);
        System.out.println("Bundle =" + " " + current_lat + " " + current_long + " " + copy);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {

        super.onDetach();
    }
*/
  /*  @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
       if(args!=null)
        {
            System.out.println("Inside searchresult fragment");
            String j =(String) args.getString("Keywords");
            Double lat=args.getDouble("latitude");
            Double longit=args.getDouble("longitude");
            current_lat=String.valueOf(lat);
            current_long=String.valueOf(longit);
            //System.out.println("Curren lat and long=" + current_lat + " " + current_long);

            copy=url+j;

            System.out.println("Bundle ="+j+ " "+ current_lat+" "+ current_long+" "+copy);
        }
        */
    //  }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_results, container,
                false);
        context = rootView.getContext();
        // Intent iin= getActivity().getIntent();
        //  Bundle b = iin.getExtras();
        Intent iin= getActivity().getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            String j =(String) b.get("keyword");
            current_lat=(String) b.get("lat");
            current_long=(String)b.get("long");
            System.out.println("Curren lat and long=" + current_lat + " " + current_long);

            copy=url+j;
        }

        resultList = new ArrayList<HashMap<String, String>>();
        //list = getActivity().getListView();
        // Calling async task to get json
        new GetResults().execute();

        return rootView;


    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class GetResults extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
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
                System.out.println(" copy after calling interface" + copy);
                jsonStr = sh.run("http://www.hobbygaze.com/api/get_search_results/?search=yoga");
                //  System.out.println("json string "+ jsonStr);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                Toaster.toastLong("No results found...Please Search Proper Keywords");
                getActivity().finish();
            }


            if (jsonStr != null) {
                try {

                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String attachment_url = null;
                    // Getting JSON Array node


                    result = jsonObj.getJSONArray(TAG_POSTS);

                    String count = jsonObj.getString("count");

                    if (count.contentEquals("0")) {
                        Toaster.toastLong("No results found...Please Search Proper Keywords");
                        getActivity().finish();

                    }
                    SearchResultsdata = new SearchResults[result.length()];
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject c = result.getJSONObject(i);


                        try {
                            title = c.getString(TAG_TITLE);
                            ;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //String title = c.getString(TAG_TITLE);
                        listing_page_url = c.getString(TAG_URL);
                        content = c.getString(TAG_CONTENT);

                        //String custom=c.getString(TAG_CUSTOM_FIELD);
                        JSONObject custom = c.getJSONObject(TAG_CUSTOM_FIELD);
                        try {
                            company_email = custom.getString(TAG_EMAIL);
                            company_email = company_email.replace("[", "").replace("]", "");
                            company_email = company_email.replace("\"", "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        try {
                            latitude = custom.getString(TAG_LAT);
                            longitude = custom.getString(TAG_LONG);
                            latitude = latitude.replace("[", "").replace("]", "");
                            latitude = latitude.replace("\"", "");
                            longitude = longitude.replace("[", "").replace("]", "");
                            longitude = longitude.replace("\"", "");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        float[] results = new float[3];
                        Location l = new Location("Point A");

                        Double lat1 = Double.parseDouble(latitude);
                        Double long1 = Double.parseDouble(longitude);
                        Double lat2 = Double.parseDouble(current_lat);
                        Double long2 = Double.parseDouble(current_long);
                        // System.out.println("Place"+latitude);
                        //System.out.println("Place" + longitude);
                        // l.distanceBetween(12.92286, 77.638555, 12.914541, 77.564722, results);


                        if (current_lat.contentEquals("0.0")) {
                            geoDistance = null;
                            System.out.println("Gps is off");

                        } else {

                            l.distanceBetween(lat1, long1, lat2, long2, results);
                            float a = results[0] / 1000;
                            double newKB = Math.round(a * 100.0) / 100.0;

                            geoDistance = String.valueOf(newKB);
                            // System.out.println("Distance=" + geoDistance +"km");
                        }


                        JSONArray attachment = c.getJSONArray(TAG_ATTACHMENTS);
                        for (int j = 0; j < 1; j++) {
                            JSONObject img = attachment.getJSONObject(j);
                            attachment_url = img.getString(TAG_ATTACHMENT_URL);
                        }


                        HashMap<String, String> result = new HashMap<String, String>();

                        // adding each child node to HashMap key => value


                        //String test=company_email.split("[");
                        // System.out.println(latitude);
                        //  System.out.println(longitude);
                        //  System.out.println(listing_page_url);
                        // System.out.println(title);
                        // System.out.println(attachment_url);
                        // System.out.println(company_email);
                        //System.out.println(content);
                        //  ar.add(content);
                        ar.add(listing_page_url);
                        result.put(TAG_TITLE, title);
                        result.put(TAG_CONTENT, content);
                        result.put(TAG_ATTACHMENT_URL, attachment_url);
                        // adding contact to contact list
                        resultList.add(result);
                        //String escaped = escapeHtml4(title);
                        //if(title!=null)
                        SearchResultsdata[i] = new SearchResults(title, attachment_url, company_email, geoDistance);
                        title = null;
                        attachment_url = null;
                        company_email = null;

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
            if (SearchResultsdata != null) {
                SearchResultsAdapter adapter = new SearchResultsAdapter(getActivity(),
                        R.layout.list_item_search_results2,
                        SearchResultsdata);

                //list.setAdapter(adapter);
                setListAdapter(adapter);
            } else {
                Toaster.toastLong("Couldn't get any data..Check You internet connection");
                getActivity().finish();
            }
        }

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);

        // Starting single page listing activity
        Intent in = new Intent(context,
                PageListingActivity.class);
        //in.putExtra(TAG_CONTENT, ar.get(position));

        in.putExtra(TAG_URL, ar.get(position));

        startActivity(in);

    }


  /*  @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        System.out.println("On pause called");

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        System.out.println("On resume called");

    }

*/



}