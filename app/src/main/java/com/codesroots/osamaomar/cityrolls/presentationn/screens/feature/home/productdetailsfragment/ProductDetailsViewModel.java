package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.productdetailsfragment;

import android.annotation.SuppressLint;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.codesroots.osamaomar.cityrolls.domain.ServerGateway;
import com.codesroots.osamaomar.cityrolls.entities.AddToFavModel;
import com.codesroots.osamaomar.cityrolls.entities.Items;
import com.codesroots.osamaomar.cityrolls.entities.ProductDetails;
import com.codesroots.osamaomar.cityrolls.entities.StoreSetting;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ProductDetailsViewModel extends ViewModel {
    public MutableLiveData<Items> productDetailsizeMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ProductDetails> productDetailsMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<AddToFavModel> addToFavMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<AddToFavModel> deleteToFavMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Throwable> throwableMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<StoreSetting> storeSettingMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Throwable> throwablefav = new MutableLiveData<>();
    private ServerGateway serverGateway;
    private  int itemid,userid;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

     ProductDetailsViewModel(ServerGateway serverGateway1,int user_id,int item_id) {
        serverGateway = serverGateway1;
         userid = user_id;
         itemid = item_id;
       // getData();
     //    getSettingData();
    }

    public void getData() {
        getObservable().subscribeWith(getObserver());
    }


    public void getSettingData() {
        mCompositeDisposable.add(
                serverGateway.getStorSetting()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( this::postDataResponse,
                                this::postError));
    }

    private void postDataResponse(StoreSetting storeSetting) {
         storeSettingMutableLiveData.postValue(storeSetting);
    }


    private void postError(Throwable throwable) {
        throwableMutableLiveData.postValue(throwable);
    }

    public  void AddToFav (int item_ids)
    {
        itemid = item_ids;
        getObservableToFavObservable().subscribeWith(getObserverAddFav());
    }

    public  void DeleteFav (int user,int product)
    {
        getObservableToDeleteFav(user,product).subscribeWith(getObserverDeletFav());
    }

    ////////////// getData
    @SuppressLint("CheckResult")
    private Observable<ProductDetails> getObservable() {
        Observable<ProductDetails> photographersData = serverGateway.getProductDetails(itemid,userid);
        photographersData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return photographersData;
    }

    private DisposableObserver<ProductDetails> getObserver() {
        return new DisposableObserver<ProductDetails>() {
            @Override
            public void onNext(@NonNull ProductDetails result) {
                if (productDetailsMutableLiveData !=null)
                    productDetailsMutableLiveData.postValue(result);
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


    //////////////// add to fav
    @SuppressLint("CheckResult")
    private Observable<AddToFavModel> getObservableToFavObservable() {
        Observable<AddToFavModel> addToFavObservable = serverGateway.addToFave(userid,itemid);
        addToFavObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return addToFavObservable;
    }

    private DisposableObserver<AddToFavModel> getObserverAddFav() {
        return new DisposableObserver<AddToFavModel>() {
            @Override
            public void onNext(@NonNull AddToFavModel result) {
                addToFavMutableLiveData.postValue(result);
            }
            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("Errors", "Error" + e);
                e.printStackTrace();
                throwablefav.postValue(e);
            }
            @Override
            public void onComplete() {
            }
        };
    }


    ///////////// delete fav
    @SuppressLint("CheckResult")
    private Observable<AddToFavModel> getObservableToDeleteFav(int user,int product) {
        Observable<AddToFavModel> addToFavObservable = serverGateway.DeleteFav(user,product);
        addToFavObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return addToFavObservable;
    }
    private DisposableObserver<AddToFavModel> getObserverDeletFav() {
        return new DisposableObserver<AddToFavModel>() {
            @Override
            public void onNext(@NonNull AddToFavModel result) {
                deleteToFavMutableLiveData.postValue(result);
            }
            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("Errors", "Error" + e);
                e.printStackTrace();
                throwablefav.postValue(e);
            }
            @Override
            public void onComplete() {
            }
        };
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }
}
