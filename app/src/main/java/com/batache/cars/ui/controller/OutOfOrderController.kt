package com.batache.cars.ui.controller;

import com.batache.cars.api.CarsResponse;
import com.batache.cars.model.adapter.table.LeftTableItemModel_;
import com.batache.cars.model.adapter.table.RightTableItemModel_;
import com.batache.cars.model.adapter.table.TopTableBorderItemModel_;

public class OutOfOrderController extends BaseController {

  public void addTable(CarsResponse.Car car) {
    data.add(new TopTableBorderItemModel_());

    addTableItem("Car number", String.format("%s %s", car.carLetter, car.carNumber));
    addTableItem("Car", String.format("%s %s %s", car.carBrand, car.carType, car.carColor));
    addTableItem("Category", car.carCategory);
    addTableItem("Name", String.format("%s %s", car.ownerFirstName, car.ownerLastName));
    addTableItem("Phone number", car.ownerPhoneNumber);
    addTableItem("Address", car.ownerAddress);
    addTableItem("Registry number", car.ownerRegistryNumber);
    addTableItem("Birthday", String.format("%s in %s", car.ownerBirthday, car.ownerBirthplace));
    addTableItem("Production year", car.carProductionYear);
    addTableItem("Put into circulation in", String.format("%s on %s", car.carPutIntoCirculationYear, car.carAcquisitionDate));

    requestModelBuild();
  }

  private void addTableItem(String label, String value) {
    data.add(new LeftTableItemModel_()
        .title(label)
    );

    data.add(new RightTableItemModel_()
        .title(value)
    );
  }

}
