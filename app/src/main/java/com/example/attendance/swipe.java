package com.example.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

public class swipe extends AppCompatActivity {
    float x1,y1,x2,y2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe);
    }
    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1<x2){
                Intent i = new Intent(swipe.this, adminlogin.class);
                startActivity(i);
            }else if(x1>x2){
                Intent i = new Intent(swipe.this, teacherregistration.class);
                startActivity(i);
            }
            break;
        }
        return false;
    }
}
