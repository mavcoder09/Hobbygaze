package com.hobbygaze.maverick.hobbygaze.oldfiles;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by abhishek on 12/1/15.
 */
public class ShareActivity  extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
String str1="Hi,I found a great app to find out interesting places nearby.Download on play store:Hobbgaze-https://goo.gl/lvga8D";
        Uri uri = Uri.parse(str1);

        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "" + uri);
        try {
            startActivity(whatsappIntent);
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast toast = Toast.makeText(getApplicationContext(), "Whatsapp not installed", Toast.LENGTH_LONG);
            toast.show();
        }

    }


}
