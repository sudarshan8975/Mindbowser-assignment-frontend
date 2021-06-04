package com.example.list.assigmnent.utility;

import android.app.Activity;

public class ProgressBarManager {
    private static LoadProgressBar instance;

    public static LoadProgressBar getInstance(Activity activity) {
        if (instance == null) {
            instance = new LoadProgressBar(activity);
        }
        return instance;
    }

}
