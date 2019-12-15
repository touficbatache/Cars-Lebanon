package com.batache.cars.app;

import com.batache.cars.app.base.BaseFragment;

public class PersonalDetailsFragment extends BaseFragment {
  @Override
  protected void initPage() {
    controller.reset();
    controller.addSearchByPersonalDetails();
  }
}
