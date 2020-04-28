package com.example.attendance.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendance.R;
import com.example.attendance.model.FacultyItem1;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddFacultyActivity1 extends AppCompatActivity {

    EditText editName, editPhone,editEmail, editPassword;
    String facultyId, facultyName,facultyPhone, facultyEmail, facultyPassword;
    String facultyUserID;
    Button btnSubmit;
    DatabaseReference databaseReference;

    private FirebaseAuth mAuth;

    String TAG = "FIREBASE_DATA";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty1);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add Faculty");
        }

        mAuth = FirebaseAuth.getInstance();
        

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnSubmit = findViewById(R.id.btnSubmit);


        databaseReference = FirebaseDatabase.getInstance().getReference("FacultyDetails");


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                next();

            }
        });
    }




    public void next() {

        facultyId = databaseReference.push().getKey();

        facultyName = editName.getText().toString();
        facultyPhone = editPhone.getText().toString();
        facultyEmail = editEmail.getText().toString();
        facultyPassword = editPassword.getText().toString();


        facultyUserID = facultyEmail + "_" + facultyPassword;



        if (!facultyName.isEmpty() &&!facultyPhone.isEmpty() && !facultyEmail.isEmpty() && isValidEmail(facultyEmail) && !facultyPassword.isEmpty() ) {


            mAuth.createUserWithEmailAndPassword(facultyEmail, facultyPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Log.d(TAG, "createUserWithEmail:success" + user);
                                FacultyItem1 faculty = new FacultyItem1(facultyId, facultyName, facultyEmail,facultyPhone ,facultyPassword, facultyUserID);
                                databaseReference.child(facultyId).setValue(faculty);
                                Toast.makeText(AddFacultyActivity1.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                                editName.setText("");
                                editPhone.setText("");
                                editEmail.setText("");
                                editPassword.setText("");


                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(AddFacultyActivity1.this, "Email ID Already Exists",
                                        Toast.LENGTH_SHORT).show();

                            }


                        }
                    });

        }else {
            Toast.makeText(this, "Enter Valid Details", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


    private boolean isValidEmail(String emailId) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailId);
        return matcher.matches();
    }


    // Validating password
    public static boolean isValidPassword1(String pass1) {

        return pass1 == null || pass1.length() <= 5;
    }

}






