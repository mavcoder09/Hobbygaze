package com.hobbygaze.maverick.hobbygaze.fragments;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ShareEvent;
import com.hobbygaze.maverick.hobbygaze.FragmentUtils;
import com.hobbygaze.maverick.hobbygaze.R;

/**
 * Created by abhishek on 12/7/15.
 */
public class ShareFragment  extends Fragment {

    public static ShareFragment newInstance() {

        return new ShareFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View rootView = null;
        //Context context;

        String str1="Hi,I found a great app to find out interesting places nearby.Download on play store:Hobbgaze-https://goo.gl/lvga8D";
        Uri uri = Uri.parse(str1);

        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "" + uri);
        try {
            Answers.getInstance().logShare(new ShareEvent());
            startActivity(whatsappIntent);
            onBackPressed();
           // getActivity().finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast toast = Toast.makeText(getActivity(), "Whatsapp not installed", Toast.LENGTH_LONG);
            toast.show();
        }
        return rootView;
    }


    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

}
