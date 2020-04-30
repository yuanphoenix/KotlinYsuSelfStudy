package com.example.ysuselfstudy

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.ysuselfstudy.databinding.NavHeaderBinding
import com.example.ysuselfstudy.logic.qqlogin.BaseUiListener
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView
import com.tencent.tauth.Tencent
import java.util.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var baseUiListener = BaseUiListener()
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        var drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.fitsSystemWindows = false
        window.statusBarColor = Color.TRANSPARENT
        var te: AppBarLayout = findViewById(R.id.titlebar)
        te.setPadding(0, 60, 0, 0)
        var navigation: NavigationView = findViewById(R.id.nav_view)

        var navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(setOf(R.id.mainFragment), drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navigation.setupWithNavController(navController)

        //初始化全局ViewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //获得加载的侧边栏布局
        val inflateHeaderView = navigation.getHeaderView(0)
        val navBinding = NavHeaderBinding.bind(inflateHeaderView)

        //侧边栏绑定ViewModel
        navBinding.viewmodel = viewModel
        viewModel.keepQQLogin()

        //少了这一句，就不会更新侧边栏了。
        navBinding.lifecycleOwner = this


        //控制侧边栏滑动，根据导航的目的地不同来进行操作
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id != R.id.mainFragment) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }

        }

        val calendar = Calendar.getInstance()
        val data = calendar[Calendar.DATE]
        val storeDate = getSharedPreferences("date", Context.MODE_PRIVATE)
        val old = storeDate.getInt("num", 0)
        if (old != data) viewModel.deleteRoom()
        val editor = getSharedPreferences("date", Context.MODE_PRIVATE).edit()
        editor.putInt("num", data)
        editor.apply()

        baseUiListener.setOnSuccessListener(object : BaseUiListener.OnSuccessListener {
            override fun changeImageView() {
                viewModel.keepQQLogin()
            }
        })
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Tencent.onActivityResultData(requestCode, resultCode, data, baseUiListener)
    }

    /**
     * 登录按钮
     */
    fun loginQQ(view: View) {
        if (!YsuSelfStudyApplication.tencent.isSessionValid()) {
            YsuSelfStudyApplication.tencent.login(
                //这里的this必须是Activity或Fragment，而Viewmodel中不能持有相关视图的引用
                //但是这个项目很特殊，只有一个Activity
                //回顾ViewModel的初衷：从界面控制器逻辑中分离出视图数据所有权的做法更易行且更高效。
                this,
                "all",
                baseUiListener
            );
        }
    }

}
