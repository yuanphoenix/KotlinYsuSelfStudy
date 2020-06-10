package com.example.ysuselfstudy.ui.webview;

import androidx.activity.OnBackPressedCallback;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toast;


import com.example.ysuselfstudy.R;
import com.example.ysuselfstudy.YsuSelfStudyApplication;
import com.example.ysuselfstudy.data.InformCollect;
import com.example.ysuselfstudy.data.Information;
import com.example.ysuselfstudy.data.QQ;
import com.example.ysuselfstudy.data.User;
import com.example.ysuselfstudy.databinding.WebFragmentBinding;
import com.example.ysuselfstudy.logic.Dao;
import com.example.ysuselfstudy.logic.qqlogin.BaseUiListener;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.Iterator;

import cn.bmob.v3.http.I;

/**
 * 这个页面发生了内存泄漏
 */
public class WebFragment extends Fragment {
    private static final String TAG = "WebFragment";
    private static final int REQUEST_CODE = 225;
    private WebFragmentBinding binding;
    private ValueCallback<Uri> uploadFile;
    private ValueCallback<Uri[]> uploadFiles;
    private NavController navController;
    private WebViewModel viewModel;
    private static int id = 0;
    private Information information;
    private Menu menu;
    private static String objectId = "";

    public static WebFragment newInstance() {
        return new WebFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        navController = NavHostFragment.findNavController(this);
        viewModel = new ViewModelProvider(this).get(WebViewModel.class);
        id = navController.getCurrentDestination().getId();
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.web_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.getSettings().setDomStorageEnabled(true);
        binding.webview.getSettings().setSupportZoom(true);
        binding.webview.getSettings().setBuiltInZoomControls(true);
        binding.webview.getSettings().setUseWideViewPort(true);

        binding.webview.setWebChromeClient(new WebChromeClient() {

            public boolean onShowFileChooser(com.tencent.smtt.sdk.WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             WebChromeClient.FileChooserParams fileChooserParams) {
                uploadFiles = filePathCallback;
                openFileChooseProcess();
                return true;
            }

            private void openFileChooseProcess() {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(Intent.createChooser(i, "test"), REQUEST_CODE);
            }

        });
        //设置不用系统浏览器打开,直接显示在当前Webview
        binding.webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
                String url = webResourceRequest.getUrl().toString();
                if (url == null) {
                    return false;
                }
                try {
                    if (url.startsWith("weixin://")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        webView.getContext().startActivity(intent);
                        return true;
                    }
                } catch (Exception e) {
                    return false;
                }
                return false;
            }
        });

        binding.webview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                //使用浏览器下载
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });


        switch (id) {
            case R.id.seat:
                User stu = Dao.INSTANCE.getStu();
                String js = "javascript:var x=document.getElementById('username').value ='" + stu.getNumber() + "';var y=document.getElementById('password').value='" + stu.getLibraryPassword() + "';";
                binding.webview.loadUrl("http://202.206.242.87/ClientWeb/m/ic2/Default.aspx");
                binding.webview.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView webView, String s) {
                        binding.webview.loadUrl(js);
                    }
                });
                break;
            case R.id.librarySearch:
                binding.webview.loadUrl("http://opac.ysu.edu.cn/m/weixin/wsearch.action");
                break;
            case R.id.school_calendar:
                binding.webview.loadUrl("http://202.206.243.9/xiaoli.asp");
                break;
            case R.id.informationDetailFragment:
                information = (Information) getArguments().get("amount");
                if (information != null) binding.webview.loadUrl(information.getUrl());
                break;
            case R.id.feedBack:
                QQ qq = Dao.INSTANCE.getQQ();
                String url = "https://support.qq.com/product/115180?d-wx-push=1";
                if (qq != null) {
                    String openid = qq.getOpenID(); // 用户的openid
                    String nickname = qq.getNickname(); // 用户的nickname
                    String headimgurl = qq.getImage();
                    headimgurl = headimgurl.replace("&", "%26");
                    String postData = "nickname=" + nickname + "&avatar=" + headimgurl + "&openid=" + openid;
                    binding.webview.postUrl(url, postData.getBytes());
                    break;
                }
                binding.webview.loadUrl(url);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                    if (null != uploadFile) {
                        Uri result = data == null || resultCode != Activity.RESULT_OK ? null
                                : data.getData();
                        uploadFile.onReceiveValue(result);
                        uploadFile = null;
                    }
                    if (null != uploadFiles) {
                        Uri result = data == null || resultCode != Activity.RESULT_OK ? null : data.getData();
                        uploadFiles.onReceiveValue(new Uri[]{result});
                        uploadFiles = null;
                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }
            if (null != uploadFiles) {
                uploadFiles.onReceiveValue(null);
                uploadFiles = null;
            }

        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (id == R.id.informationDetailFragment) {
            inflater.inflate(R.menu.inform, menu);
            this.menu = menu;
            refreshMenuState(menu, information.getUrl());
        }
    }

    private void refreshMenuState(Menu menu, String url) {
        Iterator it = YsuSelfStudyApplication.myinform.iterator();
        if (!it.hasNext()) {
            menu.getItem(1).setVisible(true);
            menu.getItem(2).setVisible(false);
            objectId = "";
        }
        while (it.hasNext()) {
            InformCollect next = (InformCollect) it.next();
            if (next.getUrl().equals(url)) {
                menu.getItem(1).setVisible(false);
                menu.getItem(2).setVisible(true);
                objectId = next.getObjectId();
                break;
            } else {
                menu.getItem(1).setVisible(true);
                menu.getItem(2).setVisible(false);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.inform_share:
                //通过QQ分享
                new BaseUiListener().shareInformation(requireActivity(), information);
                break;
            case R.id.inform_collect:
                viewModel.getUpResult().observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean) {
                            refreshMenuState(menu,information.getUrl());
                            Toast.makeText(YsuSelfStudyApplication.context, "添加成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(YsuSelfStudyApplication.context, "添加失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                //验证登录状态
                if (YsuSelfStudyApplication.tencent.isSessionValid()) {
                    viewModel.upload(information);
                } else {
                    Toast.makeText(YsuSelfStudyApplication.context, "请登录QQ", Toast.LENGTH_SHORT).show();
                }

                //登录成功后，使用
                break;
            case android.R.id.home:
                return super.onOptionsItemSelected(item);
            case R.id.cancel_collect:
                viewModel.cancel(objectId);
                viewModel.getCancelResult().observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean) {
                            item.setVisible(false);
                            Iterator it = YsuSelfStudyApplication.myinform.iterator();
                            while (it.hasNext()) {
                                InformCollect informCollect = (InformCollect) it.next();
                                if (informCollect.getObjectId().equals(objectId)) {
                                    it.remove();
                                    break;
                                }
                            }
                            refreshMenuState(menu, "");
                            Toast.makeText(YsuSelfStudyApplication.context, "删除成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(YsuSelfStudyApplication.context, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            default:
                break;
        }
        return true;
    }
}
