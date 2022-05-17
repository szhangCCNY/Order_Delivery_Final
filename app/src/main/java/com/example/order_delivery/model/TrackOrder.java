package com.example.order_delivery.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.File;

/*
    This class tracks user orders primarily used to track number of times
    an item is order by the user
    used for user popular items
 */
@ParseClassName("TrackOrder")
public class TrackOrder extends ParseObject {
    public static final String KEY_USERNAME = "username";
    public static final String KEY_COUNT = "count";
    public static final String KEY_ITEMNAME = "itemName";
    public static final String KEY_IMAGE = "imageUrl";

    //idea get TrackOrder
    //if exist
        //update count

    //else
        //set all fields with current order

    public int getCount(){
        return getNumber(KEY_COUNT).intValue();
    }
    public void setCount(int count){
        put(KEY_COUNT, count);
    }

    public String getUserName(){
        return getString(KEY_USERNAME);
    }
    public void setUserName(String username){
        put(KEY_USERNAME, username);
    }


    public void setImageUrl(String imageUrl){
        put(KEY_IMAGE, imageUrl);
    }
    public String getImageUrl(){return getString(KEY_IMAGE);}

    public String getItemName(){
        return getString(KEY_ITEMNAME);
    }
    public void setItemName(String itemName){put(KEY_ITEMNAME, itemName);}

}