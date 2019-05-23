package com.example.sli_project;

/**
 * Created by ZANA on 9/29/2017.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class CustomListAdapter extends ArrayAdapter<Product> {
    RelativeLayout relativeLayout;
    private ArrayList<Product> arraylist;
    ArrayList<Product> products;
    Context context;
    int resource;
    TextView city,age,name,blood_type,msafa;
    personal_info personal_info=new personal_info();
    ImageView imageView,call;
    String num;
    public CustomListAdapter(Context context, int resource, ArrayList<Product> products) {
        super(context, resource, products);
        this.products = products;
        this.context = context;
        this.resource = resource;
        this.arraylist = new ArrayList<Product>();
        this.arraylist.addAll(products);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_list_layout,null,true);
        }
        relativeLayout=(RelativeLayout) convertView.findViewById(R.id.doctor_list_layout);
        convertView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition_animation));
        final Product product = getItem(position);
        num=product.getPhone().toString();
        double distance,mm;
        Location locationA = new Location("");
        double mx=Double.parseDouble(personal_info.getMy_x());
        double  my=Double.parseDouble(personal_info.getMy_y());
        double x= Double.parseDouble(product.getX().toString());
        double y= Double.parseDouble(product.getY().toString());
        locationA.setLatitude(mx);
        locationA.setLongitude(my);
        Location locationB = new Location("");
        locationB.setLatitude(x);
        locationB.setLongitude(y);
        distance = locationA.distanceTo(locationB)/1000;
        mm = locationA.distanceTo(locationB);
        int km= (int) distance;
        int meter= (int)mm;
        imageView =(ImageView)convertView.findViewById(R.id.valid);
       String valid=product.getSubmit();
        if (valid.equals("NO")){
            imageView.setImageResource(R.mipmap.valid);
        }
        if (valid.equals("YES")){
            String date=product.getDate();
            String day=date.substring(0,2);
            String month=date.substring(3,5);
            String year=date.substring(6,10);
            int d=Integer.parseInt(day);
            int m=Integer.parseInt(month);
            int yy=Integer.parseInt(year);
            Calendar startDate1=Calendar.getInstance();
            int totalDays=gregorianDays(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH)+1,Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) - gregorianDays(yy,m,d);

            if (totalDays>40){
                imageView.setImageResource(R.mipmap.valid);
            }
            if (totalDays<=40){
                imageView.setImageResource(R.mipmap.unvalid);
            }

                 }
        age =(TextView)convertView.findViewById(R.id.age_list);
        age.setText(product.getAge());
        city =(TextView)convertView.findViewById(R.id.city_list);
        city.setText(product.getCity());
        name = (TextView)convertView.findViewById(R.id.name_list);
        name.setText(product.getName().toString());
        blood_type = (TextView)convertView.findViewById(R.id.blood_type);
        call = (ImageView) convertView.findViewById(R.id.call);
        blood_type.setText(product.getType().toString());
        msafa = (TextView)convertView.findViewById(R.id.distance_list);
        if (km<=0.0){
            msafa.setText(meter+" M");

        }
        if (km>0.0){

            msafa.setText(km+" Km");

        }

        return convertView;
    }
    private static int gregorianDays(int year, int month, int day) {
        month = (month + 9) % 12;
        year = year - month / 10;
        return 365 * year + year / 4 - year / 100 + year / 400 + (month * 306 + 5) / 10 + (day - 1);

    }
}



