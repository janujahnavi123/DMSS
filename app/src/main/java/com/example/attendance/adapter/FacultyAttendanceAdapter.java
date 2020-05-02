package com.example.attendance.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.attendance.R;
import com.example.attendance.model.StudentdataItem;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class FacultyAttendanceAdapter extends BaseAdapter {

    //private List<StudentItemSubjects> studentItemList;
    private Context context;
    private RadioButton radioButton;
    private DatabaseReference myRef;
    private String status, id, date, name, number;

    private ArrayList<StudentdataItem> studentItemList;

    public FacultyAttendanceAdapter(Context context, ArrayList<StudentdataItem> studentItemList) {
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

            convertView = LayoutInflater.from(context).inflate(R.layout.faculty_item_attendance1, parent, false);
        }


        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        TextView txtNameAbsent = (TextView) convertView.findViewById(R.id.txtNameAbsent);
        TextView txtRoll = (TextView) convertView.findViewById(R.id.txtRollNo);
        TextView txtRollAbsent = (TextView) convertView.findViewById(R.id.txtRollnoAbsent);
        TextView txtPresentCount = (TextView) convertView.findViewById(R.id.presentCount);
        TextView txtAbsentCount = (TextView) convertView.findViewById(R.id.absentCount);

        String listPresentNames = studentItemList.get(position).getNamesListPresent();
        listPresentNames = listPresentNames.replace(",", "\n");

        String listAbsentNames = studentItemList.get(position).getNamesListAbsent();
        listAbsentNames = listAbsentNames.replace(",", "\n");


        String listPresentNumbers = studentItemList.get(position).getNumListPresent();
        listPresentNumbers = listPresentNumbers.replace(",", "\n");

        String listAbsentNumbers = studentItemList.get(position).getNumListAbsent();
        listAbsentNumbers = listAbsentNumbers.replace(",", "\n");

        txtName.setText(listPresentNames);
        txtRoll.setText(listPresentNumbers);

        txtNameAbsent.setText(listAbsentNames);
        txtRollAbsent.setText(listAbsentNumbers);

        txtPresentCount.setText("Present Count : "+studentItemList.get(position).getNamesListPresentCount());
        txtAbsentCount.setText("Absent Count : "+studentItemList.get(position).getNamesListAbsentCount());

        return convertView;
    }


}

