package com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.smallstore

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.codesroots.osamaomar.cityrolls.R
import com.codesroots.osamaomar.cityrolls.entities.names.CAT_ID
import com.codesroots.osamaomar.cityrolls.entities.names.CAT_TYPE
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.smallstore.SmallstoreViewmodel.SmallstoreViewmodel
import com.codesroots.osamaomar.cityrolls.presentationn.screens.feature.smallstore.adapter.smallStoreAdapter
import kotlinx.android.synthetic.main.small_store_fragment.view.*

class smallStoreFramgent : Fragment() {

    lateinit var viewModel: SmallstoreViewmodel
    lateinit var MainAdapter: smallStoreAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.small_store_fragment, container, false)
        val  catid = arguments!!.getInt(CAT_ID, 0)
        val type = arguments!!.getInt(CAT_TYPE, 0)

       viewModel = ViewModelProviders.of(this).get(SmallstoreViewmodel::class.java)
       viewModel.GetAllData(catid,type);

     viewModel.SmallStoreResponseLD?.observe(this, androidx.lifecycle.Observer {
           MainAdapter = activity?.let { it1 -> smallStoreAdapter(it1, it.data) }!!
            view.smallStore.layoutManager = LinearLayoutManager(context);
            view.smallStore.adapter = MainAdapter;


            MainAdapter.notifyDataSetChanged()

     })





        return view;

    }

}
