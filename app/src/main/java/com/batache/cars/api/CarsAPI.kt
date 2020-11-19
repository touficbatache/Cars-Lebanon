package com.batache.cars.api

import com.batache.cars.api.CarsResponse.Car
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*

object CarsAPI {

  private val carsApiRetrofit: Retrofit
  private val carsApiInterface: CarsApiInterface

  init {
    carsApiRetrofit = CarsApiClient.getRetrofitClient()
    carsApiInterface = carsApiRetrofit.create(CarsApiInterface::class.java)
  }

  fun searchByCarNumber(carNumber: String?, carLetter: String?, onCarsFetchedListener: OnCarsFetchedListener) {
    val call = carsApiInterface.searchByCarNumber(carNumber, carLetter)
    call.enqueue(object : Callback<CarsResponse?> {
      override fun onResponse(call: Call<CarsResponse?>, response: Response<CarsResponse?>) {
        if (response.body() != null) {
          val cars = getSortedList(response.body()!!.cars)
          onCarsFetchedListener.onSuccess(cars)
        } else {
          onCarsFetchedListener.onFailure(Throwable("No cars found"))
        }
      }

      override fun onFailure(call: Call<CarsResponse?>, t: Throwable) {
        onCarsFetchedListener.onFailure(t)
      }
    })
  }

  fun searchByOwnerName(firstName: String?, lastName: String?, allowInaccurateResults: Boolean, onCarsFetchedListener: OnCarsFetchedListener) {
    val call = carsApiInterface.searchByOwnerName(firstName, lastName, allowInaccurateResults)
    call.enqueue(object : Callback<CarsResponse?> {
      override fun onResponse(call: Call<CarsResponse?>, response: Response<CarsResponse?>) {
        if (response.body() != null) {
          val cars = getSortedList(response.body()!!.cars)
          onCarsFetchedListener.onSuccess(cars)
        } else {
          onCarsFetchedListener.onFailure(Throwable("No cars found"))
        }
      }

      override fun onFailure(call: Call<CarsResponse?>, t: Throwable) {
        onCarsFetchedListener.onFailure(t)
      }
    })
  }

  fun searchByOwnerPhoneNumber(phoneNumber: String?, onCarsFetchedListener: OnCarsFetchedListener) {
    val call = carsApiInterface.searchByOwnerPhoneNumber(phoneNumber)
    call.enqueue(object : Callback<CarsResponse?> {
      override fun onResponse(call: Call<CarsResponse?>, response: Response<CarsResponse?>) {
        if (response.body() != null) {
          val cars = getSortedList(response.body()!!.cars)
          onCarsFetchedListener.onSuccess(cars)
        } else {
          onCarsFetchedListener.onFailure(Throwable("No cars found"))
        }
      }

      override fun onFailure(call: Call<CarsResponse?>, t: Throwable) {
        onCarsFetchedListener.onFailure(t)
      }
    })
  }

  private fun getSortedList(unsortedCars: List<Car>): List<Car> {
    val sortedCars: MutableList<Car> = ArrayList()

    for (car in unsortedCars) {
      if (!car.carOutOfOrder) {
        sortedCars.add(0, car)
      } else if (!car.carNumber.isEmpty() && !car.carLetter.isEmpty()) {
        sortedCars.add(car)
      }
    }

    return sortedCars
  }

  interface OnCarsFetchedListener {
    fun onSuccess(cars: List<Car>?)
    fun onFailure(t: Throwable?)
  }
}