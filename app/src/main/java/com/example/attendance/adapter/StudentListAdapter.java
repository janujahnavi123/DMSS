package com.example.attendance.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.attendance.R;
import com.example.attendance.model.StudentItem;

import java.util.ArrayList;
import java.util.List;

public class StudentListAdapter extends BaseAdapter {

    private List<StudentItem> studentItemList;
    private Context context;


    public StudentListAdapter(Context context, List<StudentItem> studentItemList) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.student_item, parent, false);
        }


        TextView txtName =(TextView)convertView.findViewById(R.id.txtName);
        TextView txtYear=(TextView)convertView.findViewById(R.id.txtYear);
        TextView txtRoll =(TextView)convertView.findViewById(R.id.txtRoll);
        TextView txtDept =(TextView)convertView.findViewById(R.id.txtBranch);

        StudentItem studentItem=studentItemList.get(position);


        txtName.setText("Name : "+studentItem.getStudentName());
        txtYear.setText("Year : "+studentItem.getStudentYear()+" "+"  Semester : "+studentItem.getStudentSemester());
        txtRoll.setText("Number : "+studentItem.getStudentRoll());
        txtDept.setText("Department : "+studentItem.getStudentDepartment());




        return convertView;
    }


}


