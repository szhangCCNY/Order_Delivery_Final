package com.example.order_delivery;

import android.app.Application;

import com.example.order_delivery.model.Bid;
import com.example.order_delivery.model.CheckoutList;
import com.example.order_delivery.model.Comments;
import com.example.order_delivery.model.CompleteOrder;
import com.example.order_delivery.model.Employee;
import com.example.order_delivery.model.Notification;
import com.example.order_delivery.model.Response;
import com.example.order_delivery.model.TrackOrder;
import com.example.order_delivery.model.sz_customer;
import com.example.order_delivery.model.sz_item_cust;
import com.parse.Parse;
import com.parse.ParseObject;

/*
    This class is the name of our application
    use to initialize parse connection
    without this class, application would not run
 */
public class  Order_Delivery extends Application {
    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(TrackOrder.class);
        ParseObject.registerSubclass(sz_item_cust.class);
        ParseObject.registerSubclass(CheckoutList.class);
        ParseObject.registerSubclass(sz_customer.class);
        ParseObject.registerSubclass(Comments.class);
        ParseObject.registerSubclass(Employee.class);
        ParseObject.registerSubclass(Notification.class);
        ParseObject.registerSubclass(Response.class);
        ParseObject.registerSubclass(CompleteOrder.class);
        ParseObject.registerSubclass(Bid.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("1igYSiq7EIkI0M0fQrjwoU1qdbcqtwR2NhwNXiuY")
                .clientKey("yjjrxIe00LlVbo7DnkYFOIYoKDkwqnrja3YynidM")
                .server("https://parseapi.back4app.com")
                .build()
        );

    }
}
