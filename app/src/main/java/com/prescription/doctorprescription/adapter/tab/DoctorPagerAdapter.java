package com.prescription.doctorprescription.adapter.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.prescription.doctorprescription.fragment.profile.ClinicListFragment;
import com.prescription.doctorprescription.fragment.profile.DesignationSetupFragment;

/**
 * Created by Rashed on 7/1/2016.
 */
public class DoctorPagerAdapter extends FragmentStatePagerAdapter {
    private static final String[] TITLE = {"Clinic Setup", "Designation Setup"};
    private static final int SIZE = 2;

    public DoctorPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ClinicListFragment();
                break;
            case 1:
                fragment = new DesignationSetupFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return SIZE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE[position];
    }


}
