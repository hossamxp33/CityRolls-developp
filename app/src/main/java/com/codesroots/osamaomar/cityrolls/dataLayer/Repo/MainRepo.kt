package com.codesroots.osamaomar.cityrolls.dataLayer.Repo

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.codesroots.osamaomar.cityrolls.domain.ApiClient
import com.codesroots.osamaomar.cityrolls.domain.ServerGateway
import com.codesroots.osamaomar.cityrolls.entities.MakePaymentOrderIntegration
import com.codesroots.osamaomar.cityrolls.entities.Payment
import com.codesroots.osamaomar.cityrolls.entities.StoreData
import com.codesroots.osamaomar.cityrolls.entities.Token
import com.codesroots.osamaomar.cityrolls.helper.PreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.HashMap

class  DataRepo {
    @SuppressLint("CheckResult")

    fun GetSearchData(id:Int,type:Int,livedata: MutableLiveData<StoreData>?) {

        getServergetway().GetSmallStore(id,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { data -> data }
                .subscribe(
                        { books ->
                            livedata?.postValue(books)
                        },
                        { error ->

                        }
                )
    }
    @SuppressLint("CheckResult")

    fun MakePaymentOrderIntegration(makePaymentOrderIntegration : HashMap<String, Any>, livedata: MutableLiveData<MakePaymentOrderIntegration>?) {

        getServergetwayPayment().MakePaymentOrderIntegration(makePaymentOrderIntegration)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { data -> data }
                .subscribe(
                        { books ->
                            livedata?.postValue(books)
                        },
                        { error ->
                        }
                )
    }
    @SuppressLint("CheckResult")

    fun GetPaymentkey(Payment : Payment  ,livedata: MutableLiveData<Payment>?) {

        getServergetwayPayment().GetPaymentkey(Payment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { data -> data }
                .subscribe(
                        { books ->
                            livedata?.postValue(books)
                        },
                        { error ->
                        }
                )
    }
    @SuppressLint("CheckResult")

    fun GetToken( api_key: String  ,livedata: MutableLiveData<Token>?) {
var token = Token()
        token.api_key = api_key
        getServergetwayPayment().GetToken(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { data -> data }
                .subscribe(
                        { books ->
                            livedata?.postValue(books)
                        },
                        { error ->
                        }
                )
    }
    fun getServergetway () : ServerGateway
    {
        return ApiClient.getClient().create(ServerGateway::class.java)
    }
    fun getServergetwayPayment () : ServerGateway
    {
        return ApiClient.getClientPayment().create(ServerGateway::class.java)
    }

}
