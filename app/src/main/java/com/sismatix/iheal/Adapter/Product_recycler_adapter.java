package com.sismatix.iheal.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sismatix.iheal.Fragments.Home;
import com.sismatix.iheal.Fragments.Item_details;
import com.sismatix.iheal.Model.Product_Grid_Model;
import com.sismatix.iheal.R;

import java.util.List;


public class Product_recycler_adapter extends RecyclerView.Adapter<Product_recycler_adapter.MyViewHolder> {
    private Context context;
    private List<Product_Grid_Model> models;
    String iddd;

    public Product_recycler_adapter(Context context, List<Product_Grid_Model> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public Product_recycler_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_grid_row, parent, false);

        return new Product_recycler_adapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final Product_recycler_adapter.MyViewHolder holder, final int position) {
        final Product_Grid_Model product_model = models.get(position);

        holder.lv_product_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        iddd = product_model.getProduct_id();
                        Log.e("iddddddddddd", "" + iddd);
                        Bundle b = new Bundle();
                        b.putString("prod_id", iddd);
                        Log.e("productidd", "" + iddd);
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        Fragment myFragment = new Item_details();
                        myFragment.setArguments(b);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, myFragment).addToBackStack(null).commit();
                    }
                }, 1000);
            }
        });

        holder.tv_product_name.setTypeface(Home.roboto_bold);
        holder.tv_product_price.setTypeface(Home.roboto_bold);

        holder.tv_product_name.setText(product_model.getProducr_title());
        Log.e("titleeeee",""+product_model.getProducr_title());
        holder.tv_product_price.setText(product_model.getProduct_price());
        Glide.with(context).load(product_model.getProduct_image()).into(holder.iv_product_image);

    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_product_image;
        TextView tv_product_name, tv_product_price;
        LinearLayout lv_img_click,lv_product_click;
        ProgressBar progress_grid;

        public MyViewHolder(View view) {
            super(view);
            tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
            tv_product_price = (TextView) view.findViewById(R.id.tv_product_price);
            iv_product_image = (ImageView) view.findViewById(R.id.iv_product_image);
            lv_product_click = (LinearLayout) view.findViewById(R.id.lv_product_click);

        }
    }
    /*Last commited*/
}



