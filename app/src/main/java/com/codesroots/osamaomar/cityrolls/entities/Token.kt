package com.codesroots.osamaomar.cityrolls.entities

import android.provider.ContactsContract

data class Token(
        var profile:Profile ? = null,
        var token: String ? = null,
var api_key:String? = null
)
data class Profile(
        val acq_partner: Any ? = null,
        val active: Boolean ? = null,
        val address: Any ? = null,
        val allow_encryption_bypass: Boolean ? = null,
        val allow_terminal_order_id: Boolean ? = null,
        val awb_banner: Any,
        val bank_deactivation_reason: Any,
        val bank_digital_rejection_reason: Any,
        val bank_merchant_digital_status: Int,
        val bank_merchant_status: Int,
        val bank_received_documents: Boolean,
        val bank_rejection_reason: Any,
        val bank_related: Any,
        val bank_staffs: BankStaffs,
        val city: String,
        val commercial_registration: Any,
        val commercial_registration_area: Any,
        val company_emails: List<String>,
        val company_name: String,
        val country: String,
        val created_at: String,
        val custom_export_columns: List<Any>,
        val day_end_time: Any,
        val day_start_time: String,
        val deactivated_by_bank: Boolean,
        val delivery_status_callback: String,
        val delivery_update_endpoint: Any,
        val distributor_branch_code: Any,
        val distributor_code: Any,
        val email_banner: Any,
        val email_notification: Boolean,
        val failed_attempts: Int,
        val filled_business_data: Boolean,
        val id: Int,
        val identification_number: Any,
        val is_mobadra: Boolean,
        val is_temp_password: Boolean,
        val latitude: Any,
        val logo_url: Any,
        val longitude: Any,
        val merchant_external_link: Any,
        val national_id: Any,
        val order_retrieval_endpoint: Any,
        val password: Any,
        val permissions: List<Any>,
        val phones: List<String>,
        val postal_code: String,
        val profile_phash: String,
        val profile_type: String,
        val random_iv: Any,
        val random_secret: Any,
        val sales_owner: String,
        val sector: String,
        val server_IP: List<Any>,
        val sms_sender_name: String,
        val state: String,
        val street: String,
        val super_agent: Any,
        val suspicious: Int,
        val user: UserX,
        val username: Any,
        val wallet_limit_profile: Any,
        val wallet_phone_number: Any,
        val withhold_transfers: Boolean,
        val withhold_transfers_notes: Any,
        val withhold_transfers_reason: Any
)
data class UserX(
        val date_joined: String,
        val email: String,
        val first_name: String,
        val groups: List<Any>,
        val id: Int,
        val is_active: Boolean,
        val is_staff: Boolean,
        val is_superuser: Boolean,
        val last_login: Any,
        val last_name: String,
        val user_permissions: List<Int>,
        val username: String
)