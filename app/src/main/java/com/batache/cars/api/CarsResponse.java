package com.batache.cars.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarsResponse {

  @SerializedName("records")
  @Expose
  public List<Car> cars = null;

  public class Car {

    @SerializedName("id")
    @Expose
    public String id = null;

    @SerializedName("car_number")
    @Expose
    public String carNumber = null;

    @SerializedName("car_letter")
    @Expose
    public String carLetter = null;

    @SerializedName("car_category")
    @Expose
    public String carCategory = null;

    @SerializedName("owner_first_name")
    @Expose
    public String ownerFirstName = null;

    @SerializedName("owner_last_name")
    @Expose
    public String ownerLastName = null;

    @SerializedName("owner_mother_name")
    @Expose
    public String ownerMotherName = null;

    @SerializedName("owner_phone_number")
    @Expose
    public String ownerPhoneNumber = null;

    @SerializedName("owner_address")
    @Expose
    public String ownerAddress = null;

    @SerializedName("owner_registry_number")
    @Expose
    public String ownerRegistryNumber = null;

    @SerializedName("owner_birthday")
    @Expose
    public String ownerBirthday = null;

    @SerializedName("owner_birthplace")
    @Expose
    public String ownerBirthplace = null;

    @SerializedName("car_brand")
    @Expose
    public String carBrand = null;

    @SerializedName("car_type")
    @Expose
    public String carType = null;

    @SerializedName("car_color")
    @Expose
    public String carColor = null;

    @SerializedName("car_production_year")
    @Expose
    public String carProductionYear = null;

    @SerializedName("car_put_into_circulation_year")
    @Expose
    public String carPutIntoCirculationYear = null;

    @SerializedName("car_acquisition_date")
    @Expose
    public String carAcquisitionDate = null;

    @SerializedName("car_out_of_order")
    @Expose
    public String carOutOfOrder = null;

    @SerializedName("car_chassis_number")
    @Expose
    public String carChassisNumber = null;

    @SerializedName("car_motor_number")
    @Expose
    public String carMotorNumber = null;

  }

}
