package com.example.attendance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.attendance.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Objects;

public class TeacherGraphActivity extends AppCompatActivity {

    BarChart barChart;


    ArrayList<BarEntry> entries = new ArrayList<>();
    String presentCount,absentCount;
    ImageView imgProfile;
    String image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_graph);

        Objects.requireNonNull(getSupportActionBar()).setTitle("List of Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        barChart = (BarChart)findViewById(R.id.bargraph);
        imgProfile=(ImageView) findViewById(R.id.imgProfile);
        Intent intent=getIntent();
        presentCount=intent.getStringExtra("presentCount");
        absentCount=intent.getStringExtra("absentCount");
        image=intent.getStringExtra("image");


        Glide.with(TeacherGraphActivity.this).load(image).into(imgProfile);


        entries.clear();
        entries.add(new BarEntry(0,Float.parseFloat(presentCount)));
        entries.add(new BarEntry(1,Float.parseFloat(absentCount)));


        BarDataSet barDataSet = new BarDataSet(entries, "Student Data");
        barDataSet.setBarBorderWidth(1f);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData = new BarData(barDataSet);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        String[] deptNamesList = new String[]{"Present Count","Absent Count"};
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(deptNamesList);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.animateXY(3000, 3000);
        barChart.invalidate();


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}
