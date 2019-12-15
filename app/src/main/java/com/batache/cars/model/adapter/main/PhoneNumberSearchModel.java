package com.batache.cars.model.adapter.main;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.batache.cars.R;
import com.batache.cars.api.CarsAPI;
import com.batache.cars.api.CarsResponse;
import com.batache.cars.app.base.BaseFragment;
import com.batache.cars.model.adapter.base.FullSizeModelWithHolder;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

@EpoxyModelClass(layout = R.layout.layout_search_by_phone_number)
public abstract class PhoneNumberSearchModel extends FullSizeModelWithHolder<PhoneNumberSearchModel.PersonalDetailsSearchHolder> {

  @EpoxyAttribute
  BaseFragment.SearchListener searchListener;

  @Override
  public void bind(@NonNull final PersonalDetailsSearchHolder holder) {
    super.bind(holder);

    holder.submitBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        searchListener.onSubmitClick();
        disableViews(holder);

        CarsAPI.getInstance().getCarsByPhoneNumber(holder.phoneNumberEt.getText().toString(), new CarsAPI.OnCarsFetchedListener() {
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

  private void disableViews(PersonalDetailsSearchHolder holder) {
    holder.submitBtn.setEnabled(false);
  }

  private void resetViewsState(PersonalDetailsSearchHolder holder) {
    holder.phoneNumberEt.setText("");
    holder.submitBtn.setEnabled(true);
  }

  class PersonalDetailsSearchHolder extends EpoxyHolder {

    TextInputEditText phoneNumberEt;
    MaterialButton submitBtn;

    @Override
    protected void bindView(@NonNull View itemView) {
      phoneNumberEt = itemView.findViewById(R.id.et_phone_number);
      submitBtn = itemView.findViewById(R.id.btn_submit);
    }
  }

}
