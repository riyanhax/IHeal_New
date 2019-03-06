package com.sismatix.iheal.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sismatix.iheal.Adapter.Cart_Delivery_Adapter;
import com.sismatix.iheal.Adapter.Payment_Method_Adapter;
import com.sismatix.iheal.Model.Cart_Delivery_Model;
import com.sismatix.iheal.Model.Payment_Method_Model;
import com.sismatix.iheal.Model.Product_Category_model;
import com.sismatix.iheal.Model.Product_Grid_Model;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Payment_fragment extends Fragment {

    RecyclerView payment_method_recyclerview;
    Payment_Method_Adapter payment_method_adapter;
    private List<Payment_Method_Model> payment_method_models = new ArrayList<Payment_Method_Model>();
    ImageView iv_confirm_order;
    LinearLayout lv_confirm_order,lv_goback_payment;
    String loginflag;
    View v;
    String fname_shipping, lname_shipping, zipcode_shipping, city_shipping, phone_shipping, company_shipping, streetadd_shipping,
            countryid_shipping, customerid_shipping,reg_shipping, saveaddress_shipping, shipping_method, email_shipping,
            quote_shipping;
    TextView tv_payment_title,tv_payconf;

    public Payment_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_payment, container, false);
        AllocateMEmory(v);

        tv_payment_title.setTypeface(Home.roboto_bold);
        tv_payconf.setTypeface(Home.roboto_bold);

        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            CALL_PAYMENT_API();
        } else {
            Toast.makeText(getContext(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
        }

        loginflag = Login_preference.getLogin_flag(getActivity());
        Bundle bundle = this.getArguments();

        if (paymentcode_ada == null) {
            Toast.makeText(getActivity(), "Please Select atleast one Payment Method", Toast.LENGTH_SHORT).show();
        }

        if (bundle != null){

            fname_shipping = bundle.getString("Firstname_shipping");
            lname_shipping = bundle.getString("Lastname_shipping");
            zipcode_shipping = bundle.getString("Zipcode_shipping");
            city_shipping = bundle.getString("City_shipping");
            phone_shipping = bundle.getString("Phonenumber_shipping");
            company_shipping = bundle.getString("Company_shipping");
            streetadd_shipping = bundle.getString("streetadd_shipping");
            countryid_shipping = bundle.getString("Countryid_shipping");
            customerid_shipping = bundle.getString("customer_id_shipping");
            reg_shipping = bundle.getString("region_shipping");
            saveaddress_shipping = bundle.getString("saveadd_shipping");
            shipping_method = bundle.getString("shippingmethod");
            email_shipping = bundle.getString("email_id_shipping");
            quote_shipping = bundle.getString("quote_id_shipping");

            Log.e("payment_fname",""+fname_shipping);
            Log.e("payment_lname",""+lname_shipping);
            Log.e("payment_zip",""+zipcode_shipping);
            Log.e("payment_city",""+city_shipping);
            Log.e("payment_phone",""+phone_shipping);
            Log.e("payment_comp",""+company_shipping);
            Log.e("payment_streetadd",""+streetadd_shipping);
            Log.e("payment_countrtyid",""+countryid_shipping);
            Log.e("payment_customerid",""+customerid_shipping);
            Log.e("payment_saveadd",""+saveaddress_shipping);
            Log.e("payment_shipmethod",""+shipping_method);
            Log.e("payment_emailid",""+email_shipping);
            Log.e("payment_qid",""+quote_shipping);
            Log.e("payment_code",""+paymentcode_ada);
            Log.e("regg_code",""+reg_shipping);

        }

        Checkout_fragment.iv_shipping_done.setVisibility(View.VISIBLE);
        Checkout_fragment.iv_payment_done.setVisibility(View.INVISIBLE);
        Checkout_fragment.iv_confirmation_done.setVisibility(View.INVISIBLE);

        Checkout_fragment.lv_payment_selected.setVisibility(View.VISIBLE);
        Checkout_fragment.lv_shipping_selected.setVisibility(View.INVISIBLE);
        Checkout_fragment.lv_confirmation_selected.setVisibility(View.INVISIBLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Checkout_fragment.tv_confirmation.setTextColor(getActivity().getColor(R.color.colorPrimary));
            Checkout_fragment.tv_payment.setTextColor(getActivity().getColor(R.color.white));
            Checkout_fragment.tv_shipping.setTextColor(getActivity().getColor(R.color.colorPrimary));
        }

        lv_confirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {

                            if (paymentcode_ada == null){
                                Toast.makeText(getActivity(), "Please Select atleast one payment Method", Toast.LENGTH_SHORT).show();
                            }else {
                                Bundle bundle1 = new Bundle();
                                bundle1.putString("Firstname_shipping", "" + fname_shipping);
                                bundle1.putString("Lastname_shipping", "" + lname_shipping);
                                bundle1.putString("Zipcode_shipping", "" + zipcode_shipping);
                                bundle1.putString("City_shipping", "" + city_shipping);
                                bundle1.putString("Phonenumber_shipping", "" + phone_shipping);
                                bundle1.putString("Company_shipping", "" + company_shipping);
                                bundle1.putString("streetadd_shipping", "" + streetadd_shipping);
                                bundle1.putString("Countryid_shipping", "" + countryid_shipping);
                                bundle1.putString("customer_id_shipping", "" + customerid_shipping);
                                bundle1.putString("saveadd_shipping", "" + saveaddress_shipping);
                                bundle1.putString("shippingmethod", "" + shippingmethod);
                                bundle1.putString("email_id_shipping", "" + email_shipping);
                                bundle1.putString("quote_id_shipping", "" + quote_shipping);
                                bundle1.putString("paymentcode_payment", "" + paymentcode_ada);// aa ek nave param 6
                                bundle1.putString("region_shipping", "" + reg_shipping);
                                Fragment myFragment = new Confirmation_fragment();
                                myFragment.setArguments(bundle1);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_checkout, myFragment).addToBackStack(null).commit();
                            }

                            /*Bundle bundle = new Bundle();
                            bundle.putString("paymentcode_payment",""+paymentcode_ada);
                            Fragment myFragment = new Confirmation_fragment();
                            myFragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_checkout, myFragment).addToBackStack(null).commit();*/

                        } else {

                            loadFragment(new EmailLogin());

                        }
                    }
                }, 1000);
            }
        });

        lv_goback_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        Bundle bundle1 = new Bundle();
                        bundle1.putString("Firstname_shipping", "" + fname_shipping);
                        bundle1.putString("Lastname_shipping", "" + lname_shipping);
                        bundle1.putString("Zipcode_shipping", "" + zipcode_shipping);
                        bundle1.putString("City_shipping", "" + city_shipping);
                        bundle1.putString("Phonenumber_shipping", "" + phone_shipping);
                        bundle1.putString("Company_shipping", "" + company_shipping);
                        bundle1.putString("streetadd_shipping", "" + streetadd_shipping);
                        bundle1.putString("Countryid_shipping", "" + countryid_shipping);
                        bundle1.putString("customer_id_shipping", "" + customerid_shipping);
                        bundle1.putString("saveadd_shipping", "" + saveaddress_shipping);
                        bundle1.putString("shippingmethod", "" + shippingmethod);
                        bundle1.putString("email_id_shipping", "" + email_shipping);
                        bundle1.putString("quote_id_shipping", "" + quote_shipping);
                        bundle1.putString("region_shipping", "" + reg_shipping);
                        Fragment myFragment = new Shipping_fragment();
                        myFragment.setArguments(bundle1);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_checkout, myFragment).addToBackStack(null).commit();

                    }
                }, 1000);

            }
        });

        return v;
    }

    private void loadFragment(Fragment fragment) {
        Log.e("clickone", "");
        android.support.v4.app.FragmentManager manager = getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout_checkout, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    private void CALL_PAYMENT_API() {

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> categorylist = api.getPaymentMethods();

        categorylist.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response_payment", "" + response.body().toString());
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("payment_method");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            try {
                                JSONObject vac_object = jsonArray.getJSONObject(i);
                                payment_method_models.add(new Payment_Method_Model(vac_object.getString("label"),
                                        vac_object.getString("value")));

                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {
                                payment_method_adapter.notifyItemChanged(i);
                            }

                        }

                    } else if (status.equalsIgnoreCase("error")) {
                    }

                } catch (Exception e) {
                    Log.e("Exc", "" + e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

        /*for (int i=0;i<6;i++) {
            payment_method_models.add(new Payment_Method_Model("", ""));
        }
        payment_method_adapter.notifyDataSetChanged();*/
    }

    private void AllocateMEmory(View v) {
        payment_method_recyclerview = (RecyclerView) v.findViewById(R.id.payment_method_recyclerview);
        iv_confirm_order = (ImageView) v.findViewById(R.id.iv_confirm_order);
        lv_confirm_order = (LinearLayout) v.findViewById(R.id.lv_confirm_order);
        lv_goback_payment = (LinearLayout) v.findViewById(R.id.lv_goback_payment);

        payment_method_adapter = new Payment_Method_Adapter(getActivity(), payment_method_models);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setReverseLayout(false);
        payment_method_recyclerview.setLayoutManager(layoutManager);
        payment_method_recyclerview.setAdapter(payment_method_adapter);

        tv_payment_title = (TextView)v.findViewById(R.id.tv_payment_title);
        tv_payconf = (TextView)v.findViewById(R.id.tv_payconf);

    }

}
