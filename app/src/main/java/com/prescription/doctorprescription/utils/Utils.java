package com.prescription.doctorprescription.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by medisys on 8/3/2017.
 */

public class Utils {
    public final static String APP_NAME = "Rufaida Medical Systems";

    public static Dialog dialog;

    public static Class returnClass;

    public static Activity returnActivity;

    public static boolean loginFormValidation(Context context, String userName,
                                              String password, String uNameReq, String passReq) {
        if (userName.equals("")) {
            Toast.makeText(context, uNameReq, Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.equals("")) {
            Toast.makeText(context, passReq, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public static final String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    private static Map<String, String> loadFrequency(Object k, Object v) {
        Map<String, String> hMap = new HashMap<String, String>();
        try {
            hMap.put("1", "One Time A Day");
            hMap.put("2", "Twice A Day");
            hMap.put("3", "Three Times A Day");
            hMap.put("4", "Four Times A Day");
            hMap.put("5", "Five Times A Day");
            hMap.put("6", "Once in Two Days");
            hMap.put("7", "Once Weekly");
            hMap.put("13", "Stat");
            hMap.put("14", "Six Times A Day");
            hMap.put("15", "Eight Times A Day");
            hMap.put("16", "Every Hour");
            hMap.put("17", "PRN");
            hMap.put("18", "Twice Weekly");
            hMap.put("19", "Thrice Weekly");
            hMap.put("20", "Four Times Weekly");
            hMap.put("21", "Five Times Weekly");
            hMap.put("22", "Every Two Hour");
            hMap.put("23", "Once in One Month");
            hMap.put("24", "Once in Three Days");
            hMap.put("25", "Every 16 Hour");
            hMap.put("26", "Every 36 Hour");
            hMap.put("27", "Twice A Day for 5 Days");
            hMap.put("28", "Thrice A Day for 5 Days");
            hMap.put("29", "Once A Day for 5 Days");
            hMap.put("30", "2 puff Every 8 Hour");
            hMap.put("31", "2 puff PRN");
            hMap.put("32", "Every Other Day for Month");
        } catch (Exception e) {

        }
        return hMap;
    }

    public static String getName(String id) {
        Map<String, String> hMapList = loadFrequency("", "");
        Iterator iter = hMapList.entrySet().iterator();
        String name = null;
        while (iter.hasNext()) {
            Map.Entry mEntry = (Map.Entry) iter.next();
            if (mEntry.getKey().equals(id)) {
                name = mEntry.getValue().toString();
            }
            // System.out.println(mEntry.getKey() + " : " + mEntry.getValue());
        }
        return name;
    }

    private static Map<String, String> loadDoseDuration(Object k, Object v) {
        Map<String, String> hMap = new HashMap<String, String>();
        try {
            hMap.put("15", "Fifteen Days");
            hMap.put("30", "One Month");
            hMap.put("60", "Two Months");
            hMap.put("90", "Three Months");
            hMap.put("180", "Six Months");
            hMap.put("3", "Three Days");
            hMap.put("4", "Four Days");
            hMap.put("5", "Five Days");
            hMap.put("6", "Six Days");
            hMap.put("7", "One Week");
            hMap.put("1", "One Day");
            hMap.put("2", "Two Days");
            hMap.put("8", "Eight Days");
            hMap.put("9", "Nine Days");
            hMap.put("10", "Ten Days");
            hMap.put("270", "Nine Months");
            hMap.put("21", "Twenty One Days");
            hMap.put("75", "Two and Half Month");
            hMap.put("120", "Four Months");
            hMap.put("150", "Five Months");
            hMap.put("365", "One Year");
            hMap.put("11", "Two Weeks");
        } catch (Exception e) {

        }
        return hMap;
    }

    public static String getDoseDuration(String id) {
        Map<String, String> hMapList = loadDoseDuration("", "");
        Iterator iter = hMapList.entrySet().iterator();
        String name = null;
        while (iter.hasNext()) {
            Map.Entry mEntry = (Map.Entry) iter.next();
            if (mEntry.getKey().equals(id)) {
                name = mEntry.getValue().toString();
            }
            // System.out.println(mEntry.getKey() + " : " + mEntry.getValue());
        }
        return name;

    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static Date nextDate(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); // minus number would decrement the days
        return cal.getTime();
    }

    public static Date prevDate(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); // minus number would decrement the days
        return cal.getTime();
    }

    public static String twoDigit(int value) {
        if (value < 10) {
            return "0" + value;
        } else {
            // get last two digits
            String str = Integer.toString(value);
            return str.substring(str.length() - 2);
        }
    }

   /* public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        return RufaidaUtils.twoDigit(hour) + ":" + RufaidaUtils.twoDigit(min);
    }

    public static void home(Context context){
        Intent rufaidaMenuActivityIntent = new Intent(context, RufaidaMenuActivity.class);
        rufaidaMenuActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(rufaidaMenuActivityIntent);
        ((Activity) context).finish();
    }*/

    public static void backToPrevious(Context context, Activity activity){
        Intent previousActivityIntent = new Intent(context, activity.getClass());
        previousActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(previousActivityIntent);
        ((Activity) context).finish();
    }

    /*public static void logout(Context context){
		*//*RufaidaPreferenceManager memory = new RufaidaPreferenceManager(context);
		memory.clearPreferences();*//*
        Intent logoutActivityIntent = new Intent(context, LogoutActivity.class);
        logoutActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(logoutActivityIntent);
        ((Activity) context).finish();
    }*/

    public static String getFomattedTime(String time){
        if (time != null && time.length() > 3) {
            return  time.substring(0, 2) + ":" + time.substring(2);
        } else if (time != null && time.length() == 2) {
            return  "0"+time.substring(0, 1) + ":0" + time.substring(1);
        } else {
            return "0"+time.substring(0, 1) + ":" + time.substring(1);
        }
    }


    public static String getFormattedDate(String date){
        return  date.substring(0, 10);
    }

    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    public static String getCurrentDate2() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }


    public static String getMonth(String month) {
        Map<String, String> hMapList = loadMonth("", "");
        Iterator iter = hMapList.entrySet().iterator();
        String mon = null;
        while (iter.hasNext()) {
            Map.Entry mEntry = (Map.Entry) iter.next();
            if (mEntry.getKey().equals(month)) {
                mon = mEntry.getValue().toString();
            }
            // System.out.println(mEntry.getKey() + " : " + mEntry.getValue());
        }
        return mon;

    }


    public static String addDaysWithDate(String date, String days){
        int addingDays = Integer.parseInt(days);
        Date convertedDate=null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            convertedDate=sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        // Now convertedDate to calendar.
        c.setTime(convertedDate);
        // Adding  days
        c.add(Calendar.DATE, addingDays);
        String output = sdf.format(c.getTime());
        return output;

    }



    public static String changeDateFormate(String stringDate){
        String changingDate =  "";
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");//2014-12-31
        try {
            Date d = df.parse(stringDate);
            changingDate = df2.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return changingDate;
    }


    private static Map<String, String> loadMonth(Object k, Object v) {
        Map<String, String> hMap = new HashMap<String, String>();
        try {
            hMap.put("1", "JAN");
            hMap.put("2", "FEB");
            hMap.put("3", "MAR");
            hMap.put("4", "APR");
            hMap.put("5", "MAY");
            hMap.put("6", "JUN");
            hMap.put("7", "JUL");
            hMap.put("8", "AUG");
            hMap.put("9", "SEP");
            hMap.put("10", "OCT");
            hMap.put("11", "NOV");
            hMap.put("12", "DEC");
        } catch (Exception e) {

        }
        return hMap;
    }

   /* public static RufaidaApi webserviceInitialize() {
        RufaidaApi rufaidaApi;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RufaidaWebservice.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        rufaidaApi = retrofit.create(RufaidaApi.class);
        return rufaidaApi;
    }*/


    public static  void showAPInotResponseWarn(Context context){
        Toast.makeText(context, "Database Maintainance!!! Try Again in a few minutes", Toast.LENGTH_SHORT).show();
    }


    /*public static void showProgressDialog(Context context) {
        Log.d("RufaidaUtils", "Loader start .........");
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
    }*/



    public static void hideProgressDialog(){
        Log.d("RufaidaUtils", "Loader stop .........");
        dialog.dismiss();
    }

    public static ArrayList<String> removeDuplicates(ArrayList<String> list) {
        // Store unique items in result.
        ArrayList<String> result = new ArrayList<>();

        // Record encountered Strings in HashSet.
        HashSet<String> set = new HashSet<>();

        // Loop over argument list.
        for (String item : list) {
            // If String is not in set, add it to the list and the set.
            if (!set.contains(item)) {
                result.add(item);
                set.add(item);
            }
        }
        return result;
    }
}
