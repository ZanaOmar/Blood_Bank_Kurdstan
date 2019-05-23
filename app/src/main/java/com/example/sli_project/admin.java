package com.example.sli_project;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class admin extends AppCompatActivity {

    EditText admin_name,admin_pass;
    String A_name,A_pass,A_checkpass,A_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        admin_name=(EditText)findViewById(R.id.admin_name);
        admin_pass=(EditText)findViewById(R.id.admin_pass);
    }

    public void admin_button(View view){
        A_name =admin_name.getText().toString();
        A_pass =admin_pass.getText().toString();
        new Get_Admin_Info().execute();
    }


    class Get_Admin_Info extends AsyncTask<String, String, String> {
        String data = "";
        int tmp;



        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://stadiumscollector.com/blood_bank/admin_login.php");
                String urlParams = "name=" + A_name ;
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
                Toast.makeText(admin.this, "no connection", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            String err = null;
            try {
                JSONObject root = new JSONObject(s);
                JSONArray jsonArray = root.getJSONArray("admin_info");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                         A_id =  productObject.getString("id").toString();
                         A_checkpass= productObject.getString("password").toString();
                }
                cheack();
            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
                Toast.makeText(admin.this,"no data", Toast.LENGTH_LONG).show();
            }
        }
    }

    public  void cheack(){
        if (A_checkpass.equals(A_pass)){
            Intent intent=new Intent(admin.this,admin_page.class);
            intent.putExtra("hos_id",A_id);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Valid Username or password", Toast.LENGTH_LONG).show();
        }
    }
}
