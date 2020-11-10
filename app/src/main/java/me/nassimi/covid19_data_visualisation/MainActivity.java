package me.nassimi.covid19_data_visualisation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import me.nassimi.covid19_data_visualisation.ui.AboutFragment;
import me.nassimi.covid19_data_visualisation.ui.AdviceFragment;
import me.nassimi.covid19_data_visualisation.ui.CountryFragment;
import me.nassimi.covid19_data_visualisation.ui.HomeFragment;
import me.nassimi.covid19_data_visualisation.ui.NewsFragment;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // --------- NavBottom Menu ----Start-----
        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                    new HomeFragment()).commit();
        }
        // --------- NavBottom Menu ----End-----


    }


    // --------- NavBottom Menu | navListener ----Start-----
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.navigation_country:
                            selectedFragment = new CountryFragment();
                            break;
                        case R.id.navigation_news:
                            selectedFragment = new NewsFragment();
                            break;
                        case R.id.navigation_advice:
                            selectedFragment = new AdviceFragment();
                            break;
                        case R.id.navigation_about:
                            selectedFragment = new AboutFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                            selectedFragment).commit();
                    return true;
                }
            };
    // --------- NavBottom Menu | navListener ----End-----


}