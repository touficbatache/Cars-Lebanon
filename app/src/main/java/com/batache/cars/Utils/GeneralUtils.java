package com.batache.cars.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import androidx.core.app.NotificationManagerCompat;

import com.batache.cars.R;
import com.google.android.material.shape.MaterialShapeDrawable;

import java.util.Random;

public class GeneralUtils {
  private static final String ACCOUNT_NUMBER = "account_number";
  private static final int ACCOUNT_NUMBER_MAX_LENGTH = 10;
  private static final String ACCOUNT_USED_COUNT = "account_used_count";

  public static String getDeviceUId(Context context) {
    return GeneralUtils.getAccount(context);
  }

  public static boolean isNetworkAvailable(Context context) {
    NetworkInfo networkInfo = getNetworkInfo(context);
    return networkInfo != null && (networkInfo.getType() == 1 || networkInfo.getType() == 0);
  }

  private static NetworkInfo getNetworkInfo(Context context) {
    return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
  }

  public static String generateAccount(Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = preferences.edit();
    String random = random();
    editor.putString(ACCOUNT_NUMBER, random);
    editor.apply();
    return random;
  }

  public static String random() {
    String DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    Random RANDOM = new Random();

    StringBuilder sb = new StringBuilder(ACCOUNT_NUMBER_MAX_LENGTH);

    for (int i = 0; i < ACCOUNT_NUMBER_MAX_LENGTH; i++) {
      sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
    }

    return sb.toString();
  }

  public static String getAccount(Context context) {
    String account = PreferenceManager.getDefaultSharedPreferences(context).getString(ACCOUNT_NUMBER, "generate");

    if(account.equals("generate")) {
      return generateAccount(context);
    }

    return account;
  }

  public static int incrementUsedCount(Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = preferences.edit();
    int count = getUsedCount(context) + 1;
    editor.putInt(ACCOUNT_USED_COUNT, count);
    editor.apply();
    return count;
  }

  public static int getUsedCount(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context).getInt(ACCOUNT_USED_COUNT, 5);
  }

  public static void resetUsedCount(Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putInt(ACCOUNT_USED_COUNT, 0);
    editor.apply();
  }
}
