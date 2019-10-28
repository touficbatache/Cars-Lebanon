package com.batache.cars.Utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.core.app.NotificationManagerCompat;

import com.batache.cars.R;
import com.google.android.material.shape.MaterialShapeDrawable;

public class GeneralUtils {
  public static String getDeviceUId() {
    return "cars_app";
  }

  public static boolean isNetworkAvailable(Context context) {
    NetworkInfo networkInfo = getNetworkInfo(context);
    return networkInfo != null && (networkInfo.getType() == 1 || networkInfo.getType() == 0);
  }

  private static NetworkInfo getNetworkInfo(Context context) {
    return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
  }

  public static MaterialShapeDrawable getStroke(Context context, int cornerRadius, int strokeWidth) {
    MaterialShapeDrawable stroke = new MaterialShapeDrawable();
    stroke.setTintList(ColorStateList.valueOf(Color.TRANSPARENT));
    stroke.setCornerRadius(convertDpToPixel(context, cornerRadius));
    stroke.setStroke(convertDpToPixel(context, strokeWidth), ColorStateList.valueOf(context.getResources().getColor(R.color.fieldStrokeColor)));
    return stroke;
  }

  private static float convertDpToPixel(Context context, float dp) {
    return dp * context.getResources().getDisplayMetrics().density;
  }
}
