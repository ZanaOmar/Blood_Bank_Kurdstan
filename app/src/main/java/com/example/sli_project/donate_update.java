package com.example.sli_project;

import android.content.Intent;
import android.os.AsyncTask;
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

public class donate_update extends AppCompatActivity {
    EditText name,phone,pass,x,y,city,date,age;
    String D_name,D_phone,D_pass,D_x,D_y,D_city,D_date,D_age,D_type;
    Spinner bloodSpinner;
    String bloodselct;
    String code,Name,Phone,Pass,City,Age,oldphone;
    public static String[]blood = {"A+","A-","AB+","AB-","B+","B-","O+","O-"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_update);
        Intent intent=getIntent();
        oldphone=intent.getStringExtra("oldphone");
        name=(EditText)findViewById(R.id.edt_name) ;
        phone=(EditText)findViewById(R.id.edt_tel) ;
        pass=(EditText)findViewById(R.id.edt_pass) ;
        city=(EditText)findViewById(R.id.edt_address) ;
        age=(EditText)findViewById(R.id.edt_age) ;
        bloodSpinner = (Spinner)(findViewById(R.id.sp_blood));
        code=intent.getStringExtra("code");

        if (code.equals("1")) {

            Name = intent.getStringExtra("name");
            Phone = intent.getStringExtra("phone");
            Pass = intent.getStringExtra("pass");
            City = intent.getStringExtra("city");
            Age = intent.getStringExtra("age");
            name.setText(Name);
            phone.setText(Phone);
            pass.setText(Pass);
            city.setText(City);
            age.setText(Age);
        }
        if (code.equals("2")){
            new getinfo().execute();
        }


        ArrayAdapter arrayAdapter = new ArrayAdapter(donate_update.this,
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
    public void update_info(View view){
        D_name=name.getText().toString();
        D_phone=phone.getText().toString();
        D_pass=pass.getText().toString();
        D_city=city.getText().toString();
        D_age=age.getText().toString();
        D_type=bloodselct;
        new update_account().execute();
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        name.setText(null);
        phone.setText(null);
        pass.setText(null);
        city.setText(null);
        age.setText(null);
        Toast.makeText(this, "data updated", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(donate_update.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    class update_account extends AsyncTask<String, String, String> {
        String data = "";
        int tmp;



        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://stadiumscollector.com/blood_bank/update_donor.php");
                String urlParams = "name="+D_name+"&age=" + D_age+"&phone="
                        + D_phone+"&city=" + D_city+"&blood=" + D_type+"&pass=" + D_pass+"&oldphone=" + oldphone;
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
                Toast.makeText(donate_update.this, "no connection", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }
    class getinfo extends AsyncTask<String, String, String> {
        String data = "";
        int tmp;



        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://stadiumscollector.com/blood_bank/getinfo.php");
                String urlParams = "oldphone=" + oldphone;
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
                Toast.makeText(donate_update.this, "no connection", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            String err = null;
            try {
                JSONObject root = new JSONObject(s);
                JSONArray jsonArray = root.getJSONArray("user_data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                         Name=   productObject.getString("dname").toString();
                         Phone=   productObject.getString("dphone").toString();
                         City=   productObject.getString("city").toString();
                         Age=   productObject.getString("age");
                    Pass=productObject.getString("password");
                }
                  info();
            }

            catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
                Toast.makeText(donate_update.this,"no data", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void info(){
        name.setText(Name);
        phone.setText(Phone);
        city.setText(City);
        age.setText(Age);
        pass.setText(Pass);

    }
}
