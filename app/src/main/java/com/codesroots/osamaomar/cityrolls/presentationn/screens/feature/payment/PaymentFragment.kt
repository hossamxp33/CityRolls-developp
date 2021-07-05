package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.payment

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.braintreepayments.api.BraintreeFragment
import com.codesroots.osamaomar.cityrolls.R
import com.codesroots.osamaomar.cityrolls.entities.*
import com.codesroots.osamaomar.cityrolls.entities.names.ORDER
import com.codesroots.osamaomar.cityrolls.helper.AddorRemoveCallbacks
import com.codesroots.osamaomar.cityrolls.helper.PreferenceHelper
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.confirmorder.FinishOrderFragment
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.mainfragment.MainFragment
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.smallstore.SmallstoreViewmodel.SmallstoreViewmodel
import com.google.firebase.messaging.FirebaseMessaging
import com.paymob.acceptsdk.*
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import com.paypal.android.sdk.payments.PaymentConfirmation
import kotlinx.android.synthetic.main.activity_paypal.*
import org.json.JSONException
import java.util.*
import kotlin.collections.ArrayList

class PaymentFragment : Fragment() {
    internal lateinit var paypal: ImageView
    lateinit var viewModel: SmallstoreViewmodel

    internal var orderModel: OrderModel? = null
    internal lateinit var payment: Payment
    internal lateinit var makePaymentOrderIntegration: MakePaymentOrderIntegration

    lateinit    internal var cash: TextView
    lateinit    internal var auth: String
    internal  var Total: Float = 0.toFloat()
    internal var integration_id: Int? = null
    lateinit   internal var paymentViewModel: PaymentViewModel
    internal var smallstoreViewmodel: SmallstoreViewmodel? = null
    private val mBraintreeFragment: BraintreeFragment? = null
    private val ACCEPT_PAYMENT_REQUEST = 1000
    internal var paymentKey = ""
    private val viewModelFactory: PaymentViewModelFactory
        get() = PaymentViewModelFactory(this.activity!!.application)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_paypal, container, false)
        paypal = view.findViewById(R.id.paypal)
        cash = view.findViewById(R.id.cash)
        val intent = Intent(activity, PayPalService::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration)
        activity!!.startService(intent)
        orderModel = arguments!!.getSerializable(ORDER) as OrderModel?
        payment = Payment()
        makePaymentOrderIntegration = MakePaymentOrderIntegration()
        auth = PreferenceHelper.getPaymentToken()
        integration_id = 1
        assert(orderModel != null)
        orderModel!!.user_id = PreferenceHelper.getUserId()
        for ( i in  orderModel!!.orderdetails)
        Total += (i.total).toFloat();

        Total += PreferenceHelper.getMIM_CHIPPING()
        paypal.setOnClickListener { v -> processCardpayment() }
        cash.setOnClickListener { v -> showBankDialog() }
        paymentViewModel = ViewModelProviders.of(this, viewModelFactory).get(PaymentViewModel::class.java)

        viewModel = ViewModelProviders.of(this).get(SmallstoreViewmodel::class.java)


        paymentViewModel.myOrdersMutableLiveData.observe(this, Observer{ aBoolean ->

            if (aBoolean!!) {
                PreferenceHelper.clearCart()
                FirebaseMessaging.getInstance().subscribeToTopic("admin")
                        .addOnCompleteListener {
                            //                                String msg = getString(R.string.msg_subscribed);
                            //                                if (!task.isSuccessful()) {
                            //                                    msg = getString(R.string.msg_subscribe_failed);
                            //                                }
                            //                                Log.d("TAG", msg);
                            //                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        }

                val fm = activity!!.supportFragmentManager
                for (i1 in 0 until fm.backStackEntryCount) {
                    val fragment = fm.findFragmentById(R.id.mainfram)
                    if (fragment !is MainFragment)
                        fm.popBackStack()
                }
                (activity as AddorRemoveCallbacks).onClearCart()
                activity!!.supportFragmentManager.beginTransaction().replace(R.id.mainfram, FinishOrderFragment()).addToBackStack(null).commit()
            }
        })
        return view
    }


    private fun processCashpayment() {

        orderModel!!.setservice(PreferenceHelper.getCurrencyValue())
        orderModel!!.area_id = PreferenceHelper.getCOUNTRY_ID()
        orderModel!!.type = "1"
        orderModel!!.price = Total.toString()
        sendRequest()
    }

    private fun processCardpayment() {
        payment.amount_cents = (Total  * 100).toString()
        payment.auth_token = auth
        payment.currency = "EGP"
        payment.integration_id = 408709
        var billingModel = BillingData()
        payment.billing_data = (billingModel)
        billingModel.email = PreferenceHelper.getEmail()
        billingModel.first_name = PreferenceHelper.getUserName()
        billingModel.last_name = PreferenceHelper.getUserName()
        billingModel.phone_number = PreferenceHelper.getPhoneNum()

        billingModel.apartment = "NA"
        billingModel.apartment = "NA"
        billingModel.building = "NA"
        billingModel.city = "NA"
        payment.billing_data!!.country = "Na"
        payment.billing_data!!.floor = "Na"
        payment.billing_data!!.postal_code = "Na"
        payment.billing_data!!.shipping_method = "Na"
        payment.billing_data!!.street = "Na"
        payment.billing_data!!.state = "Na"
        makePaymentOrderIntegration()



    }
    private fun makePaymentOrderIntegration() {

        var set = HashMap<String, Any>()

        set.put("currency" , "EGP")
        set.put("amount_cents" ,Total.toString())

        set.put("delivery_needed" , "false")
        set.put("auth_token" , auth)
        set.put("items" , ArrayList<String>())

        paymentViewModel.MakePaymentOrderIntegration(set)
        paymentViewModel.makePaymentOrderIntegration.observe(this, Observer { aBoolean ->
            sendpaymentRequest(aBoolean.id)

        })

    }
    private fun processpayment() {
        val pay_intent = Intent(activity, PayActivity::class.java)
        putNormalExtras(pay_intent)

        // this key is used to save the card by deafult.
        pay_intent.putExtra(PayActivityIntentKeys.SAVE_CARD_DEFAULT, false)

        // this key is used to display the savecard checkbox.
        pay_intent.putExtra(PayActivityIntentKeys.SHOW_SAVE_CARD, true)

        //this key is used to set the theme color(Actionbar, statusBar, button).
        pay_intent.putExtra(PayActivityIntentKeys.THEME_COLOR, resources.getColor(R.color.black))

        // this key is to wether display the Actionbar or not.
        pay_intent.putExtra("ActionBar", true)

        // this key is used to define the language. takes for ex ("ar", "en") as inputs.
        pay_intent.putExtra("language", "ar")

        startActivityForResult(pay_intent, ACCEPT_PAYMENT_REQUEST)
        //        Intent secure_intent = new Intent(this, ThreeDSecureWebViewActivty.class);
        //        secure_intent.putExtra("ActionBar",true);

        //        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(Total * PreferenceHelper.getDoller())), "USD", "oman", PayPalPayment.PAYMENT_INTENT_SALE);
        //        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        //        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        //        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        //        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    private fun putNormalExtras(intent: Intent) {
        intent.putExtra(PayActivityIntentKeys.PAYMENT_KEY, paymentKey)
        intent.putExtra(PayActivityIntentKeys.THREE_D_SECURE_ACTIVITY_TITLE, "Verification")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        val extras = data!!.extras

        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val payPalConfiguration = data.getParcelableExtra<PaymentConfirmation>(PaymentActivity.EXTRA_RESULT_CONFIRMATION)
                if (payPalConfiguration != null) {
                    try {
                        val jsonObject = payPalConfiguration.toJSONObject()
                        val state = jsonObject.getJSONObject("response").getString("state")
                        Toast.makeText(activity, state, Toast.LENGTH_SHORT).show()
                        orderModel!!.type = getString(R.string.paypal)
                        orderModel!!.price = Total.toString()
                        sendRequest()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }
            } else if (requestCode == Activity.RESULT_CANCELED)
                Toast.makeText(activity, "Cancel", Toast.LENGTH_SHORT).show()

        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(activity, "Invalid", Toast.LENGTH_SHORT).show()


        if (requestCode == ACCEPT_PAYMENT_REQUEST) {

            if (resultCode == IntentConstants.USER_CANCELED) {
                // User canceled and did no payment request was fired
                ToastMaker.displayShortToast(activity!!, "User canceled!!")
            } else if (resultCode == IntentConstants.MISSING_ARGUMENT) {
                // You forgot to pass an important key-value pair in the intent's extras
                ToastMaker.displayShortToast(activity!!, "Missing Argument == " + extras!!.getString(IntentConstants.MISSING_ARGUMENT_VALUE)!!)
            } else if (resultCode == IntentConstants.TRANSACTION_ERROR) {
                // An error occurred while handling an API's response
                ToastMaker.displayShortToast(activity!!, "Reason == " + extras!!.getString(IntentConstants.TRANSACTION_ERROR_REASON)!!)
            } else if (resultCode == IntentConstants.TRANSACTION_REJECTED) {
                // User attempted to pay but their transaction was rejected

                // Use the static keys declared in PayResponseKeys to extract the fields you want
                ToastMaker.displayShortToast(activity!!, extras!!.getString(PayResponseKeys.DATA_MESSAGE))
            } else if (resultCode == IntentConstants.TRANSACTION_REJECTED_PARSING_ISSUE) {
                // User attempted to pay but their transaction was rejected. An error occured while reading the returned JSON
                ToastMaker.displayShortToast(activity!!, extras!!.getString(IntentConstants.RAW_PAY_RESPONSE))
            } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL) {
                // User finished their payment successfully
                ToastMaker.displayShortToast(activity!!, "Token == " + extras!!.getString(SaveCardResponseKeys.ID)!!)
                orderModel!!.paymenttype_id = 4
                 sendRequest()
                // Use the static keys declared in PayResponseKeys to extract the fields you want
               // ToastMaker.displayShortToast(activity!!, extras!!.getString(PayResponseKeys.DATA_MESSAGE))
            } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_PARSING_ISSUE) {
                // User finished their payment successfully. An error occured while reading the returned JSON.
                ToastMaker.displayShortToast(activity!!, "TRANSACTION_SUCCESSFUL - Parsing Issue")

                // ToastMaker.displayShortToast(this, extras.getString(IntentConstants.RAW_PAY_RESPONSE));
            } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_CARD_SAVED) {
                // User finished their payment successfully and card was saved.
                orderModel!!.paymenttype_id = 4
                sendRequest()
                // Use the static keys declared in PayResponseKeys to extract the fields you want
                // Use the static keys declared in SaveCardResponseKeys to extract the fields you want
                ToastMaker.displayShortToast(activity!!, "Token == " + extras!!.getString(SaveCardResponseKeys.TOKEN)!!)
            } else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION) {
                ToastMaker.displayShortToast(activity!!, "User canceled 3-d scure verification!!")

                // Note that a payment process was attempted. You can extract the original returned values
                // Use the static keys declared in PayResponseKeys to extract the fields you want
                ToastMaker.displayShortToast(activity!!, extras!!.getString(PayResponseKeys.PENDING))
            } else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION_PARSING_ISSUE) {
                ToastMaker.displayShortToast(activity!!, "User canceled 3-d scure verification - Parsing Issue!!")

                // Note that a payment process was attempted.
                // User finished their payment successfully. An error occured while reading the returned JSON.
                ToastMaker.displayShortToast(activity!!, extras!!.getString(IntentConstants.RAW_PAY_RESPONSE))
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun sendRequest() {
        orderModel!!.notes = order_details.text.toString()
        orderModel!!.platform_id = 1
        paymentViewModel.addOrder(orderModel)
    }

    private fun sendpaymentRequest(id: Long?) {
        payment.order_id = id!!.toString()
        viewModel.PaymentData(payment);
        viewModel.paymentResponseLD!!.observe(this, Observer { aBoolean ->
paymentKey = aBoolean.token!!;

            processpayment()
        })
    }

    private fun showBankDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        val viewGroup = view!!.findViewById<ViewGroup>(android.R.id.content)
        //then we will inflate the custom alert dialog xml that we created
        val dialogView = LayoutInflater.from(context).inflate(R.layout.bank_dialog, viewGroup, false)
        //Now we need an AlertDialog.Builder object
        val button = dialogView.findViewById<Button>(R.id.buttonOk)
        val buttoncancel = dialogView.findViewById<Button>(R.id.buttoncancel)
        val builder = AlertDialog.Builder(context)
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView)
        //finally creating the alert dialog and displaying it
        val alertDialog = builder.create()
        alertDialog.show()
        button.setOnClickListener { v ->
            processCashpayment()
            alertDialog.dismiss()
        }
        buttoncancel.setOnClickListener { v -> alertDialog.dismiss() }
    }

    companion object {

        private val PAYPAL_REQUEST_CODE = 7171
        private val configuration = PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
                .clientId(Config.PAYPAL_CLIENT_ID)
    }
}




