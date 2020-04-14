package com.example.attendance;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.facebook.stetho.Stetho;

import java.util.Calendar;
public class teacherregistration extends AppCompatActivity  {
    EditText username1;
    EditText email1;
    EditText password1;
    EditText mobilenumber1;
    EditText confirmpass;
    Spinner spinner1;
    Spinner spinner2;
    String use, pass, em, mobile, cpass, r, sp1, sp2;
    private AwesomeValidation awesomeValidation;
    Button register, logout;
    private Object startactivity;
    public static final String PREFS_NAME = "LoginPrefs";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacherregistration);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        username1 = findViewById(R.id.username);
        email1 = findViewById(R.id.email);
        password1 = findViewById(R.id.password);
        mobilenumber1 = findViewById(R.id.mobilenumber);
        confirmpass = findViewById(R.id.confirmpassword);
        register = findViewById(R.id.regbutton);
        logout = findViewById(R.id.logout);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);

        awesomeValidation.addValidation(this, R.id.username, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.password, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror);
        awesomeValidation.addValidation(this, R.id.confirmpassword, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.cpassworderror);
        awesomeValidation.addValidation(this, R.id.mobilenumber, "^[0-9]{2}[0-9]{8}$", R.string.mobileerror);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                use = username1.getText().toString();
                pass = password1.getText().toString();
                em = email1.getText().toString();
                mobile = mobilenumber1.getText().toString();
                cpass = confirmpass.getText().toString();
                sp1 = spinner1.getSelectedItem().toString();
                sp2 = spinner2.getSelectedItem().toString();
                if (pass.equals(cpass)) {
                    if (sp2.equals("CSE") || sp2.equals("IT") || sp2.equals("ECE") || sp2.equals("BIOTECH") || sp2.equals("MECHANICAL")) {
                        if (awesomeValidation.validate()) {
                            /*Toast.makeText(MainActivity.this, use + "\t" + em + "\n" + mobile + "\n" + r + "\n" + sp, Toast.LENGTH_SHORT).show();
                            Boolean   ret=dbhelper.addsignup(use,em,pass,cpass,r,sp,mobile);
                            if (ret == true) {
                                Intent myintent = new Intent(v.getContext(), signup.class);
                                startActivity(myintent);
                            } else {
                                Log.d("else", "username or password incorrect");
                                Toast.makeText(MainActivity.this, "username or password incorrect", Toast.LENGTH_LONG).show();
                            }*/
                            Toast.makeText(teacherregistration.this, use + "\t" + em + "\n" + mobile, Toast.LENGTH_SHORT).show();

                        }
                    }
                } else {
                    Toast.makeText(teacherregistration.this, "password mismatched and click any radiobutton,select the branch", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }




}

//                /*Toast.makeText(MainActivity.this, "register button clicked", Toast.LENGTH_SHORT).show();*/




