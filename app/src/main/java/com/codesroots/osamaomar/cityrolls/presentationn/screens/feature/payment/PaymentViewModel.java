package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.payment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codesroots.osamaomar.cityrolls.dataLayer.Repo.DataRepo;
import com.codesroots.osamaomar.cityrolls.domain.ServerGateway;
import com.codesroots.osamaomar.cityrolls.entities.MainView;
import com.codesroots.osamaomar.cityrolls.entities.MakePaymentOrderIntegration;
import com.codesroots.osamaomar.cityrolls.entities.OrderModel;
import com.codesroots.osamaomar.cityrolls.entities.Payment;

import java.util.HashMap;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentViewModel extends ViewModel {

    public MutableLiveData<Boolean> myOrdersMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> paymentMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Throwable> throwableMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<MakePaymentOrderIntegration> makePaymentOrderIntegration = new MutableLiveData<>();
    public  DataRepo DateRepoCompnay = new DataRepo();

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private ServerGateway serverGateway;

    public PaymentViewModel(ServerGateway serverGateway1) {
        serverGateway = serverGateway1;
    }

//    private void makeOrder(OrderModel orderModel){
//        mCompositeDisposable.add(
//                serverGateway.makeOrder(orderModel)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe( this::postDataResponse,
//                                this::postError));
//    }
//
//    private void postDataResponse(MyOrders productRates) {
//        myOrdersMutableLiveData.postValue(productRates);
//    }
//
//    private void postError(Throwable throwable) {
//        throwableMutableLiveData.postValue(throwable);
//    }
public void MakePaymentOrderIntegration(HashMap<String,Object> orderModel) {
    DateRepoCompnay.MakePaymentOrderIntegration(orderModel,makePaymentOrderIntegration);
}

    public void addOrder(OrderModel orderModel) {
        serverGateway.makeOrder(orderModel).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                try {
                    if (response != null)
                        myOrdersMutableLiveData.postValue(true);
                } catch (Exception e) {
                   // onError.accept(e.getCause());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (throwableMutableLiveData != null) {
                    throwableMutableLiveData.postValue(t);
                }
            }
        });
    }

//    public void payment(Payment payment) {
//        serverGateway.GetPaymentkey(payment).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
//                try {
//                    if (response != null)
//                        paymentMutableLiveData.postValue(true);
//                } catch (Exception e) {
//                    // onError.accept(e.getCause());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                if (throwableMutableLiveData != null) {
//                    throwableMutableLiveData.postValue(t);
//                }
//            }
//        });
//    }
}
