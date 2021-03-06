package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.mainfragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.MenuAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.codesroots.osamaomar.cityrolls.R
import com.codesroots.osamaomar.cityrolls.entities.*
import com.codesroots.osamaomar.cityrolls.helper.AddorRemoveCallbacks
import com.codesroots.osamaomar.cityrolls.helper.PreferenceHelper
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.mainfragment.MainFragmentViewModel
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.mainfragment.adapters.CatAdapter
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.mainfragment.adapters.OffersProductsAdapter
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.mainfragment.adapters.SliderPagerAdapter
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.subcategryfragment.adapters.SubCatsAdapter
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.smallstore.SmallstoreViewmodel.SmallstoreViewmodel


import com.viewpagerindicator.CirclePageIndicator
import kotlinx.android.synthetic.main.cart_fragment.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*
import kotlinx.android.synthetic.main.main_fragment.view.subcategry_Rec
import kotlinx.android.synthetic.main.offer_item_adapter.view.*
import kotlinx.android.synthetic.main.subcategry_fragment.view.*

import java.util.*
import kotlin.collections.ArrayList

class homeFragment : Fragment() {

    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var     TokenviewModel: SmallstoreViewmodel

    var  data : Category? = null
    var pagers: ViewPager? = null
    var indicator: CirclePageIndicator? = null
    lateinit var MenuAdapter: SliderPagerAdapter
    var adapterr: CatAdapter? = null
    var datArray = ArrayList<Items>()
    var MainData : MainView? = null
    internal  var tokenmodel:Token ? = null

    var adapter: SubCatsAdapter? = null
    var NUM_PAGES = 0
    var currentPage = 0
    var index : Int =  0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  {

        var view = inflater.inflate(R.layout.main_fragment, container, false)
        val api_key = "ZXlKMGVYQWlPaUpLVjFRaUxDSmhiR2NpT2lKSVV6VXhNaUo5LmV5SmpiR0Z6Y3lJNklrMWxjbU5vWVc1MElpd2ljSEp2Wm1sc1pWOXdheUk2TVRBeU5EYzFMQ0p1WVcxbElqb2lNVFl5TWpJMU1EUTFNUzR3TmpnMU1ESWlmUS5sYnh4WkNla1BJX3NhbExpYXprUXBWUHlPTnc3OTRwRnExT3I0YkVXRmlvTEQtTHA5ZDctN2RKNmllT2hEYjZUaXViWVdnOFpEWDl3cmFDYVpWNG5SQQ=="
        indicator = view.indicator

        viewModel = ViewModelProviders.of(this, getViewModelFactory()).get(MainFragmentViewModel::class.java)

        viewModel.getData()

        TokenviewModel = ViewModelProviders.of(this).get(SmallstoreViewmodel::class.java)

        TokenviewModel.GetToken(api_key);
          tokenmodel = Token()

        pagers = view!!.slider

        TokenviewModel.TokenResponseLD?.observe(this,androidx.lifecycle.Observer {

            PreferenceHelper.setPaymentToken(it.token)
            Log.i("tokenInfo",PreferenceHelper.getPaymentToken())
            PreferenceHelper.setPaymentMerchatId(it.profile!!.id.toString())
        })
            viewModel.mainViewMutableLiveData?.observe(this, androidx.lifecycle.Observer {
                datArray.clear()

                adapterr = CatAdapter(context as FragmentActivity, it.items,viewModel)
                view.departments.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
                view.departments?.adapter = adapterr
                pagers!!.adapter = it?.let { it1 -> SliderPagerAdapter(activity!!, it1.sliders)  }
                it.sliders.let { it1 -> init(it1.size)}
                slider.setPadding(80, 0, 50, 0)
                slider.offscreenPageLimit = 3
                slider.pageMargin = 20
                slider.clipToPadding = false
                slider.clipChildren = false
                indicator!!.setViewPager(slider)

                stoploading()

                MainData = it

                datArray.addAll(it.items.get(index).items)

                adapter = SubCatsAdapter(context as FragmentActivity, datArray)
                view.subcategry_Rec.layoutManager = GridLayoutManager(activity, 2)
                view.subcategry_Rec?.adapter = adapter
            })

        Timer()
        SwitchingCategories()

        return view

    }


    fun SwitchingCategories(){

        viewModel.ItemIndex.observe(this,androidx.lifecycle.Observer {
            datArray.clear()
            datArray.addAll(MainData!!.items.get(it).items)
            adapter!!.notifyDataSetChanged()

        })
    }
    private fun getViewModelFactory(): MainViewModelFactory {
        return MainViewModelFactory(this.activity!!.application)
    }
    private fun init(size: Int) {
        val density = getResources().getDisplayMetrics().density
        indicator!!.setRadius(4 * density)

        NUM_PAGES = size
        val handler = Handler()
        val Update:Runnable =Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            pagers?.setCurrentItem(currentPage++, true)
        }

        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 4000, 10000)
        indicator!!.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                currentPage = position
            }

            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(pos: Int) {}
        })
    }
    override fun onResume() {
        super.onResume()
        shimmer_view_container1.startShimmerAnimation()
        shimmer_view_container3.startShimmerAnimation()

    }

    override fun onPause() {
        shimmer_view_container1.startShimmerAnimation()
        shimmer_view_container3.stopShimmerAnimation()

        super.onPause()
    }
    fun stoploading() {

        shimmer_view_container1?.stopShimmerAnimation()
        shimmer_view_container3.startShimmerAnimation()

        shimmer_view_container1?.setVisibility(View.GONE)
        shimmer_view_container3?.setVisibility(View.GONE)

    }
}

