package com.example.attendance.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendance.R;
import com.example.attendance.model.FacultyItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddFacultyActivity extends AppCompatActivity {

    EditText editFaculty, editEmail, editPassword;
    //Spinner spinnerDept, spinnerYear, spinnerSemester, spinnerSubject;
    String facultyId, facultyName, facultyEmail, facultyPassword;
    //String facultyDept, facultyYear, facultySem, facultySubject;
    String facultyUserID, facultyRandomID;
    Button btnSubmit;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceSubjects;
    DatabaseReference databaseReferenceYears;
    DatabaseReference databaseReferenceSemester;
    DatabaseReference databaseReferenceBranch;

    //List<String> subjects;
    List<String> department;
    List<String> years;
    List<String> semester;
    List<String> subjects;
    String subjectID;
    private FirebaseAuth mAuth;

    String TAG = "FIREBASE_DATA";



    TextView txtDepartment,txtYear,txtSemester,txtSubject;
    String[] listDepartment,listYear,listSemester,listSubject;
    boolean[] checkedDepartment,checkedYear,checkedSemester,checkedSubject;
    
    ArrayList<Integer> departmentItems = new ArrayList<>();
    ArrayList<Integer> yearItems = new ArrayList<>();
    ArrayList<Integer> semesterItems = new ArrayList<>();
    ArrayList<Integer> subjectItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add Faculty");
        }

        mAuth = FirebaseAuth.getInstance();
        
        department=new ArrayList<>();
        years= new ArrayList<>();
        semester=new ArrayList<>();
        subjects = new ArrayList<>();
        //subjects.add("Select Subject");


        editFaculty = findViewById(R.id.editFaculty);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        /*spinnerDept = findViewById(R.id.spinnerDept);
        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerSemester = findViewById(R.id.spinnerSemester);
        spinnerSubject = findViewById(R.id.spinnerSubject);*/
        btnSubmit = findViewById(R.id.btnSubmit);
        txtDepartment = (TextView) findViewById(R.id.txtDepartment);
        txtYear = (TextView) findViewById(R.id.txtYear);
        txtSemester = (TextView) findViewById(R.id.txtSemester);
        txtSubject = (TextView) findViewById(R.id.txtSubject);

        databaseReference = FirebaseDatabase.getInstance().getReference("FacultyDetails");
        //databaseReferenceSubjects = FirebaseDatabase.getInstance().getReference("SubjectDetails");

        databaseReferenceBranch = FirebaseDatabase.getInstance().getReference("Branch");
        databaseReferenceYears = FirebaseDatabase.getInstance().getReference("years");
        databaseReferenceSemester = FirebaseDatabase.getInstance().getReference("Semester");
        databaseReferenceSubjects = FirebaseDatabase.getInstance().getReference("Subject");

        databaseReferenceBranch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                department.clear();

                for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                    String branch = subjectSnapshot.child("branch").getValue(String.class);
                    department.add(branch);

                }
                listDepartment = new String[department.size()];
                listDepartment = department.toArray(listDepartment);
                checkedDepartment = new boolean[listDepartment.length];

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        txtDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddFacultyActivity.this);
                mBuilder.setTitle("Select Department");
                mBuilder.setMultiChoiceItems(listDepartment, checkedDepartment, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            departmentItems.add(position);
                        }else{
                            departmentItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";

                        for (int i = 0; i < departmentItems.size(); i++) {

                            item = item + listDepartment[departmentItems.get(i)];
                            if (i != departmentItems.size() - 1) {
                                item = item + ", ";
                            }



                        }


                        if (item.equalsIgnoreCase("")){
                            txtDepartment.setText("Select Department");
                        }else {
                            txtDepartment.setText(item);
                        }

                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if (departmentItems.size()==0){
                            txtDepartment.setText("Select Department");
                        }
                    }
                });

                mBuilder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedDepartment.length; i++) {
                            checkedDepartment[i] = false;
                            departmentItems.clear();
                            txtDepartment.setText("Select Department");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        databaseReferenceYears.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                years.clear();

                for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                    String year = subjectSnapshot.child("year").getValue(String.class);
                    years.add(year);

                }
                listYear = new String[years.size()];
                listYear = years.toArray(listYear);
                checkedYear = new boolean[listYear.length];

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        txtYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddFacultyActivity.this);
                mBuilder.setTitle("Select Year");
                mBuilder.setMultiChoiceItems(listYear, checkedYear, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            yearItems.add(position);
                        }else{
                            yearItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";

                        for (int i = 0; i < yearItems.size(); i++) {

                            item = item + listYear[yearItems.get(i)];
                            if (i != yearItems.size() - 1) {
                                item = item + ", ";
                            }



                        }


                        if (item.equalsIgnoreCase("")){
                            txtYear.setText("Select Year");
                        }else {
                            txtYear.setText(item);
                        }

                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if (yearItems.size()==0){
                            txtYear.setText("Select Year");
                        }
                    }
                });

                mBuilder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedYear.length; i++) {
                            checkedYear[i] = false;
                            yearItems.clear();
                            txtYear.setText("Select Year");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
        databaseReferenceSemester.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                semester.clear();

                for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                    String semesters = subjectSnapshot.child("semester").getValue(String.class);
                    semester.add(semesters);

                }
                listSemester = new String[semester.size()];
                listSemester = semester.toArray(listSemester);
                checkedSemester = new boolean[listSemester.length];

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        txtSemester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddFacultyActivity.this);
                mBuilder.setTitle("Select Semester");
                mBuilder.setMultiChoiceItems(listSemester, checkedSemester, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            semesterItems.add(position);
                        }else{
                            semesterItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";

                        for (int i = 0; i < semesterItems.size(); i++) {

                            item = item + listSemester[semesterItems.get(i)];
                            if (i != semesterItems.size() - 1) {
                                item = item + ", ";
                            }



                        }


                        if (item.equalsIgnoreCase("")){
                            txtSemester.setText("Select Semester");
                        }else {
                            txtSemester.setText(item);
                        }

                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if (semesterItems.size()==0){
                            txtSemester.setText("Select Semester");
                        }
                    }
                });

                mBuilder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedSemester.length; i++) {
                            checkedSemester[i] = false;
                            semesterItems.clear();
                            txtSemester.setText("Select Semester");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });


        databaseReferenceSubjects.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                subjects.clear();

                for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                    String subject = subjectSnapshot.child("subject").getValue(String.class);
                    subjects.add(subject);

                }
                listSubject = new String[subjects.size()];
                listSubject = subjects.toArray(listSubject);
                checkedSubject = new boolean[listSubject.length];

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        txtSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddFacultyActivity.this);
                mBuilder.setTitle("Select Subject");
                mBuilder.setMultiChoiceItems(listSubject, checkedSubject, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            subjectItems.add(position);
                        }else{
                            subjectItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";

                        for (int i = 0; i < subjectItems.size(); i++) {

                            item = item + listSubject[subjectItems.get(i)];
                            if (i != subjectItems.size() - 1) {
                                item = item + ", ";
                            }



                        }


                        if (item.equalsIgnoreCase("")){
                            txtSubject.setText("Select Subject");
                        }else {
                            txtSubject.setText(item);
                        }

                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if (subjectItems.size()==0){
                            txtSubject.setText("Select Subject");
                        }
                    }
                });

                mBuilder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedSubject.length; i++) {
                            checkedSubject[i] = false;
                            subjectItems.clear();
                            txtSubject.setText("Select Subject");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });










      /*  spinnerDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                facultyDept = spinnerDept.getSelectedItem().toString().trim();
                getSubjects(facultyDept, facultyYear, facultySem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                facultyYear = spinnerYear.getSelectedItem().toString().trim();
                getSubjects(facultyDept, facultyYear, facultySem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                facultySem = spinnerSemester.getSelectedItem().toString().trim();
                getSubjects(facultyDept, facultyYear, facultySem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                next();

            }
        });
    }



   /* public void getSubjects(String facultyDept, String facultyYear, String facultySem) {

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


                ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(AddFacultyActivity.this, android.R.layout.simple_spinner_item, subjects);
                subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSubject.setAdapter(subjectAdapter);




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



    }*/

    public void next() {

        facultyId = databaseReference.push().getKey();

        facultyName = editFaculty.getText().toString();
        facultyEmail = editEmail.getText().toString();
        facultyPassword = editPassword.getText().toString();


        facultyUserID = facultyEmail + "_" + facultyPassword;
        //facultyRandomID = facultyDept + "_" + facultyYear + "_" + facultySem + "_" + facultySubject;


        if (TextUtils.isEmpty(facultyName)) {
            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(facultyEmail) || !isValidEmail(facultyEmail)) {
            Toast.makeText(this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(facultyPassword) || !isValidPassword1(facultyPassword)) {
            Toast.makeText(this, "Please Enter Valid Password", Toast.LENGTH_SHORT).show();
        }
       /* if (facultyDept.equals("Select Department")) {
            Toast.makeText(AddFacultyActivity.this, "Please Select Department", Toast.LENGTH_SHORT).show();
        }
        if (facultyYear.equals("Select Year")) {
            Toast.makeText(AddFacultyActivity.this, "Please Select Year", Toast.LENGTH_SHORT).show();
        }
        if (facultySem.equals("Select Semester")) {
            Toast.makeText(AddFacultyActivity.this, "Please Select Semester", Toast.LENGTH_SHORT).show();
        }*/
/*
        if (!facultyName.isEmpty() && !facultyEmail.isEmpty() && !facultyPassword.isEmpty() && !facultyDept.equals("Select Department") && !facultyYear.equals("Select Year") && !facultySem.equals("Select Semester")) {
*/
        if (!facultyName.isEmpty() && !facultyEmail.isEmpty() && !facultyPassword.isEmpty() ) {


            mAuth.createUserWithEmailAndPassword(facultyEmail, facultyPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Log.d(TAG, "createUserWithEmail:success" + user);
                                FacultyItem faculty = new FacultyItem(facultyId, facultyName, facultyEmail, facultyPassword, "", "", "", "", facultyUserID, "");
                                databaseReference.child(facultyId).setValue(faculty);
                                Toast.makeText(AddFacultyActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                                editFaculty.setText("");
                                editEmail.setText("");
                                editPassword.setText("");
                               /* spinnerDept.setSelection(0);
                                spinnerYear.setSelection(0);
                                spinnerSemester.setSelection(0);
                                spinnerSubject.setSelection(0);*/

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(AddFacultyActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }


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


    private boolean isValidEmail(String emailId) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailId);
        return matcher.matches();
    }


    // Validating password
    public static boolean isValidPassword1(String pass1) {

        return pass1 == null || pass1.length() <= 5;
    }

}






