package com.sismatix.iheal.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/*import com.sendbird.android.BaseChannel;
import com.sendbird.android.OpenChannel;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;
import com.sendbird.android.UserMessage;*/
import com.sismatix.iheal.Activity.Navigation_drawer_activity;
import com.sismatix.iheal.R;
import com.sismatix.iheal.sdk.JivoDelegate;
import com.sismatix.iheal.sdk.JivoSdk;

import java.util.Locale;

/*import zendesk.core.AnonymousIdentity;
import zendesk.core.Identity;
import zendesk.core.Zendesk;
import zendesk.support.Support;
import zendesk.support.guide.HelpCenterActivity;*/

import static android.provider.UserDictionary.Words.APP_ID;
import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;

/**
 * A simple {@link Fragment} subclass.
 */

public class Meassge_fragment extends Fragment {
    public static Toolbar toolbar_messg;
    LinearLayout lv_message_parent;
    Button help_button;

    JivoSdk jivoSdk;

    public Meassge_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_meassge_fragment, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        //bottom_navigation.setVisibility(View.GONE);
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        lv_message_parent = (LinearLayout) v.findViewById(R.id.lv_message_parent);
        //help_button = (Button) v.findViewById(R.id.help_button);
        //hideKeyboard(getActivity());
        setupUI(lv_message_parent);

        setHasOptionsMenu(true);

        //String lang = Locale.getDefault().getLanguage().indexOf("ru") >= 0 ? "ru" : "en";
        //*********************************************************

        toolbar_messg = (Toolbar) v.findViewById(R.id.toolbar_messg);
        ((Navigation_drawer_activity) getActivity()).setSupportActionBar(toolbar_messg);
        ((Navigation_drawer_activity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((Navigation_drawer_activity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_36dp);

        /*SendBird.init("A5DBF5CD-8E29-4C68-9048-B0F7F71FC3EE", getContext());

        SendBird.connect("Dolphin", new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {
                if (e != null) {    // Error.
                    return;
                }
            }
        });

        OpenChannel.createChannel(new OpenChannel.OpenChannelCreateHandler() {
            @Override
            public void onResult(OpenChannel openChannel, SendBirdException e) {
                if (e != null) {    // Error.
                    return;
                }
            }
        });

        OpenChannel.getChannel("sendbird_open_channel_49268_27b162c4b36dcd871d53912d03b488fc55b0c21c", new OpenChannel.OpenChannelGetHandler() {
            @Override
            public void onResult(OpenChannel openChannel, SendBirdException e) {
                if (e != null) {    // Error.
                    return;
                }

                openChannel.enter(new OpenChannel.OpenChannelEnterHandler() {
                    @Override
                    public void onResult(SendBirdException e) {
                        if (e != null) {    // Error.
                            return;
                        }
                    }
                });
            }
        });*/

        /*channel.sendUserMessage(MESSAGE, new BaseChannel.SendUserMessageHandler() {
            @Override
            public void onSent(UserMessage userMessage, SendBirdException e) {
                if (e != null) {    // Error.
                    return;
                }
            }
        });*/


       /* Zendesk.INSTANCE.init(getContext(), "https://dolphinwebsolution.zendesk.com",
                "f3fea79c9188b306d43856994e20e03d8bdf4d4619b92b2f",
                "mobile_sdk_client_16584b9c21dca9280662");
        Support.INSTANCE.init(Zendesk.INSTANCE);*/

     /*   Zendesk.INSTANCE.init(getContext(), "https://dolphinwebsolution.zendesk.com",
                "f3fea79c9188b306d43856994e20e03d8bdf4d4619b92b2f",
                "mobile_sdk_client_16584b9c21dca9280662");
        Support.INSTANCE.init(Zendesk.INSTANCE);

        Identity identity = new AnonymousIdentity();
        Zendesk.INSTANCE.setIdentity(identity);
        Support.INSTANCE.init(Zendesk.INSTANCE);

        HelpCenterActivity.builder()
                .show(getContext());*/

        /*help_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpCenterActivity.builder()
                        .show(getContext());
            }
        });*/

      /*  jivoSdk = new JivoSdk((WebView) v.findViewById(R.id.webview), lang);
        jivoSdk.delegate = this;
        jivoSdk.prepare();*/

        return v;
    }

   /* @Override
    public void onEvent(String name, String data) {
        if (name.equals("url.click")) {
            if (data.length() > 2) {
                String url = data.substring(1, data.length() - 1);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        }
    }*/

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    Shipping_fragment.hideSoftKeyboard(getActivity());
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        /*MenuItem item_search = menu.findItem(R.id.search);
        item_search.setVisible(false);*/
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
