package com.example.sli_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class admin_page extends AppCompatActivity {
String hos_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        Intent intent=getIntent();
        hos_id=intent.getStringExtra("hos_id");
    }

    public void blood_type(View view){
        Intent intent=new Intent(admin_page.this,Blood_type.class);
        startActivity(intent);

    }
    public void donate(View view){
          Intent intent=new Intent(admin_page.this,donate.class);
          intent.putExtra("hos_id",hos_id);
          startActivity(intent);
    }
}
