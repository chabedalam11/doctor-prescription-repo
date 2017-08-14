package com.prescription.doctorprescription.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.webService.PrescriptionWebservice;
import com.prescription.doctorprescription.webService.interfaces.PrescriptionApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by medisys on 07-Aug-17.
 */

public class PrescriptionUtils {

    final static String TAG = "PrescriptionUtils";

    //loader dialog
    public static Dialog dialog;

    public static PrescriptionApi webserviceInitialize() {
        PrescriptionApi prescriptionApi;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PrescriptionWebservice.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        prescriptionApi = retrofit.create(PrescriptionApi.class);
        return prescriptionApi;
    }

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static void backToPrevious(Context context, Activity activity){
        Intent previousActivityIntent = new Intent(context, activity.getClass());
        previousActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(previousActivityIntent);
        ((Activity) context).finish();
    }

    public static void showProgressDialog(Context context) {
        Log.d(TAG, "Loader start .........");
        dialog = new Dialog(context, android.R.style.Theme_Dialog);
        dialog.setCancelable(false);
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundResource(R.drawable.custom_progress_dialog_animation);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(imageView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show();
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }

    public static void hideProgressDialog(){
        Log.d(TAG, "Loader stop .........");
        dialog.dismiss();
    }
}
