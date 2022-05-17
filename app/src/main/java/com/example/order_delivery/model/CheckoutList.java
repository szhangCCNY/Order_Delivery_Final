package com.example.order_delivery.model;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/*
    The checkoutlist model is used to retrieve information about order by the manager, chef, and delivery
    and set orders by the customer
 */
@ParseClassName("CheckoutList")
public class CheckoutList extends ParseObject {

    public static final String KEY_LIST = "list";
    public static final String KEY_TOTAL = "totalCost";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_DELIVERY = "delivery";

    public static final String KEY_CUSTNAME = "customerName";


    public String getCustomerName(){
        return getString(KEY_CUSTNAME);
    }

    public void setCustomerName(String customerName){
        put(KEY_CUSTNAME, customerName);
    }

    public String getList(){
        return getString(KEY_LIST);
    }

    public void setList(String list){
        put(KEY_LIST, list);
    }
    public double getTotal(){
        return getNumber(KEY_TOTAL).doubleValue();
    }

    public void setTotal(double total){
        put(KEY_TOTAL, total);
    }

    public String getAddress(){
        return getString(KEY_ADDRESS);
    }

    public void setAddress(String address){
        put(KEY_ADDRESS, address);
    }

    public boolean getDelivery(){
        return getBoolean(KEY_DELIVERY);
    }

    public void setDelivery(boolean delivery){
        put(KEY_DELIVERY, delivery);
    }

    public String getFormattedList(){
        String temp = getString(KEY_LIST);
        String[] test = temp.split(",");
        String order = "";
        for (String str : test) {
            String[] temp2 = str.split(":");
            order += "Item: " + temp2[0] + " x" + temp2[1] + "\n";
        }
        return order;
    }


//
//
//    public String getQuantity(){
//        return getString(KEY_QUANTITY);
//    }
//    public String getPrice(){return getString(KEY_PRICE);}
//    public String getItem(){return getString(KEY_ITEM);}
//    public String getImageURL(){
//        return getString(KEY_IMAGE);
//    }
//    public String getAddress() {return getString(KEY_ADDRESS);}
//    public boolean getDelivery(){return getBoolean(KEY_DELIVERY);}
//
//    public void setQuantity(String quantity){
//        put(KEY_QUANTITY, quantity);
//    }
//    public void setItem(String itemName){ put(KEY_ITEM, itemName);}
//    public void setPrice(String price){ put(KEY_PRICE, price);}
//    public void setImageURL(String imageURL){ put(KEY_IMAGE, imageURL);}
//    public void setAddress(String address){put(KEY_ADDRESS, address);}
//    public void setDelivery(boolean delivery){put(KEY_DELIVERY, delivery);}
}
