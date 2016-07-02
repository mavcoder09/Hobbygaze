package com.hobbygaze.maverick.hobbygaze.fragments;

import com.squareup.picasso.Picasso;

/**
 * Created by maverick on 10/31/2015.
 */
public class Blog {

    public String title;
    public String img_url;
    public Picasso icon;

    public Blog() {
        super();
    }

    public Blog(String title, String img_url) {
        super();
        this.title = title;
        //this.icon = icon;
        this.img_url=img_url;
    }



}
