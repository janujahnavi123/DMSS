package com.example.attendance.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.attendance.R;
import com.example.attendance.model.FacultyItem;

import java.util.ArrayList;
import java.util.List;

public class FacultyListAdapter extends BaseAdapter {

    private List<FacultyItem> facultyItems;
    private Context context;

    public FacultyListAdapter(Context context, List<FacultyItem> facultyItems) {
        this.facultyItems = facultyItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return facultyItems.size();
    }

    @Override
    public Object getItem(int position) {
        return facultyItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.faculty_item, parent, false);
        }

        TextView txtName =(TextView)convertView.findViewById(R.id.txtName);
        TextView txtRoll =(TextView)convertView.findViewById(R.id.txtRollNo);
        TextView txtStatus=(TextView)convertView.findViewById(R.id.txtStatus);

        txtName.setText("Name : "+facultyItems.get(position).getFacultyName());
        txtRoll.setText("Year : "+facultyItems.get(position).getFacultyYear()+"  Semester : "+facultyItems.get(position).getFacultySem());
        txtStatus.setText("Department : "+facultyItems.get(position).getFacultyDept());



        return convertView;
    }


}

