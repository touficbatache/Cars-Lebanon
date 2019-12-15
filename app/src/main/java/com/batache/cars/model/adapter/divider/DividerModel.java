package com.batache.cars.model.adapter.divider;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.batache.cars.R;
import com.batache.cars.model.adapter.base.FullSizeModelWithHolder;

@EpoxyModelClass(layout = R.layout.layout_divider)
public abstract class DividerModel extends FullSizeModelWithHolder<DividerModel.DividerHolder> {

  @EpoxyAttribute
  Context context;

  @EpoxyAttribute
  boolean showResultsText;

  @Override
  public void bind(@NonNull DividerHolder holder) {
    super.bind(holder);

    if (showResultsText) {
      holder.resultsTextTv.setVisibility(View.VISIBLE);
      holder.itemView.setPadding(0, 0, 0, 0);
    } else {
      holder.resultsTextTv.setVisibility(View.GONE);
      holder.itemView.setPadding(pxFromDp(16), 0, pxFromDp(16), 0);
    }
  }

  private int pxFromDp(float dp) {
    return (int) (dp * context.getResources().getDisplayMetrics().density);
  }

  class DividerHolder extends EpoxyHolder {

    View itemView;
    TextView resultsTextTv;

    @Override
    protected void bindView(@NonNull View itemView) {
      this.itemView = itemView;
      resultsTextTv = itemView.findViewById(R.id.tv_results);
    }
  }

}
