package com.batache.cars.app.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.airbnb.epoxy.EpoxyRecyclerView;
import com.batache.cars.R;
import com.batache.cars.api.CarsAPI;
import com.batache.cars.api.CarsResponse;
import com.batache.cars.ui.controller.CarsController;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public abstract class BaseFragment extends Fragment {

  private View rootView;

  protected CarsController controller;

  private void onCreateView() {
    controller = new CarsController(requireContext(), getSearchListener());

    EpoxyRecyclerView recyclerView = (EpoxyRecyclerView) rootView;
    recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
    recyclerView.setController(controller);

    initPage();
  }

  private SearchListener getSearchListener() {
    return new SearchListener() {
      @Override
      public void onSubmitClick() {
        hideKeyboard();
      }

      @Override
      public void onSuccess(List<CarsResponse.Car> cars) {
        initPage();
        controller.addTables(cars);
      }

      @Override
      public void onFailure(@Nullable Throwable t) {
        onNoCarsFound();
      }
    };
  }

  protected abstract void initPage();

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.layout_recycler_view, null);

    onCreateView();

    return rootView;
  }

  private void onNoCarsFound() {
    Snackbar.make(rootView.findViewById(R.id.cl_main), "No cars found", Snackbar.LENGTH_SHORT).show();

    initPage();
  }

  private void hideKeyboard() {
    // Check if no view has focus:
    View view = getActivity().getCurrentFocus();
    if (view != null) {
      InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
  }

  public interface SearchListener extends CarsAPI.OnCarsFetchedListener {
    void onSubmitClick();
  }
}
