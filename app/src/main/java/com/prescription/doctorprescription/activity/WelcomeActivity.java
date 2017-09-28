package com.prescription.doctorprescription.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.activity.barcode.BarcodeActivity;
import com.prescription.doctorprescription.activity.login.LoginActivity;
import com.prescription.doctorprescription.activity.patient.AddPatientActivity;
import com.prescription.doctorprescription.activity.patient.ReviewPatientActivity;
import com.prescription.doctorprescription.activity.profile.DoctorProfileActivity;
import com.prescription.doctorprescription.utils.PrescriptionMemories;
import com.prescription.doctorprescription.utils.PrescriptionUtils;
import com.prescription.doctorprescription.webService.interfaces.PrescriptionApi;


public class WelcomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    final String TAG = "WelcomeActivity";
    Context context;
    PrescriptionMemories memory;
    boolean hardwareBackControll;
    //init webservice
    PrescriptionApi prescriptionApi = PrescriptionUtils.webserviceInitialize();
    //Ui component
    TextView tvDoctorName, tvDoctorEmail;
    LinearLayout linLayCreatPres;
    LinearLayout linLayReviewPat;
    LinearLayout linLayAddPatient;

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
        //linLayCreatPres = (LinearLayout) findViewById(R.id.linLayCreatPres);
        //linLayCreatPres.setOnClickListener(this);
        linLayReviewPat = (LinearLayout) findViewById(R.id.linLayReviewPat);
        linLayReviewPat.setOnClickListener(this);
        linLayAddPatient = (LinearLayout) findViewById(R.id.linLayAddPatient);
        linLayAddPatient.setOnClickListener(this);
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
            //overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );

        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(context, BarcodeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } /*else if (id == R.id.nav_slideshow) {

        }*/ else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to Logout?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            memory.deletePreferences(memory.KEY_DOC_ID);
                            memory.deletePreferences(memory.KEY_DOC_NAME);
                            memory.deletePreferences(memory.KEY_DOC_PHONE1);
                            memory.deletePreferences(memory.KEY_DOC_PHONE2);
                            memory.deletePreferences(memory.KEY_DOC_EMAIL);

                            //now go to login activity
                            Intent intent = new Intent(context, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).create().show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //in backpress if drawer is open close it
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (hardwareBackControll) {
                finish(); // finish activity
            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                hardwareBackControll = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hardwareBackControll = false;
                    }
                }, 3 * 1000);

            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            /*case R.id.linLayCreatPres:
                Intent intentPrescriptionSetup = new Intent(context, PrescriptionSetupActivity.class);
                intentPrescriptionSetup.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentPrescriptionSetup);
                break;*/

            case R.id.linLayReviewPat:
                Intent intentReviewPatient = new Intent(context, ReviewPatientActivity.class);
                intentReviewPatient.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentReviewPatient);
                break;

            case R.id.linLayAddPatient:
                Intent intentPatPrescription = new Intent(context, AddPatientActivity.class);
                intentPatPrescription.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentPatPrescription);
                break;
        }
    }

}
