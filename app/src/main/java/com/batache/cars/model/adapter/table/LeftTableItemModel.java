package com.batache.cars.model.adapter.table;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.batache.cars.R;

@EpoxyModelClass(layout = R.layout.item_left_table_item)
public abstract class LeftTableItemModel extends EpoxyModelWithHolder<LeftTableItemModel.LeftTableItemModelHolder> {

  @EpoxyAttribute
  String title;

  @Override
  public void bind(@NonNull LeftTableItemModelHolder holder) {
    super.bind(holder);

    holder.title.setText(title);
  }

  class LeftTableItemModelHolder extends EpoxyHolder {

    TextView title;

    @Override
    protected void bindView(@NonNull View itemView) {
      this.title = (TextView) itemView;
    }
  }

}
