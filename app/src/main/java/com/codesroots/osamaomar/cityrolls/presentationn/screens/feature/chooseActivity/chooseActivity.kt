package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.chooseActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.codesroots.osamaomar.cityrolls.R
import com.codesroots.osamaomar.cityrolls.domain.ApiClient
import com.codesroots.osamaomar.cityrolls.entities.User
import com.codesroots.osamaomar.cityrolls.helper.PreferenceHelper
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.home.mainactivity.MainActivity
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.register.RegisterViewModel
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.register.RegisterViewModelFactory
import kotlinx.android.synthetic.main.choose_type.*

class ChooseTypes : AppCompatActivity() {
    private var mViewModel: RegisterViewModel? = null
    var restaurant: TextView? = null
    var freeze: TextView? = null
    private val user = User()
    var gotoregister: TextView? = null

       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
           setContentView(R.layout.choose_type)


        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(RegisterViewModel::class.java)
        restaurant = username
        freeze = password

           restaurant!!.setOnClickListener(View.OnClickListener { v: View? ->
               PreferenceHelper.setURL_Base("https://wokhouse.codesroots.com/api/")
               val mainIntent = Intent(this, MainActivity::class.java)
               startActivity(mainIntent)
               finish()
           })
           freeze!!.setOnClickListener(View.OnClickListener { v: View? ->
               PreferenceHelper.setURL_Base("https://system.city-rolls.com/api/")
               val mainIntent = Intent(this, MainActivity::class.java)
               startActivity(mainIntent)
               finish()
           })



    }

    private val viewModelFactory: ViewModelProvider.Factory
        private get() = RegisterViewModelFactory(this!!.application)
}
