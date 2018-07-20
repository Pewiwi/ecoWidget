package com.twinforce.trashreminder;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by TF4 on 22/12/2016.
 */

public class LogString {
    public static String log = "";

    public static void addLog(String line){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        log += dateFormat.format(date) +" || "+ line + "\n";
    }
}
