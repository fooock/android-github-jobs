package com.fooock.github.jobs.util;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

/**
 * Application utils
 */
public class Utils {

    private Utils() {
    }

    public static int getColor(Context context, @ColorRes int color) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return ContextCompat.getColor(context, color);
        } else {
            return context.getColor(color);
        }
    }
}
