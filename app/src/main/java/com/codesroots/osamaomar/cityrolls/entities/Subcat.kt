package com.codesroots.osamaomar.cityrolls.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Items(
    val category_id: Int,

    val id: Int,
    val modified: String,
    val name: String,
    val description: String,

    val price: Float,
    val photo: String,

     var total_rating: List<TotalRatingBean?>? = ArrayList()

)  : Parcelable {

}