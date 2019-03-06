package com.sismatix.iheal.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.sismatix.iheal.Activity.Navigation_drawer_activity;
import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.R;
import com.sismatix.iheal.Retrofit.ApiClient;
import com.sismatix.iheal.Retrofit.ApiInterface;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;
/**
 * A simple {@link Fragment} subclass.
 */
public class TransParant_Hair_care_freg extends Fragment {

    View v;

    android.support.v7.widget.Toolbar toolbar_hair_care;
    AppBarLayout appbar;
    Button btn_haircare_ex_prod;
    String cat_id;
    String products;
    TextView tv_hair,tv_item_count;

    public TransParant_Hair_care_freg() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_trans_parant__hair_care_freg, container, false);
        toolbar_hair_care = (android.support.v7.widget.Toolbar) v.findViewById(R.id.toolbar_hair_care);
        btn_haircare_ex_prod=(Button)v.findViewById(R.id.btn_haircare_ex_prod);
        appbar = (AppBarLayout) v.findViewById(R.id.appbar_transparent);
        tv_hair = (TextView)v.findViewById(R.id.tv_hair);
        tv_item_count = (TextView)v.findViewById(R.id.tv_item_count);

        setHasOptionsMenu(true);

        Bundle bundle = this.getArguments();

        cat_id =bundle.getString("cat_id");

        Log.e("category_id_thcf",""+cat_id);

        Addcategoryproduct(cat_id);

        btn_haircare_ex_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new Hair_Cair_fregment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, myFragment).addToBackStack(null).commit();
*/

                Bundle b=new Bundle();
                b.putString("products_array",products);
                b.putString("cat_id",cat_id);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new Hair_Cair_fregment();
                myFragment.setArguments(b);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, myFragment).addToBackStack(null).commit();



            }
        });
        if (toolbar_hair_care != null) {
            ((Navigation_drawer_activity) getActivity()).setSupportActionBar(toolbar_hair_care);
        }
        if (toolbar_hair_care != null) {
            ((Navigation_drawer_activity) getActivity()).getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(true);

            toolbar_hair_care.setNavigationIcon(R.drawable.ic_dehaze_white_36dp);
        }
        toolbar_hair_care.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Navigation_drawer_activity) getActivity()).getmDrawerLayout()
                        .openDrawer(GravityCompat.START);
            }
        });
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.trans));
        appbar.setBackgroundColor(getResources().getColor(R.color.trans));
        toolbar_hair_care.setBackgroundColor(getResources().getColor(R.color.trans));
        return v;
    }
    private void Addcategoryproduct(String category_id) {
        //making api call
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> addcategory = api.addcategoryprod(category_id);

        addcategory.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response", "" + response.body().toString());

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    Log.e("status",""+status);

                    String title = jsonObject.getString("title");
                    String count = jsonObject.getString("count");
                    String categoryimage = jsonObject.getString("categoryimage");

                    products = jsonObject.getString("products");
                    Log.e("prods",""+products);

                    Log.e("title",""+title);
                    Log.e("count",""+count);
                    Log.e("categoryimage",""+categoryimage);

                    if (status.equalsIgnoreCase("success")){
                       // Toast.makeText(getContext(),""+meassg, Toast.LENGTH_SHORT).show();

                        tv_hair.setText(title);
                        tv_item_count.setText(count+" "+getString(R.string.item));


                    }else if (status.equalsIgnoreCase("error")){
                       // Toast.makeText(getContext(), ""+meassg, Toast.LENGTH_SHORT).show();
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
