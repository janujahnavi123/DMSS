package com.example.attendance.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.attendance.R;
import com.example.attendance.model.FacultyItem1;

import java.util.List;

public class SubjectListAdapter extends BaseAdapter {

    private List<FacultyItem1> facultyItems;
    private Context context;

    public SubjectListAdapter(Context context, List<FacultyItem1> facultyItems) {
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

            convertView = LayoutInflater.from(context).inflate(R.layout.subject_item, parent, false);
        }

        TextView txtBranch =(TextView)convertView.findViewById(R.id.txtBranch);
        TextView txtYear =(TextView)convertView.findViewById(R.id.txtYear);
        TextView txtSemester=(TextView)convertView.findViewById(R.id.txtSemester);
        TextView txtSubject=(TextView)convertView.findViewById(R.id.txtSubject);

        txtBranch.setText("Branch : "+facultyItems.get(position).getFacultyDept());
        txtYear.setText("Year : "+facultyItems.get(position).getFacultyYear());
        txtSemester.setText("Semester : "+facultyItems.get(position).getFacultySem());
        txtSubject.setText("Subject : "+facultyItems.get(position).getFacultySubject());




        return convertView;
    }


}

