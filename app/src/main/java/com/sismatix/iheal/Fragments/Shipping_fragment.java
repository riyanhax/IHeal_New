package com.sismatix.iheal.Fragments;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sismatix.iheal.Adapter.Cart_Delivery_Adapter;
import com.sismatix.iheal.Model.Cart_Delivery_Model;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sismatix.iheal.Adapter.Cart_Delivery_Adapter.shippingmethod;
import static com.sismatix.iheal.Adapter.Payment_Method_Adapter.paymentcode_ada;

/**
 * A simple {@link Fragment} subclass.
 */
public class Shipping_fragment extends Fragment {

    View v;
    RecyclerView recyclerview_item_delivery;
    Cart_Delivery_Adapter cart_delivery_adapter;
    private List<Cart_Delivery_Model> cart_delivery_models = new ArrayList<Cart_Delivery_Model>();
    ImageView iv_continue_payment;
    TextView tv_fname, tv_lname, tv_pno, tv_comp, tv_street, tv_country, tv_zip, tv_city, tv_reg,tv_rad_label,tv_delivery,tv_continue_payment;
    EditText et_shippingfirstname, et_shippinglastname, et_shippingphonenumber, et_shippingcompany, et_streetadd, et_fax,
            et_shippingzipcode, et_shippingcity, et_shippingregion;
    String fname_payment, lname_payment, zip_payment, city_payment, phone_payment, company_payment, street_payment, countryid_payment,
            customer_id_payment, saveadd_payment, shippingmethod_payment, email_payment, quote_payment, reg_payment;
    LinearLayout lv_continue_payment;
    String loginflag, setdefault;
    public Spinner spinner_country_Name;
    CheckBox ck_default;
    public static ArrayList<String> country_name_code = new ArrayList<String>();
    public static ArrayList<String> country_name = new ArrayList<String>();
    String qidd, email_id, customer_id, firstName, lastName, countryid, postcode, city, region, telephone, company, street;

    public Shipping_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_shipping, container, false);
        loginflag = Login_preference.getLogin_flag(getActivity());

        AllocateMEmory(v);
        setTypeface();

        cart_delivery_models.clear();
        setValuesToeEditText();
        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            Countrylist();
            CALL_CART_DELIVERY();
        } else {
            Toast.makeText(getContext(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
        }

        qidd = Login_preference.getquote_id(getActivity());
        Log.e("quoteidd_shippingfrag", "" + qidd);

        /*String saveadd_final = MyAddress_Preference.getsaveAddress(getActivity());
        Log.e("saveaddfinal", "" + saveadd_final);*/

        Checkout_fragment.lv_payment_selected.setVisibility(View.INVISIBLE);
        Checkout_fragment.iv_shipping_done.setVisibility(View.INVISIBLE);
        Checkout_fragment.iv_payment_done.setVisibility(View.INVISIBLE);
        Checkout_fragment.iv_confirmation_done.setVisibility(View.INVISIBLE);
        Checkout_fragment.lv_shipping_selected.setVisibility(View.VISIBLE);
        Checkout_fragment.lv_payment_selected.setVisibility(View.INVISIBLE);
        Checkout_fragment.lv_confirmation_selected.setVisibility(View.INVISIBLE);

        String saveadd = MyAddress_Preference.getsaveAddress(getActivity());
        if (saveadd.equals("1")) {
            ck_default.setChecked(true);
        } else {

        }

        Bundle bundleee = this.getArguments();
        if (bundleee != null) {

            cart_delivery_models.clear();
            fname_payment = bundleee.getString("Firstname_shipping");
            lname_payment = bundleee.getString("Lastname_shipping");
            zip_payment = bundleee.getString("Zipcode_shipping");
            city_payment = bundleee.getString("City_shipping");
            phone_payment = bundleee.getString("Phonenumber_shipping");
            company_payment = bundleee.getString("Company_shipping");
            street_payment = bundleee.getString("streetadd_shipping");
            countryid_payment = bundleee.getString("Countryid_shipping");
            customer_id_payment = bundleee.getString("customer_id_shipping");
            reg_payment = bundleee.getString("region_shipping");
            saveadd_payment = bundleee.getString("saveadd_shipping");
            shippingmethod_payment = bundleee.getString("shippingmethod");
            email_payment = bundleee.getString("email_id_shipping");
            quote_payment = bundleee.getString("quote_id_shipping");

           /* fname_payment = bundleee.getString("Firstname_payment_goback");
            lname_payment = bundleee.getString("Lastname_payment_goback");
            zip_payment = bundleee.getString("Zipcode_payment_goback");
            city_payment = bundleee.getString("City_payment_goback");
            phone_payment = bundleee.getString("Phonenumber_payment_goback");
            company_payment = bundleee.getString("Company_payment_goback");
            street_payment = bundleee.getString("streetadd_payment_goback");
            countryid_payment = bundleee.getString("Countryid_payment_goback");
            customer_id_payment = bundleee.getString("customer_id_payment_goback");
            saveadd_payment = bundleee.getString("saveadd_payment_goback");
            shippingmethod_payment = bundleee.getString("shippingmethod_payment_goback");
            email_payment = bundleee.getString("email_id_payment_goback");
            quote_payment = bundleee.getString("quote_id_payment_goback");*/

            Log.e("last_fname", "" + fname_payment);
            Log.e("last_lname", "" + lname_payment);
            Log.e("last_zip", "" + zip_payment);
            Log.e("last_city", "" + city_payment);
            Log.e("last_phone", "" + phone_payment);
            Log.e("last_comp", "" + company_payment);
            Log.e("last_streetadd", "" + street_payment);
            Log.e("last_countrtyid", "" + countryid_payment);
            Log.e("last_customerid", "" + customer_id_payment);
            Log.e("last_saveadd", "" + saveadd_payment);
            Log.e("last_shipmethod", "" + shippingmethod_payment);
            Log.e("last_emailid", "" + email_payment);
            Log.e("last_qid", "" + quote_payment);
            Log.e("last_reg", "" + reg_payment);

            et_shippingfirstname.setText(fname_payment);
            et_shippinglastname.setText(lname_payment);
            et_shippingzipcode.setText(zip_payment);
            et_shippingcity.setText(city_payment);
            et_shippingphonenumber.setText(phone_payment);
            et_shippingcompany.setText(company_payment);
            et_streetadd.setText(street_payment);
            et_shippingregion.setText(reg_payment);

            if (saveadd_payment.equals("1")) {
                Log.e("last_saveadd_final", "" + saveadd_payment);
                ck_default.setChecked(true);
            } else {
                saveadd_payment = "0";
            }

        }

        spinner_country_Name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                int selected_item_position = spinner_country_Name.getSelectedItemPosition();
                countryid = country_name_code.get(selected_item_position);
                // MyAddress_Preference.setCountryId(getActivity(), String.valueOf(selected_item_position));
                Log.e("countryid", "" + countryid);
                String selected_country = String.valueOf(spinner_country_Name.getSelectedItem());

               // Toast.makeText(getActivity(), selected_item_position + " " + selected_country + " => " + countryid, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {

            }

        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Checkout_fragment.tv_confirmation.setTextColor(getActivity().getColor(R.color.colorPrimary));
            Checkout_fragment.tv_payment.setTextColor(getActivity().getColor(R.color.colorPrimary));
            Checkout_fragment.tv_shipping.setTextColor(getActivity().getColor(R.color.white));
        }

        lv_continue_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
                            validateShippingData();
                            /*loadFragment(new Payment_fragment());*/
                        } else {
                            loadFragment(new EmailLogin());
                        }
                    }
                }, 1000);

            }
        });

        Log.e("adaper_pos", "" + cart_delivery_adapter.selectedPosition);
        return v;
    }

    private void setTypeface() {

        tv_fname.setTypeface(Home.roboto_bold);
        tv_lname.setTypeface(Home.roboto_bold);
        tv_pno.setTypeface(Home.roboto_bold);
        tv_comp.setTypeface(Home.roboto_bold);
        tv_street.setTypeface(Home.roboto_bold);
        tv_country.setTypeface(Home.roboto_bold);
        tv_zip.setTypeface(Home.roboto_bold);
        tv_city.setTypeface(Home.roboto_bold);
        tv_reg.setTypeface(Home.roboto_bold);
        tv_rad_label.setTypeface(Home.roboto_bold);
        tv_delivery.setTypeface(Home.roboto_bold);
        tv_continue_payment.setTypeface(Home.roboto_bold);

        et_shippingfirstname.setTypeface(Home.roboto_bold);
        et_shippinglastname.setTypeface(Home.roboto_bold);
        et_shippingphonenumber.setTypeface(Home.roboto_bold);
        et_shippingcompany.setTypeface(Home.roboto_bold);
        et_streetadd.setTypeface(Home.roboto_bold);
        et_shippingzipcode.setTypeface(Home.roboto_bold);
        et_shippingcity.setTypeface(Home.roboto_bold);
        et_shippingregion.setTypeface(Home.roboto_bold);

    }

    private void setValuesToeEditText() {

        et_shippingfirstname.setText(MyAddress_Preference.getFirstname(getActivity()));
        Log.e("fnameeee", "" + et_shippingfirstname.getText().toString());
        et_shippinglastname.setText(MyAddress_Preference.getLastname(getActivity()));
        et_shippingphonenumber.setText(MyAddress_Preference.getPhoneNumber(getActivity()));
        et_shippingcompany.setText(MyAddress_Preference.getCompanyName(getActivity()));
        et_streetadd.setText(MyAddress_Preference.getStreetAddress(getActivity()));
        et_shippingzipcode.setText(MyAddress_Preference.getZipcode(getActivity()));
        et_shippingcity.setText(MyAddress_Preference.getCity(getActivity()));
        et_shippingregion.setText(MyAddress_Preference.getRegion(getActivity()));

    }

    private boolean validateShippingData() {
        cart_delivery_models.clear();
        customer_id = Login_preference.getcustomer_id(getActivity());
        email_id = Login_preference.getemail(getActivity());

        if (ck_default.isChecked()) {
            setdefault = "1";
        } else {
            setdefault = "0";
        }

        Log.e("ck_val", "" + setdefault);
        MyAddress_Preference.setsaveAddress(getActivity(), setdefault);

        firstName = et_shippingfirstname.getText().toString();
        lastName = et_shippinglastname.getText().toString();
        telephone = et_shippingphonenumber.getText().toString();
        company = et_shippingcompany.getText().toString();
        street = et_streetadd.getText().toString();
        postcode = et_shippingzipcode.getText().toString();
        city = et_shippingcity.getText().toString();
        region = et_shippingregion.getText().toString();

        if (et_shippingfirstname.getText().length() == 0) {
            et_shippingfirstname.setError("Please enter your Firstname");
        } else if (et_shippinglastname.getText().length() == 0) {
            et_shippinglastname.setError("Please enter your Lastname");
        } else if (telephone.length() == 0) {//et_shippingphonenumber.getText().length() == 0
            et_shippingphonenumber.setError("Please enter your Mobile no.");
        } else if (telephone.length() < 9 || telephone.length() > 13) {//et_shippingphonenumber.getText().length() == 0
            et_shippingphonenumber.setError("Please enter valid Mobile no.");
        } else if (et_shippingcompany.getText().length() == 0) {
            et_shippingcompany.setError("Please enter your Company Name");
        } else if (et_streetadd.getText().length() == 0) {
            et_streetadd.setError("Please enter your Street Address");
        } else if (et_shippingzipcode.getText().length() == 0) {
            et_shippingzipcode.setError("Please enter your Zipcode");
        } else if (et_shippingcity.getText().length() == 0) {
            et_shippingcity.setError("Please enter your Cityname");
        } else if (et_shippingregion.getText().length() == 0) {
            et_shippingregion.setError("Please enter your Region");
        } else if (shippingmethod == null) {
            Toast.makeText(getActivity(), "Please Select atleast one Shipping Method", Toast.LENGTH_SHORT).show();
        }

        /*else if (ck_default.isChecked()!=true){

        }*/

        else {
            loadfrag();
        }

        Log.e("firstname_shipping", "" + et_shippingfirstname.getText().toString());
        Log.e("lastname_shipping", "" + et_shippinglastname.getText().toString());
        Log.e("zipcode_shipping", "" + et_shippingzipcode.getText().toString());
        Log.e("city_shipping", "" + et_shippingcity.getText().toString());
        Log.e("phonenumber_shipping", "" + et_shippingphonenumber.getText().toString());
        Log.e("company_shipping", "" + et_shippingcompany.getText().toString());
        Log.e("streetadd_shipping", "" + et_streetadd.getText().toString());
        Log.e("countryid_shipping", "" + countryid);
        Log.e("Shipping_cust_id", "" + customer_id);
        Log.e("saveadd", "" + setdefault);
        Log.e("shippingmethod", "" + shippingmethod);
        Log.e("email_id_shipping", "" + email_id);
        Log.e("quoteidshipping", "" + qidd);
        /*Log.e("shippingcode", "" + setdefault);*/
        /*Log.e("address_shipping",""+et_shippingaddress.getText().toString());*/

        //callAppCreateAddressApi(customer_id, firstName, lastName, countryid, postcode, city, telephone, fax, company, street);

        return false;
    }

    private void loadfrag() {
        cart_delivery_models.clear();
        Bundle bundle = new Bundle();
        bundle.putString("Firstname_shipping", "" + et_shippingfirstname.getText().toString());
        bundle.putString("Lastname_shipping", "" + et_shippinglastname.getText().toString());
        bundle.putString("Zipcode_shipping", "" + et_shippingzipcode.getText().toString());
        bundle.putString("City_shipping", "" + et_shippingcity.getText().toString());
        bundle.putString("Phonenumber_shipping", "" + et_shippingphonenumber.getText().toString());
        bundle.putString("Company_shipping", "" + et_shippingcompany.getText().toString());
        bundle.putString("streetadd_shipping", "" + et_streetadd.getText().toString());
        bundle.putString("region_shipping", "" + et_shippingregion.getText().toString());
        bundle.putString("Countryid_shipping", "" + countryid);
        bundle.putString("customer_id_shipping", "" + customer_id);
        bundle.putString("saveadd_shipping", "" + setdefault);
        bundle.putString("shippingmethod", "" + shippingmethod);
        bundle.putString("email_id_shipping", "" + email_id);
        bundle.putString("quote_id_shipping", "" + qidd);
        Fragment myFragment = new Payment_fragment();
        myFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_checkout, myFragment).addToBackStack(null).commit();

    }


    private void Countrylist() {
        cart_delivery_models.clear();
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        final Call<ResponseBody> countrylist = api.get_country_list();
        countrylist.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response", "" + response.body().toString());
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    Log.e("status_wishlist", "" + status);
                    if (status.equalsIgnoreCase("Success")) {
                        String countries = jsonObject.getString("countries");
                        JSONArray jsonArray = jsonObject.getJSONArray("countries");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject wish_object = jsonArray.getJSONObject(i);
                                Log.e("valuessss", "" + wish_object.getString("values"));
                                country_name.add(wish_object.getString("label"));
                                country_name_code.add(wish_object.getString("values"));
                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {
                            }
                        }

                        spinner_country_Name.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, country_name));

                        Log.e("shipping_167", "" + MyAddress_Preference.getCountryId(getActivity()));
                        int selected_spinner_pos = country_name_code.indexOf(MyAddress_Preference.getCountryId(getActivity()));
                        Log.e("pos_344", "" + selected_spinner_pos);
                        spinner_country_Name.setSelection(selected_spinner_pos, true);

                    }
                } catch (Exception e) {
                    Log.e("", "" + e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private void loadFragment(Fragment fragment) {
        Log.e("clickone", "");
        android.support.v4.app.FragmentManager manager = getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout_checkout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void CALL_CART_DELIVERY() {
        cart_delivery_models.clear();
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> shippingmethods = api.getShippingMethods();
        shippingmethods.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response_shipping_methods", "" + response.body().toString());
                //progressBar.setVisibility(View.GONE);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    String msg = jsonObject.getString("message");
                    if (status.equalsIgnoreCase("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("shipping_method");
                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject obj = jsonArray.getJSONObject(j);
                            JSONArray val_array = obj.getJSONArray("value");
                            for (int k = 0; k < val_array.length(); k++) {
                                try {
                                    JSONObject val_object = val_array.getJSONObject(k);
                                    cart_delivery_models.add(new Cart_Delivery_Model(val_object.getString("code"),
                                            val_object.getString("method"), val_object.getString("title"),
                                            val_object.getString("price")));

                                } catch (Exception e) {
                                    Log.e("Exception", "" + e);
                                } finally {
                                    cart_delivery_adapter.notifyDataSetChanged();
                                }

                            }

                        }

                    } else if (status.equalsIgnoreCase("error")) {
                        Toast.makeText(getContext(), "" + msg, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.e("Exception", "" + e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AllocateMEmory(View v) {
        recyclerview_item_delivery = (RecyclerView) v.findViewById(R.id.recyclerview_item_delivery);
        iv_continue_payment = (ImageView) v.findViewById(R.id.iv_continue_payment);
        lv_continue_payment = (LinearLayout) v.findViewById(R.id.lv_continue_payment);
        spinner_country_Name = (Spinner) v.findViewById(R.id.spinner_country_Name);

        et_shippingfirstname = (EditText) v.findViewById(R.id.et_shippingfirstname);
        et_shippinglastname = (EditText) v.findViewById(R.id.et_shippinglastname);
        et_shippingphonenumber = (EditText) v.findViewById(R.id.et_shippingphonenumber);
        et_shippingcompany = (EditText) v.findViewById(R.id.et_shippingcompany);
        /*et_shippingaddress = (EditText) v.findViewById(R.id.et_shippingaddress);*/
        et_streetadd = (EditText) v.findViewById(R.id.et_street);
        et_shippingzipcode = (EditText) v.findViewById(R.id.et_shippingzipcode);
        et_shippingcity = (EditText) v.findViewById(R.id.et_shippingcity);
        et_shippingregion = (EditText) v.findViewById(R.id.et_shippingregion);
        ck_default = (CheckBox) v.findViewById(R.id.ck_default);

        tv_fname = (TextView) v.findViewById(R.id.tv_fname);
        tv_lname = (TextView) v.findViewById(R.id.tv_lname);
        tv_pno = (TextView) v.findViewById(R.id.tv_pno);
        tv_comp = (TextView) v.findViewById(R.id.tv_comp);
        tv_street = (TextView) v.findViewById(R.id.tv_street);
        tv_country = (TextView) v.findViewById(R.id.tv_country);
        tv_zip = (TextView) v.findViewById(R.id.tv_zip);
        tv_city = (TextView) v.findViewById(R.id.tv_city);
        tv_reg = (TextView) v.findViewById(R.id.tv_reg);
        tv_rad_label = (TextView) v.findViewById(R.id.tv_rad_label);
        tv_delivery = (TextView) v.findViewById(R.id.tv_delivery);
        tv_continue_payment = (TextView) v.findViewById(R.id.tv_continue_payment);

        cart_delivery_adapter = new Cart_Delivery_Adapter(getActivity(), cart_delivery_models);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setReverseLayout(false);
        layoutManager.findLastVisibleItemPosition();
        recyclerview_item_delivery.setLayoutManager(layoutManager);
        recyclerview_item_delivery.setAdapter(cart_delivery_adapter);
        recyclerview_item_delivery.setHasFixedSize(true);

    }

}
