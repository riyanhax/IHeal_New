package com.sismatix.iheal.Fragments;


import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sismatix.iheal.Activity.Navigation_drawer_activity;
import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.R;
import com.sismatix.iheal.Retrofit.ApiClient;
import com.sismatix.iheal.Retrofit.ApiInterface;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;

/**
 * A simple {@link Fragment} subclass.
 */

public class EmailLogin extends Fragment implements View.OnClickListener {

    EditText login_email, login_password;
    Button btn_login;
    TextView tv_forgotpassword,tv_email_title;
    String screen,loginflag;
    Bundle bundle;

    public EmailLogin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_email_login, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        bundle= this.getArguments();

        AllocateMemory(v);

        tv_email_title.setTypeface(Home.roboto_medium);
        login_email.setTypeface(Home.roboto_regular);
        login_password.setTypeface(Home.roboto_regular);
        btn_login.setTypeface(Home.roboto_medium);
        tv_forgotpassword.setTypeface(Home.roboto_medium);
        tv_forgotpassword.setPaintFlags(tv_forgotpassword.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);


        btn_login.setOnClickListener(this);
        tv_forgotpassword.setOnClickListener(this);

        return v;
    }

    private void AllocateMemory(View v) {
        login_email = (EditText) v.findViewById(R.id.login_email);
        login_password = (EditText) v.findViewById(R.id.login_password);
        btn_login = (Button) v.findViewById(R.id.btn_login);
        tv_forgotpassword = (TextView) v.findViewById(R.id.tv_forgotpassword);
        tv_email_title = (TextView)v.findViewById(R.id.tv_email_title);
    }

    private void validateUserData() {
        final String username = login_email.getText().toString();
        final String password = login_password.getText().toString();

          if (login_email.getText().length() == 0) {
            /*signup_input_layout_email.setError("Please enter your Email id");*/
            Toast.makeText(getContext(), "Please enter your Email id", Toast.LENGTH_SHORT).show();
        } else if (login_password.getText().length() == 0) {
            /*signup_input_layout_password.setError("Please enter your Password");*/
            Toast.makeText(getContext(), "Please enter your Password", Toast.LENGTH_SHORT).show();
        } else {
            loginUser(username, password);
        }

        //checking if username is empty
        /*if (TextUtils.isEmpty(username)) {
            login_email.setError("Please enter your Email");
            login_email.requestFocus();
            // Vibrate for 100 milliseconds
            // v.vibrate(100);
            btn_login.setEnabled(true);
            return;
        }
        //checking if password is empty
        if (TextUtils.isEmpty(password)) {
            login_password.setError("Please enter your password");
            login_password.requestFocus();
            //Vibrate for 100 milliseconds
            //v.vibrate(100);
            btn_login.setEnabled(true);
            return;
        }*/
        //Login User if everything is fine
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
                        Log.e("screennn",""+screen);
                        Log.e("bundleee",""+bundle);
                        if (bundle!=null){
                            Navigation_drawer_activity.loginflagmain=Login_preference.getLogin_flag(getActivity());
                            loginflag=Login_preference.getLogin_flag(getActivity());
                            if (loginflag.equalsIgnoreCase("1") || loginflag == "1") {
                                Navigation_drawer_activity.lv_withlogin_header.setVisibility(View.VISIBLE);
                                Navigation_drawer_activity.login_navigation.setVisibility(View.GONE);
                                Navigation_drawer_activity.lv_logout.setVisibility(View.VISIBLE);
                            } else {
                                Navigation_drawer_activity.tv_navidrawer.setText(Login_preference.getemail(getActivity()));
                                Navigation_drawer_activity.lv_withlogin_header.setVisibility(View.GONE);
                                Navigation_drawer_activity.login_navigation.setVisibility(View.VISIBLE);
                                Navigation_drawer_activity.lv_logout.setVisibility(View.GONE);
                            }
                            screen =bundle.getString("value");
                            Log.e("screen_59",""+screen);
                            Checkout_fragment nextFrag = new Checkout_fragment();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.rootLayout, nextFrag, "login")
                                    .addToBackStack(null)
                                    .commit();
                        }else{
                            Intent intent=new Intent(getActivity(),Navigation_drawer_activity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }

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
    public void loadFragment(Fragment fragment) {
        Log.e("clickone", "");
        android.support.v4.app.FragmentManager manager = getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.rootLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    @Override
    public void onClick(View view) {
       if (view == btn_login) {
            validateUserData();
        } else if (view == tv_forgotpassword) {
            Forgotpassword_fragment nextFrag = new Forgotpassword_fragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, nextFrag, "login")
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    loadFragment(new Home());

                    return true;

                }

                return false;
            }
        });
    }
}
