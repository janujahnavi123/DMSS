package com.example.attendance.activity;

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
import com.example.attendance.model.StudentItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddStudentActivity extends AppCompatActivity {
    EditText edtStudentName, edtStudentRoll;
    String studentId, studentName, studentRoll;
    Button btnStudentSubmit;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add Student");
        }
        btnStudentSubmit = (Button) findViewById(R.id.btnStudentSubmit);
        edtStudentName = (EditText) findViewById(R.id.edtStudentName);
        edtStudentRoll = (EditText) findViewById(R.id.edtStudentRoll);


        myRef = FirebaseDatabase.getInstance().getReference("StudentDetails");


        btnStudentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });
    }


    public void next() {
        studentId = myRef.push().getKey();
        studentName = edtStudentName.getText().toString().trim();
        studentRoll = edtStudentRoll.getText().toString().trim();


        if (TextUtils.isEmpty(studentName)) {
            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(studentRoll)) {
            Toast.makeText(this, "Please Enter Number", Toast.LENGTH_SHORT).show();

        }


        if (!studentName.isEmpty() && !studentRoll.isEmpty()) {


            myRef.child(studentRoll).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.getValue() != null) {
                        //user exists, do something
                        Toast.makeText(AddStudentActivity.this, "Already Student Roll Number Exists", Toast.LENGTH_SHORT).show();
                    } else {
                        StudentItem student = new StudentItem(studentId, studentName, studentRoll);
                        myRef.child(studentRoll).setValue(student);
                        Toast.makeText(AddStudentActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                        edtStudentName.setText("");
                        edtStudentRoll.setText("");


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

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
}
