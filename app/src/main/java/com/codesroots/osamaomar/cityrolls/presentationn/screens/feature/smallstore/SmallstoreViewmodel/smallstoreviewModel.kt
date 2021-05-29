package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.smallstore.SmallstoreViewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codesroots.osamaomar.cityrolls.dataLayer.Repo.DataRepo
import com.codesroots.osamaomar.cityrolls.entities.Payment
import com.codesroots.osamaomar.cityrolls.entities.StoreData
import io.reactivex.disposables.CompositeDisposable

class SmallstoreViewmodel : ViewModel() {

    var DateRepoCompnay: DataRepo = DataRepo()

    var mCompositeDisposable = CompositeDisposable()
    var SmallStoreResponseLD : MutableLiveData<StoreData>? = null
    var paymentResponseLD : MutableLiveData<Payment>? = null

    var errorLivedat: MutableLiveData<Throwable> = MutableLiveData()
    var loadingLivedat: MutableLiveData<Boolean> = MutableLiveData()


    init {

        SmallStoreResponseLD = MutableLiveData()

        errorLivedat = MutableLiveData()
        loadingLivedat  = MutableLiveData()
    }


    fun GetAllData(id:Int,type:Int){
        DateRepoCompnay.GetSearchData(id,type,SmallStoreResponseLD)
    }

    fun PaymentData(payment: Payment){
        DateRepoCompnay.GetPaymentkey(payment,paymentResponseLD)
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.dispose()
        mCompositeDisposable.clear()

    }
}


