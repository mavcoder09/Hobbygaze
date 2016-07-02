package com.hobbygaze.maverick.hobbygaze.fragments;

/**
 * Created by abhishek on 4/20/16.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.hobbygaze.maverick.hobbygaze.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class SwipeFragmentActivity extends Activity {
    private static final String TAG_IMAGE_LISTING = "image_listing";
    private static final String FLAG_ATTACHMENT_URL = "FLAG_IS_ATTACHMENT_AVAILABLE";
    String img1=null;
    String img2=null;
    String img_list=null;
    Bitmap[] result;
    ViewPager viewPager;
    String is_attachment_img_available;
    private ArrayList<String> IMAGES = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_page);
        //Button b=(Button)findViewById(R.id.change_pic);
        Intent in = getIntent();

        // Get JSON values from previous intent
        img_list = in.getStringExtra(TAG_IMAGE_LISTING);
        is_attachment_img_available=in.getStringExtra(FLAG_ATTACHMENT_URL);
        if(is_attachment_img_available.contentEquals("true")){
            if(img_list!=null){
                String[] split = img_list.split(" ");
                img1 = split[1];
                img2=split[2];
                System.out.println("Image list1="+img1);
                System.out.println("Image list2="+img2);
                IMAGES.add(img1);
                IMAGES.add(img2);

            }
        }
       else
        {
             System.out.println("Thumbnails is being shown now");
            if(img_list!=null){
               IMAGES.add(img_list);
            }


        }

         viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImageAdapter adapter = new ImageAdapter(this,IMAGES);
        viewPager.setAdapter(adapter);

    }


}