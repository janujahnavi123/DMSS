package com.example.attendance.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendance.R;
import com.example.attendance.adapter.StudentListAdapter;
import com.example.attendance.model.StudentItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ViewAllStudentsActivity extends AppCompatActivity {


    ListView allStudentList;
    FirebaseDatabase database;
    DatabaseReference myRef;
    StudentListAdapter studentListAdapter;
    List<StudentItem> studentItemList;
    String TAG = "FIREBASE_DATA";
    TextView editEmpty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_students);
        allStudentList = findViewById(R.id.allStudentList);
        editEmpty = findViewById(R.id.editEmpty);
        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("StudentDetails");
        studentItemList = new ArrayList<>();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("All Students");
        }
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                studentItemList.clear();
                for (DataSnapshot issue : dataSnapshot.getChildren()) {
                    // do something with the individual "issues"
                    StudentItem details = issue.getValue(StudentItem.class);
                    studentItemList.add(details);
                }
                if (studentItemList.size()==0){
                    editEmpty.setVisibility(View.VISIBLE);
                }
                studentListAdapter = new StudentListAdapter(ViewAllStudentsActivity.this, studentItemList);
                allStudentList.setAdapter(studentListAdapter);


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
