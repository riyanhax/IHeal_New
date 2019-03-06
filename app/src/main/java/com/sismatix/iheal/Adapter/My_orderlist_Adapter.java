package com.sismatix.iheal.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sismatix.iheal.Fragments.Hair_Cair_fregment;
import com.sismatix.iheal.Fragments.Home;
import com.sismatix.iheal.Fragments.Item_details;
import com.sismatix.iheal.Fragments.MyOrderDetails;
import com.sismatix.iheal.Model.My_order_model;
import com.sismatix.iheal.Model.Wishlist_Model;
import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.R;
import com.sismatix.iheal.Retrofit.ApiClient;
import com.sismatix.iheal.Retrofit.ApiInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class My_orderlist_Adapter extends RecyclerView.Adapter<My_orderlist_Adapter.MyViewHolder> {
    Context context;
    private List<My_order_model> myorderModels;
    String orderidd,custidd;

    public My_orderlist_Adapter(FragmentActivity context, List<My_order_model> myorderModels) {
        this.context=context;
        this.myorderModels=myorderModels;
    }

    @NonNull
    @Override
    public My_orderlist_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_orderlist_row, viewGroup, false);
        return new My_orderlist_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull My_orderlist_Adapter.MyViewHolder holder, int position) {
        final My_order_model my_order_model = myorderModels.get(position);
        holder.tv_created_date.setTypeface(Home.roboto_medium);
        holder.tv_name.setTypeface(Home.roboto_medium);
        holder.tv_order_id.setTypeface(Home.roboto_medium);
        holder.tv_paymentmethod.setTypeface(Home.roboto_medium);
        holder.grand_total.setTypeface(Home.roboto_medium);
        holder.tv_wishlist_order_now.setTypeface(Home.roboto_medium);

        holder.tv_created_date.setText(my_order_model.getCreated_at());
        holder.tv_name.setText(my_order_model.getName());
        holder.tv_order_id.setText(my_order_model.getIncrement_id());
        holder.tv_paymentmethod.setText(my_order_model.getPayment_method());
        holder.grand_total.setText(my_order_model.getGrand_total());

        orderidd = my_order_model.getOrder_id();
        Log.e("orderidddd", "" + orderidd);
        custidd = Login_preference.getcustomer_id(context);
        Log.e("custidddd", "" + custidd);

        holder.tv_wishlist_order_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        /*String orderidd = my_order_model.getOrder_id();
                        Log.e("orderidddd", "" + orderidd);
                        Bundle b = new Bundle();
                        b.putString("orderrr_id", orderidd);
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        Fragment myFragment = new MyOrderDetails();
                        myFragment.setArguments(b);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, myFragment).addToBackStack(null).commit();*/

                        callReorderapi();


                    }
                }, 1000);
            }
        });

    }

    private void callReorderapi() {

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        final Call<ResponseBody> reorder = api.AppReorder(custidd,orderidd);
        reorder.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("responseeeeee_wishlist", "" + response.body().toString());
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String message = jsonObject.getString("message");
                    Log.e("message_reorder", "" + message);
                    String status = jsonObject.getString("status");
                    Log.e("status_wishlist", "" + status);
                    if (status.equalsIgnoreCase("Success")) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return myorderModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_created_date,tv_order_id,tv_name,tv_paymentmethod,grand_total,tv_wishlist_order_now,tv_wishlist_haircare,
                tv_orderid_title,tv_rec,tv_pm,tv_tot_total;

        public MyViewHolder(@NonNull View view) {
            super(view);
            tv_created_date = (TextView) view.findViewById(R.id.tv_created_date);
            tv_order_id = (TextView) view.findViewById(R.id.tv_order_id);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_paymentmethod = (TextView) view.findViewById(R.id.tv_paymentmethod);
            grand_total = (TextView) view.findViewById(R.id.grand_total);
            tv_wishlist_order_now=(TextView)view.findViewById(R.id.tv_wishlist_order_now);
        }
    }
}
