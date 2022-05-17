package com.example.order_delivery.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/*
    The notification model is used by manager, employee, and customer
    the messages is mainly used to complaint and compliment by customer
    and complaint for employees
    employees and customer are able to see complaints/ compliments filed against them
    model retrieves and set messages
 */
@ParseClassName("Notification")
public class Notification extends ParseObject {
    public static final String KEY_USERNAME = "toUsername";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_FROMUSER = "fromUser";
    public static final String KEY_TYPE = "type";
    public static final String KEY_TARGET = "targetPerson";
    public static final String KEY_FROM_USERTYPE = "fromUserType";
    public static final String KEY_COMPLAINTID = "complaintId";

    public String getFromUserType(){
        return getString(KEY_FROM_USERTYPE);
    }

    public void setFromUserType(String userType){
        put(KEY_FROM_USERTYPE, userType);
    }

    public String getTarget(){
        return getString(KEY_TARGET);
    }

    public void setTarget(String targetPerson){
        put(KEY_TARGET, targetPerson);
    }


    public String getType(){
        return getString(KEY_TYPE);
    }

    //type can put anything, but rn focus on compliment, complaint, and normal
    public void setType(String type){
        put(KEY_TYPE, type);
    }

    public String getToUsername(){
        return getString(KEY_USERNAME);
    }

    public void setToUsername(String username){
        put(KEY_USERNAME, username);
    }

    public String getMessage(){
        return getString(KEY_MESSAGE);
    }
    public void setMessage(String message){
        put(KEY_MESSAGE, message);
    }

    public String getSubject(){
        return getString(KEY_SUBJECT);
    }

    public void setSubject(String subject){
        put(KEY_SUBJECT, subject);
    }

    public String getFromUser(){
        return getString(KEY_FROMUSER);
    }

    public void setFromUser(String fromUser){
        put(KEY_FROMUSER, fromUser);
    }

    public String getComplaintid(){
        return getString(KEY_COMPLAINTID);
    }
    public void setComplaintId(String complaintId){
        put(KEY_COMPLAINTID, complaintId);
    }

    //in terms of customer
    //Notification notification = new Notification()
    //notifcation.setToUsername(), setSubject, setMessage, setFrom
    //from is always current user
    //toUsername = manager
    //after setting everthing call
    //notification.saveInBackground()'

}
