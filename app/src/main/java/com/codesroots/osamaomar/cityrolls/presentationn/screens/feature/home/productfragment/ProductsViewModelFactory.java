package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.productfragment;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.codesroots.osamaomar.cityrolls.domain.ApiClient;
import com.codesroots.osamaomar.cityrolls.domain.ServerGateway;

public class ProductsViewModelFactory implements ViewModelProvider.Factory {


    private Application application;
    private int store_id,user_Id,type;

    public ProductsViewModelFactory(Application application1, int id,int userid,int type1) {
        application = application1;
        store_id = id;
        user_Id = userid;
        type =type1;
    }

    @SuppressWarnings("SingleStatementInBlock")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
         if (modelClass == ProductsViewModel.class)
        {
            return (T) new ProductsViewModel(getApiService(), store_id,user_Id,type);
        }

        throw new IllegalArgumentException("Invalid view model class type");
    }


    private ServerGateway getApiService() {
        return ApiClient.getClient().create(ServerGateway.class);
    }

}
