package com.batache.cars.ui.controller

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.batache.cars.R
import com.batache.cars.api.CarsResponse.Car
import com.batache.cars.model.adapter.divider.divider
import com.batache.cars.model.adapter.main.carNumberSearch
import com.batache.cars.model.adapter.main.ownerNameSearch
import com.batache.cars.model.adapter.main.ownerPhoneNumberSearch
import com.batache.cars.model.adapter.out_of_order.outOfOrderItem
import com.batache.cars.model.adapter.table.leftTableItem
import com.batache.cars.model.adapter.table.rightTableItem
import com.batache.cars.model.adapter.table.topTableBorderItem

class CarsController(private val carsListener: CarsListener) : EpoxyController() {

  companion object {
    const val CAR_NUMBER_SEARCH = 0
    const val OWNER_NAME_SEARCH = 1
    const val OWNER_PHONE_NUMBER_SEARCH = 2
  }

  var isEmptyBuild: Boolean = false

  var staticSectionId: Int? = null
  var carsList: List<Car> = ArrayList()

  fun loadStaticSection(id: Int?) {
    staticSectionId = id
  }

  fun loadCars(cars: List<Car>) {
    carsList = cars
  }

  fun clear() {
    carsList = ArrayList()
  }

  private fun getMap(car: Car): Map<String, String> {
    return mutableMapOf<String, String>().apply {
      put("Car number", String.format("%s %s", car.carLetter, car.carNumber))
      put("Car", String.format("%s %s %s", car.carBrand, car.carType, car.carColor))
      put("Category", car.carCategory)
      put("Name", String.format("%s %s", car.ownerFirstName, car.ownerLastName))
      put("Phone number", car.ownerPhoneNumber)
      put("Address", car.ownerAddress)
      put("Registry number", car.ownerRegistryNumber)
      put("Birthday", String.format("%s in %s", car.ownerBirthday, car.ownerBirthplace))
      put("Production year", car.carProductionYear)
      put("Put into circulation in", String.format("%s on %s", car.carPutIntoCirculationYear, car.carAcquisitionDate))
    }
  }

  override fun buildModels() {
    if (isEmptyBuild) {
      return
    }

    when (staticSectionId) {
      CAR_NUMBER_SEARCH -> carNumberSearch {
        id("car_details_search")
        listener(carsListener)
      }
      OWNER_NAME_SEARCH -> ownerNameSearch {
        id("owner_name_search")
        listener(carsListener)
      }
      OWNER_PHONE_NUMBER_SEARCH -> ownerPhoneNumberSearch {
        id("owner_phone_number_search")
        listener(carsListener)
      }
    }

    if (carsList.isNotEmpty()) {
      divider {
        id("results_divider")
        showResultsText(true)
      }

      for (i in carsList.indices) {
        val car = carsList[i]

        if (i > 0) {
          divider {
            id("car_divider_$i")
          }
        }

        if (!car.carOutOfOrder) {
          topTableBorderItem {
            id("top_table_border_$i")
          }

          for ((leftTableData, rightTableData) in getMap(car)) {
            leftTableItem {
              id("left_table_$i")
              title(leftTableData)
            }
            rightTableItem {
              id("right_table_$i")
              title(rightTableData)
            }
          }
        } else {
          outOfOrderItem {
            id("out_of_order_$i")
            car(car)
          }
        }
      }
    }
  }

  interface CarsListener {
    fun onSearchByCarNumber(letter: String?, number: String?)
    fun onSearchByOwnerName(firstName: String?, lastName: String?, allowInaccurate: Boolean)
    fun onSearchByOwnerPhoneNumber(phoneNumber: String?)
  }
}