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

import java.util.List;

public class SubjectListAdapter1 extends BaseAdapter {

    private List<StudentItem> studentItemList;
    private Context context;

    public SubjectListAdapter1(Context context, List<StudentItem> studentItemList) {
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

            convertView = LayoutInflater.from(context).inflate(R.layout.subject_item, parent, false);
        }

        TextView txtBranch =(TextView)convertView.findViewById(R.id.txtBranch);
        TextView txtYear =(TextView)convertView.findViewById(R.id.txtYear);
        TextView txtSemester=(TextView)convertView.findViewById(R.id.txtSemester);
        TextView txtSubject=(TextView)convertView.findViewById(R.id.txtSubject);

        txtBranch.setText("Branch : "+studentItemList.get(position).getStudentDepartment());
        txtYear.setText("Year : "+studentItemList.get(position).getStudentYear());
        txtSemester.setText("Semester : "+studentItemList.get(position).getStudentSemester());
        txtSubject.setText("Subject : "+studentItemList.get(position).getStudentSubject());




        return convertView;
    }


}

