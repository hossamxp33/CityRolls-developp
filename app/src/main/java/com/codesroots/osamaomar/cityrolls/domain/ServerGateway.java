package com.codesroots.osamaomar.cityrolls.domain;


import com.codesroots.osamaomar.cityrolls.entities.AddLocation;
import com.codesroots.osamaomar.cityrolls.entities.Addmessage;
import com.codesroots.osamaomar.cityrolls.entities.ChatList;
import com.codesroots.osamaomar.cityrolls.entities.Countries;
import com.codesroots.osamaomar.cityrolls.entities.Currency;
import com.codesroots.osamaomar.cityrolls.entities.DefaultAdd;
import com.codesroots.osamaomar.cityrolls.entities.AddToFavModel;
import com.codesroots.osamaomar.cityrolls.entities.CartItems;
import com.codesroots.osamaomar.cityrolls.entities.Details;
import com.codesroots.osamaomar.cityrolls.entities.Favoriets;
import com.codesroots.osamaomar.cityrolls.entities.LoginResponse;
import com.codesroots.osamaomar.cityrolls.entities.MainView;
import com.codesroots.osamaomar.cityrolls.entities.MakePaymentOrderIntegration;
import com.codesroots.osamaomar.cityrolls.entities.MyOrders;
import com.codesroots.osamaomar.cityrolls.entities.OrderModel;
import com.codesroots.osamaomar.cityrolls.entities.Payment;
import com.codesroots.osamaomar.cityrolls.entities.ProductDetails;
import com.codesroots.osamaomar.cityrolls.entities.ProductRate;
import com.codesroots.osamaomar.cityrolls.entities.Products;
import com.codesroots.osamaomar.cityrolls.entities.Register;
import com.codesroots.osamaomar.cityrolls.entities.Sidemenu;
import com.codesroots.osamaomar.cityrolls.entities.StoreData;
import com.codesroots.osamaomar.cityrolls.entities.StoreSetting;
import com.codesroots.osamaomar.cityrolls.entities.SubCategriesWithProducts;
import com.codesroots.osamaomar.cityrolls.entities.Token;
import com.codesroots.osamaomar.cityrolls.entities.UserLocations;
import com.codesroots.osamaomar.cityrolls.entities.ViewLocation;
import com.codesroots.osamaomar.cityrolls.entities.offers;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerGateway {

    @GET("items/homepage.json")
    Observable<MainView> getMainViewData();

    @GET("Categories/allcategories.json")
    Observable<Sidemenu> getSideMenuData();

    @GET("items/view/{item_id}/{user_id}.json")
    Observable<ProductDetails> getProductDetails(

            @Path("item_id") int product_id,
            @Path("user_id") int user_id
    );


    @GET("Subcats/getsubcats/{cat_id}/{user_id}.json")
    Observable<SubCategriesWithProducts> getSubCatswithProducts(
            @Path("cat_id") int cat_id,
            @Path("user_id") int user_id
    );
    ////////////////// Address Locations Page  ////////////////////
    @GET("BillingAddress/index/{userid}.json")
    Observable<UserLocations> retrieveUserLocations(
            @Path("userid") int userid
    );

    @FormUrlEncoded
    @POST("BillingAddress/add.json")
    Observable<AddLocation> addBillingAddress(
            @Field("customer_id") int user_id,
         //   @Field("first_name") String first_name,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("address_details") String address_details,
       //     @Field("town_city") String town_city,
            @Field("notes") String notes,
            @Field("latitude") Double lat,
            @Field("longitude") Double longitude

    );


    @FormUrlEncoded
    @POST("BillingAddress/edit/{locationid}.json")
    Observable<AddLocation> editBillingAddress(
            @Path("locationid") int locationid,
          //  @Field("first_name") String first_name,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("state_country") String state_country,
       //     @Field("town_city") String town_city,
            @Field("notes") String notes,
            @Field("latitude") Double lat,
            @Field("longitude") Double longitude
    );

    @GET("BillingAddress/view/{billingid}.json")
    Observable<ViewLocation> viewLocation(
            @Path("billingid") int billingid
    );
    //////////////////////////////////////////////////////////////////////////////

    /////////////// Get Product By Cat ID
    @GET("products/getproductsbycatid/{type}/{subcat_id}/{user_id}/1.json")
    Observable<Products> getProducts(
            @Path("type") int type,
            @Path("subcat_id") int id,
            @Path("user_id") int user_id,
            @Path("user_id") int pager
    );


    @FormUrlEncoded
    @POST("Favourites/addfavourite.json")
        Observable<AddToFavModel> addToFave(
            @Field("user_id") int user_id,
            @Field("item_id") int product_id
    );

    @FormUrlEncoded
    @POST("Favourites/addfavourite.json")
    Observable<AddToFavModel> DeleteFav(
            @Field("user_id") int user_id,
            @Field("item_id") int product_id
    );


    @GET("Favourites/getproductsfav/{userid}.json")
    Observable<Favoriets> getFavProducts(
            @Path("userid") int userid
    );


    @FormUrlEncoded
    @POST("Items/viewproduct.json")
    Observable<CartItems> getCartProducts(
            @Field("arrayofid[]") ArrayList<Integer> ids
    );


    @GET("orders/getuserorder.json")
    Observable<MyOrders> retrieveUserOrders(

    );


    @GET("Offers/getoffers.json")
    Observable<offers> retrieveOffers();

    @GET("Products/ratedetails/{product_id}.json")
    Observable<ProductRate> getProductRates(
            @Path("product_id") int product_id
    );

    @FormUrlEncoded
    @POST("Productrates/addrate.json")
    Observable<DefaultAdd> addProductRate(
            @Field("user_id") int user_id,
            @Field("item_id") int product_id,
            @Field("rate") float rate,
            @Field("comment") String comment
    );
    ////////////// make order
    @POST("Orders/addorder.json")
    @Headers({"Accept: Application/json","cache-control: no-cache"})
    Call<ResponseBody> makeOrder(
            @Body OrderModel orderModel
    );

    @POST("ecommerce/orders")
    @Headers({"Accept: Application/json","cache-control: no-cache"})
    Observable<MakePaymentOrderIntegration> MakePaymentOrderIntegration(
            @Body HashMap orderModel

    );

    ////////////// get  Settings
    @GET("Storesettings.json")
    @Headers("Accept: Application/json")
    Observable<StoreSetting> getStorSetting();

    ////////////// get  search results
    @FormUrlEncoded
    @POST("products/searchbyname/{type}/{userid}.json")
    Observable<Products> getSearchResult(
            @Field("name") String  name,
            @Path("type") String type,
            @Path("userid") int userid
    );
///// Get Small store

    @POST("smallstores/getsmallstoredata/{id}/{type}")
    Observable<StoreData> GetSmallStore(
            @Path("id") int id,
            @Path("type") int type

    );
    ////////////// get currency
    @GET("Currencies/currency.json")
    @Headers("Accept: Application/json")
    Observable<Currency> Currency();


    @FormUrlEncoded
    @POST("users/add.json")
    Observable<Register> userregister(
            @Field("username") String  username,
            @Field("password") String password,
            @Field("mobile") String phone,
            @Field("email") String email,
            @Field("email_verified") int email_verified,
            @Field("active") int active,
            @Field("user_group_id") int user_group_id
    );

    @FormUrlEncoded
    @POST("users/token.json")
    Observable<LoginResponse> userlogin(
            @Field("username") String  username,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("Chatting/getuserchat.json")
    Observable<ChatList> getChatList(
            @Field("customer_id") int user_id
    );


    @FormUrlEncoded
    @POST("Chatting/addchat.json")
    Observable<Addmessage> addmessageChat(
            @Field("customer_id") int user_id,
            @Field("sender") int address,
            @Field("message_text") String message_text
    );
    @POST("acceptance/payment_keys")
    Observable<Payment> GetPaymentkey(
            @Body Payment orderModel
    );
    @Headers({"Content-Type: application/json"})
    @POST("auth/tokens")
    Observable<Token> GetToken(
            @Body Token orderModel

    );

    @GET("areas.json")
    Observable<Countries> getCountriesData();


}
