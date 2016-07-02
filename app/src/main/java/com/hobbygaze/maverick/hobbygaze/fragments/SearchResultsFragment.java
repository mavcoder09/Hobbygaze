package com.hobbygaze.maverick.hobbygaze.fragments;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
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

import com.hobbygaze.maverick.hobbygaze.FragmentUtils;
import com.hobbygaze.maverick.hobbygaze.R;
import com.hobbygaze.maverick.hobbygaze.SearchResultsActivity;
import com.hobbygaze.maverick.hobbygaze.fragments.PageListingActivity;
import com.hobbygaze.maverick.hobbygaze.fragments.SearchResults;
import com.hobbygaze.maverick.hobbygaze.fragments.SearchResultsAdapter;


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
public class SearchResultsFragment extends android.support.v4.app.ListFragment {

    public SearchResultsFragment() {
    }

    private ProgressDialog pDialog;
    Context context;
    // URL to get contacts JSON
    private static String url = "http://www.hobbygaze.com/api/get_search_results/?search=";

    private static String uri1="http://www.hobbygaze.com/api/searchlisting/get_posts_by_meta_query/?dev=0&search_keywords=";
    private static String uri2="&search_location=";
    private static String uri3="&search_radius=10";
    private static String uri4="&search_lat=";
    private static String uri5="&search_lng=";
    private static String url1 = null;
    String post_id=null;
    String pricing=null;
    String listing_img_url=null;
    boolean is_attachment_url_available=false;
    static String copy = new String(url);
    // static View rootView=null;
    // private static final String com.hobbygaze.maverick.hobbygaze.oldfiles.TAG = "com.hobbygaze.maverick.hobbygaze.oldfiles.BlogActivity";
    // private List<com.hobbygaze.maverick.hobbygaze.oldfiles.POST> posts;

    SearchResults[] SearchResultsdata;
    // JSON Node names
    private static final String TAG_POSTS = "posts";
    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_ATTACHMENTS = "attachments";
    private static final String TAG_ATTACHMENT_URL = "url";
    private static final String TAG_IMAGES = "images";
    private static final String TAG_FULL = "full";
    private static final String TAG_IMAGES_URL = "url";
    private static final String TAG_CUSTOM_FIELD = "custom_fields";
    private static final String TAG_EMAIL = "company_email";
    private static final String TAG_ADDR = "geolocation_formatted_address";
    private static final String TAG_STREET = "geolocation_street";
    private static final String TAG_PRICE = "Pricing";
    private static final String TAG_THUMBNAIL = "thumbnail_images";


    private static final String TAG_URL = "url";



    private static final String TAG_LAT = "geolocation_lat";
    private static final String TAG_LONG = "geolocation_long";

    String geoDistance = null;

    String latitude = null;
    String longitude = null;
    double lat1;
    double lat2;
    double long1;
    double long2;
    static String current_lat = "0.0";
    static String current_long = "0.0";
    // contacts JSONArray
    JSONArray result = null;
    JSONArray attachment=null;
    String content = null;
    String title = null;
    String listing_page_url = null;
    String List_Img_url="Test";

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> resultList;
    ArrayList<String> ImageList = new ArrayList<String>();
    ArrayList<String> ar = new ArrayList<String>();
    ArrayList<String> ar1 = new ArrayList<String>();
    ArrayList<String> ar2 = new ArrayList<String>();
    ArrayList<String> ar3 = new ArrayList<String>();
    ArrayList<String> ar4 = new ArrayList<String>();
    ArrayList<String> ar5 = new ArrayList<String>();
    ArrayList<String> ar6 = new ArrayList<String>();
    ArrayList<Boolean> ar7 = new ArrayList<Boolean>();
    ListView list;

    public static SearchResultsFragment newInstance(String keywords,String location, double lat, double longit) {
        getDataFromHome(keywords,location,lat,longit);

        return new SearchResultsFragment();
    }


    public static void getDataFromHome(String keywords,String location,double lat, double longit) {
        copy=uri1+keywords+uri2+location+uri3+uri4+lat+uri5+longit;
        if(!(lat==0.0|longit==0))
            current_lat=String.valueOf(lat);
        current_long=String.valueOf(longit);
        System.out.println("Bundle =" +current_lat + " " + current_long + " " + copy);
    }

    public void updateText(String keywords, double lat, double longit) {
        // copy = url + keywords;
        // current_lat = String.valueOf(lat);
        // current_long = String.valueOf(longit);
        //
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {

        super.onDetach();
    }

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
     /*   Intent iin= getActivity().getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            String j =(String) b.get("keyword");
            current_lat=(String) b.get("lat");
            current_long=(String)b.get("long");
            System.out.println("Curren lat and long=" + current_lat + " " + current_long);

            copy=url+j;
        }
*/
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
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {


            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            String company_email = null;
            String company_addr=null;
            String company_street=null;
            String jsonStr = null;
            try {
                System.out.println(" copy after calling interface" + copy);

                jsonStr = sh.run(copy);
                //System.out.println("json string "+ jsonStr);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                Toaster.toastLong("No results found...Please Search Proper Keywords");
                //getActivity().finish();
            }


            if (jsonStr != null) {
                try {

                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String attachment_url = null;
                    String image_full_url=null;
                    // Getting JSON Array node
                    String img=null;
                    String thumbnail_image=null;

                    result = jsonObj.getJSONArray(TAG_POSTS);

                    String count = jsonObj.getString("count");

                    if (count.contentEquals("0")) {
                        Toaster.toastLong("No results found...Please Search Proper Keywords");
                        // getActivity().finish();

                    }
                    try {
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject c = result.getJSONObject(i);


                            title = c.getString(TAG_TITLE);
                            post_id = c.getString(TAG_ID);

                        }
                    }    catch (JSONException e) {
                        e.printStackTrace();
                    }


                    SearchResultsdata = new SearchResults[result.length()];
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject c = result.getJSONObject(i);


                        try {
                            title = c.getString(TAG_TITLE);
                            post_id=c.getString(TAG_ID);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //String title = c.getString(TAG_TITLE);
                        listing_page_url = c.getString(TAG_URL);

                       // System.out.println("listing page url "+ listing_page_url);
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

                        try {JSONObject thumbnail = c.getJSONObject(TAG_THUMBNAIL);

                           /* thumbnail_image = thumbnail.getString("full");
                            thumbnail_image = thumbnail_image.replace("{", "").replace("}", "");
                            thumbnail_image=thumbnail_image.replaceAll("\"url\":\"", "");
                            thumbnail_image=thumbnail_image.replace(",","");
                            String[] split = thumbnail_image.split(",");
                            thumbnail_image = " \""+ split[0];
                            String[] split2 = thumbnail_image.split("\"width\"");
                            thumbnail_image=split2[0];
                            thumbnail_image=thumbnail_image.replace("\\","");
                            */
                            JSONObject thumbnail_image_obj =thumbnail.getJSONObject("full");
                            //JSONObject image_full = thumbnail_image_obj.getJSONObject(TAG_FULL);
                            thumbnail_image=thumbnail_image_obj.getString("url");
                            //  System.out.println("Thumbnail image url="+thumbnail_image);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                        try {
                            company_addr = custom.getString(TAG_ADDR);
                            company_addr = company_addr.replace("[", "").replace("]", "");
                            company_addr = company_addr.replace("\"", "");
                            // System.out.println("address="+company_addr);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        try {
                            company_street = custom.getString(TAG_STREET);
                            company_street = company_street.replace("[", "").replace("]", "");
                            company_street = company_street.replace("\"", "");
                            //System.out.println("street="+company_street);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        try {
                            latitude = custom.getString(TAG_LAT);
                            longitude = custom.getString(TAG_LONG);

                            try {
                                pricing=custom.getString(TAG_PRICE);
                                pricing = pricing.replace("[", "").replace("]", "");
                                pricing = pricing.replace("\"", "");
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                            latitude = latitude.replace("[", "").replace("]", "");
                            //System.out.println("test Place lat mid parsing"+latitude);
                            latitude = latitude.replace("\"", "");
                            //System.out.println("test Place lat after parsing"+latitude);
                            longitude = longitude.replace("[", "").replace("]", "");
                            longitude = longitude.replace("\"", "");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        float[] results = new float[3];
                        Location l = new Location("Point A");
                        if(latitude!=null&&longitude!=null&&current_lat!=null&&current_long!=null) {
                            //System.out.println("test Place lat"+latitude);
                            lat1 = Double.parseDouble(latitude);
                            long1 = Double.parseDouble(longitude);
                            lat2 = Double.parseDouble(current_lat);
                            long2 = Double.parseDouble(current_long);
                        }
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

                        try{
                            attachment = c.getJSONArray(TAG_ATTACHMENTS);
                            for (int j = 0; j < 2; j++) {
                                JSONObject img1 = attachment.getJSONObject(j);
                                JSONObject attachment_image =img1.getJSONObject(TAG_IMAGES);
                                JSONObject image_full = attachment_image.getJSONObject(TAG_FULL);
                                image_full_url=image_full.getString(TAG_IMAGES_URL);
                                attachment_url = img1.getString(TAG_ATTACHMENT_URL);
                                List_Img_url=List_Img_url+" "+image_full_url;
                                // System.out.println("url attachment"+attachment_url);
                                //System.out.println("url attachment_images_full"+image_full_url);

                            }
                        }

                        catch (JSONException e) {
                            e.printStackTrace();
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
                        // ar.add(listing_page_url);

                        //if(!x) {
                        boolean x=(post_id.contentEquals("749"))||(post_id.contentEquals("739"))||(post_id.contentEquals("549"));
                        //  if(x)
                        //{
                        //  continue;
                        //}
                        ar.add(content);
                        ar1.add(post_id);
                        ar2.add(pricing);
                        if(attachment.length()>0)
                        {  is_attachment_url_available=true;
                            ar3.add(image_full_url);
                            if(image_full_url!=null)
                                img=image_full_url.toString();
                          //  System.out.println("Attachment url added"+img);

                        }



                        else{
                            ar3.add(thumbnail_image);
                            List_Img_url=thumbnail_image;
                            if(thumbnail_image!=null)
                                img=thumbnail_image.toString();
                           // System.out.println("Thumbnail url added"+img);

                        }


                        ar4.add(company_street);
                        ar5.add(company_email);
                        ar6.add(listing_page_url);
                        ar7.add(is_attachment_url_available);
                      //  System.out.println("list url for test is"+List_Img_url);
                        ImageList.add(List_Img_url);
                        result.put(TAG_TITLE, title);
                        result.put(TAG_CONTENT, content);
                        result.put(TAG_IMAGES_URL, image_full_url);
                        // adding contact to contact list
                        resultList.add(result);
                        //String escaped = escapeHtml4(title);
                        //if(title!=null)
                       // System.out.println("Before sending the list img ="+img);
                        SearchResultsdata[i] = new SearchResults(title, img, company_email, geoDistance);
                        title = null;
                        attachment_url = null;
                        company_email = null;
                        thumbnail_image=null;
                        image_full_url=null;
                        is_attachment_url_available=false;
                        //img=null;

                        List_Img_url=null;
                        // }
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
                SearchResultsAdapter adapter = new SearchResultsAdapter(context,
                        R.layout.list_item_search_results2,
                        SearchResultsdata);

                //list.setAdapter(adapter);
                setListAdapter(adapter);
            } else {
                Toaster.toastLong("Couldn't get any data..Check You internet connection");
                // getActivity().finish();
            }
        }

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);


        if (isNetworkAvailable(context)) {
            // code here
            FragmentUtils.changeFragment(getActivity().getSupportFragmentManager(), R.id.mainContainer,
                    PageListingFragment.newInstance(ar7.get(position),ImageList.get(position),ar6.get(position),ar5.get(position),ar4.get(position),ar3.get(position),ar.get(position), ar1.get(position), ar2.get(position)), android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        } else {
            // code
            Toaster.toastLong("Check You internet connection");
        }


        // Starting single page listing activity
        // Intent in = new Intent(context,
        //       PageListingActivity.class);
        //in.putExtra(TAG_CONTENT, ar.get(position));

        //in.putExtra(TAG_CONTENT, ar.get(position));

        //in.putExtra(TAG_ID, ar1.get(position));
        //in.putExtra(TAG_PRICE, ar2.get(position));

        //startActivity(in);

    }


    @Override
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


    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


}
