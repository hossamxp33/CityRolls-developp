package com.codesroots.osamaomar.cityrolls.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

class TotalRatingBean : Parcelable {
    /**
     * product_id : 31
     * stars : 5
     * count : 2
     */

    var product_id: Int = 0
    var stars: Float = 0.toFloat()
    var count: Int = 0


}