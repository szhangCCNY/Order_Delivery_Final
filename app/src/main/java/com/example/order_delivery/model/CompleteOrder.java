package com.example.order_delivery.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.parceler.Parcel;

@ParseClassName("CompleteOrder")
@Parcel(analyze={CompleteOrder.class})
public class CompleteOrder extends ParseObject {

    public static final String KEY_NAME = "customerName";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_LIST = "list";
    public static final String KEY_TOTAL = "totalCost";
    public static final String KEY_DELIVERY_PERSON = "deliveryPerson";
    public static final String KEY_DELIVER_JUST = "deliverJust";
    public static final String KEY_DELIVER_STAT = "deliveryStatus";
    public static final String KEY_DELIVERY_METHOD = "deliveryMethod";
    public static final String KEY_EMPLOYEE_RESPONSIBLE = "employeeResponsible";
    public static final String KEY_ORDER_STATUS = "orderStatus";

    public boolean getOrderStatus(){
        return getBoolean(KEY_ORDER_STATUS);
    }

    public void setOrderStatus(boolean status){
        put(KEY_ORDER_STATUS, status);
    }

    public String getDeliveryMethod(){
        return getString(KEY_DELIVERY_METHOD);
    }
    public void setDeliveryMethod(String deliveryMethod){
        put(KEY_DELIVERY_METHOD, deliveryMethod);
        System.out.println("delivery method called" + deliveryMethod);
    }

    public String getEmployeeResponsible(){
        return getString(KEY_EMPLOYEE_RESPONSIBLE);
    }

    public void setEmployeeResponsible(String employee){
        put(KEY_EMPLOYEE_RESPONSIBLE, employee);
        System.out.println("set employee method called" + employee);
    }

    public Boolean getDeliveryStatus(){
        return getBoolean(KEY_DELIVER_STAT);
    }

    public void setDeliveryStatus(boolean deliveryStatus){
        put(KEY_DELIVER_STAT, deliveryStatus);
    }

    public String getDeliverJust(){
        return getString(KEY_DELIVER_JUST);
    }

    public void setDeliverJust(String justification){
        put(KEY_DELIVER_JUST, justification);
    }
    public String getOrderId(){
        return getObjectId();
    }

    public String getDeliveryPerson(){
        return getString(KEY_DELIVERY_PERSON);
    }

    public void setDeliveryPerson(String deliveryPerson){
        put(KEY_DELIVERY_PERSON, deliveryPerson);
    }

    public String getName() {
        return getString(KEY_NAME);
    }
    public void setName(String name){
        put(KEY_NAME, name);
    }
    public String getAddress(){
        return getString(KEY_ADDRESS);
    }

    public void setAddress(String address){
        put(KEY_ADDRESS, address);
    }

    public String getList(){
        return getString(KEY_LIST);
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

    public void setList(String list){
        put(KEY_LIST, list);
    }
    public double getTotal(){
        return getNumber(KEY_TOTAL).doubleValue();
    }

    public void setTotal(double total){
        put(KEY_TOTAL, total);
    }
}
