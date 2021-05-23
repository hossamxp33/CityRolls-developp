package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.userlocations

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.codesroots.osamaomar.cityrolls.R
import com.codesroots.osamaomar.cityrolls.entities.OrderModel
import com.codesroots.osamaomar.cityrolls.entities.UserLocations
import com.codesroots.osamaomar.cityrolls.helper.Locationclick
import com.codesroots.osamaomar.cityrolls.helper.PreferenceHelper
import com.codesroots.osamaomar.cityrolls.presentationn.getuserlocation.GetUserLocationActivity
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.mainfragment.MainViewModelFactory
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.payment.PaymentFragment
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.userlocations.adapter.LocationsAdapter

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView

import com.codesroots.osamaomar.cityrolls.entities.names.ORDER
import java.util.*


class UserLocationsFragment : Fragment(), Locationclick {

    private var mViewModel: UserLocationsViewModel? = null
    private var locations: RecyclerView? = null
    private var addLocation: ImageView? = null
    internal var orderModel: OrderModel? = null
    internal lateinit var locationsAdapter: LocationsAdapter
    internal lateinit var notfound: TextView
    internal var timer = Timer()
    var data : List<UserLocations.DataBean> ? =null

    private val viewModelFactory: MainViewModelFactory
        get() = MainViewModelFactory(this.activity!!.application)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.user_locations_fragment, container, false)

        locations = view.findViewById(R.id.oldplaces)
        addLocation = view.findViewById(R.id.addlocation)
        notfound = view.findViewById(R.id.notfond)
        if (arguments != null)
            orderModel = arguments!!.getSerializable(ORDER) as OrderModel?

        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserLocationsViewModel::class.java)
        mViewModel!!.retrieveUserLocations(PreferenceHelper.getUserId())
        mViewModel!!.locations.observe(this , Observer {
             data = it
            if (it.size > 0) {
                locationsAdapter = LocationsAdapter(context, it, this)
                locations!!.adapter = locationsAdapter
                notfound.visibility = View.GONE
                locationsAdapter.notifyDataSetChanged()
            } else
                notfound.visibility = View.VISIBLE
        })

        mViewModel!!.error.observe(this , Observer { throwable -> Toast.makeText(activity, getText(R.string.error_in_data), Toast.LENGTH_SHORT).show() }
        )

        addLocation!!.setOnClickListener { v ->
            val intent = Intent(activity, GetUserLocationActivity::class.java)
            startActivityForResult(intent, 115)
        }
//        timer.scheduleAtFixedRate(
//                object : TimerTask() {
//                    @SuppressLint("CheckResult")
//                    override fun run() {
//
//                        if (data != null) {
//                            mViewModel!!.retrieveUserLocations(PreferenceHelper.getUserId())
//                        }
//
//                    }
//                },
//                0, 5000
//        )
        return view
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)
        mViewModel!!.retrieveUserLocations(PreferenceHelper.getUserId())
        locationsAdapter.notifyDataSetChanged()

    }

    override fun onlocationchoicw(location: UserLocations.DataBean) {
        if (orderModel != null) {
            orderModel!!.billing_id = location.billing_id
            val fragment = PaymentFragment()
            val bundle = Bundle()
            bundle.putSerializable(ORDER, orderModel)
            fragment.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.mainfram, fragment).addToBackStack(null).commit()
        }
    }
    override fun onResume() {
        super.onResume()
        mViewModel!!.retrieveUserLocations(PreferenceHelper.getUserId())
    }
}
