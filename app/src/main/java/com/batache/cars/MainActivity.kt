package com.batache.cars

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.batache.cars.api.CarsAPI
import com.batache.cars.api.CarsAPI.OnCarsFetchedListener
import com.batache.cars.api.CarsResponse.Car
import com.batache.cars.ui.controller.CarsController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import ru.semper_viventem.backdrop.BackdropBehavior


class MainActivity : AppCompatActivity(), View.OnClickListener, CarsController.CarsListener, OnCarsFetchedListener {

  private var backdropBehavior: BackdropBehavior? = null

  private var controller: CarsController? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)

    (foregroundContainer.layoutParams as CoordinatorLayout.LayoutParams).let { params ->
      (params.behavior as BackdropBehavior?)?.let {
        backdropBehavior = it
        it.addOnDropListener(object : BackdropBehavior.OnDropListener {
          override fun onDrop(dropState: BackdropBehavior.DropState, fromUser: Boolean) {
            if (dropState == BackdropBehavior.DropState.OPEN) {
              supportActionBar?.title = "Search by"
            } else if (dropState == BackdropBehavior.DropState.CLOSE) {
              supportActionBar?.title = getApplicationName()
            }
          }
        })
        it.attachBackLayout(R.id.backLayout)
        it.setClosedIcon(R.drawable.ic_menu)
        it.setOpenedIcon(R.drawable.ic_close)
      }
    }

    CarsController(this).let {
      controller = it
      recyclerview.setController(it)
      it.loadStaticSection(CarsController.CAR_NUMBER_SEARCH)
      it.requestModelBuild()
    }

    carNumberBtn.setOnClickListener(this)
    ownerNameBtn.setOnClickListener(this)
    ownerPhoneNumberBtn.setOnClickListener(this)
  }

  fun getApplicationName(): String? {
    val stringId = applicationInfo.labelRes
    return if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else getString(stringId)
  }

  override fun onClick(view: View?) {
    when (view) {
      carNumberBtn -> CarsController.CAR_NUMBER_SEARCH
      ownerNameBtn -> CarsController.OWNER_NAME_SEARCH
      ownerPhoneNumberBtn -> CarsController.OWNER_PHONE_NUMBER_SEARCH
      else -> null
    }?.let {
      controller?.isEmptyBuild = true
      controller?.requestModelBuild()

      Handler().postDelayed({
        controller?.isEmptyBuild = false
        controller?.loadStaticSection(it)
        controller?.clear()
        controller?.requestModelBuild()
        backdropBehavior?.close(true)
      }, 10)
    }
  }

  override fun onSearchByCarNumber(letter: String?, number: String?) {
    CarsAPI.searchByCarNumber(number, letter, this)
  }

  override fun onSearchByOwnerName(firstName: String?, lastName: String?, allowInaccurate: Boolean) {
    CarsAPI.searchByOwnerName(firstName, lastName, allowInaccurate, this)
  }

  override fun onSearchByOwnerPhoneNumber(phoneNumber: String?) {
    CarsAPI.searchByOwnerPhoneNumber(phoneNumber, this)
  }

  override fun onSuccess(cars: List<Car>?) {
    cars?.let {
      hideKeyboard()

      controller?.isEmptyBuild = true
      controller?.requestModelBuild()

      Handler().postDelayed({
        controller?.isEmptyBuild = false
        controller?.loadCars(it)
        controller?.requestModelBuild()
      }, 10)
    }
  }

  override fun onFailure(t: Throwable?) {
    hideKeyboard()
    Snackbar.make(foregroundContainer, "No cars found", Snackbar.LENGTH_SHORT).show()

    controller?.isEmptyBuild = true
    controller?.requestModelBuild()

    Handler().postDelayed({
      controller?.isEmptyBuild = false
      controller?.clear()
      controller?.requestModelBuild()
    }, 10)
  }

  private fun hideKeyboard() {
    (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(currentFocus?.windowToken, 0)
  }
}