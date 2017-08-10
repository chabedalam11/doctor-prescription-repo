package com.prescription.doctorprescription.profile;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.astuetz.PagerSlidingTabStrip;
import com.prescription.doctorprescription.R;

public class ClinicInformationActivity extends AppCompatActivity {

    ViewPager doctorPager;
    PagerSlidingTabStrip tabs;
    DoctorPagerAdapter doctorPagerAdapter;
    int currentPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_information);

        intialize();
    }

    private  void intialize(){
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        doctorPager = (ViewPager) findViewById(R.id.doctorPager);
        doctorPagerAdapter = new DoctorPagerAdapter(getSupportFragmentManager());
        doctorPager.setAdapter(doctorPagerAdapter);
        tabs.setViewPager(doctorPager);
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
