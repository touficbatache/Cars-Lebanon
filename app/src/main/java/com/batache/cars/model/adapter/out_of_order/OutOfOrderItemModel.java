package com.batache.cars.model.adapter.out_of_order;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyRecyclerView;
import com.batache.cars.R;
import com.batache.cars.api.CarsResponse;
import com.batache.cars.model.adapter.base.FullSizeModelWithHolder;
import com.batache.cars.ui.controller.OutOfOrderController;

@EpoxyModelClass(layout = R.layout.item_out_of_order)
public abstract class OutOfOrderItemModel extends FullSizeModelWithHolder<OutOfOrderItemModel.OutOfOrderItemModelHolder> {

  @EpoxyAttribute
  Context context;

  @EpoxyAttribute
  CarsResponse.Car car;

  @Override
  public void bind(@NonNull OutOfOrderItemModelHolder holder) {
    super.bind(holder);

    OutOfOrderController controller = new OutOfOrderController();

    holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
    holder.recyclerView.setController(controller);

    controller.addTable(car);
  }

  class OutOfOrderItemModelHolder extends EpoxyHolder {

    EpoxyRecyclerView recyclerView;

    @Override
    protected void bindView(@NonNull View itemView) {
      recyclerView = itemView.findViewById(R.id.recyclerview);
    }
  }

}
