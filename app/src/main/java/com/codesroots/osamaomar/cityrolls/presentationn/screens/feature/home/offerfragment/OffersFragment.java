package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.offerfragment;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.codesroots.osamaomar.cityrolls.R;
import com.codesroots.osamaomar.cityrolls.helper.PreferenceHelper;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.mainactivity.MainActivity;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.offerfragment.adapter.AllOffersAdapter;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.productdetailsfragment.ProductDetailsModelFactory;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.productdetailsfragment.ProductDetailsViewModel;


public class OffersFragment extends Fragment {

    private OffersViewModel mViewModel;
    RecyclerView alloffers;
    private FrameLayout progress;
    private TextView notfound;
    public ProductDetailsViewModel detailaViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.offers_fragment, container, false);
     //   ((MainActivity) getActivity()).head_title.setText(getText(R.string.day_offers));
        ((MainActivity) getActivity()).logo.setVisibility(View.VISIBLE);
        alloffers = view.findViewById(R.id.alloffers);
        progress = view.findViewById(R.id.progress);
        notfound = view.findViewById(R.id.offers_notfound);
        detailaViewModel = ViewModelProviders.of(this,ProductDetailsModelFactory()).get(ProductDetailsViewModel.class);

        mViewModel = ViewModelProviders.of(this,getViewModelFactory()).get(OffersViewModel.class);
        mViewModel.offersMutableLiveData.observe(this,offers ->
                {
                    progress.setVisibility(View.GONE);
                    if (offers.getData().size()>0)
                    alloffers.setAdapter(new AllOffersAdapter(getActivity(),offers.getData(),detailaViewModel,this));
                    else
                        notfound.setVisibility(View.VISIBLE);
                });

        mViewModel.throwableMutableLiveData.observe(this,throwable ->
                {
                    progress.setVisibility(View.GONE);
              //      Toast.makeText(getActivity(),throwable.getCause().toString(),Toast.LENGTH_SHORT).show();
                });

        return  view;
    }

    private OffersViewModelFactory getViewModelFactory() {
        return new OffersViewModelFactory(this.getActivity().getApplication());
    }
    private ProductDetailsModelFactory ProductDetailsModelFactory() {
        return new ProductDetailsModelFactory(this.getActivity().getApplication(),  PreferenceHelper.getUserId(),0);
    }
}
