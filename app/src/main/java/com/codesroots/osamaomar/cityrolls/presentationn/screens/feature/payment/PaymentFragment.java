package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.payment;

import android.app.Activity;
import android.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.mainactivity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.api.BraintreeFragment;
import com.codesroots.osamaomar.cityrolls.R;
import com.codesroots.osamaomar.cityrolls.entities.OrderModel;
import com.codesroots.osamaomar.cityrolls.helper.AddorRemoveCallbacks;
import com.codesroots.osamaomar.cityrolls.helper.PreferenceHelper;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.confirmorder.FinishOrderFragment;
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.mainfragment.MainFragment;
import com.google.firebase.messaging.FirebaseMessaging;
import com.paymob.acceptsdk.IntentConstants;
import com.paymob.acceptsdk.PayActivity;
import com.paymob.acceptsdk.PayActivityIntentKeys;
import com.paymob.acceptsdk.PayResponseKeys;
import com.paymob.acceptsdk.SaveCardResponseKeys;
import com.paymob.acceptsdk.ThreeDSecureWebViewActivty;
import com.paymob.acceptsdk.ToastMaker;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.math.BigDecimal;

import static android.app.Activity.RESULT_OK;
import static com.codesroots.osamaomar.cityrolls.entities.names.ORDER;

public class PaymentFragment extends Fragment {

    private static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration configuration = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
            .clientId(Config.PAYPAL_CLIENT_ID);
    ImageView paypal;
    OrderModel orderModel;
    TextView cash ;
    float Total;
    PaymentViewModel paymentViewModel;
    private BraintreeFragment mBraintreeFragment;
    private int  ACCEPT_PAYMENT_REQUEST = 1000;
    final String paymentKey = "ZXlKMGVYQWlPaUpLVjFRaUxDSmhiR2NpT2lKSVV6VXhNaUo5LmV5SmpiR0Z6Y3lJNklrMWxjbU5vWVc1MElpd2ljSEp2Wm1sc1pWOXdheUk2TVRBeU5EYzFMQ0p1WVcxbElqb2lhVzVwZEdsaGJDSjkuX2lXRnExVEN1VThIaFg1SUNDOE5VZUF3bzFCd0EzSzdTN1FQdmc0R1ExNUp2VklKbUFxXy0zMXo4NXdiSWliNi1UdDVKU3A1R0w1N2pMLThNSzR1V3c=";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_paypal, container, false);
        paypal = view.findViewById(R.id.paypal);
        cash = view.findViewById(R.id.cash);
        Intent intent = new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        getActivity().startService(intent);
        orderModel = (OrderModel) getArguments().getSerializable(ORDER);
        assert orderModel != null;
        orderModel.setUser_id(PreferenceHelper.getUserId());
//        for (int i = 0; i < orderModel.getOrderdetails().size(); i++)
   //         Total += Float.valueOf(orderModel.getOrderdetails().get(i).getTotal());

         Total+=PreferenceHelper.getCurrencyValue();
        paypal.setOnClickListener(v -> processpayment());
        cash.setOnClickListener(v -> showBankDialog());
        paymentViewModel = ViewModelProviders.of(this, getViewModelFactory()).get(PaymentViewModel.class);
        paymentViewModel.myOrdersMutableLiveData.observe(this, aBoolean -> {

            if (aBoolean) {
                PreferenceHelper.clearCart();
                FirebaseMessaging.getInstance().subscribeToTopic("admin")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
//                                String msg = getString(R.string.msg_subscribed);
//                                if (!task.isSuccessful()) {
//                                    msg = getString(R.string.msg_subscribe_failed);
//                                }
//                                Log.d("TAG", msg);
//                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                            }
                        });

                FragmentManager fm = getActivity().getSupportFragmentManager();
                for (int i1 = 0; i1 < fm.getBackStackEntryCount(); ++i1) {
                    Fragment fragment = fm.findFragmentById(R.id.mainfram);
                    if (!(fragment instanceof MainFragment))
                        fm.popBackStack();
                }
                ((AddorRemoveCallbacks) getActivity()).onClearCart();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainfram, new FinishOrderFragment()).addToBackStack(null).commit();
            }
        });
        paymentViewModel.throwableMutableLiveData.observe(this, throwable -> Snackbar.make(view, throwable.getCause().toString(), Snackbar.LENGTH_SHORT).show());
        return view;
    }


    private void processCashpayment() {

        orderModel.setservice(PreferenceHelper.getCurrencyValue());
        orderModel.setArea_id(PreferenceHelper.getCOUNTRY_ID());
        orderModel.setType("1");
        orderModel.setPrice(String.valueOf(Total));
        sendRequest();
    }


    private void processpayment() {
        Intent pay_intent = new Intent(getActivity(), PayActivity.class);
        putNormalExtras(pay_intent);

        // this key is used to save the card by deafult.
        pay_intent.putExtra(PayActivityIntentKeys.SAVE_CARD_DEFAULT, false);

        // this key is used to display the savecard checkbox.
        pay_intent.putExtra(PayActivityIntentKeys.SHOW_SAVE_CARD, true);

        //this key is used to set the theme color(Actionbar, statusBar, button).
        pay_intent.putExtra(PayActivityIntentKeys.THEME_COLOR,getResources().getColor(R.color.black));

        // this key is to wether display the Actionbar or not.
        pay_intent.putExtra("ActionBar",true);

        // this key is used to define the language. takes for ex ("ar", "en") as inputs.
        pay_intent.putExtra("language","ar");

        startActivityForResult(pay_intent, ACCEPT_PAYMENT_REQUEST);
//        Intent secure_intent = new Intent(this, ThreeDSecureWebViewActivty.class);
//        secure_intent.putExtra("ActionBar",true);

//        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(Total * PreferenceHelper.getDoller())), "USD", "oman", PayPalPayment.PAYMENT_INTENT_SALE);
//        Intent intent = new Intent(getActivity(), PaymentActivity.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
//        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
//        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }
    private void putNormalExtras(Intent intent) {
        intent.putExtra(PayActivityIntentKeys.PAYMENT_KEY, paymentKey);
        intent.putExtra(PayActivityIntentKeys.THREE_D_SECURE_ACTIVITY_TITLE, "Verification");
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        Bundle extras = data.getExtras();

        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation payPalConfiguration = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (payPalConfiguration != null) {
                    try {
                        JSONObject jsonObject = payPalConfiguration.toJSONObject();
                        String state = jsonObject.getJSONObject("response").getString("state");
                        Toast.makeText(getActivity(), state, Toast.LENGTH_SHORT).show();
                        orderModel.setType(getString(R.string.paypal));
                        orderModel.setPrice(String.valueOf(Total));
                        sendRequest();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == Activity.RESULT_CANCELED)
                Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();

        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(getActivity(), "Invalid", Toast.LENGTH_SHORT).show();


        if (requestCode == ACCEPT_PAYMENT_REQUEST) {

            if (resultCode == IntentConstants.USER_CANCELED) {
                // User canceled and did no payment request was fired
                ToastMaker.displayShortToast(getActivity(), "User canceled!!");
            } else if (resultCode == IntentConstants.MISSING_ARGUMENT) {
                // You forgot to pass an important key-value pair in the intent's extras
                ToastMaker.displayShortToast(getActivity(), "Missing Argument == " + extras.getString(IntentConstants.MISSING_ARGUMENT_VALUE));
            } else if (resultCode == IntentConstants.TRANSACTION_ERROR) {
                // An error occurred while handling an API's response
                ToastMaker.displayShortToast(getActivity(), "Reason == " + extras.getString(IntentConstants.TRANSACTION_ERROR_REASON));
            } else if (resultCode == IntentConstants.TRANSACTION_REJECTED) {
                // User attempted to pay but their transaction was rejected

                // Use the static keys declared in PayResponseKeys to extract the fields you want
                ToastMaker.displayShortToast(getActivity(), extras.getString(PayResponseKeys.DATA_MESSAGE));
            } else if (resultCode == IntentConstants.TRANSACTION_REJECTED_PARSING_ISSUE) {
                // User attempted to pay but their transaction was rejected. An error occured while reading the returned JSON
                ToastMaker.displayShortToast(getActivity(), extras.getString(IntentConstants.RAW_PAY_RESPONSE));
            } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL) {
                // User finished their payment successfully

                // Use the static keys declared in PayResponseKeys to extract the fields you want
                ToastMaker.displayShortToast(getActivity(), extras.getString(PayResponseKeys.DATA_MESSAGE));
            } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_PARSING_ISSUE) {
                // User finished their payment successfully. An error occured while reading the returned JSON.
                ToastMaker.displayShortToast(getActivity(), "TRANSACTION_SUCCESSFUL - Parsing Issue");

                // ToastMaker.displayShortToast(this, extras.getString(IntentConstants.RAW_PAY_RESPONSE));
            } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_CARD_SAVED) {
                // User finished their payment successfully and card was saved.

                // Use the static keys declared in PayResponseKeys to extract the fields you want
                // Use the static keys declared in SaveCardResponseKeys to extract the fields you want
                ToastMaker.displayShortToast(getActivity(), "Token == " + extras.getString(SaveCardResponseKeys.TOKEN));
            } else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION) {
                ToastMaker.displayShortToast(getActivity(), "User canceled 3-d scure verification!!");

                // Note that a payment process was attempted. You can extract the original returned values
                // Use the static keys declared in PayResponseKeys to extract the fields you want
                ToastMaker.displayShortToast(getActivity(), extras.getString(PayResponseKeys.PENDING));
            } else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION_PARSING_ISSUE) {
                ToastMaker.displayShortToast(getActivity(), "User canceled 3-d scure verification - Parsing Issue!!");

                // Note that a payment process was attempted.
                // User finished their payment successfully. An error occured while reading the returned JSON.
                ToastMaker.displayShortToast(getActivity(), extras.getString(IntentConstants.RAW_PAY_RESPONSE));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void sendRequest() {
        orderModel.setPlatform_id(1);
        paymentViewModel.addOrder(orderModel);
    }

    private PaymentViewModelFactory getViewModelFactory() {
        return new PaymentViewModelFactory(this.getActivity().getApplication());
    }

    private void showBankDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = getView().findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.bank_dialog, viewGroup, false);
        //Now we need an AlertDialog.Builder object
        Button button = dialogView.findViewById(R.id.buttonOk);
        Button buttoncancel = dialogView.findViewById(R.id.buttoncancel);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        button.setOnClickListener(v -> {
            processCashpayment();
            alertDialog.dismiss();
        });
        buttoncancel.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
    }
}




