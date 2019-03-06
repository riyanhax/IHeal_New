package com.sismatix.iheal.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sismatix.iheal.Adapter.Product_Category_adapter;
import com.sismatix.iheal.Adapter.Product_recycler_adapter;
import com.sismatix.iheal.Model.Product_Category_model;
import com.sismatix.iheal.Model.Product_Grid_Model;
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

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class Product_category_freg extends Fragment {
    View view;
    RecyclerView recycler_product_category;
    private List<Product_Category_model> product_model = new ArrayList<Product_Category_model>();
    private Product_Category_adapter product_category_adapter;
    ProgressBar progressBar;

    public Product_category_freg() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product_category_freg, container, false);

        recycler_product_category = (RecyclerView) view.findViewById(R.id.recycler_product_category);
        progressBar = view.findViewById(R.id.progressBar);

        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            CALL_PRODUCT_CATEGORY_API();
        } else {
            Toast.makeText(getContext(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
        }

        product_category_adapter = new Product_Category_adapter(getActivity(), product_model);
        recycler_product_category.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recycler_product_category.setItemAnimator(new DefaultItemAnimator());
        // recycler_product.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recycler_product_category.setAdapter(product_category_adapter);


        return view;
    }
    private void CALL_PRODUCT_CATEGORY_API() {
        progressBar.setVisibility(View.VISIBLE);

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> categorylist = api.categorylist("all");

        categorylist.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response", "" + response.body().toString());
                progressBar.setVisibility(View.GONE);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    Log.e("status_prod_cat",""+status);
                    if (status.equalsIgnoreCase("success")){
                    String category=jsonObject.getString("category");
                    Log.e("catttt_prod_cat",""+category);
                    JSONArray jsonArray=jsonObject.getJSONArray("category");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            try {
                                JSONObject vac_object = jsonArray.getJSONObject(i);
                                Log.e("Name",""+vac_object.getString("name"));
                                product_model.add(new Product_Category_model(vac_object.getString("name"),vac_object.getString("value")));

                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {
                                product_category_adapter.notifyItemChanged(i);
                            }

                        }

                    }else if (status.equalsIgnoreCase("error")){
                    }

                }catch (Exception e){
                    Log.e("",""+e);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
