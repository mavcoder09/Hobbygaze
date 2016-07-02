package com.hobbygaze.maverick.hobbygaze.oldfiles;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.hobbygaze.maverick.hobbygaze.R;

/**
 * Created by abhishek on 11/24/15.
 */
public class Browser extends Activity {

    WebView browser;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.sample2);
        browser=(WebView)findViewById(R.id.web);

        browser.loadData("<html><body><p>Step Up Dance Academy ,JP Nagar is the place where you can hone your dancing skills and the X-factor for Hip Hoppers.</p> <table class=\"table table-no-border\"> <tbody> <tr> <td colspan=\"3\"><span class=\"labelHeading\">Highlights</span></td> </tr> <tr> <td colspan=\"1\"> <ul> <li>Classical</li> <li>Free Style</li> <li>Hip Hop</li> <li>Folk</li> <li>Western</li> </ul> </td> <td colspan=\"1\"> <ul> <li>Disco</li> <li>Bhangra</li> <li>B-boying</li> <li>Locking</li> <li>Popping</li> </ul> </td> <td colspan=\"1\"> <ul> <li>Flow</li> <li>Salsa</li> <li>Zumba</li> </ul> </td> </tr> <tr> <td colspan=\"3\"></td> </tr> <tr> <td colspan=\"3\"><span class=\"labelHeading\">Other Facilities</span></td> </tr> <tr> <td colspan=\"1\"> <ul> <li>Personal Training</li> <li>Event Management</li> <li>School Choreography</li> </ul> </td> <td colspan=\"1\"> <ul> <li>Corporate Shows</li> <li>Competitions held</li> <li>Free classes for handicapped</li> </ul> </td> </tr> <tr> <td colspan=\"3\"></td> </tr> <tr> <td colspan=\"3\"><span class=\"labelHeading\">Pricing</span></td> </tr> <tr> <td colspan=\"3\">**<a href=\"#wpb_widget-2\"><em>For pricing details see here</em></a></td> </tr> <tr> <td colspan=\"3\"></td> </tr> <tr> <td colspan=\"3\"> <map style=\"line-height: 1.6em;\"><em>Are we missing anything? <a href=\"#modal_1\">Suggest</a><div id=\"modal_1\" class=\"modalDialog\"><div class=\"modelPane\"><a href=\"#close\" title=\"Close\" class=\"close\">X</a><div class=\"modelTitle\">Suggestions</div><div class=\"modal-body\"><div role=\"form\" class=\"wpcf7\" id=\"wpcf7-f5-p1213-o2\" lang=\"en-US\" dir=\"ltr\"> <div class=\"screen-reader-response\"></div> <form action=\"/api/get_search_results/?search=dance&#038;v=c86ee0d9d7ed#wpcf7-f5-p1213-o2\" method=\"post\" class=\"wpcf7-form\" novalidate=\"novalidate\"> <div style=\"display: none;\"> <input type=\"hidden\" name=\"_wpcf7\" value=\"5\" /> <input type=\"hidden\" name=\"_wpcf7_version\" value=\"4.3\" /> <input type=\"hidden\" name=\"_wpcf7_locale\" value=\"en_US\" /> <input type=\"hidden\" name=\"_wpcf7_unit_tag\" value=\"wpcf7-f5-p1213-o2\" /> <input type=\"hidden\" name=\"_wpnonce\" value=\"73f41ed4eb\" /> </div> <p>Your Email (required)<br /> <span class=\"wpcf7-form-control-wrap your-email\"><input type=\"email\" name=\"your-email\" value=\"\" size=\"40\" class=\"wpcf7-form-control wpcf7-text wpcf7-email wpcf7-validates-as-required wpcf7-validates-as-email\" aria-required=\"true\" aria-invalid=\"false\" /></span> </p> <p>Your Message<br /> <span class=\"wpcf7-form-control-wrap your-message\"><textarea name=\"your-message\" cols=\"40\" rows=\"10\" class=\"wpcf7-form-control wpcf7-textarea\" aria-invalid=\"false\"></textarea></span> </p> <p><input type=\"submit\" value=\"Send\" class=\"wpcf7-form-control wpcf7-submit\" /></p> <div class=\"wpcf7-response-output wpcf7-display-none\"></div></form></div></div></div></div></em></map> </td> </tr> </tbody> </table> <p>&nbsp;</p> <div class=\"sharedaddy sd-sharing-enabled\"><div class=\"robots-nocontent sd-block sd-social sd-social-icon sd-sharing\"><h3 class=\"sd-title\">Share this:</h3><div class=\"sd-content\"><ul><li class=\"share-facebook\"><a rel=\"nofollow\" data-shared=\"sharing-facebook-1213\" class=\"share-facebook sd-button share-icon no-text\" href=\"http://www.hobbygaze.com/listing/step-up-dance-academy-jp-nagar/?share=facebook\" target=\"_blank\" title=\"Share on Facebook\"><span></span><span class=\"sharing-screen-reader-text\">Share on Facebook (Opens in new window)</span></a></li><li class=\"share-twitter\"><a rel=\"nofollow\" data-shared=\"sharing-twitter-1213\" class=\"share-twitter sd-button share-icon no-text\" href=\"http://www.hobbygaze.com/listing/step-up-dance-academy-jp-nagar/?share=twitter\" target=\"_blank\" title=\"Click to share on Twitter\"><span></span><span class=\"sharing-screen-reader-text\">Click to share on Twitter (Opens in new window)</span></a></li><li class=\"share-whatsapp\"><a rel=\"nofollow\" data-shared=\"\" class=\"share-whatsapp sd-button share-icon no-text\" href=\"http://www.hobbygaze.com/listing/step-up-dance-academy-jp-nagar/?share=whatsapp\" target=\"_blank\" title=\"Click to share on WhatsApp\"><span></span><span class=\"sharing-screen-reader-text\">Click to share on WhatsApp (Opens in new window)</span></a></li><li class=\"share-google-plus-1\"><a rel=\"nofollow\" data-shared=\"sharing-google-1213\" class=\"share-google-plus-1 sd-button share-icon no-text\" href=\"http://www.hobbygaze.com/listing/step-up-dance-academy-jp-nagar/?share=google-plus-1\" target=\"_blank\" title=\"Click to share on Google+\"><span></span><span class=\"sharing-screen-reader-text\">Click to share on Google+ (Opens in new window)</span></a></li><li class=\"share-email\"><a rel=\"nofollow\" data-shared=\"\" class=\"share-email sd-button share-icon no-text\" href=\"http://www.hobbygaze.com/listing/step-up-dance-academy-jp-nagar/?share=email\" target=\"_blank\" title=\"Click to email this to a friend\"><span></span><span class=\"sharing-screen-reader-text\">Click to email this to a friend (Opens in new window)</span></a></li><li class=\"share-end\"></li></ul></div></div></div></body></html>",
                "text/html", "UTF-8");
    }







}
