package com.codesroots.osamaomar.cityrolls.entities

import com.google.gson.annotations.SerializedName

data class MakePaymentOrderIntegration (


        @SerializedName("auth_token")
        var authToken: String? = null,
        var id: Long? = null,

        @SerializedName("delivery_needed")
        var deliveryNeeded: String? = null,

        @SerializedName("amount_cents")
        var amountCents: String? = null,
        var currency: String? = null,


        @SerializedName("merchant_order_id")
var merchantOrderID: Long? = null,
        var items: ArrayList<ItemPayment>? = null,
        val shippingData: ShippingData? = null,
        val shippingDetails: ShippingDetails? = null
)

data class ItemPayment (
        val name: String? = null,
        var amountCents: String? = null,
        val description: String? = null,
        val quantity: String? = null
)

data class ShippingData (
        val apartment: String? = null,
        val email: String? = null,
        val floor: String? = null,
        val firstName: String? = null,
        val street: String? = null,
        val building: String? = null,
        val phoneNumber: String? = null,
        val postalCode: String? = null,
        val extraDescription: String? = null,
        val city: String? = null,
        val country: String? = null,
        val lastName: String? = null,
        val state: String? = null
)

data class ShippingDetails (
        val notes: String? = null,
        val numberOfPackages: Long? = null,
        val weight: Long? = null,
        val weightUnit: String? = null,
        val length: Long? = null,
        val width: Long? = null,
        val height: Long? = null,
        val contents: String? = null
)
