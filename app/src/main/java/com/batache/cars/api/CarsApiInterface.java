package com.batache.cars.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CarsApiInterface {

  @GET("car/read.php")
  Call<CarsResponse> searchByCarNumber(@Query("carnumber") String carNumber, @Query("carletter") String carLetter);

  @GET("car/read.php")
  Call<CarsResponse> searchByOwnerName(@Query("firstname") String firstName, @Query("lastname") String lastName, @Query("allowinaccurateresults") boolean allowInaccurateResults);

  @GET("car/read.php")
  Call<CarsResponse> searchByOwnerPhoneNumber(@Query("phonenumber") String phoneNumber);

}
