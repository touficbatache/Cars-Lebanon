package com.batache.cars.ui.controller;

import com.airbnb.epoxy.EpoxyController;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseController extends EpoxyController {

  List<EpoxyModelWithHolder> data = new ArrayList<>();

  public void reset() {
    data = new ArrayList<>();
  }

  public void clear() {
    data = new ArrayList<>();
    requestModelBuild();
  }

  @Override
  protected void buildModels() {
    long i = 0;

    for (EpoxyModel model : data) {
      model.id(i++);
      model.addTo(this);
    }
  }
}