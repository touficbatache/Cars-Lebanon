package com.batache.cars.asynctask;

import android.content.Context;
import android.util.Base64;

import com.batache.cars.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpServiceHandler {

  public String makeServiceCall(String url) {
    try {
      HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
      return httpURLConnection.getResponseMessage();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private String getPostDataString(HashMap<String, String> hashMap) throws UnsupportedEncodingException {
    StringBuilder stringBuilder = new StringBuilder();
    Object obj = 1;
    for (Entry entry : hashMap.entrySet()) {
      if (obj != null) {
        obj = null;
      } else {
        stringBuilder.append("&");
      }
      stringBuilder.append(URLEncoder.encode((String) entry.getKey(), "UTF-8"));
      stringBuilder.append("=");
      stringBuilder.append(new String(Base64.encode(((String) entry.getValue()).getBytes(), 0)));
    }
    return stringBuilder.toString();
  }

  public String makeServiceCallPost(Context context, String str, HashMap<String, String> hashMap, boolean z) {
    try {
      String string;
      HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
      if (z) {
        string = context.getResources().getString(R.string.value_method_post);
      } else {
        string = context.getResources().getString(R.string.value_method_get);
      }
      httpURLConnection.setRequestMethod(string);
      httpURLConnection.setDoInput(z);
      httpURLConnection.setDoOutput(z);
      httpURLConnection.setConnectTimeout(context.getResources().getInteger(R.integer.value_connection_timeout));
      OutputStream outputStream = httpURLConnection.getOutputStream();
      BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
      if (hashMap != null) {
        String postDataString = getPostDataString(hashMap);
        bufferedWriter.write(postDataString);
      }
      bufferedWriter.flush();
      bufferedWriter.close();
      outputStream.close();
      httpURLConnection.connect();
      return httpURLConnection.getResponseMessage();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public String doSubmit(String url, Map<String, String> data) {
    try {
      URL siteUrl = new URL(url);
      HttpURLConnection conn = (HttpURLConnection) siteUrl.openConnection();
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      conn.setUseCaches(true);
      conn.setDoOutput(true);
      conn.setDoInput(true);

      DataOutputStream out = new DataOutputStream(conn.getOutputStream());

      Set keys = data.keySet();
      Iterator keyIter = keys.iterator();
      StringBuilder content = new StringBuilder();
      for (int i = 0; keyIter.hasNext(); i++) {
        String key = (String) keyIter.next();
        if (i != 0) {
          content.append("&");
        }
        content.append(key).append("=").append(URLEncoder.encode(data.get(key), "UTF-8"));
      }
      out.writeBytes(content.toString());
      out.flush();
      out.close();

      BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line;
      StringBuilder response = new StringBuilder();
      while ((line = in.readLine()) != null) {
        response.append(line);
      }

      in.close();
      return response.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
