package com.airpurifier.airpurifier;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.airpurifier.airpurifier.Data.Data;
import com.airpurifier.airpurifier.Fragment.Accountlist;
import com.airpurifier.airpurifier.Fragment.AirStatus;
import com.airpurifier.airpurifier.Fragment.Homepage;
import com.airpurifier.airpurifier.Fragment.SprayCleaner;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    Toolbar main_toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    static String prompt = "homePage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //initialization of xml
        initXml();
        isAdmin();
        setSupportActionBar(main_toolbar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, main_toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);



        Fragment selectedFragment = null;

        if(prompt.equalsIgnoreCase("homePage")){
            selectedFragment = new Homepage();
        }else if(prompt.equalsIgnoreCase("accountlist")){
            selectedFragment = new Accountlist();
        }else if(prompt.equalsIgnoreCase("airStatus")){
            selectedFragment = new AirStatus();
        }else if(prompt.equalsIgnoreCase("sprayCleaner")){
            selectedFragment = new SprayCleaner();
        }else{
            selectedFragment = new Homepage();
        }


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();

    }

    private void isAdmin() {

        Data data = new Data();

        Boolean x = data.isAdmin;

        try{
            if(x.equals(false)){
                navigationView.getMenu().getItem(1).setVisible(false);
                Log.d("TAG", "status" + "admin");
                //Toast.makeText(MainActivity.this, "check " + navigationView.getMenu().getItem(0), Toast.LENGTH_LONG).show();
            }else{
                navigationView.getMenu().getItem(1).setVisible(true);
            }
        }catch (Exception e){
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }



    }

    public void initXml() {
        main_toolbar = findViewById(R.id.main_toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

    }



    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment selectedFragment = null;

        int itemId = item.getItemId();
        if (itemId == R.id.home) {
            selectedFragment = new Homepage();
        } else if (itemId == R.id.account) {
            selectedFragment = new Accountlist();
        } else if (itemId == R.id.airstatus) {
            selectedFragment = new AirStatus();
        } else if (itemId == R.id.aircleaner) {
            selectedFragment = new SprayCleaner();
        }


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


}