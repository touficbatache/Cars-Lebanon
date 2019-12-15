package com.batache.cars.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CarsApiClient {

  private static final String BASE_URL = "http://carsleb.esy.es/api/";
  private static Retrofit retrofit;

  public static Retrofit getRetrofitClient() {
    //If condition to ensure we don't create multiple retrofit instances in a single application
    if (retrofit == null) {
      //Defining the Retrofit using Builder
      retrofit = new Retrofit.Builder()
          .baseUrl(BASE_URL) //This is the only mandatory call on Builder object.
          .addConverterFactory(GsonConverterFactory.create()) // Convertor library used to convert response into POJO
          .build();
    }
    return retrofit;
  }

}
