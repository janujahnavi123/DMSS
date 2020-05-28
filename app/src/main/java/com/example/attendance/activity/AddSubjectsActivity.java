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
import com.example.attendance.model.SubjectItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddSubjectsActivity extends AppCompatActivity {

    Spinner spinnerDept, spinnerYear, spinnerSemester;
    String subjectId, department, year, semester, subject, randomId,subjectRandomId;
    Button btnSubmit;
    EditText editSubject;
    DatabaseReference databaseReferenceDepartment;
    DatabaseReference databaseReferenceYear;
    DatabaseReference databaseReferenceSemester;
    DatabaseReference databaseReferenceSubject;

    List<String> departmentList;
    List<String> yearsList;
    List<String> semesterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subjects);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add Subject");
        }

        departmentList = new ArrayList<>();
        departmentList.add("Select Department");

        yearsList = new ArrayList<>();
        yearsList.add("Select Year");

        semesterList = new ArrayList<>();
        semesterList.add("Select Semester");

        editSubject = findViewById(R.id.editSubject);
        spinnerDept = findViewById(R.id.spinnerDept);
        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerSemester = findViewById(R.id.spinnerSemester);
        btnSubmit = findViewById(R.id.btnSubmit);

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


                ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(AddSubjectsActivity.this, android.R.layout.simple_spinner_item, departmentList);
                departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDept.setAdapter(departmentAdapter);

                spinnerDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        department = spinnerDept.getSelectedItem().toString().trim();
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


                ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(AddSubjectsActivity.this, android.R.layout.simple_spinner_item, yearsList);
                yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerYear.setAdapter(yearAdapter);

                spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        year = spinnerYear.getSelectedItem().toString().trim();
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


                ArrayAdapter<String> semesterAdapter = new ArrayAdapter<String>(AddSubjectsActivity.this, android.R.layout.simple_spinner_item, semesterList);
                semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSemester.setAdapter(semesterAdapter);
                spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        semester = spinnerSemester.getSelectedItem().toString().trim();
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

                subjectId = databaseReferenceSubject.push().getKey();
                subject = editSubject.getText().toString().trim();
                randomId = department + "_" + year + "_" + semester;
                subjectRandomId = department + "_" + year + "_" + semester + "_" +subject;

                if (department.equals("Select Department")) {
                    Toast.makeText(AddSubjectsActivity.this, "Please Select Department", Toast.LENGTH_SHORT).show();
                }
                if (year.equals("Select Year")) {
                    Toast.makeText(AddSubjectsActivity.this, "Please Select Year", Toast.LENGTH_SHORT).show();
                }
                if (semester.equals("Select Semester")) {
                    Toast.makeText(AddSubjectsActivity.this, "Please Select Semester", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(subject)) {
                    Toast.makeText(AddSubjectsActivity.this, "Please Enter Subject", Toast.LENGTH_SHORT).show();
                }
                if (!subject.isEmpty() && !department.equals("Select Department") && !year.equals("Select Year") && !semester.equals("Select Semester")) {




                    databaseReferenceSubject.child(subjectRandomId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.getValue() != null) {
                                //user exists, do something
                                Toast.makeText(AddSubjectsActivity.this, "Already Subject Exits", Toast.LENGTH_SHORT).show();
                            } else {
                                SubjectItem subjectItem = new SubjectItem(subjectId, department, year, semester, subject, randomId,subjectRandomId);
                                databaseReferenceSubject.child(subjectRandomId).setValue(subjectItem);
                                Toast.makeText(AddSubjectsActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                                spinnerDept.setSelection(0);
                                spinnerYear.setSelection(0);
                                spinnerSemester.setSelection(0);
                                editSubject.setText("");


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else {
                    Toast.makeText(AddSubjectsActivity.this, "Enter Valid Details...!", Toast.LENGTH_SHORT).show();
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
}
