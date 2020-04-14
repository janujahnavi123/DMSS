package com.example.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class teacherandstudent extends AppCompatActivity implements View.OnClickListener {
    Button addteacher, viewteacher, addstudent, viewstudent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacherandstudent);
        addteacher = findViewById(R.id.button1);
        viewteacher = findViewById(R.id.button2);
        addstudent = findViewById(R.id.button3);
        viewstudent = findViewById(R.id.button4);
        addteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(v.getContext(), teacherregistration.class);
                startActivity(myintent);
            }
        });
        viewteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(v.getContext(), viewteacherdetails.class);
                startActivity(inte);
            }
        });
        addstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(v.getContext(), studentregistration.class);
                startActivity(myintent);
            }
        });
        viewstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(v.getContext(), viewstudentdetails.class);
                startActivity(inte);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}