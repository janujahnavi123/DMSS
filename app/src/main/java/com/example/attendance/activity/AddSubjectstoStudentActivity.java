package com.example.attendance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendance.R;
import com.example.attendance.model.StudentItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddSubjectstoStudentActivity extends AppCompatActivity {


    Spinner spinnerDept, spinnerYear, spinnerSemester, spinnerSubject;
    String studentDept, studentYear, studentSem, studentSubject;
    String studentUserID, studentRandomID;
    Button btnSubmit;
    DatabaseReference databaseReference;


    String subjectID;
    private FirebaseAuth mAuth;

    String TAG = "FIREBASE_DATA";
    DatabaseReference databaseReferenceDepartment;
    DatabaseReference databaseReferenceYear;
    DatabaseReference databaseReferenceSemester;
    DatabaseReference databaseReferenceSubject;

    List<String> departmentList;
    List<String> yearsList;
    List<String> semesterList;
    List<String> subjectList;

    String studentId,studentSubjectId,studentEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subjectsto_student);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add Subjects to Student");
        }
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        studentId = intent.getStringExtra("studentId");



        departmentList = new ArrayList<>();
        departmentList.add("Select Department");

        yearsList = new ArrayList<>();
        yearsList.add("Select Year");

        semesterList = new ArrayList<>();
        semesterList.add("Select Semester");

        subjectList = new ArrayList<>();
        subjectList.add("Select Subject");

        spinnerDept = findViewById(R.id.spinnerDept);
        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerSemester = findViewById(R.id.spinnerSemester);
        spinnerSubject = findViewById(R.id.spinnerSubject);
        btnSubmit = findViewById(R.id.btnSubmit);


        databaseReference = FirebaseDatabase.getInstance().getReference("StudentDetails");


        databaseReferenceDepartment = FirebaseDatabase.getInstance().getReference("DepartmentDetails");
        databaseReferenceYear = FirebaseDatabase.getInstance().getReference("YearDetails");
        databaseReferenceSemester = FirebaseDatabase.getInstance().getReference("SemesterDetails");
        databaseReferenceSubject = FirebaseDatabase.getInstance().getReference("SubjectDetails");

        databaseReferenceDepartment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                departmentList.clear();
                departmentList.add("Select Department");

                for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                    String department = subjectSnapshot.child("department").getValue(String.class);
                    departmentList.add(department);
                }


                ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(AddSubjectstoStudentActivity.this, android.R.layout.simple_spinner_item, departmentList);
                departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDept.setAdapter(departmentAdapter);

                spinnerDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        studentDept = spinnerDept.getSelectedItem().toString().trim();
                        getSubjects(studentDept, studentYear, studentSem);
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


        databaseReferenceYear.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                yearsList.clear();
                yearsList.add("Select Year");

                for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                    String year = subjectSnapshot.child("year").getValue(String.class);
                    yearsList.add(year);
                }


                ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(AddSubjectstoStudentActivity.this, android.R.layout.simple_spinner_item, yearsList);
                yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerYear.setAdapter(yearAdapter);

                spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        studentYear = spinnerYear.getSelectedItem().toString().trim();
                        getSubjects(studentDept, studentYear, studentSem);
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

        databaseReferenceSemester.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                semesterList.clear();
                semesterList.add("Select Semester");

                for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                    String semester = subjectSnapshot.child("semester").getValue(String.class);
                    semesterList.add(semester);
                }


                ArrayAdapter<String> semesterAdapter = new ArrayAdapter<String>(AddSubjectstoStudentActivity.this, android.R.layout.simple_spinner_item, semesterList);
                semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSemester.setAdapter(semesterAdapter);
                spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        studentSem = spinnerSemester.getSelectedItem().toString().trim();
                        getSubjects(studentDept, studentYear, studentSem);
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


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                next();

            }
        });
    }


    public void getSubjects(String studentDept, String studentYear, String studentSem) {

        subjectID = studentDept + "_" + studentYear + "_" + studentSem;
        Query query = databaseReferenceSubject.orderByChild("randomId").equalTo(subjectID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array


                subjectList.clear();
                subjectList.add("Select Subject");

                for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                    String subject = subjectSnapshot.child("subject").getValue(String.class);
                    subjectList.add(subject);
                }


                ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(AddSubjectstoStudentActivity.this, android.R.layout.simple_spinner_item, subjectList);
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

        studentRandomID = studentDept + "_" + studentYear + "_" + studentSem + "_" + studentSubject;

        studentSubjectId=databaseReference.push().getKey();
        if (studentDept.equals("Select Department")) {
            Toast.makeText(AddSubjectstoStudentActivity.this, "Please Select Department", Toast.LENGTH_SHORT).show();
        }
        if (studentYear.equals("Select Year")) {
            Toast.makeText(AddSubjectstoStudentActivity.this, "Please Select Year", Toast.LENGTH_SHORT).show();
        }
        if (studentSem.equals("Select Semester")) {
            Toast.makeText(AddSubjectstoStudentActivity.this, "Please Select Semester", Toast.LENGTH_SHORT).show();
        }

        Query query = databaseReference.orderByChild("studentId").equalTo(studentId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!studentDept.equals("Select Department") && !studentYear.equals("Select Year") && !studentSem.equals("Select Semester")) {


                    StudentItem student = new StudentItem(studentDept,studentYear,studentSem,studentSubject,studentRandomID,studentSubjectId);
                    databaseReference.child(studentId).child("SubjectDetails").child(studentSubjectId).setValue(student);
                    Toast.makeText(AddSubjectstoStudentActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    spinnerDept.setSelection(0);
                    spinnerYear.setSelection(0);
                    spinnerSemester.setSelection(0);
                    spinnerSubject.setSelection(0);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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




}






