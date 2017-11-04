package com.example.android.javagitdevs;

import android.graphics.Bitmap;

/**
 * Created by user on 9/17/2017.
 */

public class DeveloperInfo {

    // state for the username of the developer
    private String mUsername;

    // state for the bitmap type of the profile image
    private Bitmap mProfileImage;

    // state for the url of the developer.
    private String mUrl;

    // state for the image url of the profile image
    private String mImageUrl;


    /*
    ** Constructor for creating instances or objects of this class
     */
    public DeveloperInfo(String username, String url, Bitmap profileImage, String imageUrl){

        mUsername = username;
        mProfileImage = profileImage;
        mUrl = url;
        mImageUrl = imageUrl;
    }


    // method for getting the username
    public String getUsername() {
        return mUsername;
    }

    // method for getting the profile image id
    public Bitmap getProfileImage() {
        return mProfileImage;
    }

    // method for getting the url
    public  String getUrl(){
        return  mUrl;
    }

    // method for getting the image url
    public String getImageUrl() {

        return  mImageUrl;
    }

}
