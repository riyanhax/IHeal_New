package com.sismatix.iheal.Activity;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.sismatix.iheal.Fragments.Account;
import com.sismatix.iheal.Fragments.AccountTabs;
import com.sismatix.iheal.Fragments.Cart;
import com.sismatix.iheal.Fragments.EmailLogin;
import com.sismatix.iheal.Fragments.Hair_Cair_fregment;
import com.sismatix.iheal.Fragments.Home;
import com.sismatix.iheal.Fragments.Meassge_fragment;
import com.sismatix.iheal.Fragments.MyOrder;
import com.sismatix.iheal.Fragments.MyOrderDetails;
import com.sismatix.iheal.Fragments.Nature_Category_freg;
import com.sismatix.iheal.Fragments.Search;
import com.sismatix.iheal.Fragments.Wishlist_fragment;
import com.sismatix.iheal.Model.Cart_Model;
import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.R;
import com.sismatix.iheal.Retrofit.ApiClient;
import com.sismatix.iheal.Retrofit.ApiInterface;
import com.sismatix.iheal.View.CustomTypefaceSpan;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sismatix.iheal.Fragments.Cart.cartlistt;

public class Navigation_drawer_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;
    MenuItem title_account_tools, title_shop_tools;
    SpannableString shop, account;
    String cartitem_count;
    public static LinearLayout lv_withlogin_header, login_navigation, lv_logout;
    public static String loginflagmain;
    public static TextView tv_navidrawer, item_count, tv_logout, tv_bottomcount;
    private View notificationBadge;

    //bottom navigation

    public static BottomNavigationView bottom_navigation;
    private List<View> viewList;
    boolean doubleBackToExitPressedOnce = false;
    /*int navDefaultTextColor = Color.parseColor("#ffe5a8");
    int navDefaultIconColor = Color.parseColor("#ffe5a8");*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        loginflagmain = Login_preference.getLogin_flag(Navigation_drawer_activity.this);

        cartitem_count = Login_preference.getCart_item_count(Navigation_drawer_activity.this);
        Log.e("cart_total_items", "" + cartitem_count);
        Log.e("logingflag", "" + loginflagmain);

        AllocateMemory();

        login_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, new EmailLogin())
                        .addToBackStack("Login").commit();
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem mi = menu.getItem(i);
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

        title_account_tools = menu.findItem(R.id.title_account_tools);
        title_shop_tools = menu.findItem(R.id.title_shop_tools);
        account = new SpannableString(title_account_tools.getTitle());
        account.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, account.length(), 0);
        title_account_tools.setTitle(account);

        shop = new SpannableString(title_shop_tools.getTitle());
        shop.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, shop.length(), 0);
        title_shop_tools.setTitle(shop);

        disableNavigationViewScrollbars(navigationView);//remove scrollbar
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        ImageView iv_drawer_close = (ImageView) header.findViewById(R.id.iv_drawer_close);
        iv_drawer_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                }
            }
        });

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
            }
        });

        tv_logout.setTypeface(Home.roboto_regular);
        lv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login_preference.setLogin_flag(Navigation_drawer_activity.this, "0");
                Cart.cartList.clear();
                Login_preference.prefsEditor.remove("quote_id").apply();
                Login_preference.prefsEditor.remove("item_count").apply();
                Intent intent = new Intent(Navigation_drawer_activity.this, Navigation_drawer_activity.class);
                startActivity(intent);
            }
        });

        Bootom_Navigation_view();

        if (loginflagmain.equalsIgnoreCase("1") || loginflagmain == "1") {
            CALL_CART_COUNT_API();
        }
    }

    private void CALL_CART_COUNT_API() {

        String email = Login_preference.getemail(Navigation_drawer_activity.this);

        String loginflag = Login_preference.getLogin_flag(Navigation_drawer_activity.this);
        Log.e("customeriddd", "" + Login_preference.getcustomer_id(Navigation_drawer_activity.this));
        if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
            Log.e("with_login", "");
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            cartlistt = api.Cartlist(email);
        }

        cartlistt.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("responseeeeee", "" + response);

                JSONObject jsonObject = null;
                try {

                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    Log.e("status_prepare_cart", "" + status);

                    if (status.equalsIgnoreCase("success")) {

                        String cart_items_count = jsonObject.getString("items_count");
                        Login_preference.setCart_item_count(Navigation_drawer_activity.this, cart_items_count);
                        Log.e("cart_items_total_cart", "" + cart_items_count);

                        if (jsonObject.getString("items_count").equalsIgnoreCase("null") || jsonObject.getString("items_count").equals("")) {

                            Navigation_drawer_activity.tv_bottomcount.setText("0");
                            Navigation_drawer_activity.item_count.setText("0");

                        } else {

                            Navigation_drawer_activity.tv_bottomcount.setText(jsonObject.getString("items_count"));
                            Navigation_drawer_activity.item_count.setText(jsonObject.getString("items_count"));

                        }

                    } else if (status.equalsIgnoreCase("error")) {

                    }

                } catch (Exception e) {
                    Log.e("nav_exc", "" + e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Navigation_drawer_activity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "Roboto-Medium.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public DrawerLayout getmDrawerLayout() {
        return drawer;
    }

    private void Bootom_Navigation_view() {
        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu = bottom_navigation.getMenu();
        selectFragment(menu.getItem(0));

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottom_navigation.getChildAt(0);
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(3);
        notificationBadge = LayoutInflater.from(this).inflate(R.layout.badge_row, menuView, false);
        tv_bottomcount = (TextView) notificationBadge.findViewById(R.id.badge);
        if (cartitem_count.equalsIgnoreCase("null") || cartitem_count.equals("")) {
            tv_bottomcount.setText("0");
        } else {
            tv_bottomcount.setText(cartitem_count);
        }
        itemView.addView(notificationBadge);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            selectFragment(item);
            return false;
        }
    };

    private void selectFragment(MenuItem item) {

        item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.bottom_nav_home:
                // Action to perform when Home Menu item is selected.
                pushFragment(new Home(), "Home");//Search
                break;
            case R.id.bottom_nav_search:
                pushFragment(new Nature_Category_freg(), "Natures_fragment");//Search
                break;
            case R.id.bottom_nav_Wishlist:
                if (loginflagmain.equalsIgnoreCase("1") || loginflagmain == "1") {
                    pushFragment(new Wishlist_fragment(), "Wishlist_fragment");
                    break;
                } else {
                    //Toast.makeText(this, "Please try to login.", Toast.LENGTH_SHORT).show();
                    pushFragment(new Account(), "Wishlist_fragment");
                    break;
                }
            case R.id.bottom_nav_cart:
                pushFragment(new Cart(), "Cart");
                break;
            case R.id.bottom_nav_account:
                if (loginflagmain.equalsIgnoreCase("1") || loginflagmain == "1") {
                    pushFragment(new AccountTabs(), "My Account");
                    break;
                } else {
                    tv_bottomcount.setText(Login_preference.getCart_item_count(Navigation_drawer_activity.this));
                    pushFragment(new Account(), "Login_myaccount");
                    break;
                }
        }
    }

    private void pushFragment(Fragment fragment, String add_to_backstack) {
        if (fragment == null)
            return;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.setCustomAnimations(R.anim.fade_in,
                        0, 0, R.anim.fade_out);
                ft.replace(R.id.rootLayout, fragment);
                ft.addToBackStack(add_to_backstack);
                ft.commit();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_product_categories) {
            // Handle the camera action
            pushFragment(new Nature_Category_freg(), "Nature_category");

        }/* else if (id == R.id.nav_health_topic) {

        } else if (id == R.id.nav_offers) {

        } else if (id == R.id.nav_reviews) {

        }*/ /*else if (id == R.id.nav_recipes) {

        }*/ else if (id == R.id.nav_my_order) {
            if (loginflagmain.equalsIgnoreCase("1") || loginflagmain == "1") {

                pushFragment(new MyOrder(), "My Order");
            } else {

                pushFragment(new Account(), "Login_myaccount");

            }

        } else if (id == R.id.nav_my_account) {

            if (loginflagmain.equalsIgnoreCase("1") || loginflagmain == "1") {

                pushFragment(new AccountTabs(), "My Account");
            } else {

                pushFragment(new Account(), "Login_myaccount");

            }

        } else if (id == R.id.nav_wishlist) {
            /*if (loginflagmain.equalsIgnoreCase("1") || loginflagmain == "1") {
                pushFragment(new Wishlist_fragment(), "wishlist");
            } else {
                Toast.makeText(this, "Please try to login.", Toast.LENGTH_SHORT).show();
            }*/
            if (loginflagmain.equalsIgnoreCase("1") || loginflagmain == "1") {
                pushFragment(new Wishlist_fragment(), "Wishlist_fragment");

            } else {
                //Toast.makeText(this, "Please try to login.", Toast.LENGTH_SHORT).show();
                pushFragment(new Account(), "Wishlist_fragment");
            }
        } else if (id == R.id.nav_messages) {
            if (loginflagmain.equalsIgnoreCase("1") || loginflagmain == "1") {
                Intent intent=new Intent(Navigation_drawer_activity.this,Chat_meassge.class);
                startActivity(intent);
               /// pushFragment(new Meassge_fragment(), "Meassge");
            } else {

                pushFragment(new Account(), "Login_myaccount");

            }
            //pushFragment(new MyOrderDetails(), "MyOrder_details");
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void Check_String_NULL_Value(TextView textview, String text) {


        if (text.equalsIgnoreCase("null") == true) {
            textview.setText("");
        } else {

            textview.setText(Html.fromHtml(Convert_String_First_Letter(text)));
        }

    }

    public static String Convert_String_First_Letter(String convert_string) {
        String upperString;

        if (convert_string.length() > 0) {
            upperString = convert_string.substring(0, 1).toUpperCase() + convert_string.substring(1);
        } else {
            upperString = " ";
        }
        return upperString;
    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    private void AllocateMemory() {
        //set bydefault itemcount
        Login_preference.setCart_item_count(Navigation_drawer_activity.this, "0");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        login_navigation = (LinearLayout) findViewById(R.id.login_navigation);
        lv_logout = (LinearLayout) findViewById(R.id.lv_logout);
        tv_navidrawer = (TextView) header.findViewById(R.id.tv_navidraweremail);
        item_count = (TextView) header.findViewById(R.id.item_count);
        lv_withlogin_header = (LinearLayout) header.findViewById(R.id.lv_withlogin_header);
        tv_logout = (TextView) findViewById(R.id.tv_logout);
        Log.e("Username", "" + Login_preference.getfullname(Navigation_drawer_activity.this));
        tv_navidrawer.setTypeface(Home.roboto_bold);
        tv_navidrawer.setText(Login_preference.getfullname(Navigation_drawer_activity.this));
        if (cartitem_count.equalsIgnoreCase("null") || cartitem_count.equals("")) {
            item_count.setText("0");
        } else {
            item_count.setText(cartitem_count);
        }

        ///menu in login&logout opetionshow
        if (loginflagmain.equalsIgnoreCase("1") || loginflagmain == "1") {
            lv_withlogin_header.setVisibility(View.VISIBLE);
            login_navigation.setVisibility(View.GONE);
            lv_logout.setVisibility(View.VISIBLE);
        } else {
            lv_withlogin_header.setVisibility(View.GONE);
            login_navigation.setVisibility(View.VISIBLE);
            lv_logout.setVisibility(View.GONE);
        }

        ///menu icon change
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_dehaze_white_36dp);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

     /*   MenuItem item = menu.findItem(R.id.cart);

        LayerDrawable icon = (LayerDrawable) item.getIcon();

        CountDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse != null && reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(Navigation_drawer_activity.this);
        }

        badge.setCount("1");
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);
*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);*/

        switch (item.getItemId()) {
            case R.id.search:
                //Toast.makeText(getActivity(), "cart Icon Click", Toast.LENGTH_SHORT).show();
                Fragment myFragment = new Search();
                this.getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, myFragment).addToBackStack(null).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")

    ///////////
    @Override
    public void onBackPressed() {

        int seletedItemId = bottom_navigation.getSelectedItemId();
        if (R.id.home != seletedItemId) {
            setHomeItem(Navigation_drawer_activity.this);
        } else {
            super.onBackPressed();
        }

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        //String name=fragmentManager.get
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        Log.e("countt_vinod", "" + count);
        if (count == 1) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                super.finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            String title = fragmentManager.getBackStackEntryAt(count - 2).getName();
            super.onBackPressed();
            Log.e("onBackPressetitle", "" + title);
            // tv_title.setText(title);
        }
        //doExitApp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottom_navigation.getMenu().getItem(0).setChecked(true);
    }

    public static void setHomeItem(Activity activity) {
        bottom_navigation.setSelectedItemId(R.id.home);
    }

    private long exitTime = 0;

    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(Navigation_drawer_activity.this,
                    "Please click BACK again to exit",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }


}
