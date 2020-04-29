package com.example.attendance.activity;

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
import com.example.attendance.model.StudentItemSubjects;
import com.example.attendance.utils.MyAppPrefsManager;
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
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class TakeAttendanceActivity extends AppCompatActivity {


    Spinner spinnerDept, spinnerYear, spinnerSemester, spinnerSubject;
    String facultyDept, facultyYear, facultySem, facultySubject;
    String facultyUserID, facultyRandomID;
    Button btnSubmit;
    DatabaseReference databaseReference;

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

    MyAppPrefsManager myAppPrefsManager;
    FacultyAttendanceAdapter facultyAttendanceAdapter;
    List<StudentItemSubjects> studentItemList;
    List<StudentItem> studentItem;
    String facultyId;
    StudentItem details;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add Faculty");
        }

        myAppPrefsManager = new MyAppPrefsManager(TakeAttendanceActivity.this);

        email = myAppPrefsManager.getUserName();


        mAuth = FirebaseAuth.getInstance();

        departments = new ArrayList<>();
        years = new ArrayList<>();
        semesters = new ArrayList<>();
        subjects = new ArrayList<>();

        studentItemList = new ArrayList<>();
        studentItem = new ArrayList<>();
        studentList = (ListView) findViewById(R.id.studentList);

        editEmpty = findViewById(R.id.editEmpty);
        spinnerDept = findViewById(R.id.spinnerDept);
        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerSemester = findViewById(R.id.spinnerSemester);
        spinnerSubject = findViewById(R.id.spinnerSubject);
        btnSubmit = findViewById(R.id.btnSubmit);

        databaseReference = FirebaseDatabase.getInstance().getReference("FacultyDetails");
        databaseReferenceStudents = FirebaseDatabase.getInstance().getReference("StudentDetails");

        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


        getDepartment(email);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    public void getDepartment(String email) {

        Toast.makeText(this, "" + email, Toast.LENGTH_SHORT).show();

        Query query = databaseReference.orderByChild("facultyEmail").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        facultyId = issue.child("facultyId").getValue(String.class);

                    }

                    assert facultyId != null;
                    databaseReference.child(facultyId).child("SubjectDetails").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // Is better to use a List, because you don't know the size
                            // of the iterator returned by dataSnapshot.getChildren() to
                            // initialize the array


                            departments.clear();


                            years.clear();


                            semesters.clear();


                            for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                                String department = subjectSnapshot.child("facultyDept").getValue(String.class);
                                String year = subjectSnapshot.child("facultyYear").getValue(String.class);
                                String semester = subjectSnapshot.child("facultySem").getValue(String.class);
                                String subject = subjectSnapshot.child("facultySubject").getValue(String.class);
                                departments.add(department);
                                years.add(year);
                                semesters.add(semester);
                                subjects.add(subject);

                                HashSet<String> hashSet = new HashSet<String>(departments);
                                departments.clear();
                                departments.addAll(hashSet);

                                HashSet<String> hashSet1 = new HashSet<String>(years);
                                years.clear();
                                years.addAll(hashSet1);

                                HashSet<String> hashSet2 = new HashSet<String>(semesters);
                                semesters.clear();
                                semesters.addAll(hashSet2);

                                HashSet<String> hashSet3 = new HashSet<String>(subjects);
                                subjects.clear();
                                subjects.addAll(hashSet3);
                            }


                            ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(TakeAttendanceActivity.this, android.R.layout.simple_spinner_item, departments);
                            departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerDept.setAdapter(departmentAdapter);


                            spinnerDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    facultyDept = spinnerDept.getSelectedItem().toString().trim();

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
                                    next(facultyDept, facultyYear, facultySem, facultySubject);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void next(String facultyDept, String facultyYear, String facultySem, String facultySubject) {

        facultyRandomID = facultyDept + "_" + facultyYear + "_" + facultySem + "_" + facultySubject;
        Toast.makeText(this, "" + facultyRandomID, Toast.LENGTH_SHORT).show();


        databaseReferenceStudents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                studentItem.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    //String studentId=(String) dataSnapshot1.getKey();
                    details = dataSnapshot1.getValue(StudentItem.class);
                    studentItem.add(details);


                }
                query = databaseReferenceStudents.child(studentItem.get(0).getStudentId()).child("SubjectDetails").orderByChild("studentRandomId").equalTo(facultyRandomID);

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        studentItemList.clear();
                        if (dataSnapshot.exists()) {

                            for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                // do something with the individual "issues"
                                StudentItemSubjects details = issue.getValue(StudentItemSubjects.class);

                                studentItemList.add(details);

                            }
                            if (studentItemList.size() == 0) {
                                editEmpty.setVisibility(View.VISIBLE);
                            } else {
                                editEmpty.setVisibility(View.GONE);
                            }

                            facultyAttendanceAdapter = new FacultyAttendanceAdapter(TakeAttendanceActivity.this, studentItemList);
                            studentList.setAdapter(facultyAttendanceAdapter);

                        } else {
                            editEmpty.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }




        @Override
        public void onCancelled (@NonNull DatabaseError databaseError){

        }
    });


        /*databaseReferenceStudents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                studentItemList.clear();
                if (dataSnapshot.exists()) {

                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        StudentItemSubjects details = issue.getValue(StudentItemSubjects.class);

                        studentItemList.add(details);

                    }
                    if (studentItemList.size() == 0) {
                        editEmpty.setVisibility(View.VISIBLE);
                    } else {
                        editEmpty.setVisibility(View.GONE);
                    }

                    facultyAttendanceAdapter = new FacultyAttendanceAdapter(TakeAttendanceActivity.this, studentItemList);
                    studentList.setAdapter(facultyAttendanceAdapter);

                } else {
                    editEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


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
