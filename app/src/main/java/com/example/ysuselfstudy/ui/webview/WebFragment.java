package com.example.ysuselfstudy.ui.webview;

import androidx.activity.OnBackPressedCallback;
import androidx.databinding.DataBindingUtil;

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
import android.view.ViewParent;


import com.example.ysuselfstudy.R;
import com.example.ysuselfstudy.databinding.WebFragmentBinding;
import com.example.ysuselfstudy.logic.Dao;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloader;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


/**
 * 这个页面发生了内存泄漏
 */
public class WebFragment extends Fragment {
    private static final String TAG = "WebFragment";
    private WebFragmentBinding binding;


    public static WebFragment newInstance() {
        return new WebFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.web_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (binding.webview.getX5WebViewExtension() != null) {
            Log.d(TAG, "onActivityCreated: 已经加载内核");
        }
        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.getSettings().setDomStorageEnabled(true);
        NavController navController = NavHostFragment.findNavController(this);
        int id = navController.getCurrentDestination().getId();

        //var amount: String? = arguments?.getString("amount")


        binding.webview.getSettings().setSupportZoom(true);
        binding.webview.getSettings().setBuiltInZoomControls(true);
        binding.webview.getSettings().setUseWideViewPort(true);

        //设置不用系统浏览器打开,直接显示在当前Webview
        binding.webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
                return false;
            }


//太敏感了
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//
//
//             binding.webview.setVisibility(View.GONE);
//                ImageView imageView = getView().findViewById(R.id.error_pic);
//                imageView.setVisibility(View.VISIBLE);
//
//            }
        });

        binding.webview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                //确实可以下载

            }
        });


        switch (id) {
            case R.id.seat:
              //  String js = "javascript:var x=document.getElementById('username').value = 160120010208;var y=document.getElementById('password').value=111;";
                binding.webview.loadUrl("http://202.206.242.87/ClientWeb/m/ic2/Default.aspx");
//                binding.webview.setWebViewClient(new WebViewClient() {
//                    @Override
//                    public void onPageFinished(WebView webView, String s) {
//                        binding.webview.loadUrl(js);
//                    }
//                });
                break;
            case R.id.librarySearch:
                binding.webview.loadUrl("http://opac.ysu.edu.cn/m/weixin/wsearch.action");
                break;
            case R.id.school_calendar:
                binding.webview.loadUrl("http://202.206.243.9/xiaoli.asp");
                break;
            case R.id.informationDetailFragment:
                String amount = getArguments().getString("amount");
                if (amount != null) binding.webview.loadUrl(amount);
                break;
            default:
                synCookies("202.206.243.62");
                String number = Dao.INSTANCE.getStu().getNumber();
                binding.webview.loadUrl("http://202.206.243.62/xs_main.aspx?xh=" + number);
                break;

        }


        //处理返回键
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (binding.webview.canGoBack()) {
                    binding.webview.goBack();
                } else {
                    navController.popBackStack();
                }
            }
        });


    }


    @Override
    public void onDestroy() {
        if (binding.webview != null) {
            //加载null内容
            ViewParent parent = binding.webview.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(binding.webview);
            }

            binding.webview.clearHistory();
            //移除WebView
            binding.webview.getSettings().setJavaScriptEnabled(false);
            binding.webview.removeAllViews();
            //销毁VebView
            binding.webview.destroy();
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
