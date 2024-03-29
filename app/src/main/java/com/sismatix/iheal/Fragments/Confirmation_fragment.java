package com.sismatix.iheal.Fragments;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sismatix.iheal.Activity.Navigation_drawer_activity;
import com.sismatix.iheal.Activity.Web_tappayment;
import com.sismatix.iheal.Adapter.Confirmation_cart_Adapter;
import com.sismatix.iheal.Model.Cart_Model;
import com.sismatix.iheal.Preference.CheckNetwork;
import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.Preference.MyAddress_Preference;
import com.sismatix.iheal.R;
import com.sismatix.iheal.Retrofit.ApiClient;
import com.sismatix.iheal.Retrofit.ApiInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import company.tap.gosellapi.api.facade.APIRequestCallback;
import company.tap.gosellapi.api.facade.GoSellAPI;
import company.tap.gosellapi.api.facade.GoSellError;
import company.tap.gosellapi.api.model.Charge;
import company.tap.gosellapi.api.model.Redirect;
import company.tap.gosellapi.api.requests.CreateChargeRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.volley.VolleyLog.TAG;
import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;
import static com.sismatix.iheal.Adapter.Cart_Delivery_Adapter.shippingmethod;
import static com.sismatix.iheal.Adapter.Payment_Method_Adapter.paymentcode_ada;
import static com.sismatix.iheal.Fragments.Cart.cart_adapter;
import static com.sismatix.iheal.Fragments.Cart.cart_items_count;
import static com.sismatix.iheal.Fragments.Cart.cartlistt;
import static com.sismatix.iheal.Fragments.Cart.context;
import static com.sismatix.iheal.Fragments.Cart.qoute_id_cart;
import static com.sismatix.iheal.Fragments.Cart.qt;
import static com.sismatix.iheal.Fragments.Wishlist_fragment.activity;

/**
 * A simple {@link Fragment} subclass.
 */
public class Confirmation_fragment extends Fragment {
    LinearLayout iv_confirm_pay,lv_goback_confirmation;
    TextView confirm_add,tv_totalconform,tv_add_edit_confirm,tv_cart_edit_confirm,tv_shipping_title,tv_conf_order,tv_conf_totamt;
    RecyclerView recyclerview_confirmation;
    private List<Cart_Model> cartList = new ArrayList<Cart_Model>();
    private Confirmation_cart_Adapter confirmation_cart_adapter;
    String fname_confirm, lname_confirm, zipcode_confirm, city_confirm, phone_confirm, fax_confirm, company_confirm, streetadd_confirm,
            countryid_confirm, customerid_confirm, saveaddress_confirm, shipping_confirm, email_confirm, quote_confirm,reg_confirm;
    String paycode;
    double price;
    Charge charge;
    LinearLayout lv_confirm_pay;
    ProgressBar progressBar;
    View v;

    public Confirmation_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_confirmation, container, false);
        Allocatememory(v);
        settypeface();


        bottom_navigation.setVisibility(View.VISIBLE);

        Bundle bundle = this.getArguments();

        if (bundle != null) {

            fname_confirm = bundle.getString("Firstname_shipping");
            MyAddress_Preference.setFirstname(getContext(),fname_confirm);
            lname_confirm = bundle.getString("Lastname_shipping");
            MyAddress_Preference.setLastname(getContext(),lname_confirm);
            zipcode_confirm = bundle.getString("Zipcode_shipping");
            MyAddress_Preference.setZipcode(getContext(),zipcode_confirm);
            city_confirm = bundle.getString("City_shipping");
            MyAddress_Preference.setCity(getContext(),city_confirm);
            phone_confirm = bundle.getString("Phonenumber_shipping");
            MyAddress_Preference.setPhoneNumber(getContext(),phone_confirm);
            company_confirm = bundle.getString("Company_shipping");
            MyAddress_Preference.setCompanyName(getContext(),company_confirm);
            streetadd_confirm = bundle.getString("streetadd_shipping");
            MyAddress_Preference.setStreetAddress(getContext(),streetadd_confirm);
            countryid_confirm = bundle.getString("Countryid_shipping");
            MyAddress_Preference.setCountryId(getContext(),countryid_confirm);
            customerid_confirm = bundle.getString("customer_id_shipping");
            /*MyAddress_Preference.setFirstname(getContext(),fname_confirm);*/
            saveaddress_confirm = bundle.getString("saveadd_shipping");
            MyAddress_Preference.setsaveAddress(getContext(),saveaddress_confirm);
            shipping_confirm = bundle.getString("shippingmethod");
            /*MyAddress_Preference.setFirstname(getContext(),fname_confirm);*/
            email_confirm = bundle.getString("email_id_shipping");
            /*MyAddress_Preference.setFirstname(getContext(),fname_confirm);*/
            quote_confirm = bundle.getString("quote_id_shipping");
            /*MyAddress_Preference.setFirstname(getContext(),fname_confirm);*/
            reg_confirm = bundle.getString("region_shipping");
            MyAddress_Preference.setRegion(getContext(),reg_confirm);
            paycode = bundle.getString("paymentcode_payment");
            /*MyAddress_Preference.setFirstname(getContext(),fname_confirm);*/

            Log.e("confirm_fname", "" + fname_confirm);
            Log.e("confirm_lname", "" + lname_confirm);
            Log.e("confirm_zip", "" + zipcode_confirm);
            Log.e("confirm_city", "" + city_confirm);
            Log.e("confirm_phone", "" + phone_confirm);
            Log.e("confirm_fax", "" + fax_confirm);
            Log.e("confirm_comp", "" + company_confirm);
            Log.e("confirm_streetadd", "" + streetadd_confirm);
            Log.e("confirm_countrtyid", "" + countryid_confirm);
            Log.e("confirm_customerid", "" + customerid_confirm);
            Log.e("confirm_saveadd", "" + saveaddress_confirm);
            Log.e("confirm_shipmethod", "" + shipping_confirm);
            Log.e("confirm_emailid", "" + email_confirm);
            Log.e("confirm_qid", "" + quote_confirm);
            Log.e("confirm_code", "" + paymentcode_ada);
            Log.e("confirm_paycode_final", "" + paycode);

        }

        confirm_add.setText(streetadd_confirm + " " + zipcode_confirm + " " + city_confirm + " " + countryid_confirm);
        Navigation_drawer_activity.Check_String_NULL_Value(confirm_add,streetadd_confirm + " " + zipcode_confirm + " " + city_confirm + " " + countryid_confirm);
        Checkout_fragment.lv_payment_selected.setVisibility(View.INVISIBLE);
        Checkout_fragment.lv_shipping_selected.setVisibility(View.INVISIBLE);

        Checkout_fragment.lv_confirmation_selected.setVisibility(View.VISIBLE);
        Checkout_fragment.iv_payment_done.setVisibility(View.VISIBLE);
        Checkout_fragment.iv_shipping_done.setVisibility(View.VISIBLE);
        Checkout_fragment.iv_confirmation_done.setVisibility(View.INVISIBLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Checkout_fragment.tv_confirmation.setTextColor(getActivity().getColor(R.color.white));
            Checkout_fragment.tv_payment.setTextColor(getActivity().getColor(R.color.colorPrimary));
            Checkout_fragment.tv_shipping.setTextColor(getActivity().getColor(R.color.colorPrimary));
        }

        lv_confirm_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        CONFIRMATION_CART();

                        /*loadFragment(new Fianl_Order_Checkout_freg());*/

                    }
                }, 1000);

            }
        });

        lv_goback_confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        Bundle bundle1 = new Bundle();

                        bundle1.putString("Firstname_shipping", "" + fname_confirm);
                        bundle1.putString("Lastname_shipping", "" + lname_confirm);
                        bundle1.putString("Zipcode_shipping", "" + zipcode_confirm);
                        bundle1.putString("City_shipping", "" + city_confirm);
                        bundle1.putString("Phonenumber_shipping", "" + phone_confirm);
                        bundle1.putString("Company_shipping", "" + company_confirm);
                        bundle1.putString("streetadd_shipping", "" + streetadd_confirm);
                        bundle1.putString("Countryid_shipping", "" + countryid_confirm);
                        bundle1.putString("customer_id_shipping", "" + customerid_confirm);
                        bundle1.putString("saveadd_shipping", "" + saveaddress_confirm);
                        bundle1.putString("shippingmethod", "" + shippingmethod);
                        bundle1.putString("email_id_shipping", "" + email_confirm);
                        bundle1.putString("quote_id_shipping", "" + quote_confirm);
                        bundle1.putString("paymentcode_payment", "" + paymentcode_ada);// aa ek nave param 6
                        bundle1.putString("region_shipping", "" + reg_confirm);

                        Fragment myFragment = new Payment_fragment();
                        myFragment.setArguments(bundle1);
                        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,
                                0, 0, R.anim.fade_out).replace(R.id.frameLayout_checkout, myFragment).addToBackStack(null).commit();

                    }
                }, 1000);

            }
        });

        tv_add_edit_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle2 = new Bundle();

                bundle2.putString("Firstname_shipping", "" + fname_confirm);
                bundle2.putString("Lastname_shipping", "" + lname_confirm);
                bundle2.putString("Zipcode_shipping", "" + zipcode_confirm);
                bundle2.putString("City_shipping", "" + city_confirm);
                bundle2.putString("Phonenumber_shipping", "" + phone_confirm);
                bundle2.putString("Company_shipping", "" + company_confirm);
                bundle2.putString("streetadd_shipping", "" + streetadd_confirm);
                bundle2.putString("Countryid_shipping", "" + countryid_confirm);
                bundle2.putString("customer_id_shipping", "" + customerid_confirm);
                bundle2.putString("saveadd_shipping", "" + saveaddress_confirm);
                bundle2.putString("shippingmethod", "" + shippingmethod);
                bundle2.putString("email_id_shipping", "" + email_confirm);
                bundle2.putString("quote_id_shipping", "" + quote_confirm);
                bundle2.putString("paymentcode_payment", "" + paymentcode_ada);// aa ek nave param 6
                bundle2.putString("region_shipping", "" + reg_confirm);

                Fragment myFragment = new Shipping_fragment();
                myFragment.setArguments(bundle2);
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,
                        0, 0, R.anim.fade_out).replace(R.id.frameLayout_checkout, myFragment).addToBackStack(null).commit();
            }
        });

        tv_cart_edit_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment myFragment = new Cart();
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,
                        0, 0, R.anim.fade_out).replace(R.id.rootLayout, myFragment).addToBackStack(null).commit();
            }
        });

        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            prepareConfirmCart();
        } else {
            Toast.makeText(getContext(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return v;
    }
    private void conform_TPApayment(String order) {
        progressBar.setVisibility(View.VISIBLE);
        HashMap<String, String> chargeMetadata = new HashMap<>();
        chargeMetadata.put("Order Number", "ORD-1001");

        GoSellAPI.getInstance("sk_test_stR9ydEPWUcaN3kZ74TfuAYg").createCharge(
                new CreateChargeRequest
                        .Builder(price,
                        "KWD",
                        new CreateChargeRequest.Customer(email_confirm, phone_confirm, fname_confirm),
                        new Redirect("https://ihealkuwait.com/", "https://ihealkuwait.com/"))
                        .threeDSecure(true)
                        .transaction_reference(order)
                        .reference_order(order)
                        .statement_descriptor("")
                        .receipt(new Charge.Receipt(true, true))
                        .source(null)
                        .description("")
                        .metadata(chargeMetadata)
                        .build(),
                new APIRequestCallback<Charge>() {
                    @Override
                    public void onSuccess(int responseCode, Charge serializedResponse) {
                        progressBar.setVisibility(View.GONE);
                        Log.d(TAG, "onSuccess createCharge: serializedResponse:" + serializedResponse);
                        charge = serializedResponse;
                        Log.e("vinod_URL",""+charge.getRedirect());
                        Log.e("URL_main",""+ charge.getRedirect().getUrl());
                        Intent intent= new Intent(getActivity(),Web_tappayment.class);
                        intent.putExtra("url",charge.getRedirect().getUrl());
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(GoSellError errorDetails) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "errorCode:"+ errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure createCharge, errorCode: " + errorDetails.getErrorCode() + ", errorBody: " + errorDetails.getErrorBody() + ", throwable: " + errorDetails.getThrowable());
                    }
                }
        );
    }

    private void settypeface() {
        confirm_add.setTypeface(Home.roboto_bold);
        tv_totalconform.setTypeface(Home.roboto_bold);
        tv_add_edit_confirm.setTypeface(Home.roboto_bold);
        tv_cart_edit_confirm.setTypeface(Home.roboto_bold);
        tv_shipping_title.setTypeface(Home.roboto_bold);
        tv_conf_order.setTypeface(Home.roboto_bold);
        tv_conf_totamt.setTypeface(Home.roboto_bold);
    }

    private void prepareConfirmCart() {
        cartList.clear();
        String email = Login_preference.getemail(getActivity());
        Log.e("emailsss",""+email);
        String loginflag = Login_preference.getLogin_flag(getActivity());
        Log.e("customeriddd", "" + Login_preference.getcustomer_id(context));
        if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
            Log.e("with_login", "");
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            Log.e("emai_id",email);
            Log.e("review","1");
            Log.e("country_id",countryid_confirm);
            Log.e("paycode",paycode);
            Log.e("shipping_confirm",shipping_confirm);
            cartlistt = api.Cartlist_totoal(email,"1",countryid_confirm,paycode,shipping_confirm);
        } else {
            Log.e("without_login", "");
            String quote_id = Login_preference.getquote_id(context);//359
            Log.e("quoteidd", "" + quote_id);
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            cartlistt = api.getlistcart(quote_id);
        }

        cartlistt.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("responseeeeee_confirm", "" + response);

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    Log.e("status_confirm_cart", "" + status);
                    if (status.equalsIgnoreCase("success")) {
                        String grand_total = jsonObject.getString("grand_total");
                        String currentString = grand_total;
                        String[] separated = currentString.split("KWD");
                        String A =separated[0]; // this will contain "Fruit"
                        price= Double.parseDouble(separated[1]); // this will contain " they taste good"
                        Log.e("price_pass",""+price);
                        tv_totalconform.setText(grand_total);
                        Navigation_drawer_activity.Check_String_NULL_Value(tv_totalconform,grand_total);
                        Login_preference.setquote_id(context, jsonObject.getString("quote_id"));
                        qoute_id_cart = jsonObject.getString("quote_id");
                        Log.e("qoute_id_confirm_cart", "" + qoute_id_cart);

                        cart_items_count = jsonObject.getString("items_count");

                      //  Login_preference.setCart_item_count(context, cart_items_count);
                        JSONArray jsonArray = jsonObject.getJSONArray("products");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject vac_object = jsonArray.getJSONObject(i);
                                Log.e("Name", "" + vac_object.getString("product_name"));
                                cartList.add(new Cart_Model(vac_object.getString("product_name"),
                                        vac_object.getString("product_price"),
                                        vac_object.getString("product_image"),
                                        vac_object.getString("product_sku"),
                                        vac_object.getString("product_id"),
                                        vac_object.getString("row_total"),
                                        vac_object.getString("product_qty"),
                                        vac_object.getString("itemid")));
                                qt = vac_object.getString("product_qty");
                                Log.e("qtttttttt", "" + qt);
                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {
                                confirmation_cart_adapter.notifyItemChanged(i);
                            }
                        }
                    } else if (status.equalsIgnoreCase("error")) {

                    }

                } catch (Exception e) {
                    Log.e("", "" + e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        Log.e("clickone", "");
        android.support.v4.app.FragmentManager manager = getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in,
                0, 0, R.anim.fade_out);
        transaction.replace(R.id.rootLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void CONFIRMATION_CART() {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apii = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> confirm = apii.AppCreateOrder(customerid_confirm, email_confirm, quote_confirm, fname_confirm,
                lname_confirm, countryid_confirm, zipcode_confirm, city_confirm, phone_confirm, company_confirm,
                streetadd_confirm, shipping_confirm, paycode, saveaddress_confirm,reg_confirm);

        confirm.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response", "" + response);
                progressBar.setVisibility(View.GONE);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String code = jsonObject.getString("code");
                    Log.e("code_confirmation", "" + code);
                    String meassg = jsonObject.getString("message");
                    Log.e("message_confirmation", "" + meassg);
                    if (code.equalsIgnoreCase("200")) {
                     //   Toast.makeText(getContext(), "" + meassg, Toast.LENGTH_SHORT).show();
                        String order=jsonObject.getString("order_id");
                        conform_TPApayment(order);
                        //loadFragment(new Fianl_Order_Checkout_freg());
                    } else if (code.equalsIgnoreCase("error")) {
                        //Toast.makeText(getContext(), "" + meassg, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("Exception", "" + e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
             //   Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

       /* for (int i = 0; i < 4; i++) {
            cartList.add(new Cart_Model("", "",
                    "", "", "", "", "", ""));
        }
        confirmation_cart_adapter.notifyDataSetChanged();*/

    }

    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    Fragment myFragment = new Home();
                    activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,
                            0, 0, R.anim.fade_out).setCustomAnimations(R.anim.fade_in,
                            0, 0, R.anim.fade_out).replace(R.id.rootLayout, myFragment).addToBackStack(null).commit();
                    return true;
                }
                return false;
            }
        });
    }

    private void Allocatememory(View v) {
        recyclerview_confirmation = (RecyclerView) v.findViewById(R.id.recyclerview_confirmation);
        iv_confirm_pay = (LinearLayout) v.findViewById(R.id.lv_confirm_pay);
        lv_goback_confirmation = (LinearLayout)v.findViewById(R.id.lv_goback_confirmation);
        lv_confirm_pay = (LinearLayout) v.findViewById(R.id.lv_confirm_pay);
        confirm_add = (TextView) v.findViewById(R.id.confirm_add);
        progressBar = (ProgressBar)v.findViewById(R.id.confirmation_progressBar);
        tv_totalconform = (TextView)v.findViewById(R.id.tv_totalconform);
        tv_add_edit_confirm = (TextView)v.findViewById(R.id.tv_add_edit_confirm);
        tv_cart_edit_confirm = (TextView)v.findViewById(R.id.tv_cart_edit_confirm);
        tv_shipping_title = (TextView)v.findViewById(R.id.tv_shipping_title);
        tv_conf_order = (TextView)v.findViewById(R.id.tv_conf_order);
        tv_conf_totamt = (TextView)v.findViewById(R.id.tv_conf_totamt);

        confirmation_cart_adapter = new Confirmation_cart_Adapter(getActivity(), cartList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerview_confirmation.setLayoutManager(mLayoutManager);
        recyclerview_confirmation.setItemAnimator(new DefaultItemAnimator());
        recyclerview_confirmation.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerview_confirmation.setAdapter(confirmation_cart_adapter);
    }

}
