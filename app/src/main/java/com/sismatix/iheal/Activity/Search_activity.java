package com.sismatix.iheal.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sismatix.iheal.Fragments.Home;
import com.sismatix.iheal.Preference.CheckNetwork;
import com.sismatix.iheal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Search_activity extends AppCompatActivity {

    SearchView searchview;
    String query, URL_SEARCH;
    TextView txt_location, txt_categories, txt_msg;
    LinearLayout lv_location, lv_category_value, lv_location_value, lv_categoris, lv_nodatafound;
    View loc_view, cat_view;
    Toolbar toolbar;
    int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activity);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        int hight = display.getHeight();
        Log.e("withscreen====++", "" + width);
        Log.e("hight====++", "" + hight);

       // Allocatememory();
        //searchview.setOnQueryTextListener(this);

    }

    private void Allocatememory() {
        //searchview = (SearchView) findViewById(R.id.search);
        txt_location = (TextView) findViewById(R.id.txt_location);
        txt_categories = (TextView) findViewById(R.id.txt_categories);
        txt_msg = (TextView) findViewById(R.id.txt_msg);
        lv_location_value = (LinearLayout) findViewById(R.id.lv_location_value);
        lv_categoris = (LinearLayout) findViewById(R.id.lv_categoris);
        lv_location = (LinearLayout) findViewById(R.id.lv_location);
        lv_category_value = (LinearLayout) findViewById(R.id.lv_catgori_value);
        lv_nodatafound = (LinearLayout) findViewById(R.id.lv_nodatafound);
        loc_view = (View) findViewById(R.id.loc_view);
        cat_view = (View) findViewById(R.id.cat_view);



    }

    public void ADD_View(View v) {
        v.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
        ));
        v.setBackgroundColor(Color.parseColor("#B3B3B3"));
    }

    private void Call_Search_API() {

        //  http://travel.demoproject.info/api/search.php?search_string=ma
        URL_SEARCH = "http://travel.demoproject.info/api/search.php?search_string=" + query;
        Log.e("urlsearch", "" + URL_SEARCH);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SEARCH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("URL..URL_SEARCH", "" + URL_SEARCH);

                        Log.e("response_search", "" + response);
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("search_obj", "" + jsonObject);
                            String status = jsonObject.getString("status");
                            Log.e("status_search", "" + status);
                            String message = jsonObject.getString("message");

                            if (status.equals("Success") == true) {
                                Log.e("message", "" + message);
                                JSONObject data_obj = jsonObject.getJSONObject("data");
                                JSONArray categories_array = data_obj.getJSONArray("categories");
                                Log.e("categories_array", "" + categories_array);

                                if (categories_array != null && categories_array.isNull(0) != true) {
                                    final TextView[] myTextViews = new TextView[categories_array.length()]; // create an empty array;

                                    lv_category_value.setVisibility(View.VISIBLE);
                                    lv_categoris.setVisibility(View.VISIBLE);
                                    cat_view.setVisibility(View.VISIBLE);
                                    lv_nodatafound.setVisibility(View.GONE);

                                    lv_category_value.removeAllViews();
                                    txt_categories.setText(data_obj.getString("category_name"));

                                    for (int i = 0; i < categories_array.length(); i++) {
                                        try {
                                            final JSONObject catt_object = categories_array.getJSONObject(i);
                                            Log.e("catt_object", "" + catt_object);
                                            Log.e("id", "" + catt_object.getString("id"));
                                            Log.e("category_name", "" + catt_object.getString("category_name"));
                                            Log.e("cat_nm", "" + catt_object.getString("category_name"));
                                            Log.e("cat_id", "" + catt_object.getString("id"));

                                            final TextView rowTextView = new TextView(Search_activity.this);

                                            View v = new View(Search_activity.this);
                                            //ADD_View(v);
                                            //TextView_style(rowTextView);

                                            rowTextView.setText(catt_object.getString("category_name"));
                                            rowTextView.setTag(catt_object.getString("id"));

                                            lv_category_value.addView(rowTextView);
                                            lv_category_value.addView(v);
                                            myTextViews[i] = rowTextView;


                                        } catch (Exception e) {
                                            Log.e("Exception", "" + e);
                                        } finally {
                                        }
                                    }
                                } else {
                                    lv_category_value.setVisibility(View.GONE);
                                    lv_categoris.setVisibility(View.GONE);
                                    cat_view.setVisibility(View.GONE);
                                }

                                //Location


                                JSONArray locations_array = data_obj.getJSONArray("locations");
                                Log.e("locations_array", "" + locations_array);


                                if (locations_array != null && locations_array.isNull(0) != true) {
                                    final TextView[] myTextViews = new TextView[locations_array.length()]; // create an empty array;

                                    lv_location_value.setVisibility(View.VISIBLE);
                                    lv_location.setVisibility(View.VISIBLE);
                                    loc_view.setVisibility(View.VISIBLE);
                                    lv_location_value.removeAllViews();
                                    txt_location.setText(data_obj.getString("category_name2"));
                                    lv_nodatafound.setVisibility(View.GONE);

                                    for (int i = 0; i < locations_array.length(); i++) {

                                        try {
                                            JSONObject loc_object = locations_array.getJSONObject(i);
                                            Log.e("loc_object", "" + loc_object);
                                            Log.e("id", "" + loc_object.getString("location_id"));
                                            Log.e("location_title", "" + loc_object.getString("location_title"));

                                            final String loc_nm = loc_object.getString("location_title");
                                            Log.e("loc_nm", "" + loc_nm);

                                            TextView rowTextView = new TextView(Search_activity.this);
                                            View v = new View(Search_activity.this);
                                            //ADD_View(v);
                                           // TextView_style(rowTextView);

                                            rowTextView.setText(loc_nm);
                                            rowTextView.setTag(loc_object.getString("location_id"));

                                            Log.e("gettagg_location", "" + rowTextView.getTag());

                                            lv_location_value.addView(rowTextView);
                                            lv_location_value.addView(v);
                                            myTextViews[i] = rowTextView;

                                        } catch (Exception e) {
                                            Log.e("Exception", "" + e);
                                        } finally {
                                        }
                                    }
                                } else {
                                    loc_view.setVisibility(View.GONE);
                                    lv_location_value.setVisibility(View.GONE);
                                    lv_location.setVisibility(View.GONE);

                                }


                            } else {

                                if (message.equals("No Data Found!!!") == true) {
                                    lv_category_value.setVisibility(View.GONE);
                                    lv_categoris.setVisibility(View.GONE);
                                    lv_location.setVisibility(View.GONE);
                                    lv_location_value.setVisibility(View.GONE);
                                    txt_msg.setText(message);
                                    lv_nodatafound.setVisibility(View.VISIBLE);

                                }
                                Toast.makeText(Search_activity.this, "" + message, Toast.LENGTH_SHORT).show();
                                Log.e("response_msggg_toast", "" + message);
                            }
                        } catch (JSONException e) {

                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Search_activity.this, "Response error" + error, Toast.LENGTH_SHORT).show();
                        // progressBar.setVisibility(View.VISIBLE);
                    }
                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(Search_activity.this);
        requestQueue.add(stringRequest);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_searchbar, menu);

        MenuItem search_item = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) search_item.getActionView();
        searchView.setFocusable(true);
        searchView.setQueryHint("Search");
        searchView.setIconified(false);
        /// searchView.clearFocus();
        searchView.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        searchView.setPadding(-30, 0, 0, 0);
        searchView.setIconifiedByDefault(true);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                //clear the previous data in search arraylist if exist
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                query = s;
                Log.e("query", "" + query);
                if (CheckNetwork.isNetworkAvailable(Search_activity.this)) {
                    //Call_Search_API();
                } else {
                    Toast.makeText(Search_activity.this, "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

        });

        return true;
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(Search_activity.this, Home.class);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

        super.onBackPressed();
        finish();
        //overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
         /* *//*  Intent i = new Intent(Search_activity.this, Home.class);
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);*//*
            onBackPressed();
            //  overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);*/
            Intent i = new Intent(Search_activity.this,Navigation_drawer_activity.class);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }


}
