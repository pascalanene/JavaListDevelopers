package com.example.android.javagitdevs;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 9/17/2017.
 */

public class DeveloperInfoAdapter extends ArrayAdapter<DeveloperInfo> {



    private static final String LOG_TAG = DeveloperInfoAdapter.class.getSimpleName();




    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context The current context. Used to inflate the layout file.
     * @param developerInfo   A List of QuakeInfo objects to display in a list
     */
    public DeveloperInfoAdapter(Activity context, ArrayList<DeveloperInfo> developerInfo) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, developerInfo);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.developer_info, parent, false);
        }



        // Create a new DeveloperInfo object and Get the {@link DeveloperInfo} object located at this position in the list
        DeveloperInfo currentDeveloper = getItem(position);




        // Find the Image view in the developer_info.xml layout with the ID profile_image
        ImageView profileImage = (ImageView)listItemView.findViewById(R.id.profile_image);
        // display the image of the developer
        profileImage.setImageBitmap(currentDeveloper.getProfileImage());




        // Find the text view in the developer_info.xml layout with the ID profile_name
        TextView profileName = (TextView) listItemView.findViewById(R.id.profile_name);
        // display the username of the developer
        profileName.setText(currentDeveloper.getUsername());



        return  listItemView;
    }

}
