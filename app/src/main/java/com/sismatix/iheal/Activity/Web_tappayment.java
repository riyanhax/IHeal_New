package com.sismatix.iheal.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.sismatix.iheal.Fragments.Fianl_Order_Checkout_freg;
import com.sismatix.iheal.Fragments.Hair_Cair_fregment;
import com.sismatix.iheal.Fragments.Nature_Category_freg;
import com.sismatix.iheal.R;
import com.sismatix.iheal.Retrofit.ApiClient;
import com.sismatix.iheal.Retrofit.ApiInterface;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Web_tappayment extends AppCompatActivity {

    WebView web_view;
    private String mUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_web_tappayment);
        web_view=(WebView)findViewById(R.id.web_view);
        Intent intent=getIntent();

        // web_view.loadUrl(intent.getStringExtra("url"));
        renderWebPage(intent.getStringExtra("url"));


    }
    // Custom method to render a web page
    protected void renderWebPage(String urlToRender){
        web_view.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){

                mUrl = view.getUrl();
                Log.e("Page loadedstarted_get1",""+mUrl);
                Uri uri = Uri.parse(mUrl);
                String result = uri.getQueryParameter("result");
                Log.e("result_vinod",""+result);
                if (result != null && result.equalsIgnoreCase("SUCCESS")) {
                    Log.e("success","");
                    String ref = uri.getQueryParameter("ref");
                    String trackid = uri.getQueryParameter("trackid");
                    String hash = uri.getQueryParameter("hash");
                    String crdtype = uri.getQueryParameter("crdtype");
                    String payid = uri.getQueryParameter("payid");
                    Log.e("ref",""+ref);
                    Log.e("trackid",""+trackid);
                    Log.e("hash",""+hash);
                    Log.e("crdtype",""+crdtype);
                    Log.e("payid",""+payid);
                    payment_sucess(result,ref,trackid,hash,crdtype,payid);

                }else {

                }

            }

            @Override
            public void onPageFinished(WebView view, String url){
                mUrl = view.getUrl();
                Log.e("Pageloadedfinished_get2",""+mUrl);
            }

        });
        Log.e("final_set",""+urlToRender);
        // Enable the javascript
        web_view.getSettings().setJavaScriptEnabled(true);
        // Render the web page
        web_view.loadUrl(urlToRender);
    }

    private void payment_sucess(String result, String ref, String trackid, String hash,String payid,String cartype) {
        //progressBar.setVisibility(View.VISIBLE);
        ApiInterface apii = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> confirm = apii.AppTapPaymentResponse(ref, result, trackid,cartype,payid,hash);

        confirm.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response", "" + response);
               // progressBar.setVisibility(View.GONE);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String code = jsonObject.getString("code");
                    Log.e("code_confirmation", "" + code);
                    String meassg = jsonObject.getString("message");
                    Log.e("message_confirmation", "" + meassg);
                    if (code.equalsIgnoreCase("200")) {

                        Intent intent=new Intent(Web_tappayment.this,Confirmation_screen.class);
                        startActivity(intent);
                        finish();
                    } else if (code.equalsIgnoreCase("error")) {
                        Toast.makeText(Web_tappayment.this, "" + meassg, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("Exception", "" + e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //   Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
