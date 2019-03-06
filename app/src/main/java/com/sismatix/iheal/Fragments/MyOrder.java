package com.sismatix.iheal.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sismatix.iheal.Adapter.My_orderlist_Adapter;
import com.sismatix.iheal.Adapter.Wishlist_Adapter;
import com.sismatix.iheal.Model.My_order_model;
import com.sismatix.iheal.Model.Wishlist_Model;
import com.sismatix.iheal.Preference.CheckNetwork;
import com.sismatix.iheal.Preference.Login_preference;
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

import static com.sismatix.iheal.Fragments.Wishlist_fragment.progressBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrder extends Fragment {

    RecyclerView recycler_orderlist;
    private static List<My_order_model> my_order_models = new ArrayList<My_order_model>();
    private static My_orderlist_Adapter my_orderlist_adapter;
    ProgressBar progressBar;

    public MyOrder() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_my_order,container, false);
        AllocateMemory(v);

        my_orderlist_adapter = new My_orderlist_Adapter(getActivity(), my_order_models);
        recycler_orderlist.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycler_orderlist.setAdapter(my_orderlist_adapter);
        // snapHelper.attachToRecyclerView(recycler_wishlist);

        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            CALL_Orderlist_API();
        } else {
            Toast.makeText(getContext(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    private void CALL_Orderlist_API() {
        progressBar.setVisibility(View.VISIBLE);
        my_order_models.clear();

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        final Call<ResponseBody> wishlist = api.AppOrderList(Login_preference.getcustomer_id(getActivity()));
        wishlist.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("responseeeeee_wishlist", "" + response.body().toString());
                progressBar.setVisibility(View.GONE);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    Log.e("status_wishlist", "" + status);
                    if (status.equalsIgnoreCase("Success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("order");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject wish_object = jsonArray.getJSONObject(i);
                                Log.e("Name_wishlist", "" + wish_object.getString("name"));
                                my_order_models.add(new My_order_model("" + wish_object.getString("increment_id"),
                                        "" + wish_object.getString("created_at"),
                                        "" + wish_object.getString("name"),
                                        "" + wish_object.getString("grand_total"),
                                        ""+wish_object.getString("Paymentmethod"),
                                        ""+wish_object.getString("order_id")));
                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {
                                my_orderlist_adapter.notifyItemChanged(i);
                                my_orderlist_adapter.notifyDataSetChanged();
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
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void AllocateMemory(View v) {

        recycler_orderlist=(RecyclerView)v.findViewById(R.id.recycler_orderlist);
        progressBar=(ProgressBar)v.findViewById(R.id.progressBar);

    }

}
