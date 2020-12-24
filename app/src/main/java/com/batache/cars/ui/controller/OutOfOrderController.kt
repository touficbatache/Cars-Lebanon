package com.batache.cars.ui.controller

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.batache.cars.api.CarsResponse.Car
import com.batache.cars.model.adapter.table.LeftTableItemModel_
import com.batache.cars.model.adapter.table.RightTableItemModel_
import com.batache.cars.model.adapter.table.TopTableBorderItemModel_
import java.util.*

class OutOfOrderController : EpoxyController() {

  var data: MutableList<EpoxyModelWithHolder<*>> = ArrayList()

  fun addTable(car: Car) {
    data.add(TopTableBorderItemModel_())

    addTableItem("Car number", String.format("%s %s", car.carLetter, car.carNumber))
    addTableItem("Car", String.format("%s %s %s", car.carBrand, car.carType, car.carColor))
    addTableItem("Category", car.carCategory)
    addTableItem("Name", String.format("%s %s", car.ownerFirstName, car.ownerLastName))
    addTableItem("Phone number", car.ownerPhoneNumber)
    addTableItem("Address", car.ownerAddress)
    addTableItem("Registry number", car.ownerRegistryNumber)
    addTableItem("Birthday", String.format("%s in %s", car.ownerBirthday, car.ownerBirthplace))
    addTableItem("Production year", car.carProductionYear)
    addTableItem("Put into circulation in", String.format("%s on %s", car.carPutIntoCirculationYear, car.carAcquisitionDate))

    requestModelBuild()
  }

  private fun addTableItem(label: String, value: String?) {
    data.add(LeftTableItemModel_()
        .title(label)
    )

    data.add(RightTableItemModel_()
        .title(value)
    )
  }

  override fun buildModels() {
    var i: Long = 0

    for (model in data) {
      model.id(i++)
      model.addTo(this)
    }
  }
}