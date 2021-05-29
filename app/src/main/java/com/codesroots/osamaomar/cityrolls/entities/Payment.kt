package com.codesroots.osamaomar.cityrolls.entities

data class Payment(
    var amount_cents: String?=null,
    var auth_token: String?=null,
    var billing_data: BillingData?=null,
    var currency: String?=null, // EGP
    var integration_id: Int?=null,
    var lock_order_when_paid: String?=null,
    var order_id: String?=null,
    var token:String?=null
)
data class BillingData(
        var apartment: String?=null,
        var building: String?=null,
        var city: String?=null,
        var country: String?=null,
        var email: String?=null,
        var first_name: String?=null,
        var floor: String?=null,
        var last_name: String?=null,
        var phone_number: String?=null,
        var postal_code: String?=null,
        var shipping_method: String?=null,
        var state: String?=null,
        var street: String?=null
)