package com.sismatix.iheal.Fragments;


import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.sismatix.iheal.Activity.Navigation_drawer_activity;
import com.sismatix.iheal.Adapter.Cart_List_Adapter;
import com.sismatix.iheal.Model.Cart_Model;
import com.sismatix.iheal.Preference.CheckNetwork;
import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.R;
import com.sismatix.iheal.Retrofit.ApiClient;
import com.sismatix.iheal.Retrofit.ApiInterface;

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
public class Cart extends Fragment {
    private Paint p = new Paint();
    private View view;
    public static RecyclerView cart_recyclerview;
    public static List<Cart_Model> cartList = new ArrayList<Cart_Model>();
    public static Cart_List_Adapter cart_adapter;
    Toolbar toolbar;
    ImageView iv_place_order;
    public static TextView tv_maintotal;
    public static Context context = null;
    LinearLayout lv_place_order;
    public static ProgressBar progressBar_cart;
    public static String cart_items_count;
    public static Call<ResponseBody> cartlistt = null;
    String loginflag;
    static String grand_total;
    public static String qt, qoute_id_cart, productslist;
    public static LinearLayout lv_productnot;
    ImageView iv_close;
    Dialog fullscreenDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        loginflag = Login_preference.getLogin_flag(getActivity());
        context = getActivity();
        AllocateMemory(view);

        if (CheckNetwork.isNetworkAvailable(getActivity())) {
            prepare_Cart();
        } else {
            Toast.makeText(getContext(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
        }

        cart_adapter = new Cart_List_Adapter(getActivity(), cartList);
       // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        cart_recyclerview.setLayoutManager(mLayoutManager);
        cart_recyclerview.setItemAnimator(new DefaultItemAnimator());
        cart_recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        cart_recyclerview.setAdapter(cart_adapter);

        init_Swipe_recyclerview();//swiper recyclerview
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Navigation_drawer_activity.class);
                startActivity(intent);
            }
        });

        lv_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {

                            if (productslist.equalsIgnoreCase("[]") || productslist.equalsIgnoreCase("")) {
                                Toast.makeText(getActivity(), "Product Not found in shopping cart.", Toast.LENGTH_SHORT).show();
                            } else {
                                Bundle b = new Bundle();
                                b.putString("grand_tot_cart", grand_total);
                                Log.e("grand_tot_cart", "" + grand_total);
                                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                Fragment myFragment = new Checkout_fragment();
                                myFragment.setArguments(b);
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, myFragment).addToBackStack(null).commit();
                            }
                        } else {
                            String screen_type = "cart";
                            // fullscreen_login_dialog();
                            loadFragment(new EmailLogin(), screen_type);
                        }
                    }
                }, 1000);

            }
        });


        return view;
    }

    private void validateUserData(EditText email, EditText password, Button btnlogin) {
        final String username = email.getText().toString();
        final String psd = password.getText().toString();

        //checking if username is empty
        if (TextUtils.isEmpty(username)) {
            email.setError("Please enter your Email");
            email.requestFocus();
            // Vibrate for 100 milliseconds
            // v.vibrate(100);
            btnlogin.setEnabled(true);
            return;
        }
        //checking if password is empty
        if (TextUtils.isEmpty(psd)) {
            password.setError("Please enter your password");
            password.requestFocus();
            //Vibrate for 100 milliseconds
            //v.vibrate(100);
            btnlogin.setEnabled(true);
            return;
        }
        //Login User if everything is fine
        loginUser(username, psd);
    }


    public void fullscreen_login_dialog() {
        fullscreenDialog = new Dialog(getContext(), R.style.DialogFullscreen);
        fullscreenDialog.setContentView(R.layout.dialog_fullscreen);
        ImageView img_full_screen_dialog = fullscreenDialog.findViewById(R.id.img_full_screen_dialog);
        // Glide.with(getContext()).load(R.drawable.app_logo).into(img_full_screen_dialog);
        ImageView img_dialog_fullscreen_close = fullscreenDialog.findViewById(R.id.img_dialog_fullscreen_close);
        final EditText login_dialog_email, login_dialog_password;
        final Button btn_dialog_login;
        TextView tv_dialog_forgotpassword;

        login_dialog_email = (EditText) fullscreenDialog.findViewById(R.id.login_dialog_email);
        login_dialog_password = (EditText) fullscreenDialog.findViewById(R.id.login_dialog_password);
        btn_dialog_login = (Button) fullscreenDialog.findViewById(R.id.btn_dialog_login);
        tv_dialog_forgotpassword = (TextView) fullscreenDialog.findViewById(R.id.tv_dialog_forgotpassword);

        btn_dialog_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUserData(login_dialog_email, login_dialog_password, btn_dialog_login);

            }
        });


        img_dialog_fullscreen_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullscreenDialog.dismiss();
            }
        });
        fullscreenDialog.show();

    }

    private void loginUser(String username, String password) {

        Log.e("username ", "" + username);
        Log.e("password ", "" + password);

        //makin g api call
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> login = api.login(username, password);

        login.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response", "" + response.body().toString());

                JSONObject jsonObject = null;

                try {
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    Log.e("status", "" + status);
                    String meassg = jsonObject.getString("message");
                    Log.e("message", "" + meassg);
                    if (status.equalsIgnoreCase("success")) {
                        Toast.makeText(getContext(), "" + meassg, Toast.LENGTH_SHORT).show();
                        Login_preference.setLogin_flag(getActivity(), "1");
                        Login_preference.setcustomer_id(getActivity(), jsonObject.getString("customer_id"));
                        Login_preference.setemail(getActivity(), jsonObject.getString("email"));
                        Login_preference.setfullname(getActivity(), jsonObject.getString("fullname"));

                        Toast.makeText(getActivity(), "Login succcessfull", Toast.LENGTH_SHORT).show();

                        getActivity().finish();
                        getActivity().overridePendingTransition(0, 0);
                        startActivity(getActivity().getIntent());
                        getActivity().overridePendingTransition(0, 0);
                        fullscreenDialog.dismiss();
                        // Intent intent=new Intent(getActivity(),Navigation_drawer_activity.class);
                        //  startActivity(intent);
                        //  getActivity().finish();
                       /* Home nextFrag = new Home();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.rootLayout, nextFrag, "home")
                                .addToBackStack(null)
                                .commit();*/
                    } else if (status.equalsIgnoreCase("error")) {
                        Toast.makeText(getContext(), "" + meassg, Toast.LENGTH_SHORT).show();
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

    private void loadFragment(Fragment fragment, String value) {
        Log.e("clickone", "");
        Bundle bundle = new Bundle();
        bundle.putString("value", value);

        fragment.setArguments(bundle);
        android.support.v4.app.FragmentManager manager = getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.rootLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void init_Swipe_recyclerview() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    cart_adapter.removeItem(position);
                }/*else if(direction == ItemTouchHelper.RIGHT)
                {
                    Toast.makeText(getActivity(), "edit data", Toast.LENGTH_SHORT).show();

                }*/ else {
                    removeView();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        p.setColor(Color.parseColor("#3f7a5c"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_create_black_18dp);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else {
                        p.setColor(Color.parseColor("#f45d64"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_close_black_18dp);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(cart_recyclerview);

    }

    private void removeView() {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    private void AllocateMemory(View v) {
        cart_recyclerview = v.findViewById(R.id.cart_recyclerview);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar_cart);
        iv_place_order = (ImageView) v.findViewById(R.id.iv_place_order);
        iv_close = (ImageView) v.findViewById(R.id.iv_close);
        lv_place_order = (LinearLayout) v.findViewById(R.id.lv_place_order);
        lv_productnot = (LinearLayout) v.findViewById(R.id.lv_productnotavelable);
        tv_maintotal = (TextView) v.findViewById(R.id.tv_maintotal);
        progressBar_cart = (ProgressBar) v.findViewById(R.id.progressBar_cart);
    }

    /**
     * method make volley network call and parses json
     */

    public static void prepare_Cart() {
        progressBar_cart.setVisibility(View.VISIBLE);
        cartList.clear();
        String email = Login_preference.getemail(context);

        String loginflag = Login_preference.getLogin_flag(context);
        Log.e("customeriddd", "" + Login_preference.getcustomer_id(context));
        if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
            Log.e("with_login", "");
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            cartlistt = api.Cartlist(email);
        } else {
            Log.e("without_login", "");
            String quote_id = Login_preference.getquote_id(context);
            Log.e("quoteidd", "" + quote_id);
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            cartlistt = api.getlistcart(quote_id);
        }

        cartlistt.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("responseeeeee", "" + response.body().toString());

                JSONObject jsonObject = null;
                try {

                    progressBar_cart.setVisibility(View.GONE);
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    Log.e("status_prepare_cart", "" + status);

                    if (status.equalsIgnoreCase("success")) {
                        lv_productnot.setVisibility(View.VISIBLE);
                        grand_total = jsonObject.getString("grand_total");
                        Log.e("gtot",""+grand_total);
                        tv_maintotal.setText(grand_total);

                        qoute_id_cart = jsonObject.getString("quote_id");
                        Log.e("qoute_id_cart", "" + qoute_id_cart);
                        cart_items_count = jsonObject.getString("items_count");
                        Log.e("cart_items_total_cart", "" + cart_items_count);

                        productslist = jsonObject.getString("products");
                        Log.e("prod_list_cart",""+productslist);
                        if (productslist.equalsIgnoreCase("[]") || productslist.equalsIgnoreCase("")) {

                            lv_productnot.setVisibility(View.VISIBLE);

                        } else {
                            lv_productnot.setVisibility(View.GONE);
                            Login_preference.setCart_item_count(context, cart_items_count);
                            JSONArray jsonArray = jsonObject.getJSONArray("products");
                            Log.e("jsonarr_cart",""+jsonArray);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject vac_object = jsonArray.getJSONObject(i);
                                    Log.e("Name", "" + vac_object.getString("product_name"));
                                    cartList.add(new Cart_Model(vac_object.getString("product_name"),
                                            vac_object.getString("product_price"),
                                            vac_object.getString("product_image"),
                                            vac_object.getString("product_sku"),
                                            vac_object.getString("product_id"),
                                            vac_object.getString("row_total"),
                                            vac_object.getString("product_qty"),
                                            vac_object.getString("itemid")));
                                    qt = vac_object.getString("product_qty");
                                    Log.e("qtttttttt", "" + qt);
                                } catch (Exception e) {
                                    Log.e("Exception", "" + e);
                                } finally {
                                    cart_adapter.notifyItemChanged(i);
                                }
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
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
