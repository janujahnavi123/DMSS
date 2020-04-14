package com.example.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

public class adminlogin extends AppCompatActivity implements View.OnClickListener {
    EditText useradmin,passwordadmin;
    Button submitadmin;
    String  useradmin1,passwordadmin1;
    SharedPreferences sp;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminlogin);
        useradmin = (EditText)findViewById(R.id.adminuser);
         passwordadmin = (EditText)findViewById(R.id.adminpass);
         submitadmin=findViewById(R.id.button2);

        sp = getSharedPreferences("login", MODE_PRIVATE);

        //if SharedPreferences contains username and password then directly redirect to Home activity
        if(sp.contains("username") && sp.contains("password")){
            startActivity(new Intent(adminlogin.this,teacherandstudent.class));
            finish();   //finish current activity
        }
        submitadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==submitadmin)
                {
                    useradmin1=useradmin.getText().toString();
                    passwordadmin1=passwordadmin.getText().toString();
                    if(useradmin1.equals("admin")&&passwordadmin1.equals("password"))
                    {
                        SharedPreferences.Editor e = sp.edit();
                        e.putString("username", "programmer");
                        e.putString("password", "programmer");
                        e.commit();
                        Intent myintent=new Intent(v.getContext(),teacherandstudent.class);
                        startActivity(myintent);
                        finish();

                    }
                    else
                    {
                        Toast.makeText(v.getContext(),useradmin1+"/n"+passwordadmin1,Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    public void onClick(View view)
    {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
