package com.example.attendance.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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
import com.example.attendance.adapter.ItemSelectedAdapter;
import com.example.attendance.model.AttendanceModel;
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
    DatabaseReference databaseReferenceStudentSubjects;


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
    //FacultyAttendanceAdapter facultyAttendanceAdapter;
    ItemSelectedAdapter facultyAttendanceAdapter;
    //List<AttendanceModel> studentItemList;
    ArrayList<AttendanceModel> studentItemList;
    List<StudentItem> studentItem;
    String facultyId;
    StudentItem details;
    Query query;
    String namesPresent = "";
    String namesAbsent = "";
    String numPresent = "";
    String numAbsent = "";
    int presentCount = 0;
    int absentCount = 0;
    ProgressDialog progressDialog;
    Button btnGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add Attendance");
        }

        myAppPrefsManager = new MyAppPrefsManager(TakeAttendanceActivity.this);

        progressDialog = new ProgressDialog(TakeAttendanceActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        email = myAppPrefsManager.getUserName();


        mAuth = FirebaseAuth.getInstance();

        departments = new ArrayList<>();

        years = new ArrayList<>();

        semesters = new ArrayList<>();

        subjects = new ArrayList<>();


        studentItemList = new ArrayList<>();
        studentItem = new ArrayList<>();
        studentList = (ListView) findViewById(R.id.studentList);

        btnGet = findViewById(R.id.btnGet);
        editEmpty = findViewById(R.id.editEmpty);
        spinnerDept = findViewById(R.id.spinnerDept);
        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerSemester = findViewById(R.id.spinnerSemester);
        spinnerSubject = findViewById(R.id.spinnerSubject);
        btnSubmit = findViewById(R.id.btnSubmit);

        databaseReference = FirebaseDatabase.getInstance().getReference("FacultyDetails");
        databaseReferenceStudents = FirebaseDatabase.getInstance().getReference("StudentDetails");
        databaseReferenceStudentSubjects = FirebaseDatabase.getInstance().getReference("StudentSubjectDetails");


        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        editEmpty.setVisibility(View.VISIBLE);
        getDepartment(email);

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
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


                            ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(TakeAttendanceActivity.this, android.R.layout.simple_spinner_dropdown_item, departments);
                            departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerDept.setAdapter(departmentAdapter);

                            ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(TakeAttendanceActivity.this, android.R.layout.simple_spinner_dropdown_item, years);
                            departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerYear.setAdapter(yearAdapter);

                            ArrayAdapter<String> semesterAdapter = new ArrayAdapter<String>(TakeAttendanceActivity.this, android.R.layout.simple_spinner_dropdown_item, semesters);
                            departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerSemester.setAdapter(semesterAdapter);

                            ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(TakeAttendanceActivity.this, android.R.layout.simple_spinner_dropdown_item, subjects);
                            subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

        query = databaseReferenceStudentSubjects.orderByChild("studentRandomId").equalTo(facultyRandomID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                studentItemList.clear();
                if (dataSnapshot.exists()) {

                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        AttendanceModel details = issue.getValue(AttendanceModel.class);

                        studentItemList.add(details);

                    }
                    if (studentItemList.size() == 0) {
                        editEmpty.setVisibility(View.VISIBLE);
                    } else {
                        editEmpty.setVisibility(View.GONE);
                    }

                    /*facultyAttendanceAdapter = new FacultyAttendanceAdapter(TakeAttendanceActivity.this, studentItemList);
                    studentList.setAdapter(facultyAttendanceAdapter);*/

                    facultyAttendanceAdapter = new ItemSelectedAdapter(TakeAttendanceActivity.this, studentItemList);
                    studentList.setAdapter(facultyAttendanceAdapter);

                    studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView adapterView, View view, int position, long l) {
                            //Item Selected from list
                            facultyAttendanceAdapter.setCheckBox(position);
                            Log.d(TAG, "onItemClick: " + position);

                        }
                    });


                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Display All Item Selected

                            List<String> listPresent = new ArrayList<>();
                            List<String> listAbsent = new ArrayList<>();


                            listPresent.clear();
                            listAbsent.clear();
                            for (AttendanceModel hold : facultyAttendanceAdapter.getAllData()) {
                                if (hold.isCheckbox()) {


                                    namesPresent += hold.getStudentName() + ",";
                                    numPresent += hold.getStudentRoll() + ",";
                                    listPresent.add(namesPresent);

                                } else {

                                    namesAbsent += hold.getStudentName() + ",";
                                    numAbsent += hold.getStudentRoll() + ",";
                                    listAbsent.add(namesAbsent);

                                }
                            }

                            presentCount = listPresent.size();
                            Log.d(TAG, "onClickPresent: " + presentCount);
                            absentCount = listAbsent.size();
                            Log.d(TAG, "onClickAbsent: " + absentCount);

                            String id = databaseReference.push().getKey();
                            String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                            String randomId = facultyRandomID + "_" + date;

                            //Query query = databaseReference.orderByChild("facultyId").equalTo(facultyId);
                            databaseReference.child(facultyId).child("Attendance").child(randomId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.getChildrenCount() > 0) {
                                        //username found

                                        if (dataSnapshot.getValue() != null) {
                                            Toast.makeText(TakeAttendanceActivity.this, "Already Attendance was taken", Toast.LENGTH_SHORT).show();

                                        } else {
                                            StudentdataItem student = new StudentdataItem(id, date, namesPresent, namesAbsent, numPresent, numAbsent, String.valueOf(presentCount), String.valueOf(absentCount), email, facultyRandomID, randomId);
                                            databaseReference.child(facultyId).child("Attendance").child(randomId).setValue(student);
                                            Toast.makeText(TakeAttendanceActivity.this, "Attendance was Sucessfully Taken", Toast.LENGTH_SHORT).show();

                                        }

                                    } else {
                                        StudentdataItem student = new StudentdataItem(id, date, namesPresent, namesAbsent, numPresent, numAbsent, String.valueOf(presentCount), String.valueOf(absentCount), email, facultyRandomID, randomId);
                                        databaseReference.child(facultyId).child("Attendance").child(randomId).setValue(student);
                                        Toast.makeText(TakeAttendanceActivity.this, "Attendance was Sucessfully Taken", Toast.LENGTH_SHORT).show();

                                    }


                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    });
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
