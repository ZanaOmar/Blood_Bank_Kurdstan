package com.example.sli_project;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class donate_page extends AppCompatActivity {
EditText donate_phone,donate_pass;
    String D_phone,D_pass,D_checkpass,D_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donor_login);

        donate_phone=(EditText)findViewById(R.id.donate_login_phone);
        donate_pass=(EditText)findViewById(R.id.donate_login_pass);
    }

    public void create_account(View view){
        Intent intent=new Intent(donate_page.this,donate_detail.class);
        startActivity(intent);
    }
    public  void log_in_donate(View view){
        D_phone =donate_phone.getText().toString();
        D_pass =donate_pass.getText().toString();

        new Get_user_Info().execute();
    }
    class Get_user_Info extends AsyncTask<String, String, String> {
        String data = "";
        int tmp;



        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://stadiumscollector.com/blood_bank/donor_login.php");
                String urlParams = "phone=" + D_phone ;
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
                Toast.makeText(donate_page.this, "no connection", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            String err = null;
            try {
                JSONObject root = new JSONObject(s);
                JSONArray jsonArray = root.getJSONArray("user_info");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    D_id =  productObject.getString("id").toString();
                    D_checkpass= productObject.getString("password").toString();
                }
                cheack();
            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
                Toast.makeText(donate_page.this,"no data", Toast.LENGTH_LONG).show();
            }
        }
    }
    public  void cheack(){
        if (D_checkpass.equals(D_pass)){
            Intent intent=new Intent(donate_page.this,donate_update.class);
            intent.putExtra("oldphone",D_phone);
            intent.putExtra("code","2");
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Valid Username or password", Toast.LENGTH_LONG).show();
        }
    }
}
