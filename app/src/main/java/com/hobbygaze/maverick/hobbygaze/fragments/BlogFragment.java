package com.hobbygaze.maverick.hobbygaze.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.hobbygaze.maverick.hobbygaze.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import xdroid.toaster.Toaster;

/**
 * Created by abhishek on 12/7/15.
 */
public class BlogFragment  extends ListFragment {

    Context context;
    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url = "http://www.hobbygaze.com/api/get_posts";


    Blog[] Blogdata;
    // JSON Node names
    private static final String TAG_POSTS = "posts";
    private static final String TAG_TITLE = "title";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_ATTACHMENTS ="attachments";
    private static final String TAG_ATTACHMENT_URL ="url" ;
    // contacts JSONArray
    JSONArray results = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> resultList;
    ArrayList<String> ar = new ArrayList<String>();
    String content=null;
    ListView list;
    public static BlogFragment newInstance() {

        return new BlogFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_blog, container, false);
        context = rootView.getContext();
        resultList = new ArrayList<HashMap<String, String>>();
       // list = rootView.getListView();
       // list = getListView();
        // Calling async task to get json
        new GetResults().execute();
        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);

        // Starting single page listing activity
        Intent in = new Intent(context,
                BlogPostsActivity.class);
        in.putExtra(TAG_CONTENT, ar.get(position));
        startActivity(in);

    }

    private void failedLoadingPosts() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Failed to load Posts. Have a look at LogCat.", Toast.LENGTH_SHORT).show();
            }
        });
    }

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

            String jsonStr = null;
            try {
                jsonStr = sh.run(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String attachment_url=null;
                    // Getting JSON Array node
                    results = jsonObj.getJSONArray(TAG_POSTS);
                    Blogdata = new Blog[results.length()];
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject c = results.getJSONObject(i);

                        String title = c.getString(TAG_TITLE);
                        content = c.getString(TAG_CONTENT);



                        JSONArray attachment = c.getJSONArray(TAG_ATTACHMENTS);
                        for (int j = 0; j< attachment.length();j++){
                            JSONObject img = attachment.getJSONObject(j);
                            attachment_url = img.getString(TAG_ATTACHMENT_URL);
                        }




                        HashMap<String, String> result = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        if(title.contains("Don&#8217;t"))
                            title="Don't let Your hobby die";
                        // System.out.println(title);
                        //System.out.println(attachment_url);
                        //System.out.println(content);
                        ar.add(content);
                        result.put(TAG_TITLE, title);
                        result.put(TAG_CONTENT, content);
                        result.put(TAG_ATTACHMENT_URL, attachment_url);
                        // adding contact to contact list
                        resultList.add(result);
                        //String escaped = escapeHtml4(title);

                        Blogdata[i] = new Blog(title, attachment_url);
                        title=null;
                        attachment_url=null;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
                //Toaster.toastLong("Couldn't get any data from the url");
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
            if(Blogdata!=null) {
                BlogAdapter adapter = new BlogAdapter(context,
                        R.layout.list_item_blog,
                        Blogdata);
                setListAdapter(adapter);
               // list.setAdapter(adapter);

            }
            else {
                Toaster.toastLong("Couldn't get any data..Check You internet connection");
                //finish();
            }
        }









    }






















}
