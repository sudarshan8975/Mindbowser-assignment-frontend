package com.example.list.assigmnent.utility;

import android.app.Activity;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    public static void showAlert(Activity activity,String message){
        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
    }
    public static boolean isEmail(String email){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
