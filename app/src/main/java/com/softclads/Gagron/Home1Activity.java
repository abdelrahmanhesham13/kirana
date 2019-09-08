package com.softclads.Gagron;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softclads.Gagron.R;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.softclads.Gagron.HomeFragments.CategoriesFragment;
import com.softclads.Gagron.HomeFragments.HomeFragment;
import com.softclads.Gagron.HomeFragments.SearchFragment;
import com.softclads.Gagron.HomeFragments.SupportFragment;
import com.softclads.Gagron.Models.ControlModel;
import com.softclads.Gagron.NavItemsActivities.AboutUsActivity;
import com.softclads.Gagron.NavItemsActivities.MyAccountActivity;
import com.softclads.Gagron.NavItemsActivities.MyOrderActivity;
import com.softclads.Gagron.NavItemsActivities.NotificationActivity;
import com.softclads.Gagron.NavItemsActivities.ServiceAreaActivity;
import com.softclads.Gagron.NavItemsActivities.ShoppingCartActivity;
import com.softclads.Gagron.RecyclerView.CategroiesAdapter;
import com.softclads.Gagron.Utils.Connectors;
import com.softclads.Gagron.Utils.Constants;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home1Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public AHBottomNavigation bottomNavigation;
    public AHBottomNavigationItem item2, item3;
    public LinearLayout searchbarhome1;
    public ImageView homebar1item1, homebar1item2, homebar1item3, homebar1item4;
    public ImageView homebar2item1, homebar2item2, app_bar_home1_Shiping;
    public ImageView homesection3button;
    RecyclerView CategryRV;
    CategroiesAdapter mCategroiesAdapter;
    public View headerView;
    public Toolbar toolbar;
    TextView toolbarTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);
        //********Declearaton****************
        Control();
        searchbarhome1 = findViewById(R.id.searchbarhome);

        homesection3button = findViewById(R.id.homesection3button);

        //************************
/*
        searchbarhome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavigation.setCurrentItem(2);
            }
        });
*/

/*\
        homesection3button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavigation.setCurrentItem(1);
            }
        });
*/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarTitle = findViewById(R.id.app_bar_home1_title);
        app_bar_home1_Shiping = findViewById(R.id.app_bar_home1_Shiping);
        app_bar_home1_Shiping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home1Activity.this, ShoppingCartActivity.class);
                startActivity(i);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        TextView nav_name = headerView.findViewById(R.id.nav_name);
        nav_name.setText(Hawk.get(Constants.muserFirstName) + "");
        TextView nav_location = headerView.findViewById(R.id.nav_location);
        nav_location.setText(Hawk.get(Constants.muserAddress) + "");
        navigationView.setNavigationItemSelectedListener(this);
        final HomeFragment homeFragment = new HomeFragment();
        homeFragment.setOnSearchBarHomeClicked(new HomeFragment.OnSearchBarHomeClicked() {
            @Override
            public void setOnSearchBarHomeClicked(int type) {
                setCurrentTab();
            }
        });
        homeFragment.setOnCateriesHomeClicked(new HomeFragment.OnCategroiesHomeClicked() {
            @Override
            public void setOnCateriesHomeClicked(int type) {
                setCurrentTabCatgries();
            }
        });
        final CategoriesFragment categoriesFragment = new CategoriesFragment();
        final SearchFragment searchFragment = new SearchFragment();
        final SupportFragment supportFragment = new SupportFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.FramContainer, homeFragment);
        fragmentTransaction.commit();
        toolbarTitle.setText("Main");

        //**************
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Home", R.drawable.ic_home_black_24dp, R.color.hint);
        item2 = new AHBottomNavigationItem("Categories", R.drawable.ic_apps_black_24dp, R.color.hint);
        item3 = new AHBottomNavigationItem("Search", R.drawable.ic_loupe, R.color.hint);

        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Support", R.drawable.ic_headset_mic_black_24dp, R.color.hint);

// Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);


// Set background color
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

// Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(false);

// Change colors
        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        bottomNavigation.setInactiveColor(Color.parseColor("#F63D2B"));
// Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);
// Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @SuppressLint("ResourceType")
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (position == 0) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.FramContainer, homeFragment);
                    fragmentTransaction.commit();
                    toolbarTitle.setText("Main");
                    // bottomNavigation.setInactiveColor(Color.parseColor("#747474"));
                    //  item3.setColorRes(Color.parseColor("#747474"));


                }
                if (position == 1) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.FramContainer, categoriesFragment);
                    fragmentTransaction.commit();
                    toolbarTitle.setText("Categries");


                }
                if (position == 2) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.FramContainer, searchFragment);
                    fragmentTransaction.commit();
                    toolbarTitle.setText("Search");


                }
                if (position == 3) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.FramContainer, supportFragment);
                    fragmentTransaction.commit();
                    toolbarTitle.setText("Support");

                }

                return true;
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.shop_By_Categorie) {
            // Handle the camera action
            bottomNavigation.setCurrentItem(1);

        } else if (id == R.id.Shopping_Cart) {
            Intent intent = new Intent(getApplicationContext(), ShoppingCartActivity.class);
            startActivity(intent);


        } else if (id == R.id.Account) {
            Intent intent = new Intent(getApplicationContext(), MyAccountActivity.class);
            startActivity(intent);

        } else if (id == R.id.Notification) {

            Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
            startActivity(intent);
        } else if (id == R.id.Order) {

            Intent intent = new Intent(getApplicationContext(), MyOrderActivity.class);
            startActivity(intent);
        } /*else if (id == R.id.Adress_Book) {
            Intent intent = new Intent(getApplicationContext(), AdressBook.class);
            startActivity(intent);

        }*/ else if (id == R.id.Service_Area) {
            Intent intent = new Intent(getApplicationContext(), ServiceAreaActivity.class);
            startActivity(intent);
        } else if (id == R.id.share) {
        /*    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, Hawk.get(Constants.muserFirstName) + " invites you to Download Gagron from Google Play 'Link' ");
            try {
                startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(Home1Activity.this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
            }*/

            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Gagron");
            intent.putExtra(Intent.EXTRA_TEXT, Hawk.get(Constants.muserFirstName) + " invites you to Download Gagron from Google Play " +Uri.parse("market://details?id=" + appPackageName)+" ");
            startActivity(Intent.createChooser(intent, "choose one"));

        } else if (id == R.id.RateUs) {

            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.vending");

// package name and activity
            ComponentName comp = new ComponentName("com.android.vending",
                    "com.google.android.finsky.activities.LaunchUrlHandlerActivity");
            launchIntent.setComponent(comp);

// sample to open facebook app
            launchIntent.setData(Uri.parse("market://details?id=com.facebook.GagronIN"));
            startActivity(launchIntent);
        } else if (id == R.id.Support) {
            bottomNavigation.setCurrentItem(3);

        } else if (id == R.id.About) {
            Intent intent = new Intent(getApplicationContext(), AboutUsActivity.class);
            startActivity(intent);

        } else if (id == R.id.Out) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
            Hawk.deleteAll();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setCurrentTab() {
        Home1Activity.this.bottomNavigation.setCurrentItem(2);
    }

    public void setCurrentTabCatgries() {
        Home1Activity.this.bottomNavigation.setCurrentItem(1);
    }

    public void Control() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Connectors.getLoginDataService.BaseURL)
                .addConverterFactory(GsonConverterFactory
                        .create(new Gson())).build();
        Connectors.getLoginDataService getLoginDataService = retrofit.create(Connectors.getLoginDataService.class);
        getLoginDataService.control().enqueue(new Callback<ControlModel>() {
            @Override
            public void onResponse(Call<ControlModel> call, Response<ControlModel> response) {
                ControlModel controlModel = response.body();
                if (response.isSuccessful()) {
                    Log.d("TTTTT", "onResponse: " + controlModel.getControl());
                    if (controlModel.getControl().equals("1")) {

                    } else {
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);

                    }
                } else {
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            }

            @Override
            public void onFailure(Call<ControlModel> call, Throwable t) {

            }
        });
    }

}
