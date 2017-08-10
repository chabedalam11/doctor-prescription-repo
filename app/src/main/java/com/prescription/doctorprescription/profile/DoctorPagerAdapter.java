package com.prescription.doctorprescription.profile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Rashed on 7/1/2016.
 */
public class DoctorPagerAdapter extends FragmentStatePagerAdapter {
    private static final String[] TITLE = {"Clinic Setup", "Clinic List"};
    private static final int SIZE = 2;

    public DoctorPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ClinicSetupFragment();
                break;
            case 1:
                fragment = new ClinicListFragment();
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
