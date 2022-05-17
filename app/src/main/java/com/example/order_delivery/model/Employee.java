package com.example.order_delivery.model;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
/*
    The employee model is used by the manager retrieve and set employees
    The employee model is also used by the customer to see current employees in the restaurant
 */
@ParseClassName("Employee")
public class Employee extends ParseObject {

    public static final String KEY_USERNAME = "username";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMPLOYEEID = "employeeId";
    public static final String KEY_TITLE = "title";
    public static final String KEY_SALARY = "salary";
    public static final String KEY_RATING = "rating";
    public static final String KEY_WARNING = "warning";
    public static final String KEY_COMPLIMENT = "compliment";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_CATEIMAGE = "cateImage";
    public static final String KEY_GETRATINGCOUNT = "ratingCount";


    public int getRateCount(){
        return getNumber(KEY_GETRATINGCOUNT).intValue();
    }
    public void setRateCount(int count){
        put(KEY_GETRATINGCOUNT, count);
    }



    public static final String KEY_DEMOTENUM = "demoteNum";

    public ParseFile getCateImage(){
        return getParseFile(KEY_CATEIMAGE);
    }

    public String getCategory(){
        return getString(KEY_CATEGORY);
    }

    public void setCategory(String category){
        put(KEY_CATEGORY, category);
    }

    public int getDemoteNum(){
        return getNumber(KEY_DEMOTENUM).intValue();
    }

    public void setDemoteNum(int demoteNum){
        put(KEY_DEMOTENUM, demoteNum);
    }
    public int getCompliment(){
        return getNumber(KEY_COMPLIMENT).intValue();
    }

    public void setCompliment(int compliment){
        put(KEY_COMPLIMENT, compliment);
    }


    public int getWarning(){
        return getNumber(KEY_WARNING).intValue();
    }

    public void setWarning(int warning){
        put(KEY_WARNING, warning);
    }

    public String getUsername() {
        return getString(KEY_USERNAME);
    }
    public void setUsername(String name){put(KEY_USERNAME, name);}

    public String getName(){return getString(KEY_NAME);}
    public void setName(String name){put(KEY_NAME, name);}
    public String getEmployId() {
        return getString(KEY_EMPLOYEEID);
    }
    public void setEmployId(String employId){put(KEY_EMPLOYEEID, employId);}
    public String getEmployTitle() {
        return getString(KEY_TITLE);
    }
    public void setEmployTitle(String title){put(KEY_TITLE, title);}
    public double getSalary() {return getNumber(KEY_SALARY).doubleValue();}
    public void setSalary(double salary){
        put(KEY_SALARY, salary);
    }
    public double getRating() {
        return getNumber(KEY_RATING).doubleValue();
    }
    public void setRating(double rating){put(KEY_RATING, rating);}

    public void setNewEmployee(String username, String name, String title, double salary){
        put(KEY_USERNAME, username);
        put(KEY_NAME, name);
        put(KEY_EMPLOYEEID, username + ":" + name);
        put(KEY_TITLE, title);
        put(KEY_SALARY, salary);
        put(KEY_COMPLIMENT, 0);
        put(KEY_WARNING, 0);
        put(KEY_RATING, 0);
        put(KEY_DEMOTENUM, 0);
    }
}
