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
public class SearchResultsAdapter extends ArrayAdapter<SearchResults> {

    Context context;
    int layoutResId;
    SearchResults data[] = null;

    public SearchResultsAdapter(Context context, int layoutResId, SearchResults[] data) {
        super(context, layoutResId, data);
        this.layoutResId = layoutResId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchResultsHolder holder = null;

        if(convertView == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(layoutResId, parent, false);

            holder = new SearchResultsHolder();
            holder.imageIcon = (ImageView)convertView.findViewById(R.id.search_results_img);
            holder.SearchResultsHolder = (TextView)convertView.findViewById(R.id.search_results_title);
            holder.company_email=(TextView)convertView.findViewById(R.id.email);
            holder.geoDistance=(TextView)convertView.findViewById(R.id.gpsdistance);
            convertView.setTag(holder);
        }
        else
        {
            holder = (SearchResultsHolder)convertView.getTag();
        }

        SearchResults searchresults = data[position];
        if(searchresults!=null)
        {
            holder.SearchResultsHolder.setText(searchresults.title);
            //System.out.println("IMG URL="+searchresults.img_url);
            Picasso.with(this.context).load(searchresults.img_url).placeholder(R.drawable.fvhigh).fit().centerInside().into(holder.imageIcon);
            //holder.company_email.setText(searchresults.email);
            if(searchresults.distance!=null)
            holder.geoDistance.setText("Distance is "+searchresults.distance+" km away");
            else
                holder.geoDistance.setText("Turn on GPS to view distances");
        }
        return convertView;
    }

    static class SearchResultsHolder
    {
        ImageView imageIcon;
        TextView SearchResultsHolder;
        TextView company_email;
        TextView geoDistance;

    }
}