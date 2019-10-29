package com.batache.cars;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;

import com.batache.cars.Utils.GeneralUtils;
import com.batache.cars.asynctask.HttpServiceHandler;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

  private Context context;

  private String carLtr = "--";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    context = MainActivity.this;

//    new UserLoginRequest(context).execute();

    initViews();
  }

  private void initViews() {
    AppCompatTextView carLetterTv = findViewById(R.id.tv_car_letter);
    carLetterTv.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        PopupMenu popup = new PopupMenu(context, view);
        popup.getMenuInflater().inflate(R.menu.menu_car_letters, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
          @Override
          public boolean onMenuItemClick(MenuItem item) {
            carLtr = item.getTitle().toString();
            ((TextView) findViewById(R.id.tv_car_letter)).setText(carLtr);
            return true;
          }
        });
      }
    });

    MaterialButton submitBtn = findViewById(R.id.btn_submit);
    submitBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AppCompatEditText carNbEt = findViewById(R.id.et_car_number);
        tryDoNextCall(context, carNbEt.getText().toString(), getArabicCarLetter(carLtr));
      }
    });
  }

  private String getArabicCarLetter(String arabicCarLetter) {
    switch (arabicCarLetter) {
      case "#":
        return "#";
      case "*":
        return "*";
      case "--":
        return "--";
      case "B":
        return "ب";
      case "G":
        return "ج";
      case "D":
        return "د";
      case "R":
        return "ر";
      case "Z":
        return "ز";
      case "S":
        return "ص";
      case "T":
        return "ط";
      case "A":
        return "ع"; //TODO: ع is not A
      case "K":
        return "ق"; //TODO: Not sure if ق is K
      case "M":
        return "م";
      case "N":
        return "ن";
      case "O":
        return "و";
    }
    return "#";
  }

  private static class UserLoginRequest extends AsyncTask<Void, Void, String> {
    Context context;
    RequestCallback callback;
    String response = "";

    UserLoginRequest(Context context, RequestCallback requestCallback) {
      this.context = context;
      callback = requestCallback;
    }

    public String doInBackground(Void... voidArr) {
      if (!GeneralUtils.isNetworkAvailable(context)) {
        callback.onFailure();
        return null;
      }
      response = new HttpServiceHandler().makeServiceCall("http://lebanonride.com/and1.0/apns.php?task=register&deviceuid=" + GeneralUtils.getDeviceUId(context));
      return response;
    }

    public void onPostExecute(String str) {
      super.onPostExecute(str);
      callback.onSuccess();
    }
  }

  private void tryDoNextCall(final Context context, final String carNb, final String carLtr) {
    if (GeneralUtils.getUsedCount(context) >= 5) {
      GeneralUtils.resetUsedCount(context);
      GeneralUtils.generateAccount(context);
      new UserLoginRequest(context, new RequestCallback() {
        @Override
        public void onSuccess() {
          new CarInfoRequest(context, carNb, carLtr).execute();
        }

        @Override
        public void onFailure() {
          Toast.makeText(context, "Please make sure you're connected to the internet", Toast.LENGTH_SHORT).show();
        }
      }).execute();
    } else {
      new CarInfoRequest(context, carNb, carLtr).execute();
    }
//    new UserRewardRequest(context, new RequestCallback() {
//      @Override
//      public void onSuccess() {
//        new CarInfoRequest(context, carNb, carLtr).execute();
//      }
//
//      @Override
//      public void onFailure() {
//        Toast.makeText(context, "Please make sure you're connected to the internet", Toast.LENGTH_SHORT).show();
//      }
//    }).execute();
  }

//  private class UserRewardRequest extends AsyncTask<Void, Void, String> {
//    Context context;
//    RequestCallback callback;
//    String response = "";
//
//    UserRewardRequest(Context context, RequestCallback requestCallback) {
//      this.context = context;
//      callback = requestCallback;
//    }
//
//    public String doInBackground(Void... voidArr) {
//      if(!GeneralUtils.isNetworkAvailable(context)) {
//        callback.onFailure();
//        return null;
//      }
//
//      HashMap<String, String> hashMap = new HashMap();
//      long currentTimeMillis = System.currentTimeMillis() / 1000;
//      currentTimeMillis = ((currentTimeMillis * 2) - 1000) / 4;
//      hashMap.put("X", String.valueOf(currentTimeMillis));
//      hashMap.put("Y", GeneralUtils.getDeviceUId(context));
//      response = new HttpServiceHandler().makeServiceCallPost(context, "http://lebanonride.com/and1.0/reward.php", hashMap, true);
//      return response;
//    }
//
//    public void onPostExecute(String str) {
//      super.onPostExecute(str);
//      callback.onSuccess();
//    }
//  }

  private class CarInfoRequest extends AsyncTask<Void, Void, String> {
    Context context;
    String carNb, carLtr;

    CarInfoRequest(Context context, String carNb, String carLtr) {
      this.context = context;
      this.carNb = carNb;
      this.carLtr = carLtr;
    }

    public String doInBackground(Void... voidArr) {
      HashMap<String, String> hashMap = new HashMap<>();
      hashMap.put("deviceuid", GeneralUtils.getDeviceUId(context));
      hashMap.put("number", carNb);
      hashMap.put("letter", carLtr);
      hashMap.put("submitBtn", "");

      return new HttpServiceHandler().doSubmit("http://lebanonride.com/and1.0/?deviceuid=" + GeneralUtils.getDeviceUId(context), hashMap);
    }

    public void onPostExecute(final String str) {
      super.onPostExecute(str);

      GeneralUtils.incrementUsedCount(context);

      if (str.split("<div class=\"col-md-6 col-md-offset-3\">")[1].contains("alert-danger")) {
        new MaterialAlertDialogBuilder(context)
            .setMessage("No results")
            .setCancelable(false)
            .setNeutralButton("Ok", null)
            .show();
        return;
      }

      WebView webView = new WebView(context);
      webView.loadData(str.split("<div class=\"col-md-6 col-md-offset-3\">")[1].split("<hr>")[1], "text/html", "utf-8");
      new MaterialAlertDialogBuilder(context)
          .setView(webView)
          .setCancelable(false)
          .setNeutralButton("Ok", null)
          .show();
    }
  }

  private interface RequestCallback {
    void onSuccess();

    void onFailure();
  }
}
