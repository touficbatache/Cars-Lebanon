package com.batache.cars.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CarsApiInterface {

  @GET("car/read.php")
  Call<CarsResponse> getCarsByCarDetails(@Query("carnumber") String carNumber, @Query("carletter") String carLetter);

  @GET("car/read.php")
  Call<CarsResponse> getCarsByPersonalDetails(@Query("firstname") String firstName, @Query("lastname") String lastName, @Query("allowinaccurateresults") boolean allowInaccurateResults);

  @GET("car/read.php")
  Call<CarsResponse> getCarsByPhoneNumber(@Query("phonenumber") String phoneNumber);

}
