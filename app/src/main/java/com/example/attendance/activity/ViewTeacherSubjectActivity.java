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
import com.example.attendance.adapter.FacultySubjectListAdapter;
import com.example.attendance.model.FacultyItemSubjects;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewTeacherSubjectActivity extends AppCompatActivity {


    ListView allSubjectList;
    FacultySubjectListAdapter facultySubjectListAdapter;
    List<FacultyItemSubjects> facultyItems;
    DatabaseReference myRef;
    String TAG = "FIREBASE_DATA";
    TextView editEmpty;
    String facultyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teacher_subject);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("All Subjects");
        }
        Intent intent=getIntent();
        facultyId=intent.getStringExtra("facultyId");

        allSubjectList=findViewById(R.id.allSubjectList);
        editEmpty=findViewById(R.id.editEmpty);
        myRef = FirebaseDatabase.getInstance().getReference("FacultyDetails");
        facultyItems = new ArrayList<>();



        myRef.child(facultyId).child("SubjectDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                facultyItems.clear();
                for (DataSnapshot issue : dataSnapshot.getChildren()) {
                    // do something with the individual "issues"
                    FacultyItemSubjects details = issue.getValue(FacultyItemSubjects.class);
                    facultyItems.add(details);
                }
                if (facultyItems.size()==0){
                    editEmpty.setVisibility(View.VISIBLE);
                }
                facultySubjectListAdapter = new FacultySubjectListAdapter(ViewTeacherSubjectActivity.this, facultyItems);
                allSubjectList.setAdapter(facultySubjectListAdapter);


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