package com.insthub.ecmobile;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015/2/27.
 */
public class Utils {

    public static void ChangeActionBarHomeUpDrawable(Activity activity, int rid) {
        Drawable homeUp = activity.getResources().getDrawable(rid);
        final View home = activity.findViewById(android.R.id.home);
        if (home == null) {
            // Action bar doesn't have a known configuration, an OEM messed with
            // things.
            return;
        }

        final ViewGroup parent = (ViewGroup) home.getParent();
        final int childCount = parent.getChildCount();
        if (childCount != 2) {
            // No idea which one will be the right one, an OEM messed with
            // things.
            return;
        }

        final View first = parent.getChildAt(0);
        final View second = parent.getChildAt(1);
        final View up = first.getId() == android.R.id.home ? second : first;

        if (up instanceof ImageView) {
            // Jackpot! (Probably...)
            ImageView upIndicatorView = (ImageView) up;
            upIndicatorView.setImageDrawable(homeUp);
        }
    }
}
