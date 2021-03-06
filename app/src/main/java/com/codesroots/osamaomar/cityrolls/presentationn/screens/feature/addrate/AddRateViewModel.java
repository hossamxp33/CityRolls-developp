package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.addrate;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codesroots.osamaomar.cityrolls.domain.ServerGateway;
import com.codesroots.osamaomar.cityrolls.entities.DefaultAdd;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class AddRateViewModel extends ViewModel {


    public MutableLiveData<Boolean> AddRateMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Throwable> throwableMutableLiveData = new MutableLiveData<>();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private ServerGateway serverGateway;


    AddRateViewModel(ServerGateway serverGateway1) {
        serverGateway = serverGateway1;
    }


    public void AddRateToProduct(int userid,int productid,float rate,String comment){
        mCompositeDisposable.add(
                serverGateway.addProductRate(userid,productid,rate,comment)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( this::postDataResponse,
                                this::postError));
    }


    private void postDataResponse(DefaultAdd addResponse) {
        AddRateMutableLiveData.postValue(addResponse.isSuccess());
    }


    private void postError(Throwable throwable) {
        throwableMutableLiveData.postValue(throwable);
    }

}
