package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.myorders;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.codesroots.osamaomar.cityrolls.domain.ServerGateway;
import com.codesroots.osamaomar.cityrolls.entities.MyOrders;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class MyOrdersViewModel extends ViewModel {

    public MutableLiveData<MyOrders> myOrdersMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Throwable> throwableMutableLiveData = new MutableLiveData<>();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private ServerGateway serverGateway;
    private int user_id;

    public MyOrdersViewModel(ServerGateway serverGateway1, int id) {
        serverGateway = serverGateway1;
        user_id = id;
        getMyOrders();
    }

    private void getMyOrders(){
        mCompositeDisposable.add(
                serverGateway.retrieveUserOrders()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( this::postDataResponse,
                                this::postError));
    }

    private void postDataResponse(MyOrders productRates) {
        myOrdersMutableLiveData.postValue(productRates);
    }

    private void postError(Throwable throwable) {
        throwableMutableLiveData.postValue(throwable);
    }

}
