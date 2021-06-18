package com.codesroots.osamaomar.cityrolls.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {

    public int getPaymenttype_id() {
        return paymenttype_id;
    }

    public void setPaymenttype_id(int paymenttype_id) {
        this.paymenttype_id = paymenttype_id;
    }

    int paymenttype_id;

    @SerializedName("user_id")
    int user_id;
    @SerializedName("billing_address_id")
    int billing_address_id;
    @SerializedName("address")
    String address;

    @SerializedName("user_lat")
    String user_lat;

    @SerializedName("platform_id")
    int platform_id;


    @SerializedName("area_id")
    int area_id;

    @SerializedName("user_long")
    String user_long;

    @SerializedName("typeorder")
    String type;

    @SerializedName("notes")
    String notes;

    @SerializedName("currency_id")
    int currency_id;

    @SerializedName("total")
    String price;

    @SerializedName("service")
    float service;

    public float getservice() {
        return service;
    }

    public void setservice(float user_id) {
        this.service = user_id;
    }


    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int user_id) {
        this.area_id = user_id;
    }
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public int getPlatform_id() {
        return platform_id;
    }

    public void setPlatform_id(int user_id) {
        this.platform_id = user_id;
    }


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    public int getBilling_id() {
        return billing_address_id;
    }

    public void setBilling_id(int billing_id) {
        this.billing_address_id = billing_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser_lat() {
        return user_lat;
    }

    public void setUser_lat(String user_lat) {
        this.user_lat = user_lat;
    }

    public String getUser_long() {
        return user_long;
    }

    public void setUser_long(String user_long) {
        this.user_long = user_long;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(int currency_id) {
        this.currency_id = currency_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<productSize> getOrderdetails() {
        return orderdetails;
    }

    public void  setOrderdetails(List<productSize> orderdetails) {
        this.orderdetails = orderdetails;
    }

    @SerializedName("orderdetails")
    List<productSize> orderdetails;

    public static  class  productSize implements Serializable
    {
        @SerializedName("item_id")
        int productsize_id;

        @SerializedName("itemamount")
        int amount = 1;

        @SerializedName("itemtotal")
        String total;


        @SerializedName("notice")
        String notice;


        @SerializedName("productcolor_id")
        int productcolor_id;

        public int getproductcolor_id() {
            return productcolor_id;
        }

        public void setproductcolor_id(int productcolor_id) {
            this.productcolor_id = productcolor_id;
        }

        public int getProductsize_id() {
            return productsize_id;
        }

        public void setProductsize_id(int productsize_id) {
            this.productsize_id = productsize_id;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public productSize(int productsize_id, int amount, String total, String notice) {
            this.productsize_id = productsize_id;
            this.amount = amount;
            this.total = total;
            this.notice = notice;
        }

        public productSize(int productsize_id) {
            this.productsize_id = productsize_id;
        }
    }


}
