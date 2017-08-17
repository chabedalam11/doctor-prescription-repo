package com.prescription.doctorprescription.activity.profile;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.astuetz.PagerSlidingTabStrip;
import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.adapter.tab.CreatePrescriptionAdapter;

public class PrescriptionSetupActivity extends AppCompatActivity {

    ViewPager createPrescriptionPager;
    PagerSlidingTabStrip tabForCreatePrescriptionAdapter;
    CreatePrescriptionAdapter createPrescriptionAdapter;
    int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_setup);
        intialize();
    }


    private  void intialize(){
        tabForCreatePrescriptionAdapter = (PagerSlidingTabStrip) findViewById(R.id.tabForCreatePrescriptionAdapter);
        createPrescriptionPager = (ViewPager) findViewById(R.id.createPrescriptionPager);
        createPrescriptionAdapter = new CreatePrescriptionAdapter(getSupportFragmentManager());
        createPrescriptionPager.setAdapter(createPrescriptionAdapter);
        tabForCreatePrescriptionAdapter.setViewPager(createPrescriptionPager);
        tabForCreatePrescriptionAdapter.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
