package com.sismatix.iheal.Fragments;


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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sismatix.iheal.Adapter.Cart_List_Adapter;
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
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;
import static com.sismatix.iheal.Adapter.Cart_Delivery_Adapter.shippingmethod;
import static com.sismatix.iheal.Adapter.Payment_Method_Adapter.paymentcode_ada;
import static com.sismatix.iheal.Fragments.Cart.cart_adapter;
import static com.sismatix.iheal.Fragments.Cart.cart_items_count;
import static com.sismatix.iheal.Fragments.Cart.cartlistt;
import static com.sismatix.iheal.Fragments.Cart.context;
import static com.sismatix.iheal.Fragments.Cart.qoute_id_cart;
import static com.sismatix.iheal.Fragments.Cart.qt;
import static com.sismatix.iheal.Fragments.Cart.tv_maintotal;

/**
 * A simple {@link Fragment} subclass.
 */
public class Confirmation_fragment extends Fragment {
    LinearLayout iv_confirm_pay,lv_goback_confirmation;
    TextView confirm_add,tv_totalconform,tv_add_edit_confirm,tv_cart_edit_confirm,tv_shipping_tit,tv_conf_order,tv_conf_totamt;
    RecyclerView recyclerview_confirmation;
    private List<Cart_Model> cartList = new ArrayList<Cart_Model>();
    private Confirmation_cart_Adapter confirmation_cart_adapter;
    String fname_confirm, lname_confirm, zipcode_confirm, city_confirm, phone_confirm, fax_confirm, company_confirm, streetadd_confirm,
            countryid_confirm, customerid_confirm, saveaddress_confirm, shipping_confirm, email_confirm, quote_confirm,reg_confirm;
    String paycode;
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

        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            prepareConfirmCart();
        } else {
            Toast.makeText(getContext(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
        }

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
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_checkout, myFragment).addToBackStack(null).commit();

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
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_checkout, myFragment).addToBackStack(null).commit();
            }
        });

        tv_cart_edit_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment myFragment = new Cart();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, myFragment).addToBackStack(null).commit();
            }
        });

        return v;
    }

    private void settypeface() {
        confirm_add.setTypeface(Home.roboto_bold);
        tv_totalconform.setTypeface(Home.roboto_bold);
        tv_add_edit_confirm.setTypeface(Home.roboto_bold);
        tv_cart_edit_confirm.setTypeface(Home.roboto_bold);
        tv_shipping_tit.setTypeface(Home.roboto_bold);
        tv_conf_order.setTypeface(Home.roboto_bold);
        tv_conf_totamt.setTypeface(Home.roboto_bold);
    }

    private void prepareConfirmCart() {
        cartList.clear();
        String email = Login_preference.getemail(context);

        String loginflag = Login_preference.getLogin_flag(context);
        Log.e("customeriddd", "" + Login_preference.getcustomer_id(context));
        if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
            Log.e("with_login", "");
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            cartlistt = api.Cartlist(email);
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
                Log.e("responseeeeee_confirm", "" + response.body().toString());

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    Log.e("status_confirm_cart", "" + status);
                    if (status.equalsIgnoreCase("success")) {
                        String grand_total = jsonObject.getString("grand_total");
                        tv_totalconform.setText(grand_total);
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
                Log.e("response", "" + response.body().toString());
                progressBar.setVisibility(View.GONE);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String code = jsonObject.getString("code");
                    Log.e("code_confirmation", "" + code);
                    String meassg = jsonObject.getString("message");
                    Log.e("message_confirmation", "" + meassg);
                    if (code.equalsIgnoreCase("200")) {
                        Toast.makeText(getContext(), "" + meassg, Toast.LENGTH_SHORT).show();
                        loadFragment(new Fianl_Order_Checkout_freg());
                    } else if (code.equalsIgnoreCase("error")) {
                        Toast.makeText(getContext(), "" + meassg, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("Exception", "" + e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

       /* for (int i = 0; i < 4; i++) {
            cartList.add(new Cart_Model("", "",
                    "", "", "", "", "", ""));
        }
        confirmation_cart_adapter.notifyDataSetChanged();*/

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
        tv_shipping_tit = (TextView)v.findViewById(R.id.tv_shipping_tit);
        tv_conf_order = (TextView)v.findViewById(R.id.tv_conf_order);
        tv_conf_totamt = (TextView)v.findViewById(R.id.tv_conf_totamt);

        confirmation_cart_adapter = new Confirmation_cart_Adapter(getActivity(), cartList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview_confirmation.setLayoutManager(mLayoutManager);
        recyclerview_confirmation.setItemAnimator(new DefaultItemAnimator());
        recyclerview_confirmation.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerview_confirmation.setAdapter(confirmation_cart_adapter);
    }

}
