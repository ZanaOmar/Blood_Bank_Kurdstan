package com.example.sli_project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import java.util.ArrayList;

public class MyList extends AppCompatActivity {
    String blood;
    CustomListAdapter adapter;
    ArrayList<Product> arrayList;
    ListView lv;
    String  mx, my;
    String call;
    personal_info personal_info = new personal_info();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // if not available request permission
            ActivityCompat.requestPermissions(MyList.this, new String[]{Manifest.permission.CALL_PHONE},1);
            return;

        }
        mx = personal_info.getMy_x();
        my = personal_info.getMy_y();
        Intent intent  = getIntent();
         blood = intent.getStringExtra("blood");
        System.out.println("dashgdhsagdshdhdjasgdgjas   "+ blood);
        lv = (ListView) findViewById(R.id.mylist);
        arrayList = new ArrayList<>();
        new Get_donor().execute();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product pr = (Product) parent.getAdapter().getItem(position);
                Toast.makeText(MyList.this, pr.getPhone() + "", Toast.LENGTH_SHORT).show();
                call=pr.getPhone().toString();
                phoneCall();

            }
        });
    }
    class Get_donor extends AsyncTask<String, String, String> {
        String data = "";
        int tmp;

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://stadiumscollector.com/blood_bank/list.php");
                String urlParams = "type=" + blood+ "&my_x=" + mx + "&my_y=" + my;
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
                    arrayList.add(new Product(
                            productObject.getString("dname").toString(),
                            productObject.getString("dphone").toString(),
                            productObject.getString("XGBS" ).toString(),
                            productObject.getString("YGBS").toString(),
                            productObject.getString("city").toString(),
                            productObject.getString("did" ).toString(),
                            productObject.getString("date1").toString(),
                            productObject.getString("submate").toString(),
                            productObject.getString("age"),
                            productObject.getString("blodType")
                    ));
                }

            }

            catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
                Toast.makeText(MyList.this,"no data", Toast.LENGTH_LONG).show();
            }
            adapter = new CustomListAdapter(getApplicationContext(), R.layout.custom_list_layout, arrayList);
            lv.setAdapter(adapter);
        }
    }

    public void phoneCall( ) {
        String n = call;
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + n));
        //check permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // if not available request permission
            ActivityCompat.requestPermissions(MyList.this, new String[]{Manifest.permission.CALL_PHONE},1);
            return;

        }
        //if permission is allow then start Activity
        startActivity(callIntent);
    }
}
