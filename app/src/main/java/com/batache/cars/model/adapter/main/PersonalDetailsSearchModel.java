package com.batache.cars.model.adapter.main;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

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

@EpoxyModelClass(layout = R.layout.layout_search_by_personal_details)
public abstract class PersonalDetailsSearchModel extends FullSizeModelWithHolder<PersonalDetailsSearchModel.PersonalDetailsSearchHolder> {

  @EpoxyAttribute
  BaseFragment.SearchListener searchListener;

  private boolean allowInaccurateResults = false;

  @Override
  public void bind(@NonNull final PersonalDetailsSearchHolder holder) {
    super.bind(holder);

    holder.allowInaccurateResultsChbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        allowInaccurateResults = isChecked;
      }
    });
    holder.allowInaccurateResultsChbx.setChecked(false);

    holder.submitBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        searchListener.onSubmitClick();
        disableViews(holder);

        CarsAPI.getInstance().getCarsByPersonalDetails(holder.firstNameEt.getText().toString(), holder.lastNameEt.getText().toString(), allowInaccurateResults, new CarsAPI.OnCarsFetchedListener() {
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
    holder.allowInaccurateResultsChbx.setEnabled(false);
    holder.submitBtn.setEnabled(false);
  }

  private void resetViewsState(PersonalDetailsSearchHolder holder) {
    holder.firstNameEt.setText("");
    holder.lastNameEt.setText("");
    holder.allowInaccurateResultsChbx.setEnabled(true);
    holder.submitBtn.setEnabled(true);
  }

  class PersonalDetailsSearchHolder extends EpoxyHolder {

    TextInputEditText firstNameEt, lastNameEt;
    CheckBox allowInaccurateResultsChbx;
    MaterialButton submitBtn;

    @Override
    protected void bindView(@NonNull View itemView) {
      firstNameEt = itemView.findViewById(R.id.et_first_name);
      lastNameEt = itemView.findViewById(R.id.et_last_name);
      allowInaccurateResultsChbx = itemView.findViewById(R.id.chbx_allow_inaccurate_results);
      submitBtn = itemView.findViewById(R.id.btn_submit);
    }
  }

}
