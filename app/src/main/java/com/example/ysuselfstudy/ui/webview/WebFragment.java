package com.example.ysuselfstudy.ui.webview;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
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
        NavController navController = NavHostFragment.findNavController(this);
        int id = navController.getCurrentDestination().getId();

        //var amount: String? = arguments?.getString("amount")


        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);

        //设置不用系统浏览器打开,直接显示在当前Webview
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
//太敏感了
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//
//
//                webView.setVisibility(View.GONE);
//                ImageView imageView = getView().findViewById(R.id.error_pic);
//                imageView.setVisibility(View.VISIBLE);
//
//            }
        });

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                //确实可以下载
                Log.d(TAG, "onDownloadStart: " + url);
            }
        });


        switch (id) {
            case R.id.seat:
                webView.loadUrl("http://202.206.242.87/ClientWeb/m/ic2/Default.aspx");
                break;
            case R.id.librarySearch:
                webView.loadUrl("http://opac.ysu.edu.cn/m/weixin/wsearch.action");
                break;
            case R.id.school_calendar:
                webView.loadUrl("http://202.206.243.9/xiaoli.asp");
                break;
            case R.id.informationDetailFragment:
                String amount = getArguments().getString("amount");
                if (amount != null) webView.loadUrl(amount);
                break;
            default:
                synCookies("202.206.243.62");
                String number = Dao.INSTANCE.getStu().getNumber();
                webView.loadUrl("http://202.206.243.62/xs_main.aspx?xh=" + number);
                break;

        }


        //处理返回键
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    navController.popBackStack();
                }
            }
        });


    }


    @Override
    public void onDestroy() {
        if (webView != null) {
            //加载null内容
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            //清除历史记录
            webView.clearHistory();
            //移除WebView
            ((ViewGroup) webView.getParent()).removeView(webView);
            //销毁VebView
            webView.destroy();
            //WebView置为null
            webView = null;
        }
        super.onDestroy();

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
        cookieManager.setCookie(host, cookies);
        cookieManager.flush();
    }

}
