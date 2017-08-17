package com.prescription.doctorprescription.activity.barcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.prescription.doctorprescription.R;

public class BarcodeActivity extends AppCompatActivity {
    Button btnBarcode;
    TextView tvBercode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        // Scanner
        tvBercode=(TextView) findViewById(R.id.tvBercode);
        btnBarcode = (Button) findViewById(R.id.btnBarcode);
        btnBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*IntentIntegrator integrator = new IntentIntegrator(BarcodeActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan Code");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.initiateScan();*/

                IntentIntegrator scanIntegrator = new IntentIntegrator(BarcodeActivity.this);
                scanIntegrator.initiateScan();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            /*formatTxt.setText("FORMAT: " + scanFormat);
            contentTxt.setText("CONTENT: " + scanContent);*/
            Toast toast = Toast.makeText(getApplicationContext(),
                    "becode : "+scanFormat+"\t"+scanContent, Toast.LENGTH_SHORT);
            tvBercode.setText(scanContent);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
