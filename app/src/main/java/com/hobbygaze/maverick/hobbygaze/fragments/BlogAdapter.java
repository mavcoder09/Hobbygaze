package com.hobbygaze.maverick.hobbygaze.fragments;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hobbygaze.maverick.hobbygaze.R;
import com.squareup.picasso.Picasso;

/**
 * Created by abhishek on 11/20/15.
 */
public class BlogAdapter extends ArrayAdapter<Blog> {

    Context context;
    int layoutResId;
    Blog data[] = null;

    public BlogAdapter(Context context, int layoutResId, Blog[] data) {
        super(context, layoutResId, data);
        this.layoutResId = layoutResId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BlogHolder holder = null;

        if(convertView == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(layoutResId, parent, false);

            holder = new BlogHolder();
            holder.imageIcon = (ImageView)convertView.findViewById(R.id.blog_img);
            holder.BlogTitle = (TextView)convertView.findViewById(R.id.blog_title);

            convertView.setTag(holder);
        }
        else
        {
            holder = (BlogHolder)convertView.getTag();
        }

        Blog blog = data[position];
       if(blog!=null) {
            holder.BlogTitle.setText(blog.title);
            Picasso.with(this.context).load(blog.img_url).placeholder(R.drawable.fvhigh).fit().centerInside().into(holder.imageIcon);
        }

        return convertView;
    }

    static class BlogHolder
    {
        ImageView imageIcon;
        TextView BlogTitle;

    }
}