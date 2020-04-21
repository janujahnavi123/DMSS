package com.example.attendance.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendance.R;
import com.example.attendance.model.StudentItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddStudentActivity extends AppCompatActivity {
    EditText edtStudentName, edtStudentRoll;
    Spinner spinnerDepartment, spinnerYear, spinnerSemester, spinnerSubject;
    String studentId, studentName, studentRoll, studentDepartment, studentYear, studentSemester, studentSubject, studentRandomId;
    Button btnStudentSubmit;
    DatabaseReference myRef;

    DatabaseReference databaseReferenceSubjects;

    List<String> subjects;
    String subjectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add Student");
        }
        subjects=new ArrayList<>();
        subjects.add("Select Subject");
        btnStudentSubmit = (Button) findViewById(R.id.btnStudentSubmit);
        edtStudentName = (EditText) findViewById(R.id.edtStudentName);
        edtStudentRoll = (EditText) findViewById(R.id.edtStudentRoll);

        spinnerDepartment = (Spinner) findViewById(R.id.spinnerDepartment);
        spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        spinnerSemester = (Spinner) findViewById(R.id.spinnerSemester);
        spinnerSubject = (Spinner) findViewById(R.id.spinnerSubject);

        myRef = FirebaseDatabase.getInstance().getReference("StudentDetails");
        databaseReferenceSubjects = FirebaseDatabase.getInstance().getReference("SubjectDetails");

        spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                studentDepartment = spinnerDepartment.getSelectedItem().toString().trim();
                getSubjects(studentDepartment,studentYear,studentSemester);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                studentYear = spinnerYear.getSelectedItem().toString().trim();
                getSubjects(studentDepartment,studentYear,studentSemester);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                studentSemester = spinnerSemester.getSelectedItem().toString().trim();
                getSubjects(studentDepartment,studentYear,studentSemester);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnStudentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });
    }


    public void getSubjects(String studentDepartment, String studentYear, String studentSemester) {

        subjectID = studentDepartment + "_" + studentYear + "_" + studentSemester;
        Query query = databaseReferenceSubjects.orderByChild("randomId").equalTo(subjectID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array


                subjects.clear();
                subjects.add("Select Subject");

                for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                    String subject = subjectSnapshot.child("subject").getValue(String.class);
                    subjects.add(subject);
                }


                ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(AddStudentActivity.this, android.R.layout.simple_spinner_item, subjects);
                subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSubject.setAdapter(subjectAdapter);


                spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        studentSubject = spinnerSubject.getSelectedItem().toString().trim();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void next() {
        studentId = myRef.push().getKey();
        studentName = edtStudentName.getText().toString().trim();
        studentRoll = edtStudentRoll.getText().toString().trim();
        studentRandomId = studentDepartment + "_" + studentYear + "_" + studentSemester + "_" + studentSubject;

        if (TextUtils.isEmpty(studentName)) {
            if ((TextUtils.isEmpty(studentRoll)))
                Toast.makeText(this, "Please Enter Details", Toast.LENGTH_SHORT).show();

        }
        if (TextUtils.isEmpty(studentName)) {
            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(studentRoll)) {
            Toast.makeText(this, "Please Enter Number", Toast.LENGTH_SHORT).show();

        }


        if (studentDepartment.equals("Select Department")) {
            Toast.makeText(AddStudentActivity.this, "Please Select Department", Toast.LENGTH_SHORT).show();
        }
        if (studentYear.equals("Select Year")) {
            Toast.makeText(AddStudentActivity.this, "Please Select Year", Toast.LENGTH_SHORT).show();
        }
        if (studentSemester.equals("Select Semester")) {
            Toast.makeText(AddStudentActivity.this, "Please Select Semester", Toast.LENGTH_SHORT).show();
        }
        if (studentSubject.equals("Select Subject")) {
            Toast.makeText(AddStudentActivity.this, "Please Select Subject", Toast.LENGTH_SHORT).show();
        }


        if (!studentName.isEmpty() && !studentRoll.isEmpty() && !studentDepartment.equals("Select Department") && !studentYear.equals("Select Year") && !studentSemester.equals("Select Semester")) {

            StudentItem student = new StudentItem(studentId, studentName, studentRoll, studentDepartment, studentYear, studentSemester, studentSubject, studentRandomId);
            myRef.child(studentId).setValue(student);
            Toast.makeText(AddStudentActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();

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
