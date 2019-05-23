package com.example.sli_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Blood_type extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_type);

        ImageView img_A_plus = (ImageView)(findViewById(R.id.img_A_plus));
        ImageView img_A_minus = (ImageView)(findViewById(R.id.img_A_minus));
        ImageView img_B_plus = (ImageView)(findViewById(R.id.img_B_plus));
        ImageView img_B_minus = (ImageView)(findViewById(R.id.img_B_minus));
        ImageView img_AB_plus = (ImageView)(findViewById(R.id.img_AB_plus));
        ImageView img_AB_minus = (ImageView)(findViewById(R.id.img_AB_minus));
        ImageView img_O_plus = (ImageView)(findViewById(R.id.img_O_plus));
        ImageView img_O_minus = (ImageView)(findViewById(R.id.img_O_minus));


        img_A_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Blood_type.this,MyList.class);
                intent.putExtra("blood","A+");
                startActivity(intent);
            }
        });
        img_A_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Blood_type.this,MyList.class);
                intent.putExtra("blood","A-");
                startActivity(intent);
            }
        });
        img_B_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Blood_type.this,MyList.class);
                intent.putExtra("blood","B+");
                startActivity(intent);
            }
        });
        img_B_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Blood_type.this,MyList.class);
                intent.putExtra("blood","B-");
                startActivity(intent);
            }
        });
        img_AB_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Blood_type.this,MyList.class);
                intent.putExtra("blood","AB+");
                startActivity(intent);
            }
        });
        img_AB_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Blood_type.this,MyList.class);
                intent.putExtra("blood","AB-");
                startActivity(intent);
            }
        });
        img_O_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Blood_type.this,MyList.class);
                intent.putExtra("blood","O+");
                startActivity(intent);
            }
        });img_O_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Blood_type.this,MyList.class);
                intent.putExtra("blood","O-");
                startActivity(intent);
            }
        });
    }
}
