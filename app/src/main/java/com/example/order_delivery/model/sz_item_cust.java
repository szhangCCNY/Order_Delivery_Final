package com.example.order_delivery.model;


import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import org.parceler.Parcel;

/*
    This class retrieves menu items for customer
    this class sets menu items for customer by chefs
 */
@ParseClassName("sz_item_cust")
@Parcel(analyze={sz_item_cust.class})
public class sz_item_cust extends ParseObject {
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_PRICE = "price";
    public static final String KEY_RATING = "rating";
    public static final String KEY_IMGFILE = "imgFile";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_COUNT = "orderCount";
    public static final String KEY_RATE_COUNT = "ratingCount";
    public static final String KEY_CHEF = "chef";
//    public static final String KEY_OBJECTID = "objectId";

    //blank constructor for parceler
    public sz_item_cust(){}

    public int getRatingCount(){
        return getNumber(KEY_RATE_COUNT).intValue();
    }
    public void setRatingCount(int rateCount){
        put(KEY_RATE_COUNT, rateCount);
    }
    public String getItemName() {
        return getString(KEY_NAME);
    }
    public void setItemName(String name) { put(KEY_NAME, name);}
    public String getItemDescription() {
        return getString(KEY_DESCRIPTION);
    }
    public void setItemDescription(String description){
        put(KEY_DESCRIPTION, description);
    }
    public double getItemPrice() {
        return getNumber(KEY_PRICE).doubleValue();
    }
    public void setItemPrice(double price){put(KEY_PRICE, price);}
    public double getItemRating() {
        return getNumber(KEY_RATING).doubleValue();
    }
//    public String getObjectId() {return getString(KEY_OBJECTID);}
    public void setItemRating(double rating){put(KEY_RATING, rating);}
    public ParseFile getItemImage() {
        return getParseFile(KEY_IMGFILE);
    }
    public void setItemImage(ParseFile parseFile) {
        put(KEY_IMGFILE, parseFile);
    }

    public String getCategory(){
        return getString(KEY_CATEGORY);
    }
    //remember to chagne category to chef name
    //need to use it for multi menu in cust
    public void getCategory(String category){put(KEY_CATEGORY, category);}

    public int getOrderCount(){return getNumber(KEY_COUNT).intValue();}
    public void setOrderCount(int orderCount){put(KEY_COUNT, orderCount);}

    public void setChef(String chef) {
        put(KEY_CHEF, chef);
    }

    public String getChef(){
        return getString(KEY_CHEF);
    }
}
