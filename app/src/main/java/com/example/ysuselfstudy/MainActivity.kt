package com.example.ysuselfstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.ysuselfstudy.data.User
import com.example.ysuselfstudy.logic.Repository
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.app_bar_main.*
import org.litepal.LitePal

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        var drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        var navigation: NavigationView = findViewById(R.id.nav_view)

        var navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.mainFragment), drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navigation.setupWithNavController(navController)

        //初始化全局ViewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        if (LitePal.count(User::class.java) > 0) {
            val user = LitePal.findFirst(User::class.java)
            viewModel.getLogin(user)
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
