package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.subcategryfragment;

import android.annotation.SuppressLint;
import android.util.Log;

import com.codesroots.osamaomar.cityrolls.domain.ApiClient;
import com.codesroots.osamaomar.cityrolls.domain.ServerGateway;
import com.codesroots.osamaomar.cityrolls.entities.MainView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


class MainFragmentViewModel extends ViewModel {


    public MutableLiveData<MainView> mainViewMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Throwable> throwableMutableLiveData = new MutableLiveData<>();
    private ServerGateway serverGateway;

     MainFragmentViewModel(ServerGateway serverGateway1) {
        serverGateway = serverGateway1;
       // getData();
    }

    public void getData() {


        getObservable().subscribeWith(getObserver());
    }
    private ServerGateway getApiService() {
        return ApiClient.getClient().create(ServerGateway.class);
    }

    @SuppressLint("CheckResult")
    private Observable<MainView> getObservable() {
        Observable<MainView> photographersData = getApiService().getMainViewData();
        photographersData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return photographersData;
    }

    private DisposableObserver<MainView> getObserver() {
        return new DisposableObserver<MainView>() {
            @Override
            public void onNext(@NonNull MainView result) {
                if (mainViewMutableLiveData !=null)
                mainViewMutableLiveData.postValue(result);
            }
            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("Errors", "Error" + e);
                e.printStackTrace();
                throwableMutableLiveData.postValue(e);
            }
            @Override
            public void onComplete() {
            }
        };
    }
}
