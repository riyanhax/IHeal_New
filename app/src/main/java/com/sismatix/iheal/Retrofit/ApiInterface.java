package com.sismatix.iheal.Retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("AppCreateUser.php")
    @FormUrlEncoded
    Call<ResponseBody> signup(@Field("firstname") String fullname,
                              @Field("email") String email,
                              @Field("password") String password);

    @POST("AppLogin.php")
    @FormUrlEncoded
    Call<ResponseBody> login(@Field("email") String username,
                             @Field("password") String password);

    @POST("logout.php")
    @FormUrlEncoded
    Call<ResponseBody> logout(@Field("customer_id") String username);

    @POST("AppResetPassword.php")
    @FormUrlEncoded
    Call<ResponseBody> forgotpassword(@Field("email") String username);

    @POST("AppCategoryList.php")
    @FormUrlEncoded
    Call<ResponseBody> categorylist(@Field("type") String type);

    @POST("AppCategoryProducts.php")
    @FormUrlEncoded
    Call<ResponseBody> addcategoryprod(@Field("category_id") String category_id);

    @POST("AppProductView.php")
    @FormUrlEncoded
    Call<ResponseBody> appprodview(@Field("product_id") String product_id,
                                   @Field("customer_id") String customer_id);

    @POST("AppAddToCart.php")
    @FormUrlEncoded
    Call<ResponseBody> addtocart(@Field("product_id") String product_id,
                                 @Field("customer_id") String customer_id);

    @POST("AppAddToCart.php")
    @FormUrlEncoded
    Call<ResponseBody> withoutlogin_quote_addtocart(@Field("product_id") String product_id);

    @POST("AppAddToCart.php")
    @FormUrlEncoded
    Call<ResponseBody> withoutlogin_addtocart(@Field("product_id") String product_id,
                                              @Field("quote_id") String quote_id);

    @POST("AppCartList.php")
    @FormUrlEncoded
    Call<ResponseBody> Cartlist(@Field("email") String email);

    @POST("AppCartList.php")
    @FormUrlEncoded
    Call<ResponseBody> getlistcart(@Field("quote_id") String quote_id);

    //wishlist api
    //https://ihealkuwait.com/customapi/AppAddWishlist.php?productid=50&customerid=1

    @POST("AppAddWishlist.php")
    @FormUrlEncoded
    Call<ResponseBody> add_to_wishlist(@Field("productid") String product_id,
                                       @Field("customerid") String customerid,
                                       @Field("action") String action);

    //remove from wishlist api
    //https://ihealkuwait.com/customapi/AppRemoveWishlistProduct.php?productid=50&customerid=1

    @POST("AppRemoveWishlistProduct.php")
    @FormUrlEncoded
    Call<ResponseBody> remove_from_wishlist(@Field("productid") String product_id,
                                            @Field("customerid") String customerid);

    //remove from cartlist
    //https://ihealkuwait.com/customapi/AppRemoveFromCart.php?product_id=4&email=developertest2018@gmail.com

    @POST("AppRemoveFromCart.php")
    @FormUrlEncoded
    Call<ResponseBody> remove_from_cartlist(@Field("product_id") String product_id, @Field("email") String email);

    @POST("AppRemoveFromCart.php")
    @FormUrlEncoded
    Call<ResponseBody> withoutlogin_remove_from_cartlist(@Field("product_id") String product_id, @Field("quote_id") String email);

    @POST("AppGetWishlist.php")
    @FormUrlEncoded
    Call<ResponseBody> GetWishlist(@Field("customerid") String email);

    @POST("AppAddWishlist.php")
    @FormUrlEncoded
    Call<ResponseBody> Wishlistactions(@Field("action") String action,
                                       @Field("productid") String productid,
                                       @Field("customerid") String customerid);

    //country list
    //https://ihealkuwait.com/customapi/AppGetCountryList.php
    @GET("AppGetCountryList.php")
    Call<ResponseBody> get_country_list();

    @POST("AppCreateAddress.php")
    @FormUrlEncoded
    Call<ResponseBody> AppCreateAddress(@Field("customer_id") String customer_id,
                                        @Field("FirstName") String FirstName,
                                        @Field("LastName") String LastName,
                                        @Field("countryid") String countryid,
                                        @Field("postcode") String postcode,
                                        @Field("city") String city,
                                        @Field("region") String region,
                                        @Field("telephone") String telephone,
                                        @Field("fax") String fax,
                                        @Field("company") String company,
                                        @Field("street") String street);

    //call shipping address api
    //https://ihealkuwait.com/customapi/AppGetAddressList.php?customer_id=1

    @POST("AppGetAddressList.php")
    @FormUrlEncoded
    Call<ResponseBody> GET_SHIPPING_ADDRESS(@Field("customer_id") String customer_id);

    @GET("AppShippingMethod.php")
    Call<ResponseBody> getShippingMethods();

    @GET("AppPaymentMethod.php")
    Call<ResponseBody> getPaymentMethods();

    @POST("AppUpdateCart.php")
    @FormUrlEncoded
    Call<ResponseBody> appUpdatecart(@Field("quote_id") String quote_id,
                                     @Field("qty") String qty,
                                     @Field("item_id") String item_id);

    @POST("AppCreateOrder.php")
    @FormUrlEncoded
    Call<ResponseBody> AppCreateOrder(@Field("customer_id") String customer_id,
                                      @Field("email") String email,
                                      @Field("quote_id") String quote_id,
                                      @Field("FirstName") String FirstName,
                                      @Field("LastName") String LastName,
                                      @Field("countryid") String countryid,
                                      @Field("postcode") String postcode,
                                      @Field("city") String city,
                                      @Field("telephone") String telephone,
                                      @Field("company") String company,
                                      @Field("street") String street,
                                      @Field("shippingcode") String shippingcode,
                                      @Field("paymentcode") String paymentcode,
                                      @Field("saveaddress") String saveaddress,
                                      @Field("region") String region);

    //https://ihealkuwait.com/customapi/AppUpdateAddress.php?
    // address_id=137&customer_id=1&firstname=uaua&middlename=mama&city=yryr&postcode=888888&telephone=2222222222&company=dreamdcm

    @POST("AppUpdateAddress.php")
    @FormUrlEncoded
    Call<ResponseBody> AppUpdateAddress(@Field("address_id") String address_id,
                                        @Field("customer_id") String customer_id,
                                        @Field("FirstName") String FirstName,
                                        @Field("LastName") String LastName,
                                        @Field("telephone") String telephone,
                                        @Field("company") String company,
                                        @Field("street") String street,
                                        @Field("countryid") String countryid,
                                        @Field("postcode") String postcode,
                                        @Field("city") String city);

    @POST("AppOrderList.php")
    @FormUrlEncoded
    Call<ResponseBody> AppOrderList(@Field("customer_id") String customer_id);

    @POST("AppReorder.php")
    @FormUrlEncoded
    Call<ResponseBody> AppReorder(@Field("customer_id") String customer_id,
                                    @Field("order_id")String order_id);

}
