package com.batache.cars;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.batache.cars.Utils.GeneralUtils;
import com.batache.cars.asynctask.HttpServiceHandler;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

  private Context context;

  private String carLtr = "--";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    context = MainActivity.this;

    new UserLoginRequest(context).execute();

    initViews();
  }

  private void initViews() {
    final TextInputEditText carNbEt = findViewById(R.id.et_car_number);

    FrameLayout carLtrLayout = findViewById(R.id.layout_car_letter);
    carLtrLayout.setBackground(GeneralUtils.getStroke(context, 4, 1));
    carLtrLayout.setOnClickListener(new View.OnClickListener() {
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
        tryDoNextCall(context, carNbEt.getText().toString(), carLtr);
      }
    });
  }

  private static class UserLoginRequest extends AsyncTask<Void, Void, String> {
    Context context;
    String response = "";

    UserLoginRequest(Context context) {
      this.context = context;
    }

    public String doInBackground(Void... voidArr) {
      response = new HttpServiceHandler().makeServiceCall("http://lebanonride.com/and1.0/apns.php?task=register&deviceuid=" + GeneralUtils.getDeviceUId());
      return response;
    }
  }

  private void tryDoNextCall(final Context context, final String carNb, final String carLtr) {
    new UserRewardRequest(context, new RequestCallback() {
      @Override
      public void onSuccess() {
        new CarInfoRequest(context, carNb, carLtr).execute();
      }

      @Override
      public void onFailure() {
        Toast.makeText(context, "Please make sure you're connected to the internet", Toast.LENGTH_SHORT).show();
      }
    }).execute();
  }

  private class UserRewardRequest extends AsyncTask<Void, Void, String> {
    Context context;
    RequestCallback callback;
    String response = "";

    UserRewardRequest(Context context, RequestCallback requestCallback) {
      this.context = context;
      callback = requestCallback;
    }

    public String doInBackground(Void... voidArr) {
      if(!GeneralUtils.isNetworkAvailable(context)) {
        callback.onFailure();
        return null;
      }

      HashMap<String, String> hashMap = new HashMap();
      long currentTimeMillis = System.currentTimeMillis() / 1000;
      currentTimeMillis = ((currentTimeMillis * 2) - 1000) / 4;
      hashMap.put("X", String.valueOf(currentTimeMillis));
      hashMap.put("Y", GeneralUtils.getDeviceUId());
      response = new HttpServiceHandler().makeServiceCallPost(context, "http://lebanonride.com/and1.0/reward.php", hashMap, true);
      return response;
    }

    public void onPostExecute(String str) {
      super.onPostExecute(str);
      callback.onSuccess();
    }
  }

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
      hashMap.put("deviceuid", GeneralUtils.getDeviceUId());
      hashMap.put("number", carNb);
      hashMap.put("letter", carLtr);
      hashMap.put("submitBtn", "");

      return new HttpServiceHandler().doSubmit("http://lebanonride.com/and1.0/", hashMap);
    }

    public void onPostExecute(final String str) {
      super.onPostExecute(str);

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
