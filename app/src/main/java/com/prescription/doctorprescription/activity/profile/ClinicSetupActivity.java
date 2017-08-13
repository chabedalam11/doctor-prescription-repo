package com.prescription.doctorprescription.activity.profile;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ViewSwitcher;

import com.prescription.doctorprescription.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClinicSetupActivity extends AppCompatActivity implements View.OnClickListener{

    final String TAG = "ClinicActivity";
    Context context;
    boolean hardwareBackControll;

    TextView mobileNo;
    TextView address;
    TextView chamberAddress;
    TextView visitingHour;
    TextView visitingDay;
    ViewSwitcher timeSwitcher;
    TimePicker from;
    TimePicker to;

    boolean[] checkItem = {false, false, false, false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_setup);
        setTitle("Clinic Setup");
        initialize();
    }

    private void initialize() {
        context = ClinicSetupActivity.this;

        mobileNo = (TextView) findViewById(R.id.mobileNo);
        chamberAddress = (TextView) findViewById(R.id.chamberAddress);
        chamberAddress.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.visitingHour:
                showVisitingHourPicker();
                break;
            case R.id.next:
                timeSwitcher.showNext();
                break;
            case R.id.previous:
                timeSwitcher.showPrevious();
                break;
            case R.id.addressLayout:
                showAddressPicker();
                break;
            case R.id.visitingDay:
                showVisitDay();
                break;
            case R.id.chamberAddress:
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.doctor_chamber_address_layout, null);
                promptsView.findViewById(R.id.addressLayout).setOnClickListener(this);
                promptsView.findViewById(R.id.visitingHour).setOnClickListener(this);
                promptsView.findViewById(R.id.visitingDay).setOnClickListener(this);
                visitingHour = (TextView) promptsView.findViewById(R.id.vh);
                address = (TextView) promptsView.findViewById(R.id.address);
                visitingDay = (TextView) promptsView.findViewById(R.id.vd);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(promptsView);
                builder.setCancelable(false)
                        .setPositiveButton("SAVE",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        chamberAddress.setText(address.getText().toString()+" || "+visitingHour.getText().toString()+" || "+visitingDay.getText().toString());
                                    }
                                })
                        .setNegativeButton("CANCEL",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = builder.create();

                // show it
                alertDialog.show();
        }
    }

    public void showAddressPicker() {
        final EditText addressBox = new EditText(context);
        addressBox.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addressBox.setHint("Type address");
        if (!address.getText().toString().equalsIgnoreCase("Not set")) {
            addressBox.setText(address.getText());
        }
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setView(addressBox);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = addressBox.getText().toString();
                if (text == null || text.isEmpty()) address.setText("Not set");
                else address.setText(text);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    public void showVisitingHourPicker() {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.visiting_hour_layout, null);
        timeSwitcher = (ViewSwitcher) view.findViewById(R.id.timeSwitcher);
        from = (TimePicker) view.findViewById(R.id.form);
        to = (TimePicker) view.findViewById(R.id.to);
        view.findViewById(R.id.next).setOnClickListener(this);
        view.findViewById(R.id.previous).setOnClickListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Calendar cf = Calendar.getInstance();
                cf.set(Calendar.HOUR_OF_DAY, from.getCurrentHour());
                cf.set(Calendar.MINUTE, from.getCurrentMinute());
                Calendar ct = Calendar.getInstance();
                ct.set(Calendar.HOUR_OF_DAY, to.getCurrentHour());
                ct.set(Calendar.MINUTE, to.getCurrentMinute());
                SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
                visitingHour.setText(format.format(cf.getTime()) + " - " + format.format(ct.getTime()));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }


    public void showVisitDay() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

        builder.setMultiChoiceItems(R.array.weekDay, checkItem, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    checkItem[which] = true;
                }
            }
        });
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String repeatDay = "";
                String[] days = getResources().getStringArray(R.array.weekDay);
                for (int i = 0; i < 7; i++) {
                    if (checkItem[i]) repeatDay += days[i] + ",";
                }
                if (repeatDay.length() == 0) visitingDay.setText("Not set");
                else visitingDay.setText(repeatDay.substring(0, repeatDay.length() - 1));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

}
