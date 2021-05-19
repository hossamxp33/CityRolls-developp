package com.codesroots.osamaomar.cityrolls.presentationn.getuserlocation;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.codesroots.osamaomar.cityrolls.R;
import com.codesroots.osamaomar.cityrolls.entities.names;
import com.codesroots.osamaomar.cityrolls.helper.PreferenceHelper;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.mainfragment.MainViewModelFactory;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.userlocations.UserLocationsViewModel;


public class GetUserLocationActivity extends AppCompatActivity {

    TextView search,send,title;
    UserLocationsViewModel userLocationsViewModel;
    EditText location,city,country,notes,name,phone;
    int addressid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewaddress);
        userLocationsViewModel = ViewModelProviders.of(this, getViewModelFactory()).get(UserLocationsViewModel.class);
        search = findViewById(R.id.search);
        location = findViewById(R.id.location);
        city = findViewById(R.id.city);
        country = findViewById(R.id.country);
        country = findViewById(R.id.country);
        notes = findViewById(R.id.notes);
        send = findViewById(R.id.send);
        title = findViewById(R.id.title);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        addressid = getIntent().getIntExtra(names.BILLING_ID, 0);
        if (addressid>0)
        {
            title.setText(R.string.editaddress);
            userLocationsViewModel.viewLocation(addressid);
        }

        userLocationsViewModel.viewLocationMutableLiveData.observe(this,viewLocation ->
                {
                    location.setText(viewLocation.getData().getAddress());
                    city.setText(viewLocation.getData().getTown_city());
                    country.setText(viewLocation.getData().getState_country());
                    notes.setText(viewLocation.getData().getNotes());
                    name.setText(viewLocation.getData().getFirst_name());
                    phone.setText(viewLocation.getData().getPhone());
                });

        userLocationsViewModel.addLocationMutableLiveData.observe(this,addLocation ->
                {
                    try {
                        if (addLocation.isSuccess())
                            this.finish();
                    }catch (Exception e)
                    {}
                });

        userLocationsViewModel.error.observe(this, throwable ->
                {
                    Toast.makeText(GetUserLocationActivity.this, getText(R.string.error_tryagani), Toast.LENGTH_SHORT).show();
                    send.setText(getText(R.string.save));
                    send.setEnabled(true);
                });
    }


    private MainViewModelFactory getViewModelFactory() {
        return new MainViewModelFactory(getApplication());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void send(View view) {
        ((TextView) view).setText(getText(R.string.wait));
        (view).setEnabled(false);

        if (addressid>0)
            userLocationsViewModel.editUserLocation(addressid,name.getText().toString(),phone.getText().toString(),
                    location.getText().toString(), country.getText().toString(), city.getText().toString(), notes.getText().toString());

        else
        userLocationsViewModel.addUserLocation(PreferenceHelper.getUserId(),name.getText().toString(),phone.getText().toString(),
                location.getText().toString(), country.getText().toString(), city.getText().toString(), notes.getText().toString());
    }
}
