package com.example.attendance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.attendance.R;
import com.example.attendance.model.AttendanceModel;

import java.util.ArrayList;

public class ItemSelectedAdapter extends BaseAdapter {

    private Context activity;
    private ArrayList<AttendanceModel> data;
    private static LayoutInflater inflater = null;
    private View vi;
    private ViewHolder viewHolder;

    public ItemSelectedAdapter(Context context, ArrayList<AttendanceModel> items) {
        this.activity = context;
        this.data = items;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        vi = view;
        //Populate the Listview
        final int pos = position;
        AttendanceModel items = data.get(pos);
        if (view == null) {
            vi = inflater.inflate(R.layout.faculty_item_attendance, null);
            viewHolder = new ViewHolder();
            viewHolder.checkBox = (CheckBox) vi.findViewById(R.id.checkPresent);
            viewHolder.name = (TextView) vi.findViewById(R.id.txtName);
            viewHolder.number = (TextView) vi.findViewById(R.id.txtRollNo);
            vi.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();
        viewHolder.name.setText("Name : "+items.getStudentName());
        viewHolder.number.setText("Number : "+items.getStudentRoll());
        if (items.isCheckbox()) {
            viewHolder.checkBox.setChecked(true);
        } else {
            viewHolder.checkBox.setChecked(false);
        }
        return vi;
    }

    public ArrayList<AttendanceModel> getAllData() {
        return data;
    }

    public void setCheckBox(int position) {
        //Update status of checkbox
        AttendanceModel items = data.get(position);
        items.setCheckbox(!items.isCheckbox());
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView name;
        TextView number;
        CheckBox checkBox;
    }
}
