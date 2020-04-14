package com.example.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class adminandlogin extends AppCompatActivity implements View.OnClickListener {
    Button tvSwipDescription1,tvs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminandlogin);
        tvSwipDescription1=findViewById(R.id.button1);
        tvs=findViewById(R.id.button2);
        tvSwipDescription1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(v.getContext(),adminlogin.class);
                startActivity(myintent);
            }
        });
        tvs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte=new Intent(v.getContext(),teacherlogin.class);
                startActivity(inte);
            }
        });
    }


    @Override
    public void onClick(View v) {


    }

}

