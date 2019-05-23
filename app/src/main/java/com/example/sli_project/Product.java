package com.example.sli_project;

/**
 * Created by ZANA on 9/29/2017.
 */

public class Product {

    private String name;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubmit() {
        return submit;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }

    private String date;
    private String submit;
    private String phone;
    private String x;
    private String y;
    private String id;
    private String city;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    private String age;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Product(String name, String phone,String x, String y, String city, String id, String date,String submit,String age,String type ) {
        this.name = name;
        this.phone = phone;
        this.x = x;
        this.y = y;
        this.city = city;
        this.id=id;
        this.date=date;
        this.age=age;
        this.type=type;
        this.submit=submit;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getX() {
        return x;
    }
    public void setX(String x) {
        this.x = x;
    }
    public String getY() {
        return y;
    }
    public void setY(String y) {
        this.y = y;
    }

}
