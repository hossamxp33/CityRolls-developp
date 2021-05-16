package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.codesroots.osamaomar.cityrolls.R
import com.codesroots.osamaomar.cityrolls.entities.Category
import com.codesroots.osamaomar.cityrolls.entities.Items
import com.codesroots.osamaomar.cityrolls.entities.names
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.detailsfragment.ItemDetails
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.mainactivity.MainActivity
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.productdetailsfragment.ProductDetailsFragment
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.subcategryfragment.SubcategryFragment

@BindingAdapter("app:imageResource")
fun setImageResource(imageView: ImageView, resource: String?) {
    Glide.with(imageView.context).load(resource).into(imageView)
}

  class ClickHandler {



    fun SwitchToItemDetails(context: Context,  data: Items){
        val fragment: Fragment = ProductDetailsFragment()
        val bundle = Bundle()
        bundle.putInt(names.PRODUCT_ID, data.id)
        fragment.arguments = bundle
        (context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.mainfram, fragment)
                .addToBackStack(null).commit()
//        val homeIntent = Intent(context, ItemDetails()::class.java)
//     //   homeIntent.putExtra("position",int)
//        homeIntent.putExtra("myobj", data)
//        (context as MainActivity).startActivity(homeIntent)

    }

      fun SwitchToPackages( context: Context,comid :String) {

          val homeIntent = Intent(context, ItemDetails()::class.java)
          val bundle = Bundle()
          homeIntent.putExtra("packageId" , comid)
          (context as MainActivity).startActivity(homeIntent)

      }

      fun SwitchToSubcats( context: Context, data: Category) {

          val bundle = Bundle()
          //  bundle.putParcelable("cliObj" ,clients[position] )
          val frag = SubcategryFragment()
          bundle.putParcelable("data" , data)
          frag.arguments = bundle
          (context as MainActivity).supportFragmentManager.beginTransaction()
                  .replace(R.id.main_frame_a, frag).addToBackStack(null).commit()

      }


}