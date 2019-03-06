package com.sismatix.iheal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sismatix.iheal.Model.Product_Grid_Model;
import com.sismatix.iheal.R;

import java.util.ArrayList;


    public class Product_grid_adapter extends BaseAdapter
    {

        private Context ctx;
        private ArrayList<Product_Grid_Model> gridmodel;
        LayoutInflater inflater ;
        public Product_grid_adapter(Context ctx,ArrayList<Product_Grid_Model> gridmodel)
        {
            this.ctx = ctx;
            this.gridmodel = gridmodel;
            inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount()
        {
            return gridmodel.size();
        }

        @Override
        public Object getItem(int i)
        {
            return gridmodel.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return i;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup)
        {
            View CurrentGridView;
            CurrentGridView = view;
            final MyViewHolder holder;
            if(CurrentGridView==null)
            {
                holder = new MyViewHolder();
                CurrentGridView = inflater.inflate(R.layout.product_grid_row,null);
              /*  holder.imageview = (ImageView) CurrentGridView.findViewById(R.id.imageview);
                holder.lv_img_click = (LinearLayout) CurrentGridView.findViewById(R.id.lv_img_click);
                holder.progress_grid = (ProgressBar) CurrentGridView.findViewById(R.id.progress_grid);*/
                CurrentGridView.setTag(holder);
            }
            else
            {
                holder = (MyViewHolder)CurrentGridView.getTag();
            }
            final Product_Grid_Model model = gridmodel.get(position);


         /* holder.lv_img_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    String images=gridmodel.get(position).getImages();
                    String image_id=gridmodel.get(position).getImg_id();
                    Intent i = new Intent(ctx, Package_slider.class);
                    i.putExtra("images", images);
                    Log.e("image_id",image_id);
                    Log.e("images",images);
                    i.putExtra("image_id",image_id);
                    i.putExtra("Package_id", Package_image_gridview.Package_id);
                    activity.startActivity(i);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);


                }
            });
*/                          //  holder.progress_grid.setVisibility(View.VISIBLE);

         /*   Glide.with(ctx).load(model.getImages())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            holder.progress_grid.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            holder.progress_grid.setVisibility(View.GONE);
                            return false;
                        }
                    }).placeholder(R.drawable.placeholder).fallback(R.drawable.placeholder).into(holder.imageview);
*/
            return CurrentGridView;
        }
        class MyViewHolder {
            public ImageView imageview;
            LinearLayout lv_img_click,lv_product_click;
            ProgressBar progress_grid;
        }
    }


