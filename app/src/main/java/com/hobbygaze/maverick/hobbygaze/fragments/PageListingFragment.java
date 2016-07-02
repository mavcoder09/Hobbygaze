package com.hobbygaze.maverick.hobbygaze.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hobbygaze.maverick.hobbygaze.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import xdroid.toaster.Toaster;

/**
 * Created by abhishek on 11/25/15.
 */
public class PageListingFragment extends Fragment {
    private static final String TAG_CONTENT = "content";
    private static final String TAG_IMAGE_LISTING = "image_listing";
    private static final String FLAG_ATTACHMENT_URL = "FLAG_IS_ATTACHMENT_AVAILABLE";


    private static final String TAG_HOURS_OF_OP = "hours_of_operation";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_PRICE = "Pricing";

    static String post_id;
    Context context;
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
    TextView phone;
    String Mon;
    String Tue;
    String Wed;
    String Thu;
    String Fri;
    String Sat;
    String Sun;
    static String content;
    static String price;
    static String l_img=null;
    static String company_addr=null;
    static String company_email=null;
    static String company_url=null;
    static String company_img_list=null;
    JSONArray result = null;
    JSONArray result_phone = null;
    static boolean is_attachmenturl_available=false;
    static String is_url_available_attachment=null;
    WebView ourBrow;
    String phone_no=null;

    public static PageListingFragment newInstance(boolean is_attachment_url_available,String ImageList,String listing_page_url,String email,String addr,String listing_url,String content,String id,String price) {
        getDataFromSearchResults(is_attachment_url_available,ImageList,listing_page_url,email,addr,listing_url,content,id,price);
        return new PageListingFragment();
    }

    public static void getDataFromSearchResults(boolean th,String img_list,String l,String e,String a,String lurl,String c,String i,String p) {
        company_img_list=null;
        company_url=null;
        company_email=null;
        l_img=null;
        company_addr=null;
        content=null;
        post_id=null;
        price=null;
        is_url_available_attachment=null;
        is_attachmenturl_available=th;
        is_url_available_attachment = String.valueOf(is_attachmenturl_available);
        if(img_list!=null)
        company_img_list=img_list.toString();
        if(l!=null)
        company_url=l.toString();
        if(e!=null)
        company_email=e.toString();
        if(a!=null){
            company_addr=a.toString();
            company_addr=company_addr.replaceAll("\\\\r\\\\n", "");
            company_addr=company_addr.replaceAll("\\\\a\\\\b", "");
            company_addr=company_addr.replaceAll("\\\\f\\\\t", "");
            company_addr=company_addr.replaceAll("\\\\v", "");
            company_addr=company_addr.replaceAll("\\\\/","/");
           System.out.println("address after converting="+company_addr);



        }

        if(lurl!=null)
        l_img=lurl.toString();
        if(c!=null)
      content=c.toString();
        if(i!=null)
        post_id=i.toString();
        if(p!=null)
      price=p.toString();
    }
    public class ourViewClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView v, String url) {
            v.loadUrl(url);
            return true;

        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_listing_places, container, false);
        context = rootView.getContext();

        ImageView imageIcon = (ImageView)rootView.findViewById(R.id.listing_img);
        Button b=(Button)rootView.findViewById(R.id.g);
        b.setBackgroundColor(Color.TRANSPARENT);




        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(context,
                        SwipeFragmentActivity.class);
                in.putExtra(TAG_IMAGE_LISTING, company_img_list);
                in.putExtra(FLAG_ATTACHMENT_URL,is_url_available_attachment);
                startActivity(in);

            }
        });

        TextView ad=(TextView)rootView.findViewById(R.id.addr);
        ad.setText(company_addr);
        ad.setVisibility(View.GONE);
        TextView mail=(TextView)rootView.findViewById(R.id.email);
        mail.setText(company_email);
        mail.setVisibility(View.GONE);
        phone=(TextView)rootView.findViewById(R.id.phone_no);
        phone.setText(null);
        ourBrow = (WebView)rootView.findViewById(R.id.web);

        if(l_img!=null)
           // Picasso.with(this.context).load(l_img).placeholder(R.drawable.fvhigh).fit().centerInside().into(imageIcon);
        Picasso.with(this.context).load(l_img).placeholder(R.drawable.fvhigh).resize(2000, 1000).into(imageIcon);

        else
            Picasso.with(this.context).load(R.drawable.fvhigh).placeholder(R.drawable.fvhigh).into(imageIcon);
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
        //Intent in = getIntent();

        // Get JSON values from previous intent
       // content = in.getStringExtra(TAG_CONTENT);
        //post_id=in.getStringExtra(TAG_ID);
        //price=in.getStringExtra(TAG_PRICE);

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
        //System.out.println("Monday timing"+Mon);



        return rootView;
    }

    private class GetResults2 extends AsyncTask<Void, Void, Void> {

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

                    result_phone = jsonObj.getJSONArray(TAG_PHONE);
                    if(result_phone.toString()!=null) {
                        phone_no = result_phone.toString();
                        phone_no = phone_no.replace("[", "").replace("]", "");
                        phone_no = phone_no.replace("\"", "");
                        phone_no=phone_no.replaceAll("\\\\/","/");

                    }
                    System.out.println("Phone no=" + phone_no);
                    result = jsonObj.getJSONArray(TAG_HOURS_OF_OP);

                   // for (int i = 0; i < result.length(); i++) {
                     //   JSONObject c = result.getJSONObject(i);
                       // phone_no=c.getString();
                    //}



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
            phone.setText(phone_no);
            phone.setVisibility(View.GONE);
            //phone_no=null;
            if(Mon!=null&&Tue!=null&&Wed!=null&&Thu!=null&&Fri!=null&&Sat!=null&&Sun!=null){
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

            }


            content=content.replaceAll("Highlights","<span style=\"font-weight: 700;color: #00C3FE;\">Highlights</span>");
            //String hrs=Mon+Tue+Wed+Thu+Fri+Sat+Sun;
            content=content.replaceAll("Other Facilities","<span style=\"font-weight: 700;color: #00C3FE;\">Other Facilities</span>");

            String[] split = content.split("Pricing");
            content = split[0] + "</span></td></tr></tbody></table>";
            String[] splitto=content.split("<map style");
            content=splitto[0]+"</td></tr></tbody></table>";
          //  System.out.println("split1"+" " +split[0]);
           // System.out.println("split2"+" " +splitto[0]);



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


            String pricing1="<span style=\"font-weight: 700;color: #00C3FE;\">Pricing:</span>";
            String addr1="<span style=\"font-weight: 700;color: #00C3FE;\">Address:</span>";
            String phone1="<span style=\"font-weight: 700;color: #00C3FE;\">Phone:</span>";
            String email1="<span style=\"font-weight: 700;color: #00C3FE;\">Email:</span>";
            if(company_addr==null)
                company_addr="Not Available";
            if(company_email==null)
                company_email="Not Available";
            if(phone_no==null)
                phone_no="Not Available";

            try {

                ourBrow.loadData("<html><body style=\"margin:0px; padding:0px; width:100%; height:100%\">"+"<br>"+"</br>"+addr1+"&nbsp;"+company_addr+"<br>"+"&nbsp;</br>"+"\n"+ phone1+"&nbsp;"+phone_no+"<br>"+"&nbsp;</br>"+"\n"+email1+"&nbsp;"+company_email+"<br>"+"&nbsp;</br>"+pricing1+"<br>"+"<a href=" + company_url + ">**For pricing details see here</a>"+"<br>"+"&nbsp;</br>"+content + "<span style=\"font-weight: 700;color: #00C3FE; padding-bottom:10px\">Hours of Operation</span>"+"<br>"+"&nbsp;</br>"+"\n"+ table+ "</body></html>",
                        "text/html; charset=UTF-8", null);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


}
