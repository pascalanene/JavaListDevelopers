package com.example.android.javagitdevs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.button;
import static android.R.attr.data;

public class ProfileDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);


        // here we get the intent first
        Intent intent = getIntent();

        // then we extract the bundle containing our data
        Bundle b = intent.getBundleExtra("devBundle");

        // from the bundle we extract the image url
        String imageURL = b.getString("profileImage");

        // Start the AsyncTask to fetch the processed Bitmap image
        BitmapAsyncTask task = new BitmapAsyncTask();
        task.execute(imageURL);



        // from the bundle we extract the username
        final String devUsername = b.getString("username");
        TextView nameView = (TextView) findViewById(R.id.username_view);
        nameView.setText(devUsername);

        // from the bundle we extract the web profile url
        // we also set an on click listener on to initiate a browser intent
        final String webURL = b.getString("webprofile");
        TextView profileUrlView = (TextView) findViewById(R.id.profile_url_view);
        profileUrlView.setText(webURL);

       profileUrlView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses url

                Uri webpage = Uri.parse(webURL);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

            }
        });



        // we reference the button and set an cnClick listener on the button
        // we also set an on click listener on to initiate a share intent
        Button shareButton = (Button) findViewById(R.id.button_share_view);

        shareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                // We build the intent and specify a send action to make it a share intent
                Intent intent = new Intent(Intent.ACTION_SEND);


                // Always use string resources for UI text.
                // This says Check out this awesome developer @<github username>, <github profile url
                String title = getResources().getString(R.string.chooser_title) + "\n" + devUsername + "\n" + webURL;
                // Create intent to show chooser
                Intent chooser = Intent.createChooser(intent, title);

                // Verify the intent will resolve to at least one activity
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }


            }
        });



    }



    // the essence of usingn an async task will take off the bitmap image processing off the main thread
    // leaving UI to perform without hitches
    private class BitmapAsyncTask extends AsyncTask<String, Void, Bitmap> {

        /**
         * This method runs on a background thread and performs the image processing
         * We should not update the UI from a background thread, so we return bitmap image as the result
         */

        @Override
        protected Bitmap doInBackground(String... urls) {

            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            Bitmap processedImage = QueryUtils.extractBitmap(urls[0]);

            return processedImage;

        }

        @Override
        protected void onPostExecute(Bitmap image) {


            // Find the Image view in the developer_info.xml layout with the ID profile_image
            ImageView profileImage = (ImageView) findViewById(R.id.profile_image_view);
            // display the image of the developer
            profileImage.setImageBitmap(image);

        }
    }

}
