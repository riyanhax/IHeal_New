package com.sismatix.iheal.Adapter;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sismatix.iheal.Model.Cart_Delivery_Model;
import com.sismatix.iheal.R;

import java.util.List;


public class Cart_Delivery_Adapter extends RecyclerView.Adapter<com.sismatix.iheal.Adapter.Cart_Delivery_Adapter.MyViewHolder> {
    private Context context;
    private List<Cart_Delivery_Model> model;
    //int minteger = 1;
    int current_price = 30;
  public static   int selectedPosition = -1;
    public static String shippingmethod;
    //int product_total = current_price;

    public Cart_Delivery_Adapter(Context context, List<Cart_Delivery_Model> cartList) {
        this.context = context;
        this.model = cartList;
    }

    @Override
    public Cart_Delivery_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shipping_delivery_row, parent, false);
        return new Cart_Delivery_Adapter.MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final Cart_Delivery_Adapter.MyViewHolder holder, final int position) {
        final Cart_Delivery_Model cart_delivery_model = model.get(position);

        holder.tv_product_delivery_type.setText(cart_delivery_model.getTitle());
        Log.e("shipping_title",""+cart_delivery_model.getTitle());
        holder.tv_delivery_price.setText(cart_delivery_model.getPrice());
        Log.e("shipping_price",""+cart_delivery_model.getPrice());

        holder.lv_greylayout_click.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                shippingmethod=cart_delivery_model.getCode();
                notifyDataSetChanged();
                Toast.makeText(context, "lv_click"+shippingmethod, Toast.LENGTH_SHORT).show();
            }
        });

        if (selectedPosition == position) {
            Log.e("selectedpo_76",""+selectedPosition);
            holder.itemView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.round_boder_green));
            holder.tv_delivery_price.setTextColor(context.getColor(R.color.colorPrimary));
            holder.tv_product_delivery_type.setTextColor(context.getColor(R.color.colorPrimary));
            holder.lv_greenlayout.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.round_boder_green));
        } else {
            holder.itemView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.round_boder_white));
            holder.tv_delivery_price.setTextColor(context.getColor(R.color.titlepayal));
            holder.tv_product_delivery_type.setTextColor(context.getColor(R.color.textcolor));
            holder.lv_greenlayout.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.round_boder_white));
        }

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_delivery_price, tv_product_delivery_type;
        LinearLayout lv_greylayout_click, lv_greenlayout;

        public MyViewHolder(View view) {
            super(view);
            tv_delivery_price = (TextView) view.findViewById(R.id.tv_delivery_price);
            tv_product_delivery_type = (TextView) view.findViewById(R.id.tv_product_delivery_type);
            lv_greylayout_click = (LinearLayout) view.findViewById(R.id.lv_greylayout_click);
            lv_greenlayout = (LinearLayout) view.findViewById(R.id.lv_greenlayout);
        }
    }
}