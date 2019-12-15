package com.batache.cars.model.adapter.main;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.batache.cars.R;
import com.batache.cars.api.CarsAPI;
import com.batache.cars.api.CarsResponse;
import com.batache.cars.app.base.BaseFragment;
import com.batache.cars.model.adapter.base.FullSizeModelWithHolder;
import com.google.android.material.button.MaterialButton;

import java.util.List;

@EpoxyModelClass(layout = R.layout.layout_search_by_car_details)
public abstract class CarDetailsSearchModel extends FullSizeModelWithHolder<CarDetailsSearchModel.CarDetailsSearchHolder> {

  @EpoxyAttribute
  Context context;

  @EpoxyAttribute
  BaseFragment.SearchListener searchListener;

  private String carLtr = "A";

  @Override
  public void bind(@NonNull final CarDetailsSearchHolder holder) {
    super.bind(holder);

    holder.carLetterTv.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        PopupMenu popup = new PopupMenu(context, view);
        popup.getMenuInflater().inflate(R.menu.menu_car_letters, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
          @Override
          public boolean onMenuItemClick(MenuItem item) {
            carLtr = item.getTitle().toString();
            holder.carLetterTv.setText(getCarLetter(carLtr));
            return true;
          }
        });
      }
    });

    holder.submitBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        searchListener.onSubmitClick();
        disableViews(holder);

        CarsAPI.getInstance().getCarsByCarDetails(holder.carNbEt.getText().toString(), getCarLetter(carLtr), new CarsAPI.OnCarsFetchedListener() {
          @Override
          public void onSuccess(List<CarsResponse.Car> cars) {
            searchListener.onSuccess(cars);

            resetViewsState(holder);
          }

          @Override
          public void onFailure(@Nullable Throwable t) {
            searchListener.onFailure(t);

            resetViewsState(holder);
          }
        });
      }
    });
  }

  private String getCarLetter(String arabicCarLetter) {
    return arabicCarLetter.replaceAll("\\(.*\\)", "").trim();
  }

  private void disableViews(CarDetailsSearchHolder holder) {
    holder.submitBtn.setEnabled(false);
  }

  private void resetViewsState(CarDetailsSearchHolder holder) {
    carLtr = "A";
    holder.carLetterTv.setText(carLtr);
    holder.carNbEt.setText("");
    holder.submitBtn.setEnabled(true);
  }

  class CarDetailsSearchHolder extends EpoxyHolder {

    AppCompatTextView carLetterTv;
    AppCompatEditText carNbEt;
    MaterialButton submitBtn;

    @Override
    protected void bindView(@NonNull View itemView) {
      carLetterTv = itemView.findViewById(R.id.tv_car_letter);
      carNbEt = itemView.findViewById(R.id.et_car_number);
      submitBtn = itemView.findViewById(R.id.btn_submit);
    }
  }

}
