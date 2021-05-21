package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.mainactivity;

import android.Manifest;
import android.annotation.SuppressLint;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;

import com.codesroots.osamaomar.cityrolls.domain.ApiClient;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.mainfragment.homeFragment;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.morefragment.MenuFragment;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuInflater;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codesroots.osamaomar.cityrolls.R;
import com.codesroots.osamaomar.cityrolls.helper.AddorRemoveCallbacks;
import com.codesroots.osamaomar.cityrolls.helper.Converter;
import com.codesroots.osamaomar.cityrolls.helper.PreferenceHelper;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.cartfragment.CartFragment;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.favorite.FavoritsFragment;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.mainactivity.adapter.AllDepartsAdapter;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.myorders.MyOrdersFragment;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.offerfragment.OffersFragment;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.productfragment.ProductsFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener, AddorRemoveCallbacks , OnMapReadyCallback {

    RecyclerView alldepartsinNavigation;
    BottomNavigationView bottomNavigationView;
    MainActivityViewModel mainActivityViewModel;
 //   NavigationView navigationView;
    public DrawerLayout drawer;
    public ImageView logo, search;
    public TextView head_title;
    private EditText searchName;
    private ArrayList<String> arrayList = new ArrayList<>();
    private PreferenceHelper preferenceHelper;
    private static int cart_count = 0;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferenceHelper = new PreferenceHelper(this);
        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance();
       initialize();
        //setUpToggle();
        GetLocation();
//        search.setOnClickListener(v -> {
//
//   //         performSearch();
//            /// Hidden keyboard  after click
//            HiddenKeyboard(v);
//        });
//        searchName.setOnEditorActionListener((v, actionId, event) -> {
//            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//          //      performSearch();
//                /// Hidden keyboard  after click
//                HiddenKeyboard(v);
//                return true;
//            }
//
//            return false;
//        });
         head_title.setVisibility(View.INVISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainfram, new homeFragment()).commit();

    }
private void HiddenKeyboard(View view ){
    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
}
    private void initialize() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
     //   drawer = findViewById(R.id.drawer_layout);
        search = findViewById(R.id.search);
        head_title = findViewById(R.id.head_title);
      //  searchName = findViewById(R.id.searchName);
    //    toggle = new ActionBarDrawerToggle(
   //           this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
        //toggle.syncState();
//        alldepartsinNavigation = findViewById(R.id.all_departs);
        logo = findViewById(R.id.logo);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        mainActivityViewModel = ViewModelProviders.of(this, getViewModelFactory()).get(MainActivityViewModel.class);
        mainActivityViewModel.sidemenuMutableLiveData.observe(this, sidemenu ->
                alldepartsinNavigation.setAdapter(new AllDepartsAdapter(this, sidemenu.getCategory())));
    //    navigationView = findViewById(R.id.nav_view);
    //    navigationView.setNavigationItemSelectedListener(this);
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CALL_PHONE,Manifest.permission.READ_CONTACTS
                        ,Manifest.permission.WRITE_CONTACTS}, 112);

    }

    private void performSearch() {
        if (!searchName.getText().toString().matches("")) {
            Fragment fragment = new ProductsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("name", searchName.getText().toString());
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.mainfram, fragment).addToBackStack(null).commit();
        } else
            searchName.setError(getText(R.string.nosearchname));
    }

    private MainActivityModelFactory getViewModelFactory() {
        return new MainActivityModelFactory(getApplication());
    }

//    @Override
//    public void onBackPressed() {
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_cart);
        cart_count = PreferenceHelper.retriveCartItemsSize();
        menuItem.setIcon(Converter.convertLayoutToImage(MainActivity.this, cart_count, R.drawable.shoppcart));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainfram, new CartFragment()).addToBackStack(null).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.more:
           //     drawer.closeDrawers();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainfram, new MenuFragment()).addToBackStack(null).commit();
                break;

            case R.id.notifications:
                if (PreferenceHelper.getUserId() > 0) {
               //     drawer.closeDrawers();
                    popupFragments();
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainfram, new FavoritsFragment()).addToBackStack(null).commit();
                } else
                    Toast.makeText(MainActivity.this, getText(R.string.loginfirst), Toast.LENGTH_SHORT).show();
                break;
            case R.id.main:
              //  drawer.closeDrawers();
                popupFragments();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainfram, new homeFragment()).addToBackStack(null).commit();
                break;

            case R.id.myorders:
                if (PreferenceHelper.getUserId() > 0) {
               //     drawer.closeDrawers();
                    popupFragments();
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainfram, new MyOrdersFragment()).addToBackStack(null).commit();
                } else
                    Toast.makeText(MainActivity.this, getText(R.string.loginfirst), Toast.LENGTH_SHORT).show();
                break;

            case R.id.offers:
             //   drawer.closeDrawers();
                popupFragments();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainfram, new OffersFragment()).addToBackStack(null).commit();
                break;
        }

       // DrawerLayout drawer = findViewById(R.id.drawer_layout);
    //    drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void  popupFragments()
    {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }
    @Override
    public void onAddProduct() {
        cart_count++;
        invalidateOptionsMenu();
    }

    @Override
    public void onRemoveProduct() {
        cart_count--;
        invalidateOptionsMenu();
    }

    @Override
    public void onClearCart() {
        PreferenceHelper.clearCart();
        cart_count = 0;
        invalidateOptionsMenu();
    }

    @SuppressLint("ResourceType")
    public void setUpToggle() {
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        toggle.setDrawerIndicatorEnabled(false);

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.list, this.getTheme());
        toggle.setHomeAsUpIndicator(drawable);

        drawer.addDrawerListener(toggle);
        toggle.setToolbarNavigationClickListener(v -> {
            if (drawer.isDrawerVisible(GravityCompat.START)) {

                drawer.closeDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        toggle.syncState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.usermap)).getMapAsync(this);
        } else if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                LatLng latLng = place.getLatLng();


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void GetLocation() {
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.choice_location)
                        .setMessage(R.string.addlocation)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(new AppCompatActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            }
            statusCheck();

        }


    }
    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
