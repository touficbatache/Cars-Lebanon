package com.batache.cars.model.adapter.table;

import android.view.View;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.batache.cars.R;
import com.batache.cars.model.adapter.base.FullSizeModelWithHolder;

@EpoxyModelClass(layout = R.layout.item_top_table_border_item)
public abstract class TopTableBorderItemModel extends FullSizeModelWithHolder<TopTableBorderItemModel.TopTableBorderItemModelHolder> {

  class TopTableBorderItemModelHolder extends EpoxyHolder {
    @Override
    protected void bindView(@NonNull View itemView) {

    }
  }

}
