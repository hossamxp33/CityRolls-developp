package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.userlocations;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.codesroots.osamaomar.cityrolls.R;
import com.codesroots.osamaomar.cityrolls.entities.OrderModel;
import com.codesroots.osamaomar.cityrolls.entities.UserLocations;
import com.codesroots.osamaomar.cityrolls.helper.Locationclick;
import com.codesroots.osamaomar.cityrolls.helper.PreferenceHelper;
import com.codesroots.osamaomar.cityrolls.presentationn.getuserlocation.GetUserLocationActivity;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.mainfragment.MainViewModelFactory;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.payment.PaymentFragment;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.userlocations.adapter.LocationsAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import static com.codesroots.osamaomar.cityrolls.entities.names.ORDER;


public class UserLocationsFragment extends Fragment implements Locationclick {

    private UserLocationsViewModel mViewModel;
    private RecyclerView locations;
    private ImageView addLocation;
    OrderModel orderModel;
    LocationsAdapter locationsAdapter;
    TextView notfound;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_locations_fragment, container, false);

        locations = view.findViewById(R.id.oldplaces);
        addLocation = view.findViewById(R.id.addlocation);
        notfound = view.findViewById(R.id.notfond);
        if (getArguments()!=null)
        orderModel = (OrderModel) getArguments().getSerializable(ORDER);

        mViewModel = ViewModelProviders.of(this, getViewModelFactory()).get(UserLocationsViewModel.class);
        mViewModel.retrieveUserLocations(PreferenceHelper.getUserId());
        mViewModel.locations.observe(this, dataBeans ->
        {
            if (dataBeans.size() > 0) {
                locationsAdapter = new LocationsAdapter(getContext(), dataBeans, this);
                locations.setAdapter(locationsAdapter);
                notfound.setVisibility(View.GONE);
            } else
                notfound.setVisibility(View.VISIBLE);
        });

        mViewModel.error.observe(this, throwable ->
                Toast.makeText(getActivity(), getText(R.string.error_in_data), Toast.LENGTH_SHORT).show()
        );

        addLocation.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), GetUserLocationActivity.class);
            startActivityForResult(intent, 115);
        });
        return view;
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        mViewModel.retrieveUserLocations(PreferenceHelper.getUserId());
    }

    private MainViewModelFactory getViewModelFactory() {
        return new MainViewModelFactory(this.getActivity().getApplication());
    }

    @Override
    public void onlocationchoicw(UserLocations.DataBean location) {
        if (orderModel!=null) {
            orderModel.setBilling_id(location.getBilling_id());
            Fragment fragment = new PaymentFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(ORDER, orderModel);
            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainfram, fragment).addToBackStack(null).commit();
        }
    }
}
