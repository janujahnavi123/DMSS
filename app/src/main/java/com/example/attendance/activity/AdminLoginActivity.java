package com.example.attendance.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendance.R;
import com.example.attendance.utils.ConstantValues;
import com.example.attendance.utils.MyAppPrefsManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminLoginActivity extends AppCompatActivity {

    EditText editEmail, editPassword;
    Button btnLogin;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    String email;
    String password;
    MyAppPrefsManager myAppPrefsManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Admin Login");
        }
        mAuth = FirebaseAuth.getInstance();
        myAppPrefsManager=new MyAppPrefsManager(AdminLoginActivity.this);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        progressDialog = new ProgressDialog(AdminLoginActivity.this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });
    }


    public void next() {
        email = editEmail.getText().toString().trim();
        password = editPassword.getText().toString().trim();
        if (!isValidEmail(email)) {
            Toast.makeText(this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();

        }

        if (TextUtils.isEmpty(email)) {
            if ((TextUtils.isEmpty(password)))
                Toast.makeText(this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {

                            progressDialog.dismiss();
                            Toast.makeText(AdminLoginActivity.this, "Please Enter Valid Email/Password", Toast.LENGTH_SHORT).show();


                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(AdminLoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                            myAppPrefsManager.setAdminLoggedIn(true);
                            ConstantValues.IS_USER_LOGGED_IN_ADMIN = myAppPrefsManager.isAdminLoggedIn();
                            Intent intent = new Intent(AdminLoginActivity.this, AdminHomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);


                        }


                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}