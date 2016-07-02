package com.hobbygaze.maverick.hobbygaze.fragments;

import com.squareup.picasso.Picasso;

/**
 * Created by abhishek on 11/21/15.
 */
public class SearchResults {

    public String title;
    public String img_url;
    public String email;
    String distance;
    public Picasso icon;

    public SearchResults() {
        super();
    }

    public SearchResults(String title, String img_url,String email,String distance) {
        super();
       // if(title!=null)
        this.title = title;
        this.distance=distance;
        //else
        //this.title=null;
        //this.icon = icon;
        this.img_url=img_url;
        this.email=email;
    }





}
