package com.example.order_delivery.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/*
    Bid model is used by the manager to retrieve bids from delivery person
    and used by delivery person to set bids
 */
@ParseClassName("Bid")
public class Bid extends ParseObject {
    public static final String KEY_BID = "bid";
    public static final String KEY_DELIVERY_PERSON = "deliveryPerson";
    public static final String KEY_ORDERID = "orderId";

    public String getOrderId(){
        return getString(KEY_ORDERID);
    }

    public void setOrderId(String orderId){
        put(KEY_ORDERID, orderId);
    }

    public String getDeliveryPerson(){
        return getString(KEY_DELIVERY_PERSON);
    }

    public void setDeliveryPerson(String deliveryPerson){
        put(KEY_DELIVERY_PERSON, deliveryPerson);
    }

    public double getBid(){
        return getNumber(KEY_BID).doubleValue();
    }

    public void setBid(double bid){
        put(KEY_BID, bid);
    }
}