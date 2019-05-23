package com.example.sli_project;

import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class donate extends AppCompatActivity {
    EditText phone;
    String D_phone,D_date,D_type;
    String hos_id;
    public static String[]blood = {"A+","A-","AB+","AB-","B+","B-","O+","O-"};
    Spinner bloodSpinner;
    String bloodselct;
    String formattedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        Intent intent=getIntent();
        hos_id=intent.getStringExtra("hos_id");
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        formattedDate = df.format(c);

        phone=(EditText)findViewById(R.id.edt_tel) ;
        bloodSpinner = (Spinner)(findViewById(R.id.sp_blood));
        ArrayAdapter arrayAdapter = new ArrayAdapter(donate.this,
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

    class creat_account extends AsyncTask<String, String, String> {
        String data = "";
        int tmp;



        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://stadiumscollector.com/blood_bank/donate.php");
                String urlParams ="phone="+ D_phone+"&date="+D_date+"&blood=" + D_type+"&hid=" + hos_id;
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
                Toast.makeText(donate.this, "no connection", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }
    public void save_info(View view) {
        D_phone = phone.getText().toString();
        D_date = formattedDate;
        D_type = bloodselct;
        new creat_account().execute();
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        phone.setText(null);

    }
}
