package com.sismatix.iheal.Adapter;

import android.content.Context;
import android.os.Build;
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

import com.sismatix.iheal.Fragments.Home;
import com.sismatix.iheal.Model.Cart_Delivery_Model;
import com.sismatix.iheal.Model.Payment_Method_Model;
import com.sismatix.iheal.R;

import java.util.List;


public class Payment_Method_Adapter  extends RecyclerView.Adapter<com.sismatix.iheal.Adapter.Payment_Method_Adapter.MyViewHolder> {
    private Context context;
    private List<Payment_Method_Model> model;
    //int minteger = 1;
    int current_price = 30;
    int selectedPosition = -1;
    public static String paymentcode_ada;
    //int product_total = current_price;

    public Payment_Method_Adapter (Context context, List<Payment_Method_Model> cartList) {
        this.context = context;
        this.model = cartList;
    }

    @Override
    public Payment_Method_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_method_row, parent, false);
        return new Payment_Method_Adapter.MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final Payment_Method_Adapter.MyViewHolder holder, final int position) {
        final Payment_Method_Model payment_model = model.get(position);

        holder.tv_payment_name.setTypeface(Home.roboto_bold);
        holder.tv_payment_name.setText(payment_model.getLabel());
        holder.lv_greylayout_click.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                paymentcode_ada = payment_model.getValue();
                Log.e("paycode",""+payment_model.getValue());
                notifyDataSetChanged();
                Toast.makeText(context, "lv_click", Toast.LENGTH_SHORT).show();
            }
        });

        if (selectedPosition == position) {
            holder.itemView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.round_boder_green));
            holder.tv_payment_name.setTextColor(context.getColor(R.color.colorPrimary));

        } else {
            holder.itemView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.round_boder_white));
            holder.tv_payment_name.setTextColor(context.getColor(R.color.titlepayal));

        }

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_payment_name;
        LinearLayout lv_greylayout_click, lv_greenlayout;

        public MyViewHolder(View view) {
            super(view);
            tv_payment_name = (TextView) view.findViewById(R.id.tv_payment_name);
            lv_greylayout_click = (LinearLayout) view.findViewById(R.id.lv_greylayout_click);
            lv_greenlayout = (LinearLayout) view.findViewById(R.id.lv_greenlayout);
        }
    }
}