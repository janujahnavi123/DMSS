package com.example.attendance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendance.R;
import com.example.attendance.adapter.StudentSubjectListAdapter;
import com.example.attendance.model.StudentItemSubjects;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentSubjectActivity extends AppCompatActivity {


    ListView allSubjectList;
    StudentSubjectListAdapter subjectListAdapter;
    List<StudentItemSubjects> studentItems;
    DatabaseReference myRef;
    String TAG = "FIREBASE_DATA";
    TextView editEmpty;
    String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_subject);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("All Subjects");
        }
        Intent intent=getIntent();
        studentId=intent.getStringExtra("studentId");

        allSubjectList=findViewById(R.id.allSubjectList);
        editEmpty=findViewById(R.id.editEmpty);
        myRef = FirebaseDatabase.getInstance().getReference("StudentDetails");
        studentItems = new ArrayList<>();



        myRef.child(studentId).child("SubjectDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                studentItems.clear();
                for (DataSnapshot issue : dataSnapshot.getChildren()) {
                    // do something with the individual "issues"
                    StudentItemSubjects details = issue.getValue(StudentItemSubjects.class);
                    studentItems.add(details);
                }
                if (studentItems.size()==0){
                    editEmpty.setVisibility(View.VISIBLE);
                }
                subjectListAdapter = new StudentSubjectListAdapter(ViewStudentSubjectActivity.this, studentItems);
                allSubjectList.setAdapter(subjectListAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.d(TAG, "onCancelled: " + error);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}