package com.batache.cars.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CarsAPI {

  private static CarsAPI sInstance;
  public static final String TAG = "CarsAPI";

  private Retrofit carsApiRetrofit;
  private CarsApiInterface carsApiInterface;

  public static CarsAPI getInstance() {
    if (sInstance == null) {
      sInstance = new CarsAPI();
    }
    return sInstance;
  }

  private CarsAPI() {
    carsApiRetrofit = CarsApiClient.getRetrofitClient();
    carsApiInterface = carsApiRetrofit.create(CarsApiInterface.class);
  }

  public void getCarsByCarDetails(String carNumber, String carLetter, final OnCarsFetchedListener onCarsFetchedListener) {
    Call<CarsResponse> call = carsApiInterface.getCarsByCarDetails(carNumber, carLetter);
    call.enqueue(new Callback<CarsResponse>() {
      @Override
      public void onResponse(@NonNull Call<CarsResponse> call, @NonNull Response<CarsResponse> response) {
        if (response.body() != null) {
          List<CarsResponse.Car> cars = response.body().cars;
          onCarsFetchedListener.onSuccess(cars);
        } else {
          onCarsFetchedListener.onFailure(new Throwable("No cars found"));
        }
      }

      @Override
      public void onFailure(@NonNull Call<CarsResponse> call, @NonNull Throwable t) {
        onCarsFetchedListener.onFailure(t);
      }
    });
  }

  public void getCarsByPersonalDetails(String firstName, String lastName, boolean allowInaccurateResults, final OnCarsFetchedListener onCarsFetchedListener) {
    Call<CarsResponse> call = carsApiInterface.getCarsByPersonalDetails(firstName, lastName, allowInaccurateResults);
    call.enqueue(new Callback<CarsResponse>() {
      @Override
      public void onResponse(@NonNull Call<CarsResponse> call, @NonNull Response<CarsResponse> response) {
        if (response.body() != null) {
          List<CarsResponse.Car> cars = response.body().cars;
          onCarsFetchedListener.onSuccess(cars);
        } else {
          onCarsFetchedListener.onFailure(new Throwable("No cars found"));
        }
      }

      @Override
      public void onFailure(@NonNull Call<CarsResponse> call, @NonNull Throwable t) {
        onCarsFetchedListener.onFailure(t);
      }
    });
  }

  public void getCarsByPhoneNumber(String phoneNumber, final OnCarsFetchedListener onCarsFetchedListener) {
    Call<CarsResponse> call = carsApiInterface.getCarsByPhoneNumber(phoneNumber);
    call.enqueue(new Callback<CarsResponse>() {
      @Override
      public void onResponse(@NonNull Call<CarsResponse> call, @NonNull Response<CarsResponse> response) {
        if (response.body() != null) {
          List<CarsResponse.Car> cars = response.body().cars;
          onCarsFetchedListener.onSuccess(cars);
        } else {
          onCarsFetchedListener.onFailure(new Throwable("No cars found"));
        }
      }

      @Override
      public void onFailure(@NonNull Call<CarsResponse> call, @NonNull Throwable t) {
        onCarsFetchedListener.onFailure(t);
      }
    });
  }

  public interface OnCarsFetchedListener {
    void onSuccess(List<CarsResponse.Car> cars);
    void onFailure(@Nullable Throwable t);
  }
}
