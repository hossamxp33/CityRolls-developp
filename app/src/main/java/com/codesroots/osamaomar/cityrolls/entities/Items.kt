package com.codesroots.osamaomar.cityrolls.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Items(
        val category_id: Int,

        val id: Int,
        val modified: String?=null,
        val name: String,
        val description: String,

        val price: Float,
        val photo: String,
        val favourites: List<FavouritesBean?>? = null,
var item_photo:List<ItemPhoto>? = null,
        var total_rating: List<TotalRatingBean?>? = null

)  : Parcelable {

}
@Parcelize
class FavouritesBean(
        var product_id: Int = 0,
        var id: Int = 0,
        var user_id: Int = 0
)
 : Parcelable {

}
@Parcelize
data class ItemPhoto( var id: Int = 0,
                         var item_id: Int = 0,
                         var photo: String? = null)  : Parcelable{
 /**
  * id : 1
  * product_id : 30
  * photo : http://shopgate.codesroots.com/library/attachment/pd1.jpg
  */


}