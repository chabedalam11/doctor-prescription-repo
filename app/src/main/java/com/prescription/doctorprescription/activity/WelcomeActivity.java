package com.prescription.doctorprescription.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.activity.profile.DoctorProfileActivity;
import com.prescription.doctorprescription.activity.profile.PrescriptionSetupActivity;
import com.prescription.doctorprescription.utils.PrescriptionMemories;
import com.prescription.doctorprescription.utils.PrescriptionUtils;
import com.prescription.doctorprescription.webService.interfaces.PrescriptionApi;

public class WelcomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    Context context;
    PrescriptionMemories memory;
    final String TAG = "WelcomeActivity";
    //init webservice
    PrescriptionApi prescriptionApi = PrescriptionUtils.webserviceInitialize();
    //Ui component
    TextView tvDoctorName, tvDoctorEmail;
    LinearLayout linLayCreatPres;
    LinearLayout linLayReviewPat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initialize();
    }

    private void initialize() {
        context = WelcomeActivity.this;
        memory = new PrescriptionMemories(getApplicationContext());

        //Navigation Drawer init
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Navigation Drawer Header component initialization
        View headerView = navigationView.getHeaderView(0);
        tvDoctorName = (TextView) headerView.findViewById(R.id.tvDoctorName);
        tvDoctorEmail = (TextView) headerView.findViewById(R.id.tvDoctorEmail);
        linLayCreatPres = (LinearLayout) findViewById(R.id.linLayCreatPres);
        linLayCreatPres.setOnClickListener(this);
        linLayReviewPat = (LinearLayout) findViewById(R.id.linLayReviewPat);
        linLayReviewPat.setOnClickListener(this);
        //set value
        tvDoctorName.setText(memory.getPref(memory.KEY_DOC_NAME));
        tvDoctorEmail.setText(memory.getPref(memory.KEY_DOC_EMAIL));
    }


    //set action in navigation drawer items
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {

            Intent intent = new Intent(context, DoctorProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //in pack press if drawer is open close it
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.linLayCreatPres:
                Intent intentPrescriptionSetup = new Intent(context, PrescriptionSetupActivity.class);
                intentPrescriptionSetup.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentPrescriptionSetup);
                break;

            /*case R.id.linLayReviewPat:
                Intent intentPatPrescription = new Intent(context, PatientPrescriptionInfoActivity.class);
                intentPatPrescription.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentPatPrescription);
                break;*/
        }
    }

}
