package com.sismatix.iheal.Adapter;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonArray;
import com.sismatix.iheal.Activity.Navigation_drawer_activity;
import com.sismatix.iheal.Fragments.Cart;
import com.sismatix.iheal.Fragments.Hair_Cair_fregment;
import com.sismatix.iheal.Fragments.Home;
import com.sismatix.iheal.Fragments.Item_details;
import com.sismatix.iheal.Model.Cart_Model;
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


import static com.sismatix.iheal.Fragments.Cart.prepare_Cart;
import static java.security.AccessController.getContext;

public class Cart_List_Adapter extends RecyclerView.Adapter<Cart_List_Adapter.MyViewHolder> {
    private Context context;
    private List<Cart_Model> cartList;
    int quantity;
    int current_price = 30;
    int product_total = current_price;
    Call<ResponseBody> remove_from_cart = null;
    String itemid_cart, quoteid_cart, item_qty;

    public static String cart_item_grand_total;

    public Cart_List_Adapter(Context context, List<Cart_Model> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_item_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Cart_Model cart_model = cartList.get(position);
        holder.tv_cart_product_title.setTypeface(Home.roboto_bold);
        holder.tv_cart_product_description.setTypeface(Home.roboto_light);
        holder.tv_product_price_total.setTypeface(Home.roboto_black);

        item_qty = cart_model.getProduct_qty();
        Log.e("item_qty_81", "" + item_qty);

        Navigation_drawer_activity.Check_String_NULL_Value(holder.tv_cart_product_title, cart_model.getProduct_name());
        Navigation_drawer_activity.Check_String_NULL_Value(holder.tv_product_price_total, cart_model.getProduct_price());
        Navigation_drawer_activity.Check_String_NULL_Value(holder.tv_cart_product_description, cart_model.getProduct_description());
        Navigation_drawer_activity.Check_String_NULL_Value(holder.tv_cart_quantity_total, cart_model.getProduct_qty());

        //  holder.tv_cart_product_title.setText(cart_model.getProduct_name());
        //holder.tv_product_price_total.setText(cart_model.getProduct_price());
        //holder.tv_cart_product_description.setText(cart_model.getProduct_description());
        //holder.tv_cart_quantity_total.setText(cart_model.getProduct_qty());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.app_logo_placeholder);
        requestOptions.error(R.drawable.app_logo_placeholder);

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(cart_model.getProduct_image()).into(holder.iv_cart_product_image);

        Log.e("total_price", "" + cart_model.getProduct_price());
        Log.e("itemid_cart", "" + cart_model.getItemid());
        Log.e("quoteid_cart", "" + Login_preference.getquote_id(context));

        holder.iv_cart_quantity_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.iv_cart_quantity_increase.setEnabled(false);

                int textqut = Integer.parseInt(holder.tv_cart_quantity_total.getText().toString());
                //quantity = textqut + 1;
                int Result = textqut + 1;
                itemid_cart = cart_model.getItemid();
                quoteid_cart = Login_preference.getquote_id(context);
                callAppUpdateCart(Result, itemid_cart, quoteid_cart, view, holder);
            }

        });

        holder.iv_cart_quantity_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.iv_cart_quantity_decrease.setEnabled(false);
                int textqut = Integer.parseInt(holder.tv_cart_quantity_total.getText().toString());
                itemid_cart = cart_model.getItemid();
                quoteid_cart = Login_preference.getquote_id(context);
                if (textqut != 0) {
                    int Result = textqut - 1;
                    if (Result == 0) {
                        Result = 1;
                        callAppUpdateCart(Result, itemid_cart, quoteid_cart, view, holder);
                        // product_total = product_total - current_price;
                    } else {
                        callAppUpdateCart(Result, itemid_cart, quoteid_cart, view, holder);
                    }
                } else {
                }
            }
        });

    }

    private void callAppUpdateCart(final int Resultt, String itemid_carttt, String quoteid_carttt, final View view, final MyViewHolder holder) {
        holder.cart_count_pb.setVisibility(View.VISIBLE);

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> appupdate = api.appUpdatecart(quoteid_carttt, String.valueOf(Resultt), itemid_carttt);
        appupdate.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("responseeeeee", "" + response.body().toString());

                JSONObject jsonObject = null;
                try {
                    holder.cart_count_pb.setVisibility(View.GONE);
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    Log.e("status_prepare_cart", "" + status);
                    if (status.equalsIgnoreCase("success")) {
                        holder.iv_cart_quantity_increase.setEnabled(true);
                        holder.iv_cart_quantity_decrease.setEnabled(true);

                        //prepare_Cart();

                        holder.tv_cart_quantity_total.setText(String.valueOf(Resultt));

                        //holder.tv_cart_quantity_total.setText(item_qty);

                    } else if (status.equalsIgnoreCase("error")) {

                    }

                } catch (Exception e) {
                    Log.e("exception", "" + e);
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
        return cartList.size();
    }

    public void removeItem(int position) {
        String product_id = cartList.get(position).getProduct_id();
        Log.e("remove_product_id_113", "" + product_id);
        CALL_REMOVE_FROM_CART_API(product_id);
        cartList.remove(position);
        notifyItemRemoved(position);
    }

    public void CALL_REMOVE_FROM_CART_API(String proddd_id) {
        String loginflag = Login_preference.getLogin_flag(context);
        if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            remove_from_cart = api.remove_from_cartlist(proddd_id, Login_preference.getemail(context));
            Log.e("proddd_idddd", "" + proddd_id);
        } else {
            String quote_id = Login_preference.getquote_id(context);
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            remove_from_cart = api.withoutlogin_remove_from_cartlist(proddd_id, quote_id);
            Log.e("proddd_idddd", "" + proddd_id);
        }
        remove_from_cart.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("responjse_remove_cart", "" + response.body().toString());
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    Log.e("status_remove", "" + status);
                    if (status.equalsIgnoreCase("Success")) {
                        cart_item_grand_total = jsonObject.getString("grand_total");
                        Cart.tv_maintotal.setText(cart_item_grand_total);
                        Navigation_drawer_activity.Check_String_NULL_Value(Cart.tv_maintotal, cart_item_grand_total);
                        Cart.cart_items_count = jsonObject.getString("items_count");
                        Login_preference.setCart_item_count(context, Cart.cart_items_count);

                        if (jsonObject.getString("items_count").equalsIgnoreCase("null") || jsonObject.getString("items_count").equals("")) {

                            Navigation_drawer_activity.tv_bottomcount.setText("0");
                            Navigation_drawer_activity.item_count.setText("0");
                            if (Item_details.count.equalsIgnoreCase("null") || Item_details.count.equals("")) {
                                Item_details.badge.setCount("0");
                            } else {
                                Item_details.badge.setCount(Item_details.count);
                            }
                            Item_details.icon.mutate();
                            Item_details.icon.setDrawableByLayerId(R.id.ic_group_count, Item_details.badge);

                            Log.e("count_remove_40", "" + jsonObject.getString("items_count"));

                        } else {
                            Log.e("count_remove_80", "" + jsonObject.getString("items_count"));

                            Navigation_drawer_activity.tv_bottomcount.setText(jsonObject.getString("items_count"));

                            Navigation_drawer_activity.item_count.setText(jsonObject.getString("items_count"));
                            Item_details.count = jsonObject.getString("items_count");


                            if (Item_details.count.equalsIgnoreCase("null") || Item_details.count.equals("")) {
                                Log.e("count_40", "" + jsonObject.getString("items_count"));

                                Item_details.badge.setCount("0");
                                Item_details.icon.mutate();
                                Item_details.icon.setDrawableByLayerId(R.id.ic_group_count, Item_details.badge);

                            } else {
                                Log.e("count_80", "" + jsonObject.getString("items_count"));

                                Item_details.badge.setCount(jsonObject.getString("items_count"));
                                Item_details.icon.mutate();
                                Item_details.icon.setDrawableByLayerId(R.id.ic_group_count, Item_details.badge);

                            }

                        }

                        Toast.makeText(context, "" + status, Toast.LENGTH_SHORT).show();
                        // prepare_Cart();
                    } else if (status.equalsIgnoreCase("error")) {
                        // Toast.makeText(context, ""+meassg, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("exception", "" + e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_cart_product_title, tv_cart_product_description, tv_product_price_total, tv_cart_quantity_total;
        public ImageView iv_cart_product_image, iv_cart_quantity_decrease, iv_cart_quantity_increase;
        public RelativeLayout viewForeground;
        ProgressBar cart_count_pb;

        public MyViewHolder(View view) {
            super(view);
            tv_cart_product_description = (TextView) view.findViewById(R.id.tv_cart_product_description);
            tv_cart_product_title = (TextView) view.findViewById(R.id.tv_cart_product_title);
            tv_product_price_total = (TextView) view.findViewById(R.id.tv_product_price_total);
            tv_cart_quantity_total = (TextView) view.findViewById(R.id.tv_cart_quantity_total);
            iv_cart_product_image = (ImageView) view.findViewById(R.id.iv_cart_product_image);
            iv_cart_quantity_decrease = (ImageView) view.findViewById(R.id.iv_cart_quantity_decrease);
            iv_cart_quantity_increase = (ImageView) view.findViewById(R.id.iv_cart_quantity_increase);
            viewForeground = (RelativeLayout) view.findViewById(R.id.view_foreground);
            cart_count_pb = (ProgressBar) view.findViewById(R.id.cart_count_pb);
        }
    }
}

