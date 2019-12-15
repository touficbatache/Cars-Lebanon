package com.batache.cars.model.adapter.base;

import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelWithHolder;

public abstract class FullSizeModelWithHolder<T extends EpoxyHolder> extends EpoxyModelWithHolder<T> {
  @Override
  public int getSpanSize(int totalSpanCount, int position, int itemCount) {
    return 2;
  }
}
