package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.detailsfragment

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.codesroots.osamaomar.cityrolls.R
import com.codesroots.osamaomar.cityrolls.databinding.ItemDetailsBinding
import com.codesroots.osamaomar.cityrolls.entities.Items
import com.codesroots.osamaomar.cityrolls.helper.AddorRemoveCallbacks
import com.codesroots.osamaomar.cityrolls.helper.Converter
import com.codesroots.osamaomar.cityrolls.helper.PreferenceHelper
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.ClickHandler
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.cartfragment.CartFragment
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.mainactivity.MainActivity
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.mainfragment.MainFragmentViewModel
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.mainfragment.MainViewModelFactory

public class ItemDetails  : AppCompatActivity() , AddorRemoveCallbacks {


    lateinit var viewModel: MainFragmentViewModel
    var Itemdata: Items? = null
    private var cart_count = 0

    var binding: ItemDetailsBinding? = null
    private fun getViewModelFactory(): MainViewModelFactory {
        return MainViewModelFactory(this.application)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.item_details)

        viewModel = ViewModelProviders.of(this, getViewModelFactory()).get(MainFragmentViewModel::class.java)

        val extras = intent.extras

        Itemdata = extras?.getParcelable<Items>("myobj")


        // binding!!.itemName.text = Itemdata!!.name

        binding!!.context = this
        binding!!.listener = ClickHandler()
        binding!!.viewmodel = viewModel
        binding!!.data = Itemdata

        binding!!.addtocart.setOnClickListener {
            if (PreferenceHelper.getUserId() > 0) {
                if (PreferenceHelper.retriveCartItemsValue() != null) {
                    if (!PreferenceHelper.retriveCartItemsValue().contains(Itemdata!!.id)) {
                        PreferenceHelper.addItemtoCart(Itemdata!!.id)
                        PreferenceHelper.addColorstoCart(Itemdata!!.id)
                        (this as AddorRemoveCallbacks).onAddProduct()
                        Toast.makeText(this, this.getText(R.string.addtocartsuccess), Toast.LENGTH_SHORT).show()
                    } else Toast.makeText(this, this.getText(R.string.aleady_exists), Toast.LENGTH_SHORT).show()
                } else {
                    PreferenceHelper.addItemtoCart(Itemdata!!.id)
                    PreferenceHelper.addColorstoCart(Itemdata!!.id)
                    (this as AddorRemoveCallbacks).onAddProduct()
                    Toast.makeText(this, this.getText(R.string.addtocartsuccess), Toast.LENGTH_SHORT).show()
                }
            } else Toast.makeText(this, this.getText(R.string.loginfirst), Toast.LENGTH_SHORT).show()
        }


//    fun SwitchingCategories(){
//
//        viewModel.ItemIndex.observe(this,androidx.lifecycle.Observer {
//
//            datArray.addAll(MainData!!.items.get(it).items)
//
//        })
//    }


    }

    override fun onAddProduct() {
        cart_count++
        invalidateOptionsMenu()
    }

    override fun onRemoveProduct() {
        cart_count--
        invalidateOptionsMenu()
    }

    override fun onClearCart() {
        PreferenceHelper.clearCart()
        cart_count = 0
        invalidateOptionsMenu()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        val menuItem = menu.findItem(R.id.action_cart)
        cart_count = PreferenceHelper.retriveCartItemsSize()
        menuItem.icon = Converter.convertLayoutToImage(this, cart_count, R.drawable.shoppcart)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                supportFragmentManager.beginTransaction().replace(R.id.mainfram, CartFragment()).addToBackStack(null).commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        return super.onPrepareOptionsMenu(menu)


    }

}