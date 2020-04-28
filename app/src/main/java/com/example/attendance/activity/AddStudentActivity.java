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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddStudentActivity extends AppCompatActivity {
    EditText edtStudentName, edtStudentRoll;
    Spinner spinnerDepartment, spinnerYear, spinnerSemester;
    String studentId, studentName, studentRoll, studentDepartment, studentYear, studentSemester, studentRandomId;
    Button btnStudentSubmit;
    DatabaseReference myRef;
    DatabaseReference databaseReferenceDepartment;
    DatabaseReference databaseReferenceYear;
    DatabaseReference databaseReferenceSemester;
    List<String> departmentList;
    List<String> yearsList;
    List<String> semesterList;

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

        spinnerDepartment = (Spinner) findViewById(R.id.spinnerDepartment);
        spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        spinnerSemester = (Spinner) findViewById(R.id.spinnerSemester);

        departmentList = new ArrayList<>();
        departmentList.add("Select Department");

        yearsList = new ArrayList<>();
        yearsList.add("Select Year");

        semesterList = new ArrayList<>();
        semesterList.add("Select Semester");

        myRef = FirebaseDatabase.getInstance().getReference("StudentDetails");
        databaseReferenceDepartment = FirebaseDatabase.getInstance().getReference("DepartmentDetails");
        databaseReferenceYear = FirebaseDatabase.getInstance().getReference("YearDetails");
        databaseReferenceSemester = FirebaseDatabase.getInstance().getReference("SemesterDetails");


        databaseReferenceDepartment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                departmentList.clear();
                departmentList.add("Select Department");

                for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                    String department = subjectSnapshot.child("department").getValue(String.class);
                    departmentList.add(department);
                }


                ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(AddStudentActivity.this, android.R.layout.simple_spinner_item, departmentList);
                departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDepartment.setAdapter(departmentAdapter);

                spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        studentDepartment = spinnerDepartment.getSelectedItem().toString().trim();

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


                ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(AddStudentActivity.this, android.R.layout.simple_spinner_item, yearsList);
                yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerYear.setAdapter(yearAdapter);

                spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        studentYear = spinnerYear.getSelectedItem().toString().trim();

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


                ArrayAdapter<String> semesterAdapter = new ArrayAdapter<String>(AddStudentActivity.this, android.R.layout.simple_spinner_item, semesterList);
                semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSemester.setAdapter(semesterAdapter);
                spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        studentSemester = spinnerSemester.getSelectedItem().toString().trim();

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
        studentRandomId = studentDepartment + "_" + studentYear + "_" + studentSemester;

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


        if (!studentName.isEmpty() && !studentRoll.isEmpty() && !studentDepartment.equals("Select Department") && !studentYear.equals("Select Year") && !studentSemester.equals("Select Semester")) {


            myRef.child(studentRoll).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.getValue() != null) {
                        //user exists, do something
                        Toast.makeText(AddStudentActivity.this, "Already Student Roll Number Exists", Toast.LENGTH_SHORT).show();
                    } else {
                        StudentItem student = new StudentItem(studentId, studentName, studentRoll, studentDepartment, studentYear, studentSemester, studentRandomId);
                        myRef.child(studentId).setValue(student);
                        Toast.makeText(AddStudentActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                        edtStudentName.setText("");
                        edtStudentRoll.setText("");
                        spinnerDepartment.setSelection(0);
                        spinnerYear.setSelection(0);
                        spinnerSemester.setSelection(0);

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
