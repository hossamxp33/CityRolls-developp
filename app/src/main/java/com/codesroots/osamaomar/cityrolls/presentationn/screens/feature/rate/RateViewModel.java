package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.rate;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codesroots.osamaomar.cityrolls.domain.ServerGateway;
import com.codesroots.osamaomar.cityrolls.entities.ProductRate;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class RateViewModel extends ViewModel {

    public MutableLiveData<ProductRate> rateMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Throwable> throwableMutableLiveData = new MutableLiveData<>();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private ServerGateway serverGateway;
    private int product_id;

    public RateViewModel(ServerGateway serverGateway1,int id) {
        serverGateway = serverGateway1;
        product_id = id;
        getProductRates();
    }

    private void getProductRates(){
        mCompositeDisposable.add(
                serverGateway.getProductRates(product_id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( this::postDataResponse,
                                this::postError));
    }


    private void postDataResponse(ProductRate productRates) {
        rateMutableLiveData.postValue(productRates);
    }


    private void postError(Throwable throwable) {
        throwableMutableLiveData.postValue(throwable);
    }

}
