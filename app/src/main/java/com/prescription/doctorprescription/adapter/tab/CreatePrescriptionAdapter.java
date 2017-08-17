package com.prescription.doctorprescription.adapter.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.prescription.doctorprescription.fragment.create_prescription.AdviceNextVisitFragment;
import com.prescription.doctorprescription.fragment.create_prescription.InvestigationFragment;
import com.prescription.doctorprescription.fragment.create_prescription.PatientInfoFragment;
import com.prescription.doctorprescription.fragment.create_prescription.PrescriptionFragment;

/**
 * Created by medisys on 8/14/2017.
 */

public class CreatePrescriptionAdapter extends FragmentStatePagerAdapter {
    private static final String[] TITLE = {"Information", "Investigation", "Prescription", "Advice & NextVisit"};
    private static final int SIZE = 4;

    public CreatePrescriptionAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
               fragment = new PatientInfoFragment();
                break;
            case 1:
                 fragment = new InvestigationFragment();
                break;
            case 2:
                 fragment = new PrescriptionFragment();
                break;
            case 3:
                 fragment = new AdviceNextVisitFragment();
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
