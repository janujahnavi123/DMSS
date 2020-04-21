package com.example.attendance.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.attendance.R;
import com.example.attendance.model.SubjectItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddSubjectsActivity extends AppCompatActivity {

    Spinner spinnerDept, spinnerYear, spinnerSemester;
    String subjectId,department, year, semester, subject,randomId;
    Button btnSubmit;
    EditText editSubject;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subjects);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add Subject");
        }

        editSubject = findViewById(R.id.editSubject);
        spinnerDept = findViewById(R.id.spinnerDept);
        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerSemester = findViewById(R.id.spinnerSemester);
        btnSubmit = findViewById(R.id.btnSubmit);

        databaseReference = FirebaseDatabase.getInstance().getReference("SubjectDetails");

        spinnerDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                department = spinnerDept.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year = spinnerYear.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                semester = spinnerSemester.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subjectId = databaseReference.push().getKey();
                subject=editSubject.getText().toString().trim();
                randomId = department + "_" + year + "_" + semester;

                if (department.equals("Select Department")) {
                    Toast.makeText(AddSubjectsActivity.this, "Please Select Department", Toast.LENGTH_SHORT).show();
                }
                if (year.equals("Select Year")) {
                    Toast.makeText(AddSubjectsActivity.this, "Please Select Year", Toast.LENGTH_SHORT).show();
                }
                if (semester.equals("Select Semester")) {
                    Toast.makeText(AddSubjectsActivity.this, "Please Select Semester", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(subject)){
                    Toast.makeText(AddSubjectsActivity.this, "Please Enter Subject", Toast.LENGTH_SHORT).show();
                }
                if (!subject.isEmpty() &&  !department.equals("Select Department") && !year.equals("Select Year") && !semester.equals("Select Semester")) {

                    SubjectItem subjectItem = new SubjectItem(subjectId, department, year, semester, subject, randomId);
                    databaseReference.child(subjectId).setValue(subjectItem);
                    Toast.makeText(AddSubjectsActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    spinnerDept.setSelection(0);
                    spinnerYear.setSelection(0);
                    spinnerSemester.setSelection(0);
                    editSubject.setText("");

                }else {
                    Toast.makeText(AddSubjectsActivity.this, "Failed...!", Toast.LENGTH_SHORT).show();
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
