package com.sismatix.iheal.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.sismatix.iheal.Fragments.Nature_Category_freg;
import com.sismatix.iheal.R;

public class Web_tappayment extends AppCompatActivity {

    WebView web_view;
    private String mUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_tappayment);
        web_view=(WebView)findViewById(R.id.web_view);
        Intent intent=getIntent();

        // web_view.loadUrl(intent.getStringExtra("url"));
        renderWebPage(intent.getStringExtra("url"));


    }
    // Custom method to render a web page
    protected void renderWebPage(String urlToRender){
        web_view.setWebViewClient(new WebViewClient(){
            /*
                public void onPageStarted (WebView view, String url, Bitmap favicon)
                    Notify the host application that a page has started loading. This method is
                    called once for each main frame load so a page with iframes or framesets will
                    call onPageStarted one time for the main frame. This also means that
                    onPageStarted will not be called when the contents of an embedded frame changes,
                    i.e. clicking a link whose target is an iframe, it will also not be called for
                    fragment navigations (navigations to #fragment_id).

                Parameters
                    view : The WebView that is initiating the callback.
                    url : The url to be loaded.
                    favicon : The favicon for this page if it already exists in the database.

            */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                // Do something on page loading started
                //  Toast.makeText(mContext,"Page loading started",Toast.LENGTH_SHORT).show();
                Log.e("Page loading started","");
                /*
                    public String getUrl ()
                        Gets the URL for the current page. This is not always the same as the URL
                        passed to WebViewClient.onPageStarted because although the load for that
                        URL has begun, the current page may not have changed.

                    Returns
                        the URL for the current page
                */
                // Only url is available in this stage
                mUrl = view.getUrl();
                Log.e("Page loadedstarted_get1",""+mUrl);
                // Update the action ba
                Log.e("Pageloadedfinished_get2",""+mUrl);
                String urll=" https://ihealkuwait.com/?chargeid=ch_Rh4u20191045Lu5w273203&ref=3003272019104516293&result=SUCCESS&payid=TST_29555911020190327&crdtype=MASTERCARD&trackid=123456&crd=0008&hash=35ea07a605b790011031e22e1c9051f39c63b86b3a2d6077a6f777a77325f888";
                https://ihealkuwait.com/?chargeid=ch_t9MY2019122Oi442748603&ref=3003272019120129770&result=SUCCESS&payid=TST_29561332720190327&crdtype=MASTERCARD&trackid=123456&crd=0008&hash=ee2f3cb155b7adbf62d8a26f241fde618655f08a254fd3b1592ed2a3c922ead5
                if(mUrl.equals(urll)){
                    Log.e("Both sites are equal","");
                    // System.out.println("Both sites are equal");
                } else {
                    Log.e("sites are not equal","");
                    //System.out.println("Both sites are not equal");
                }
                getSupportActionBar().setSubtitle(mUrl);
            }

            /*
                public void onPageFinished (WebView view, String url)
                    Notify the host application that a page has finished loading. This method is
                    called only for main frame. When onPageFinished() is called, the rendering
                    picture may not be updated yet. To get the notification for the new Picture,
                    use onNewPicture(WebView, Picture).

                Parameters
                    view : The WebView that is initiating the callback.
                    url : The url of the page.
            */
            @Override
            public void onPageFinished(WebView view, String url){
                // Do something when page loading finished
                // Toast.makeText(mContext,"Page loaded",Toast.LENGTH_SHORT).show();
                Log.e("Page loaded","");
                // Both url and title is available in this stage
                mUrl = view.getUrl();
                Log.e("Pageloadedfinished_get2",""+mUrl);

                Uri uri = Uri.parse(mUrl);
                String server = uri.getAuthority();
                String path = uri.getPath();
                String protocol = uri.getScheme();
                Log.e("server",""+server);
                Log.e("path",""+path);
                Log.e("protocol",""+protocol);
                String result = uri.getQueryParameter("result");
                Log.e("result_vinod",""+result);
                if (result != null && result.equalsIgnoreCase("SUCCESS")) {
                    Log.e("success","");
                    Intent intent=new Intent(Web_tappayment.this,Confirmation_screen.class);
                    startActivity(intent);
                }else {

                }


                //   mTitle = view.getTitle();

                // Update the action bar
                //   getSupportActionBar().setTitle(mTitle);
                getSupportActionBar().setSubtitle(mUrl);
            }

        });

        // Enable the javascript
        web_view.getSettings().setJavaScriptEnabled(true);
        // Render the web page
        web_view.loadUrl(urlToRender);
    }
}
