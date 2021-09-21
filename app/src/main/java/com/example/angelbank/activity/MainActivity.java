package com.example.angelbank.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.example.angelbank.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    NavController navController;
    ExtendedFloatingActionButton addCustomer;
    MaterialToolbar materialToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addCustomer = findViewById(R.id.add_customer);
        materialToolbar = findViewById(R.id.top_AppBar);

        setSupportActionBar(materialToolbar);

        navController = NavHostFragment.findNavController(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)));

        addCustomer.setOnClickListener(v -> {
            navController.navigate(R.id.action_homeFragment_to_addCustomerFragment);
        });

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.homeFragment){
                materialToolbar.getMenu().clear();
                materialToolbar.inflateMenu(R.menu.main_menu);
                addCustomer.show();
            }else{
                materialToolbar.getMenu().clear();
                addCustomer.hide();
            }
        });

        materialToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.setting){
                navController.navigate(R.id.action_homeFragment_to_settingsFragment);
            }
            return false;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}