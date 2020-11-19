package com.batache.cars;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import ru.semper_viventem.backdrop.BackdropBehavior;

public class MainActivity extends AppCompatActivity {

  private BackdropBehavior backdropBehavior;

  private NavigationView navigationView;
  private NavController navController;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) findViewById(R.id.foregroundContainer).getLayoutParams();
    backdropBehavior = (BackdropBehavior) params.getBehavior();
    backdropBehavior.attachBackLayout(R.id.backLayout);
    backdropBehavior.setClosedIcon(R.drawable.ic_menu);
    backdropBehavior.setOpenedIcon(R.drawable.ic_close);

    setupNavigation();
  }

  private void setupNavigation() {
    navigationView = findViewById(R.id.navigation_view);
    navController = Navigation.findNavController(this, R.id.nav_host_fragment);

    NavigationUI.setupWithNavController(navigationView, navController);
    navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
      @Override
      public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        backdropBehavior.close(true);
      }
    });
  }
}
