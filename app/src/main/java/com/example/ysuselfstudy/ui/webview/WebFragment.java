package com.example.ysuselfstudy.ui.webview;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;

import com.example.ysuselfstudy.R;
import com.example.ysuselfstudy.logic.Dao;

public class WebFragment extends Fragment {
    private static final String TAG = "WebFragment";
    private WebViewModel mViewModel;
    private WebView webView;


    public static WebFragment newInstance() {
        return new WebFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.web_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WebViewModel.class);
        webView = getView().findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        synCookies("202.206.243.62");
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        String number = Dao.INSTANCE.getStu().getNumber();
        webView.loadUrl("http://202.206.243.62/xs_main.aspx?xh=" + number);

    }

    /**
     * 同步Cookie
     */
    private void synCookies(String host) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookies(null);
        SharedPreferences pref = requireActivity().getSharedPreferences("cookiegroup", Context.MODE_PRIVATE);
        String cookies = pref.getString("cookies", "0");
        Log.d(TAG, "synCookies: " + cookies);
        cookieManager.setCookie(host, cookies);
        cookieManager.flush();

    }

}
