package com.example.attendance.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.attendance.BuildConfig;
import com.example.attendance.R;
import com.example.attendance.adapter.ItemSelectedAdapter;
import com.example.attendance.model.AttendanceModel;
import com.example.attendance.model.StudentItem;
import com.example.attendance.model.StudentdataItem;
import com.example.attendance.utils.FileCompressor;
import com.example.attendance.utils.MyAppPrefsManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class TakeAttendanceActivity extends AppCompatActivity {

    public static final int REQUEST_TAKE_PHOTO = 1;
    File mPhotoFile;
    FileCompressor mCompressor;
    ImageView imgProfile;

    Spinner spinnerDept, spinnerYear, spinnerSemester, spinnerSubject, spinnerPeriod;
    String facultyDept, facultyYear, facultySem, facultySubject, facultyPeriod;
    String facultyUserID, facultyRandomID;
    Button btnSubmit;
    DatabaseReference databaseReference;

    DatabaseReference databaseReferenceStudents;
    DatabaseReference databaseReferenceStudentSubjects;
    DatabaseReference databaseReferencePeriods;


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
    ProgressDialog progressDialog, regProgress;
    Button btnGet;
    StorageReference storageReference;

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
        regProgress = new ProgressDialog(TakeAttendanceActivity.this);
        progressDialog = new ProgressDialog(TakeAttendanceActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        email = myAppPrefsManager.getUserName();


        mAuth = FirebaseAuth.getInstance();

        departments = new ArrayList<>();

        years = new ArrayList<>();
        periods = new ArrayList<>();

        semesters = new ArrayList<>();

        subjects = new ArrayList<>();


        studentItemList = new ArrayList<>();
        studentItem = new ArrayList<>();
        studentList = (ListView) findViewById(R.id.studentList);

        imgProfile = findViewById(R.id.imgProfile);
        btnGet = findViewById(R.id.btnGet);
        editEmpty = findViewById(R.id.editEmpty);
        spinnerDept = findViewById(R.id.spinnerDept);
        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerSemester = findViewById(R.id.spinnerSemester);
        spinnerPeriod = findViewById(R.id.spinnerPeriod);
        spinnerSubject = findViewById(R.id.spinnerSubject);
        btnSubmit = findViewById(R.id.btnSubmit);

        mCompressor = new FileCompressor(TakeAttendanceActivity.this);

        storageReference = FirebaseStorage.getInstance().getReference();


        databaseReference = FirebaseDatabase.getInstance().getReference("FacultyDetails");
        databaseReferenceStudents = FirebaseDatabase.getInstance().getReference("StudentDetails");
        databaseReferenceStudentSubjects = FirebaseDatabase.getInstance().getReference("StudentSubjectDetails");
        databaseReferencePeriods = FirebaseDatabase.getInstance().getReference("PeriodDetails");


        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        editEmpty.setVisibility(View.VISIBLE);
        getDepartment(email);

        getPeriods();
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }


    public void getPeriods() {

        databaseReferencePeriods.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                periods.clear();

                if (dataSnapshot.exists()) {


                    for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                        String period = subjectSnapshot.child("period").getValue(String.class);


                        periods.add(period);


                    }


                    ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(TakeAttendanceActivity.this, R.layout.support_simple_spinner_dropdown_item, periods);
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


                            ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(TakeAttendanceActivity.this, R.layout.support_simple_spinner_dropdown_item, departments);
                            departmentAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            spinnerDept.setAdapter(departmentAdapter);

                            ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(TakeAttendanceActivity.this, R.layout.support_simple_spinner_dropdown_item, years);
                            departmentAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            spinnerYear.setAdapter(yearAdapter);

                            ArrayAdapter<String> semesterAdapter = new ArrayAdapter<String>(TakeAttendanceActivity.this, R.layout.support_simple_spinner_dropdown_item, semesters);
                            departmentAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            spinnerSemester.setAdapter(semesterAdapter);

                            ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(TakeAttendanceActivity.this, R.layout.support_simple_spinner_dropdown_item, subjects);
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
                    facultyAttendanceAdapter.notifyDataSetChanged();

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

                            if (mPhotoFile == null) {
                                Toast.makeText(TakeAttendanceActivity.this, "Please select Image", Toast.LENGTH_SHORT).show();
                            } else {
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

                                String randomId = facultyRandomID + "_" + date+"_"+facultyPeriod;

                                //Query query = databaseReference.orderByChild("facultyId").equalTo(facultyId);
                                databaseReference.child(facultyId).child("Attendance").child(randomId).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if (dataSnapshot.getChildrenCount() > 0) {
                                            //username found

                                            if (dataSnapshot.getValue() != null) {

                                                numPresent="";
                                                namesPresent="";
                                                namesAbsent="";
                                                numAbsent="";

                                                Toast.makeText(TakeAttendanceActivity.this, "Already Attendance was taken", Toast.LENGTH_SHORT).show();

                                            } else {
                                                StorageReference ref = storageReference.child("Images/" + id);
                                                ref.putFile(Uri.fromFile(mPhotoFile)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                                        while (!uriTask.isSuccessful()) ;
                                                        Uri downloadUrl = uriTask.getResult();
                                                        assert downloadUrl != null;

                                                        StudentdataItem student = new StudentdataItem(downloadUrl.toString(), id, date, namesPresent, namesAbsent, numPresent, numAbsent, String.valueOf(presentCount), String.valueOf(absentCount), email, facultyRandomID, randomId);
                                                        databaseReference.child(facultyId).child("Attendance").child(randomId).setValue(student);
                                                        Toast.makeText(TakeAttendanceActivity.this, "Attendance was Sucessfully Taken", Toast.LENGTH_SHORT).show();

                                                        listPresent.clear();
                                                        listAbsent.clear();
                                                        numPresent="";
                                                        namesPresent="";
                                                        namesAbsent="";
                                                        numAbsent="";
                                                        facultyAttendanceAdapter.notifyDataSetChanged();
                                                        regProgress.dismiss();


                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        regProgress.dismiss();
                                                        listPresent.clear();
                                                        listAbsent.clear();
                                                        numPresent="";
                                                        namesPresent="";
                                                        namesAbsent="";
                                                        numAbsent="";
                                                        facultyAttendanceAdapter.notifyDataSetChanged();
                                                        Toast.makeText(TakeAttendanceActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                                        regProgress.show();
                                                        regProgress.setCanceledOnTouchOutside(false);
                                                        regProgress.setCancelable(false);

                                                        double progress
                                                                = (100.0
                                                                * taskSnapshot.getBytesTransferred()
                                                                / taskSnapshot.getTotalByteCount());
                                                        regProgress.setMessage(
                                                                "Uploaded "
                                                                        + (int) progress + "%");
                                                    }
                                                });


                                            }

                                        } else {
                                            StorageReference ref = storageReference.child("Images/" + id);
                                            ref.putFile(Uri.fromFile(mPhotoFile)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                                    while (!uriTask.isSuccessful()) ;
                                                    Uri downloadUrl = uriTask.getResult();
                                                    assert downloadUrl != null;

                                                    StudentdataItem student = new StudentdataItem(downloadUrl.toString(), id, date, namesPresent, namesAbsent, numPresent, numAbsent, String.valueOf(presentCount), String.valueOf(absentCount), email, facultyRandomID, randomId);
                                                    databaseReference.child(facultyId).child("Attendance").child(randomId).setValue(student);
                                                    Toast.makeText(TakeAttendanceActivity.this, "Attendance was Sucessfully Taken", Toast.LENGTH_SHORT).show();

                                                    listPresent.clear();
                                                    listAbsent.clear();
                                                    numPresent="";
                                                    namesPresent="";
                                                    namesAbsent="";
                                                    numAbsent="";
                                                    facultyAttendanceAdapter.notifyDataSetChanged();
                                                    regProgress.dismiss();


                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    regProgress.dismiss();
                                                    listPresent.clear();
                                                    listAbsent.clear();
                                                    numPresent="";
                                                    namesPresent="";
                                                    namesAbsent="";
                                                    numAbsent="";
                                                    facultyAttendanceAdapter.notifyDataSetChanged();
                                                    Toast.makeText(TakeAttendanceActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                                    regProgress.show();
                                                    regProgress.setCanceledOnTouchOutside(false);
                                                    regProgress.setCancelable(false);

                                                    double progress
                                                            = (100.0
                                                            * taskSnapshot.getBytesTransferred()
                                                            / taskSnapshot.getTotalByteCount());
                                                    regProgress.setMessage(
                                                            "Uploaded "
                                                                    + (int) progress + "%");
                                                }
                                            });


                                        }


                                    }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        listPresent.clear();
                                        listAbsent.clear();
                                        numPresent="";
                                        namesPresent="";
                                        namesAbsent="";
                                        numAbsent="";
                                        facultyAttendanceAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
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


    /**
     * Alert dialog for capture or select from galley
     */
    private void selectImage() {
        final CharSequence[] items = {"Take Photo",
                "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TakeAttendanceActivity.this);
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {
                requestStoragePermission(true);
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * Capture image from camera
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(TakeAttendanceActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);

                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {
                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(TakeAttendanceActivity.this).load(mPhotoFile).apply(new RequestOptions().centerCrop().circleCrop().placeholder(R.drawable.ic_add_a_photo_black_24dp)).into(imgProfile);
            }
        }
    }

    /**
     * Requesting multiple permissions (storage and camera) at once
     * This uses multiple permission model from dexter
     * On permanent denial opens settings dialog
     */
    private void requestStoragePermission(boolean isCamera) {
        Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            if (isCamera) {
                                dispatchTakePictureIntent();
                            }
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(error -> Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }


    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    /**
     * Create file with current timestamp name
     *
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }

    /**
     * Get real file path from URI
     *
     * @param contentUri
     * @return
     */
    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
