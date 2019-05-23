package com.example.sli_project;

public class personal_info {
   static String my_x;
   static String my_y;
    public personal_info(String my_x, String my_y) {
        this.my_x = my_x;
        this.my_y = my_y;
    }
    public personal_info() {
    }
    public String getMy_x() {
        return my_x;
    }
    public void setMy_x(String my_x) {
        this.my_x = my_x;
    }
    public String getMy_y() {
        return my_y;
    }
    public void setMy_y(String my_y) {
        this.my_y = my_y;
    }
}
