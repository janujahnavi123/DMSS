package com.example.attendance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendance.R;
import com.example.attendance.adapter.FacultyAttendanceAdapter;
import com.example.attendance.model.StudentItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TakeAttendanceActivity extends AppCompatActivity {


    Spinner spinnerDept, spinnerYear, spinnerSemester, spinnerSubject;
    String facultyDept, facultyYear, facultySem, facultySubject;
    String facultyUserID, facultyRandomID;
    Button btnSubmit;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceSubjects;
    DatabaseReference databaseReferenceStudents;

    List<String> departments;
    List<String> years;
    List<String> semesters;
    List<String> subjects;
    String subjectID;
    private FirebaseAuth mAuth;
    TextView editEmpty;
    String TAG = "FIREBASE_DATA";
    String date;
    ListView studentList;
    String email;

    FacultyAttendanceAdapter facultyAttendanceAdapter;
    List<StudentItem> studentItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add Faculty");
        }

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        Toast.makeText(this, ""+email, Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();

        departments = new ArrayList<>();
        departments.add("Select Department");

        years = new ArrayList<>();
        years.add("Select Year");

        semesters = new ArrayList<>();
        semesters.add("Select Semester");

        subjects = new ArrayList<>();
        subjects.add("Select Subject");

        studentItemList = new ArrayList<>();
        studentList = (ListView) findViewById(R.id.studentList);

        editEmpty = findViewById(R.id.editEmpty);
        spinnerDept = findViewById(R.id.spinnerDept);
        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerSemester = findViewById(R.id.spinnerSemester);
        spinnerSubject = findViewById(R.id.spinnerSubject);
        btnSubmit = findViewById(R.id.btnSubmit);

        databaseReference = FirebaseDatabase.getInstance().getReference("FacultyDetails");
        databaseReferenceStudents = FirebaseDatabase.getInstance().getReference("StudentDetails");
        databaseReferenceSubjects = FirebaseDatabase.getInstance().getReference("SubjectDetails");
        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


        getDepartment();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    public void getDepartment() {

        Query query = databaseReference.orderByChild("facultyEmail").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array


                departments.clear();
                departments.add("Select Department");

                years.clear();
                years.add("Select Year");

                semesters.clear();
                semesters.add("Select Semester");


                for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                    String department = subjectSnapshot.child("facultyDept").getValue(String.class);
                    String year = subjectSnapshot.child("facultyYear").getValue(String.class);
                    String semester = subjectSnapshot.child("facultySem").getValue(String.class);
                    departments.add(department);
                    years.add(year);
                    semesters.add(semester);
                }


                ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(TakeAttendanceActivity.this, android.R.layout.simple_spinner_item, departments);
                departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDept.setAdapter(departmentAdapter);


                spinnerDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        facultyDept = spinnerDept.getSelectedItem().toString().trim();
                        getSubjects(facultyDept,facultyYear,facultySem);
                        next(facultyDept, facultyYear, facultySem, facultySubject);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(TakeAttendanceActivity.this, android.R.layout.simple_spinner_item, years);
                departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerYear.setAdapter(yearAdapter);


                spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        facultyYear = spinnerYear.getSelectedItem().toString().trim();
                        getSubjects(facultyDept,facultyYear,facultySem);
                        next(facultyDept, facultyYear, facultySem, facultySubject);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                ArrayAdapter<String> semesterAdapter = new ArrayAdapter<String>(TakeAttendanceActivity.this, android.R.layout.simple_spinner_item, semesters);
                departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSemester.setAdapter(semesterAdapter);


                spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        facultySem = spinnerSemester.getSelectedItem().toString().trim();
                        getSubjects(facultyDept,facultyYear,facultySem);
                        next(facultyDept, facultyYear, facultySem, facultySubject);
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

    public void getSubjects(final String facultyDept, final String facultyYear, final String facultySem) {

        subjectID = facultyDept + "_" + facultyYear + "_" + facultySem;
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


                ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(TakeAttendanceActivity.this, android.R.layout.simple_spinner_item, subjects);
                subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSubject.setAdapter(subjectAdapter);


                spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        facultySubject = spinnerSubject.getSelectedItem().toString().trim();
                        next(facultyDept, facultyYear, facultySem, facultySubject);
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

    public void next(String facultyDept, String facultyYear, String facultySem, String facultySubject) {

        facultyRandomID = facultyDept + "_" + facultyYear + "_" + facultySem + "_" + facultySubject;
        Toast.makeText(this, "" + facultyRandomID, Toast.LENGTH_SHORT).show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    facultyRandomID = areaSnapshot.child("facultyRandomID").getValue(String.class);
                }

                Query query = databaseReferenceStudents.orderByChild("studentRandomId").equalTo(facultyRandomID);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        studentItemList.clear();
                        if (dataSnapshot.exists()) {

                            for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                // do something with the individual "issues"
                                StudentItem details = issue.getValue(StudentItem.class);
                                studentItemList.add(details);
                            }
                            if (studentItemList.size() == 0) {
                                editEmpty.setVisibility(View.VISIBLE);
                            }

                            facultyAttendanceAdapter = new FacultyAttendanceAdapter(TakeAttendanceActivity.this, studentItemList);
                            studentList.setAdapter(facultyAttendanceAdapter);

                        } else {
                            editEmpty.setVisibility(View.VISIBLE);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Failed to read value

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value

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
