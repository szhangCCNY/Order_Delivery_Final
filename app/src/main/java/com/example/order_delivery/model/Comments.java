package com.example.order_delivery.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/*
    The comments model is used to retrieve and set comments by the customer
 */
@ParseClassName("Comments")
public class Comments extends ParseObject {
    public static final String KEY_NAME = "username";
    public static final String KEY_RATING = "rating";
    public static final String KEY_COMMENT = "comment";
    public static final String KEY_ITEMNAME = "itemName";
    public static final String KEY_STATUS = "status";
    public static final String KEY_WEIGHT = "commentWeight";

    public String getUsername(){
        return getString(KEY_NAME);
    }

    public void setUsername(String username){
        put(KEY_NAME, username);
    }

    public int getRating(){
        return getNumber(KEY_RATING).intValue();
    }

    public void setRating(int rating){
        put(KEY_RATING, rating);
    }

    public String getComment(){
        return getString(KEY_COMMENT);
    }

    public void setComment(String comment){
        put(KEY_COMMENT, comment);
    }

    public String getItemName(){
        return getString(KEY_ITEMNAME);
    }

    public void setItemName(String itemName){
        put(KEY_ITEMNAME, itemName);
    }

    public String getStatus(){
        return getString(KEY_STATUS);
    }
    public void setStatus(String status){
        put(KEY_STATUS, status);
    }
    public int getWeight(){
        return getNumber(KEY_WEIGHT).intValue();
    }

    public void setWeight(int weight){
        put(KEY_WEIGHT, weight);
    }
}
