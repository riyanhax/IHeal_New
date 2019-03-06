package com.sismatix.iheal.Fragments;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
public class Forgotpassword_fragment extends Fragment implements View.OnClickListener {

    EditText forgot_email;
    Button btn_submit;
    TextInputLayout forgot_input_layout_email;
    ImageView iv_forget_password;
    public Forgotpassword_fragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_forgotpassword_fragment, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));


        AllocateMemory(v);


        btn_submit.setOnClickListener(this);
        iv_forget_password.setOnClickListener(this);

        return v;
    }

    private void AllocateMemory(View v) {
        iv_forget_password=(ImageView) v.findViewById(R.id.iv_forget_password);
        forgot_email=(EditText)v.findViewById(R.id.forgot_email);
        btn_submit=(Button) v.findViewById(R.id.btn_submit);
        forgot_input_layout_email = (TextInputLayout) v.findViewById(R.id.forgot_input_layout_email);

    }

    public  void loadFragment(Fragment fragment) {
        Log.e("clickone", "");
        android.support.v4.app.FragmentManager manager =getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.rootLayout, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private void forgotpassword(String forgot) {
        ApiInterface apii = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> forgotpw = apii.forgotpassword(forgot);

        forgotpw.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response", "" + response.body().toString());
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    Log.e("status",""+status);
                    String meassg=jsonObject.getString("message");
                    Log.e("message",""+meassg);
                    if (status.equalsIgnoreCase("success")){
                        Toast.makeText(getContext(), ""+meassg, Toast.LENGTH_SHORT).show();
                        /*Login_preference.setcustomer_id(getActivity(),jsonObject.getString("customer_id"));
                        Login_preference.setemail(getActivity(),jsonObject.getString("email"));
                        Login_preference.setfullname(getActivity(),jsonObject.getString("fullname"));*/
                        EmailLogin nextFrag= new EmailLogin();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.rootLayout, nextFrag, "login")
                                .addToBackStack(null)
                                .commit();
                    }else if (status.equalsIgnoreCase("error")){
                        Toast.makeText(getContext(), ""+meassg, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.e("",""+e);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onClick(View view) {
        if(view==iv_forget_password){
            loadFragment(new EmailLogin());
        }else if(view==btn_submit){
            String forgot= forgot_email.getText().toString();
            if (forgot_email.getText().length() == 0) {
                forgot_input_layout_email.setError("Please enter your Email id");
            }
            else if (isValidEmailAddress(forgot_email.getText().toString()) == false) {
                forgot_input_layout_email.setError("Please enter valid email.");
            }else{
                forgotpassword(forgot);
            }
        }
    }
}
