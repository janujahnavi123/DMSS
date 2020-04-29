package com.example.attendance.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.attendance.R;
import com.example.attendance.activity.AddSubjectstoTeacherActivity;
import com.example.attendance.activity.ViewTeacherSubjectActivity;
import com.example.attendance.model.FacultyItem;

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
        TextView txtEmail =(TextView)convertView.findViewById(R.id.txtEmail);
        TextView txtPhone=(TextView)convertView.findViewById(R.id.txtPhone);
        Button btnAdd=(Button) convertView.findViewById(R.id.btnAdd);
        Button btnView=(Button) convertView.findViewById(R.id.btnView);

        txtName.setText("Name : "+facultyItems.get(position).getFacultyName());
        txtEmail.setText("Email : "+facultyItems.get(position).getFacultyEmail());
        txtPhone.setText("Phone : "+facultyItems.get(position).getFacultyPhone());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddSubjectstoTeacherActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("facultyId",facultyItems.get(position).getFacultyId());
                intent.putExtra("facultyEmail",facultyItems.get(position).getFacultyEmail());
                context.startActivity(intent);
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewTeacherSubjectActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("facultyId",facultyItems.get(position).getFacultyId());
                context.startActivity(intent);
            }
        });


        return convertView;
    }


}

