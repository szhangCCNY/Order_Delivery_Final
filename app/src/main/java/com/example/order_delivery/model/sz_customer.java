package com.example.order_delivery.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/*
    This model is used by the manager to retrieve and set basic customer information
    also used to initialize custoemr when log in
 */
@ParseClassName("sz_customer")
public class sz_customer extends ParseObject {
    public static final String KEY_NAME = "name";
    public static final String KEY_BALANCE = "balance";
    public static final String KEY_WARNING = "warning";
    public static final String KEY_VIP = "vip";
    public static final String KEY_USERID = "userId";
    public static final String KEY_VERIFIED = "verified";
    public static final String KEY_ACTIVATE = "activate";
    public static final String KEY_BLACKLIST = "blacklist";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_ORDERCOUNT = "orderCount";
    public static final String KEY_SPENDING = "spending";

    public double getSpending(){return getNumber(KEY_SPENDING).doubleValue();}

    public void setSpending(double spending){put(KEY_SPENDING, spending);}

    public int getOrderCount(){
        return getNumber(KEY_ORDERCOUNT).intValue();
    }
    public void setOrderCount(int orderCount){
        put(KEY_ORDERCOUNT, orderCount);
    }
    public String getName(){
        return getString(KEY_NAME);
    }
    public void setName(String name){
        put(KEY_NAME, name);
    }
    public void setUserName(String username){
        put(KEY_USERNAME, username);
    }
    public String getUserName(){
        return getString(KEY_USERNAME);
    }

    public double getBalance(){
        return getNumber(KEY_BALANCE).doubleValue();
    }

    public void setBalance(double balance){
        put(KEY_BALANCE, balance);
    }

    public int getWarning(){
        return getNumber(KEY_WARNING).intValue();
    }

    public void setWarning(int warning){
        put(KEY_WARNING, warning);
    }
    public boolean getVip(){
        return getBoolean(KEY_VIP);
    }
    public void setVip(boolean vip ){
        put(KEY_VIP, vip);
    }
    public void setVerified(boolean verified ){
        put(KEY_VERIFIED, verified);
    }
    public boolean getVerified(){
        return getBoolean(KEY_VERIFIED);
    }
    public void setActivate(boolean activate ){
        put(KEY_ACTIVATE, activate);
    }
    public boolean getActivate(){
        return getBoolean(KEY_ACTIVATE);
    }
    public String getUserId(){
        return getString(KEY_USERID);
    }
    public void setUserId(String userId){
        put(KEY_USERID, userId);
    }
    public void setBlackList(boolean blackList ){
        put(KEY_BLACKLIST, blackList);
    }
    public boolean getBlackList(){
        return getBoolean(KEY_BLACKLIST);
    }
}