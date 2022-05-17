package com.example.order_delivery.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/*
    The response model is used only for manager - customer or manager - employee communication
    retrieve and set responses
    no customer and employee response
    the response is used by customer or employee to defend their positions
 */
@ParseClassName("Response")
public class Response extends ParseObject {
    public static final String KEY_USERNAME = "username";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_COMPLAINTID = "complaintId";

    public String getUsername(){
        return getString(KEY_USERNAME);
    }
    public void setUsername(String username){
        put(KEY_USERNAME, username);
    }

    public String getMessage(){
        return getString(KEY_MESSAGE);
    }
    public void setMessage(String message){
        put(KEY_MESSAGE, message);
    }

    public String getComplaintId(){
        return getString(KEY_COMPLAINTID);
    }
    public void setComplaintId(String messageid){
        put(KEY_COMPLAINTID, messageid);
    }
}