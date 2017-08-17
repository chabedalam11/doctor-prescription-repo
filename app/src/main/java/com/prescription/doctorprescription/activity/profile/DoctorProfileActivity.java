package com.prescription.doctorprescription.activity.profile;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.astuetz.PagerSlidingTabStrip;
import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.adapter.tab.DoctorPagerAdapter;

public class DoctorProfileActivity extends AppCompatActivity {

    ViewPager doctorPager;
    PagerSlidingTabStrip tabs;
    DoctorPagerAdapter doctorPagerAdapter;
    int currentPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        intialize();
    }

    private  void intialize(){
        //get tab from DesignationSetupActivity
        String  tab=getIntent().getStringExtra("tab");

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        doctorPager = (ViewPager) findViewById(R.id.doctorPager);
        doctorPagerAdapter = new DoctorPagerAdapter(getSupportFragmentManager());
        doctorPager.setAdapter(doctorPagerAdapter);
        tabs.setViewPager(doctorPager);

        //show tab 2 because activity call form designationSetupActivity
        if (tab != null && tab.equals("2")){
            doctorPager.setCurrentItem(1);
        }

        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPage = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
