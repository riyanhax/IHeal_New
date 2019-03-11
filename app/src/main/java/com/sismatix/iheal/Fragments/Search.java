package com.sismatix.iheal.Fragments;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sismatix.iheal.Activity.Search_activity;
import com.sismatix.iheal.Adapter.Product_recycler_adapter;
import com.sismatix.iheal.Adapter.SlideingImageAdapter;
import com.sismatix.iheal.Model.Cart_Model;
import com.sismatix.iheal.Model.Product_Category_model;
import com.sismatix.iheal.Model.Product_Grid_Model;
import com.sismatix.iheal.Model.sliderimage_model;
import com.sismatix.iheal.Preference.CheckNetwork;
import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.R;
import com.sismatix.iheal.Retrofit.ApiClient;
import com.sismatix.iheal.Retrofit.ApiInterface;
import com.sismatix.iheal.View.CountDrawable;

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
public class Search extends Fragment implements SearchView.OnQueryTextListener {

    SearchView searchView;
    RecyclerView recyclerview_search;
    private List<Product_Grid_Model> product_model = new ArrayList<Product_Grid_Model>();
    private Product_recycler_adapter product_adapter;

    LinearLayout lv_search,lv_nodatafound;
    TextView tv_search, text;
    View v;

    public Search() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_search, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        setHasOptionsMenu(true);
        lv_search = (LinearLayout)v.findViewById(R.id.lv_nodatafound);
        lv_nodatafound = (LinearLayout)v.findViewById(R.id.lv_nodatafound);

        searchView = (SearchView)v.findViewById(R.id.search);
        recyclerview_search = (RecyclerView)v.findViewById(R.id.recyclerview_search);

        product_adapter = new Product_recycler_adapter(getActivity(), product_model);
        recyclerview_search.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerview_search.setItemAnimator(new DefaultItemAnimator());
        recyclerview_search.setAdapter(product_adapter);

        searchView.setOnQueryTextListener(this);

       /* lv_search = (LinearLayout) v.findViewById(R.id.lv_search);
        tv_search = (TextView) v.findViewById(R.id.tv_search);*/
        //text = (TextView) v.findViewById(R.id.text);

       /* lv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Search", Toast.LENGTH_SHORT).show();
                *//*Intent i=new Intent(getActivity(), Search_activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);*//*
                *//* getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);*//*
            }
        });*/

        return v;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();


        super.onCreateOptionsMenu(menu, inflater);
    }
    private void CALL_Search_Api(String text) {

       // progressBar.setVisibility(View.VISIBLE);
        product_model.clear();
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> addcategory = api.AppSearchCategory(text);
        addcategory.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response", "" + response.body().toString());
           //     progressBar.setVisibility(View.GONE);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    Log.e("status", "" + status);
                    product_model.clear();
                  //  String title = jsonObject.getString("title");
                  //  collapsingToolbar.setTitle(title);
                  //  String categoryimage = jsonObject.getString("categoryimage");
                   // Log.e("categoryimage", "" + categoryimage);
                  // // Glide.with(getContext()).load(categoryimage).into(header);

                    if (status.equalsIgnoreCase("Success")) {
                        String products = jsonObject.getString("products");
                        if (products.equalsIgnoreCase("[]")) {
                            Log.e("nulll", "");

                            //lv_productnotfound.setVisibility(View.VISIBLE);
                        } else {
                            //lv_productnotfound.setVisibility(View.GONE);
                        }
                        JSONArray jsonArray = new JSONArray(products);
                        Log.e("arrprod", "" + jsonArray);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject vac_object = jsonArray.getJSONObject(i);
                                Log.e("prod_name", "" + vac_object.getString("sku"));
                                product_model.add(new Product_Grid_Model(vac_object.getString("image"),
                                        vac_object.getString("price"),
                                        vac_object.getString("name"),
                                        vac_object.getString("name"),
                                        vac_object.getString("product_id"),
                                        "image"));

                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {
                                product_adapter.notifyItemChanged(i);
                                product_adapter.notifyDataSetChanged();

                            }

                        }

                    } else if (status.equalsIgnoreCase("error")) {
                        // Toast.makeText(getContext(), ""+meassg, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.e("", "" + e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });



    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        Log.e("serch_text",""+text);
        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            product_model.clear();
            CALL_Search_Api(text);
        } else {
            Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
