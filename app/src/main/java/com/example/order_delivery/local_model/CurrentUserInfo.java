package com.example.order_delivery.local_model;

import com.example.order_delivery.model.sz_customer;

/*
    When the customer logs in, their basic information is saved in this local model
    this is used to get basic customer information without having to call query in the database
 */
public class CurrentUserInfo {
    public static sz_customer currentUser;
    public static String currentUserId;
    public static String currentUserName;
    public static double currentUserBalance;
    public static boolean currentUserVip;
    public static int currentUserWarning;
    public static int currentUserOrderCount;


    public CurrentUserInfo(sz_customer currentUser){
        this.currentUser = currentUser;
        this.currentUserId = currentUser.getObjectId();
        this.currentUserName = currentUser.getName();
        this.currentUserBalance = currentUser.getBalance();
        this.currentUserVip = currentUser.getVip();
        this.currentUserWarning = currentUser.getWarning();
        this.currentUserOrderCount = currentUser.getOrderCount();
    }
}
