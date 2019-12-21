package com.batache.cars.ui.controller;

import android.content.Context;

import com.batache.cars.api.CarsResponse;
import com.batache.cars.app.base.BaseFragment;
import com.batache.cars.model.adapter.divider.DividerModel_;
import com.batache.cars.model.adapter.main.CarDetailsSearchModel_;
import com.batache.cars.model.adapter.main.PersonalDetailsSearchModel_;
import com.batache.cars.model.adapter.main.PhoneNumberSearchModel_;
import com.batache.cars.model.adapter.out_of_order.OutOfOrderItemModel_;
import com.batache.cars.model.adapter.table.LeftTableItemModel_;
import com.batache.cars.model.adapter.table.RightTableItemModel_;
import com.batache.cars.model.adapter.table.TopTableBorderItemModel_;

import java.util.List;

public class CarsController extends BaseController {

  private Context context;
  private BaseFragment.SearchListener searchListener;

  public CarsController(Context context, BaseFragment.SearchListener searchListener) {
    this.context = context;
    this.searchListener = searchListener;
  }

  public void addSearchByCarDetails() {
    data.add(new CarDetailsSearchModel_()
        .context(context)
        .searchListener(searchListener)
    );

    requestModelBuild();
  }

  public void addSearchByPersonalDetails() {
    data.add(new PersonalDetailsSearchModel_()
        .searchListener(searchListener)
    );

    requestModelBuild();
  }

  public void addSearchByPhoneNumber() {
    data.add(new PhoneNumberSearchModel_()
        .searchListener(searchListener)
    );

    requestModelBuild();
  }

  public void addTables(List<CarsResponse.Car> cars) {
    data.add(new DividerModel_()
        .context(context)
        .showResultsText(true)
    );

    if (cars.size() > 0) {
      for (int i = 0; i < cars.size(); i++) {
        if (i > 0) {
          data.add(new DividerModel_()
              .context(context)
              .showResultsText(false)
          );
        }

        addTable(cars.get(i));
      }
    }

    requestModelBuild();
  }

  private void addTable(CarsResponse.Car car) {
    if (!car.carOutOfOrder) {
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
    } else {
      data.add(new OutOfOrderItemModel_()
          .context(context)
          .car(car)
      );
    }
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
