package com.sismatix.iheal.Fragments;


import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sismatix.iheal.Activity.Navigation_drawer_activity;
import com.sismatix.iheal.Adapter.Cart_List_Adapter;
import com.sismatix.iheal.Adapter.Wishlist_Adapter;
import com.sismatix.iheal.Model.Cart_Model;
import com.sismatix.iheal.Model.Wishlist_Model;
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
public class Wishlist_fragment extends Fragment {
    RecyclerView recycler_wishlist;
    private static List<Wishlist_Model> wishlist_models = new ArrayList<Wishlist_Model>();
    private static Wishlist_Adapter wishlist_adapter;
    View v;
    public static Toolbar toolbar_mywishlist;

    public static LayerDrawable icon;
    public String count = "1";
    public static CountDrawable badge;
    static ProgressBar progressBar;
    static AppCompatActivity activity;
    TextView tv_wishlist_title;

    public Wishlist_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_wishlist_fragment, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        AllocateMemory(v);

        //option manu
        setHasOptionsMenu(true);
        tv_wishlist_title.setTypeface(Home.roboto_bold);
        toolbar_mywishlist = (Toolbar) v.findViewById(R.id.toolbar_mywishlist);
        // toolbar_mywishlist.setTitle("My Wishlist");

        ((Navigation_drawer_activity) getActivity()).setSupportActionBar(toolbar_mywishlist);
        ((Navigation_drawer_activity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((Navigation_drawer_activity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_36dp);

        wishlist_adapter = new Wishlist_Adapter(getContext(), wishlist_models);
        recycler_wishlist.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycler_wishlist.setAdapter(wishlist_adapter);
        // snapHelper.attachToRecyclerView(recycler_wishlist);

        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            CALL_WISHLIST_API();
        } else {
            Toast.makeText(getContext(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
        }

        return v;

    }

    public static void CALL_WISHLIST_API() {
        progressBar.setVisibility(View.VISIBLE);

        wishlist_models.clear();

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        final Call<ResponseBody> wishlist = api.GetWishlist(Login_preference.getcustomer_id(activity));
        //cartList.clear();
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
                    if (status.equalsIgnoreCase("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("product");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject wish_object = jsonArray.getJSONObject(i);
                                Log.e("Name_wishlist", "" + wish_object.getString("name"));
                                wishlist_models.add(new Wishlist_Model("" + wish_object.getString("image"),
                                        "" + wish_object.getString("name"),
                                        "" + wish_object.getString("price"),
                                        "" + wish_object.getString("category"),
                                        "" + wish_object.getString("product_id")));
                            } catch (Exception e) {
                                Log.e("Exception", "" + e);
                            } finally {
                                wishlist_adapter.notifyItemChanged(i);
                                wishlist_adapter.notifyDataSetChanged();
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
                Toast.makeText(activity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void AllocateMemory(View v) {
        recycler_wishlist = (RecyclerView) v.findViewById(R.id.recycler_wishlist);
        tv_wishlist_title = (TextView)v.findViewById(R.id.tv_wishlist_title);
        activity = (AppCompatActivity) getContext();
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.cart, menu);
        MenuItem item = menu.findItem(R.id.cart);
        /*MenuItem item_search = menu.findItem(R.id.search);
        item_search.setVisible(false);*/

//        icon = (LayerDrawable) item.getIcon();
        icon = (LayerDrawable) item.getIcon();

        CountDrawable badge;
        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse != null && reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(getActivity());
        }
        count = Login_preference.getCart_item_count(getActivity());
        Log.e("countt", "" + Login_preference.getCart_item_count(getActivity()));
        Log.e("count_142", "" + count);
        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cart:
                Toast.makeText(getActivity(), "cart Icon Click", Toast.LENGTH_SHORT).show();
                return true;

            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
