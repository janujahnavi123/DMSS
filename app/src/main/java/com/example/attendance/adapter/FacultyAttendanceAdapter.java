package com.example.attendance.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.attendance.R;
import com.example.attendance.model.StudentItemSubjects;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;



public class FacultyAttendanceAdapter extends BaseAdapter {

    private List<StudentItemSubjects> studentItemList;
    private Context context;
    private RadioButton radioButton;
    private DatabaseReference myRef;
    private String status,id,date,name,number;



    public FacultyAttendanceAdapter(Context context, List<StudentItemSubjects> studentItemList) {
        this.studentItemList = studentItemList;
        this.context = context;

    }



    @Override
    public int getCount() {
        return studentItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.faculty_item_attendance, parent, false);
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Attendance");

        TextView txtName =(TextView)convertView.findViewById(R.id.txtName);
        TextView txtRoll =(TextView)convertView.findViewById(R.id.txtRollNo);
        RadioGroup radioGroup = (RadioGroup) convertView.findViewById(R.id.radioGroup);

        final View finalConvertView = convertView;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup radioGroup, int i) {
               int selectedId = radioGroup.getCheckedRadioButtonId();
               // find the radiobutton by returned id
               radioButton = (RadioButton) finalConvertView.findViewById(selectedId);
               status=radioButton.getText().toString().trim();
               id = myRef.push().getKey();
               date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
               name=studentItemList.get(position).getStudentName();
               number=studentItemList.get(position).getStudentRoll();
               /*AttendanceModel student = new AttendanceModel(id, date,name,number,status);
               myRef.child(id).setValue(student);*/
           }
       });

        txtName.setText("Name : "+studentItemList.get(position).getStudentName());
        txtRoll.setText("Number : "+studentItemList.get(position).getStudentRoll());



        return convertView;
    }


}

