package com.example.android.javagitdevs;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    public static final String LOG_TAG = MainActivity.class.getName();


    /** Adapter for the list of earthquakes */
    private static DeveloperInfoAdapter mAdapter;

    /** URL for developers data from github site */
    private static final String GITHUB_REQUEST_URL =" https://api.github.com/search/users?q=type:user+location:lagos+language:java";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Find a reference to the {@link ListView} in the layout
        ListView developerListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
       developerListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new DeveloperInfoAdapter(this, new ArrayList<DeveloperInfo>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        developerListView.setAdapter(mAdapter);



        // Set an item click listener on the ListView, which sends an intent to open another acitivity to display the profile details


        developerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                // we create a new developer info object
                DeveloperInfo developerDetails = mAdapter.getItem(position);

                // we initiate an intent to call the Profile details activity
                Intent intent = new Intent(getApplicationContext(), ProfileDetails.class);

               // We use a bundle as the data container
                Bundle b = new Bundle();

                // Inserting data into the bundle
                b.putString("profileImage", developerDetails.getImageUrl());
                b.putString("username", developerDetails.getUsername());
                b.putString("webprofile", developerDetails.getUrl());

                // inserting bundle into the intent
                intent.putExtra("devBundle", b);


                // Send the intent to launch a new activity
                startActivity(intent);
            }
        });




        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();



        if (networkInfo != null && networkInfo.isConnected()) {

            // Start the AsyncTask to fetch the earthquake data
            DeveloperAsyncTask task = new DeveloperAsyncTask();
            task.execute(GITHUB_REQUEST_URL);

        } else {

            //otherwise , display error
            // First, hide loading indicator so error message will be visible

            // Hide loading indicator because the data has been loaded
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);


            // Set empty state text to display "No earthquakes found."
            mEmptyStateTextView.setText(R.string.no_internet_connection);


        }



    }



    private class DeveloperAsyncTask extends AsyncTask<String, Void, List<DeveloperInfo>> {


        /**
         * This method runs on a background thread and performs the network request.
         * We should not update the UI from a background thread, so we return a list of
         * {@link DeveloperInfo}s as the result.
         */


        @Override
        protected List<DeveloperInfo> doInBackground(String... urls) {

            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<DeveloperInfo> result = QueryUtils.fetchDeveloperData(urls[0]);
            return result;

        }

        @Override
        protected void onPostExecute(List<DeveloperInfo> data) {


            // Hide loading indicator because the data has been loaded
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Set empty state text to display "No _developers found)
            mEmptyStateTextView.setText(R.string.no_developers);

            mAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }


        }
    }







}
