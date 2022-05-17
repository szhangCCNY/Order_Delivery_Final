package com.example.order_delivery.local_model;

import com.example.order_delivery.model.Employee;

/*
    When the Employee, chef or delivery, logs in, their information is saved in this local model
    this is used to get basic employee information without having to call query in the database
 */
public class CurrentEmployeeInfo {
    public static Employee employee;
    public static String currentEmployeeName;
    public static String currentEmployeeId;
    public static String currentEmployeeTitle;
    public static double currentEmployeeSalary;
    public static double currentEmployeeRating;
    public static int currentEmployeeWarning;
    public static int currentEmployeeCompliments;
    public static String currentEmployeeUsername;

    public CurrentEmployeeInfo(Employee employee){
        this.employee = employee;
        this.currentEmployeeId = employee.getEmployId();
        this.currentEmployeeWarning = employee.getWarning();
        this.currentEmployeeName = employee.getName();
        this.currentEmployeeTitle = employee.getEmployTitle();
        this.currentEmployeeSalary = employee.getSalary();
        this.currentEmployeeRating = employee.getRating();
        this.currentEmployeeCompliments = employee.getCompliment();
        this.currentEmployeeUsername = employee.getUsername();
    }
}
