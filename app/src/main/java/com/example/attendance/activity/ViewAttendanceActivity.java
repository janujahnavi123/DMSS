package com.example.attendance.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendance.R;
import com.example.attendance.adapter.FacultyAttendanceAdapter;
import com.example.attendance.model.StudentItem;
import com.example.attendance.model.StudentdataItem;
import com.example.attendance.utils.MyAppPrefsManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

public class ViewAttendanceActivity extends AppCompatActivity {

    Spinner spinnerDept, spinnerYear, spinnerSemester, spinnerSubject,spinnerPeriod;
    String facultyDept, facultyYear, facultySem, facultySubject,facultyPeriod;
    String facultyUserID, facultyRandomID;

    DatabaseReference databaseReference;

    DatabaseReference databaseReferenceStudents;
    DatabaseReference databaseReferenceStudentSubjects;
    DatabaseReference databaseReferencePeriod;

    List<String> departments;
    List<String> years;
    List<String> semesters;
    List<String> subjects;
    List<String> periods;
    String subjectID;
    private FirebaseAuth mAuth;
    TextView editEmpty;
    String TAG = "FIREBASE_DATA";
    String date;
    ListView studentList;
    String email;

    MyAppPrefsManager myAppPrefsManager;
    FacultyAttendanceAdapter facultyAttendanceAdapter;
    ArrayList<StudentdataItem> studentItemList;
    List<StudentItem> studentItem;
    String facultyId;
    StudentItem details;
    Query query;
    String namesPresent = "";
    String namesAbsent = "";
    int presentCount = 0;
    int absentCount = 0;
    ProgressDialog progressDialog;
    EditText selectDate;
    int mYear, mMonth, mDay;
    Button btnGet;

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("View Attendance");
        }

        myAppPrefsManager = new MyAppPrefsManager(ViewAttendanceActivity.this);

        progressDialog = new ProgressDialog(ViewAttendanceActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        email = myAppPrefsManager.getUserName();


        mAuth = FirebaseAuth.getInstance();

        departments = new ArrayList<>();
        periods = new ArrayList<>();

        years = new ArrayList<>();

        semesters = new ArrayList<>();

        subjects = new ArrayList<>();


        studentItemList = new ArrayList<>();
        studentItem = new ArrayList<>();
        studentList = (ListView) findViewById(R.id.studentList);

        selectDate = findViewById(R.id.selectDate);
        btnGet = findViewById(R.id.btnGet);


        editEmpty = findViewById(R.id.editEmpty);
        spinnerDept = findViewById(R.id.spinnerDept);
        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerSemester = findViewById(R.id.spinnerSemester);
        spinnerSubject = findViewById(R.id.spinnerSubject);
        spinnerPeriod = findViewById(R.id.spinnerPeriod);

        databaseReference = FirebaseDatabase.getInstance().getReference("FacultyDetails");
        databaseReferenceStudents = FirebaseDatabase.getInstance().getReference("StudentDetails");
        databaseReferenceStudentSubjects = FirebaseDatabase.getInstance().getReference("StudentSubjectDetails");
        databaseReferencePeriod = FirebaseDatabase.getInstance().getReference("PeriodDetails");

        editEmpty.setVisibility(View.VISIBLE);


        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date of birth picker


                Calendar calendar = Calendar.getInstance();
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                int mMonth = calendar.get(Calendar.MONTH);
                int mYear = calendar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(ViewAttendanceActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear += 1;
                        String mt, dy;   //local variable
                        if (monthOfYear < 10)
                            mt = "0" + monthOfYear; //if month less than 10 then ad 0 before month
                        else mt = String.valueOf(monthOfYear);

                        if (dayOfMonth < 10)
                            dy = "0" + dayOfMonth;
                        else dy = String.valueOf(dayOfMonth);
                        selectDate.setText(dy + "-" + mt + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();


            }
        });


        getDepartment(email);
        getPeriods();


        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                next();
            }
        });


    }

    public void getPeriods() {

        databaseReferencePeriod.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                periods.clear();

                if (dataSnapshot.exists()) {


                    for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                        String period = subjectSnapshot.child("period").getValue(String.class);


                        periods.add(period);


                    }


                    ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(ViewAttendanceActivity.this, R.layout.support_simple_spinner_dropdown_item, periods);
                    departmentAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerPeriod.setAdapter(departmentAdapter);


                    spinnerPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            facultyPeriod = spinnerPeriod.getSelectedItem().toString().trim();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                    progressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }

    public void getDepartment(String email) {

        Query query = databaseReference.orderByChild("facultyEmail").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                departments.clear();
                years.clear();
                semesters.clear();
                subjects.clear();

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
                                //departments.add("Select Department");
                                departments.addAll(hashSet);


                                HashSet<String> hashSet1 = new HashSet<String>(years);
                                years.clear();
                                //years.add("Select Year");
                                years.addAll(hashSet1);

                                HashSet<String> hashSet2 = new HashSet<String>(semesters);
                                semesters.clear();
                                //semesters.add("Select Semester");
                                semesters.addAll(hashSet2);

                                HashSet<String> hashSet3 = new HashSet<String>(subjects);
                                subjects.clear();
                                //subjects.add("Select Subject");
                                subjects.addAll(hashSet3);
                            }


                            ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(ViewAttendanceActivity.this, R.layout.support_simple_spinner_dropdown_item, departments);
                            departmentAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            spinnerDept.setAdapter(departmentAdapter);

                            ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(ViewAttendanceActivity.this, R.layout.support_simple_spinner_dropdown_item, years);
                            departmentAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            spinnerYear.setAdapter(yearAdapter);

                            ArrayAdapter<String> semesterAdapter = new ArrayAdapter<String>(ViewAttendanceActivity.this, R.layout.support_simple_spinner_dropdown_item, semesters);
                            departmentAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            spinnerSemester.setAdapter(semesterAdapter);

                            ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(ViewAttendanceActivity.this, R.layout.support_simple_spinner_dropdown_item, subjects);
                            subjectAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            spinnerSubject.setAdapter(subjectAdapter);

                            spinnerDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    facultyDept = spinnerDept.getSelectedItem().toString().trim();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });


                            spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    facultyYear = spinnerYear.getSelectedItem().toString().trim();

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });


                            spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    facultySem = spinnerSemester.getSelectedItem().toString().trim();

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });


                            spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    facultySubject = spinnerSubject.getSelectedItem().toString().trim();

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

                    progressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                next(facultyDept, facultyYear, facultySem, facultySubject);
            }
        },3000);*/


    }


    public void next() {

        facultyRandomID = facultyDept + "_" + facultyYear + "_" + facultySem + "_" + facultySubject;

        date = selectDate.getText().toString();

        String randomId = facultyRandomID + "_" + date+"_"+facultyPeriod;


        Query query = databaseReference.child(facultyId).child("Attendance").orderByChild("randomId").equalTo(randomId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                studentItemList.clear();
                if (dataSnapshot.exists()) {

                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        StudentdataItem details = issue.getValue(StudentdataItem.class);

                        studentItemList.add(details);

                    }
                    if (studentItemList.size() == 0) {
                        editEmpty.setVisibility(View.VISIBLE);
                    } else {
                        editEmpty.setVisibility(View.GONE);
                    }


                    facultyAttendanceAdapter = new FacultyAttendanceAdapter(ViewAttendanceActivity.this, studentItemList);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}

