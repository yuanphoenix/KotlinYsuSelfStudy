package com.example.ysuselfstudy.ui.first

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.ysuselfstudy.MainViewModel
import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.adapter.FirstVPAdapter
import com.example.ysuselfstudy.logic.getWeek
import com.example.ysuselfstudy.ui.classschedule.ClassScheduleViewModel
import com.example.ysuselfstudy.ui.classschedule.GlideEngine
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import java.util.*


class MainFragment : Fragment() {
    lateinit var viewPager: ViewPager2
    lateinit var viewPagerAdapter: FragmentStateAdapter
    lateinit var bottomNav: BottomNavigationView
    lateinit var mainViewModel: MainViewModel
    lateinit var navController: NavController
    lateinit var courseViewModel: ClassScheduleViewModel
    lateinit var toolbar: Toolbar
    private val TAG = "MainFragment"
    private var week = getWeek()


    companion object {
        fun newInstance() = MainFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.main_fragment, container, false)

        viewPager = view.findViewById(R.id.viewpager)
        bottomNav = view.findViewById(R.id.bottom_nav)
        viewPagerAdapter = FirstVPAdapter(this)
        viewPager.adapter = viewPagerAdapter
        viewPager.offscreenPageLimit = 1
        navController = findNavController()

        setHasOptionsMenu(true)


        toolbar = requireActivity().findViewById(R.id.toolbar)


        bottomNav.setOnNavigationItemSelectedListener() {
            var id = it.itemId
            viewPager.setCurrentItem(
                when (id) {
                    R.id.empty_room -> 0
                    R.id.class_schedule -> 1
                    R.id.user_info -> 2
                    else -> 0
                }
            )
            return@setOnNavigationItemSelectedListener false
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                requireActivity().invalidateOptionsMenu()
            }

            override fun onPageSelected(position: Int) {
                bottomNav.menu.getItem(position).setChecked(true)
                when (position) {
                    0 -> {
                        toolbar.visibility = View.VISIBLE
                        toolbar.title = "空教室"
                    }
                    1 -> {
                        toolbar.title = "第${getWeek()}周"
                    }
                    2 -> {
                        toolbar.visibility = View.VISIBLE
                        toolbar.title = "公告"
                    }
                }
            }
        })

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        courseViewModel =
            ViewModelProvider(requireActivity()).get(ClassScheduleViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.course_menu, menu)
        if (viewPager.currentItem == 1) {
            menu.findItem(R.id.refresh_course).setVisible(true)
            menu.findItem(R.id.choose_week).setVisible(true)
            menu.findItem(R.id.change_background).setVisible(true)
        } else {
            menu.findItem(R.id.refresh_course).setVisible(false)
            menu.findItem(R.id.choose_week).setVisible(false)
            menu.findItem(R.id.change_background).setVisible(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> return super.onOptionsItemSelected(item)
            R.id.refresh_course -> courseViewModel.clearCourse(mainViewModel.authenticationState == MainViewModel.AuthenticationState.AUTHENTICATED)
            R.id.choose_week -> showDialog()
            R.id.change_background -> {
                PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .enableCrop(true)//是否开启裁剪
                    .withAspectRatio(9, 16)//裁剪比例

                    .loadImageEngine(GlideEngine.createGlideEngine())
                    .selectionMode(PictureConfig.SINGLE)
                    .forResult(object : OnResultCallbackListener<LocalMedia> {
                        override fun onResult(result: MutableList<LocalMedia>?) {
                            for (media in result!!) {
//                                Log.i(TAG, "是否压缩:" + media.isCompressed)
//                                Log.i(TAG, "压缩:" + media.compressPath)
//                                Log.i(TAG, "原图:" + media.path)
//                                Log.i(TAG, "是否裁剪:" + media.isCut)
//                                Log.i(TAG, "裁剪:" + media.cutPath)
//                                Log.i(TAG, "是否开启原图:" + media.isOriginal)
//                                Log.i(TAG, "原图路径:" + media.originalPath)
//                                Log.i(
//                                    TAG,
//                                    "Android Q 特有Path:" + media.androidQToPath
//                                )
                                val realpath = if (media.isCut) media.cutPath else media.path
                                val editor = requireActivity().getSharedPreferences(
                                    "data",
                                    Context.MODE_PRIVATE
                                ).edit()
                                editor.putString("picpath", realpath)
                                editor.apply()
                                courseViewModel.setPath(realpath)
                            }
                        }


                        override fun onCancel() {

                        }

                    })
            }
        }
        return true

    }


    private fun showDialog() {
        val list = ArrayList<String>()
        for (i in 1..25) {
            list.add("第" + i + "周")
        }
        val items = arrayOfNulls<String>(25)
        list.toArray(items)
        val singleChoiceDialog =
            AlertDialog.Builder(requireActivity())
        singleChoiceDialog.setIcon(R.mipmap.ic_week)
        singleChoiceDialog.setTitle("选择")
        //第二个参数是默认的选项
        singleChoiceDialog.setSingleChoiceItems(
            items, getWeek() - 1
        ) { dialog, which -> week = which }
        singleChoiceDialog.setPositiveButton(
            "确定"
        ) { dialog, which ->
            if (week != -1) {
                Log.d(TAG, "showDialog: " + week);
                //  initByWeek(weekChoice + 1)
                courseViewModel.getCourse(week + 1)
                toolbar.setTitle("第${week + 1}周")
            }
        }
        singleChoiceDialog.show()
    }
}
