package com.example.sli_project;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class donate_detail extends AppCompatActivity {
EditText name,phone,pass,x,y,city,date,age;
String D_name,D_phone,D_pass,D_x,D_y,D_city,D_date,D_age,D_type;
    LocationManager locationManager;
    String lattitude,longitude;
    double latti;
    double longi;
    private static final int REQUEST_LOCATION = 1;
    public static String[]blood = {"A+","A-","AB+","AB-","B+","B-","O+","O-"};
    Spinner bloodSpinner;
    String bloodselct;
    String formattedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_detail);
        onrun();
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        formattedDate = df.format(c);

        name=(EditText)findViewById(R.id.edt_name) ;
        phone=(EditText)findViewById(R.id.edt_tel) ;
        pass=(EditText)findViewById(R.id.edt_pass) ;
        city=(EditText)findViewById(R.id.edt_address) ;
        age=(EditText)findViewById(R.id.edt_age) ;
        bloodSpinner = (Spinner)(findViewById(R.id.sp_blood));
        ArrayAdapter arrayAdapter = new ArrayAdapter(donate_detail.this,
                android.R.layout.simple_spinner_dropdown_item,blood);
        bloodSpinner.setAdapter(arrayAdapter);
        bloodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodselct = bloodSpinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void onrun() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "The Gps Off", Toast.LENGTH_SHORT).show();
            buildAlertMessageNoGps();
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(donate_detail.this, "The Gps On", Toast.LENGTH_SHORT).show();
            getLocation();
        }
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(donate_detail.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (donate_detail.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(donate_detail.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latti = location.getLatitude();
                longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                personal_info personal_info=new personal_info(lattitude,longitude);
                personal_info.setMy_x(lattitude);
                personal_info.setMy_y(longitude);
            }else{
                Toast.makeText(this,"Unble to Trace your location", Toast.LENGTH_SHORT).show();
            }
        }
    }
    protected void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        getlocationn();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        onrun();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

    }
    protected void getlocationn() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You need to get your loation")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        getLocation();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        getLocation();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

    }

    public void save_info(View view){
        D_name=name.getText().toString();
        D_phone=phone.getText().toString();
        D_pass=pass.getText().toString();
        D_city=city.getText().toString();
        D_age=age.getText().toString();
        D_x=lattitude;
        D_y=longitude;
        D_date=formattedDate;
        D_type=bloodselct;
        new creat_account().execute();
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        name.setText(null);
        phone.setText(null);
        pass.setText(null);
        city.setText(null);
        age.setText(null);
        Intent intent=new Intent(donate_detail.this,donate_update.class);
        intent.putExtra("code","1");
        intent.putExtra("name",D_name);
        intent.putExtra("phone",D_phone);
        intent.putExtra("oldphone",D_phone);
        intent.putExtra("pass",D_pass);
        intent.putExtra("city",D_city);
        intent.putExtra("age",D_age);
        startActivity(intent);
        finish();




    }

    class creat_account extends AsyncTask<String, String, String> {
        String data = "";
        int tmp;



        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://stadiumscollector.com/blood_bank/add_donor.php");
                String urlParams = "xgps="+D_x+"&ygps=" + D_y+"&name=" + D_name+"&age=" + D_age+"&phone="
                        + D_phone+"&city=" + D_city+"&date="+D_date+"&blood=" + D_type+"&pass=" + D_pass;
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();
                InputStream is = httpURLConnection.getInputStream();
                while ((tmp = is.read()) != -1) {
                    data += (char) tmp;
                }
                is.close();
                httpURLConnection.disconnect();
                return data;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            } catch (IOException e) {
                Toast.makeText(donate_detail.this, "no connection", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }

}
