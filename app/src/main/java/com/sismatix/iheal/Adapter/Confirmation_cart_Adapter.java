package com.sismatix.iheal.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sismatix.iheal.Model.Cart_Model;
import com.sismatix.iheal.R;

import java.util.List;

import static com.sismatix.iheal.Fragments.Cart.qoute_id_cart;


public class Confirmation_cart_Adapter extends RecyclerView.Adapter<Confirmation_cart_Adapter.MyViewHolder> {
    private Context context;
    private List<Cart_Model> cartList;

    public Confirmation_cart_Adapter(Context context, List<Cart_Model> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public Confirmation_cart_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.confirmation_cartlist_row, parent, false);

        return new Confirmation_cart_Adapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final Confirmation_cart_Adapter.MyViewHolder holder, final int position) {
        final Cart_Model cart_model = cartList.get(position);

        holder.tv_cart_product_title.setText(cart_model.getProduct_name());
        Glide.with(context).load(cart_model.getProduct_image()).into(holder.iv_cart_product_image);
        holder.tv_product_price_total.setText(cart_model.getProduct_price());
        holder.tv_cart_product_description.setText(cart_model.getProduct_description());
        String tot_qty = cart_model.getProduct_qty();
        holder.tv_cart_quantity_total.setText(tot_qty);

    }



    @Override
    public int getItemCount() {
        return cartList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_cart_product_title, tv_cart_product_description, tv_product_price_total, tv_cart_quantity_total;
        public ImageView iv_cart_product_image;
        public RelativeLayout viewForeground;

        public MyViewHolder(View view) {
            super(view);
            tv_cart_product_description = (TextView) view.findViewById(R.id.tv_cart_product_description);
            tv_cart_product_title = (TextView) view.findViewById(R.id.tv_cart_product_title);
            tv_product_price_total = (TextView) view.findViewById(R.id.tv_product_price_total);
            tv_cart_quantity_total = (TextView) view.findViewById(R.id.tv_cart_quantity_total);
            iv_cart_product_image = (ImageView) view.findViewById(R.id.iv_cart_product_image);
            viewForeground = (RelativeLayout) view.findViewById(R.id.view_foreground);

        }
    }
}



