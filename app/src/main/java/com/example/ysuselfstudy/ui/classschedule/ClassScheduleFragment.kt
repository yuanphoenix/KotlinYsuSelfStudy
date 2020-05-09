package com.example.ysuselfstudy.ui.classschedule

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.ysuselfstudy.MainViewModel
import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.YsuSelfStudyApplication
import com.example.ysuselfstudy.adapter.CourseAdapter
import com.example.ysuselfstudy.adapter.WeekAdapter
import com.example.ysuselfstudy.data.Course
import com.example.ysuselfstudy.databinding.ClassScheduleFragmentBinding
import com.example.ysuselfstudy.logic.Dao


class ClassScheduleFragment : Fragment() {
    companion object {
        fun newInstance() = ClassScheduleFragment()
    }

    private val TAG = "ClassScheduleFragment"
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModel: ClassScheduleViewModel
    private lateinit var binding: ClassScheduleFragmentBinding
    private lateinit var navController: NavController
    private lateinit var adapter: CourseAdapter
    private var mData = ArrayList<Course>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.class_schedule_fragment, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        binding.classLoginBtn.setOnClickListener {
            navController.navigate(R.id.loginFragment)
        }
        var layoutManager = StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL)
        binding.schedule.layoutManager = layoutManager
        adapter = CourseAdapter(mData)
        binding.schedule.adapter = adapter


        adapter.setOnClickListener(object :CourseAdapter.OnclickListener{
            override fun OnItemClickListener(course: Course) {
                var temp = CourseDetailDialogFragment(course)
                temp.show(parentFragmentManager, "hello")
            }

        })


        viewModel =
            ViewModelProvider(requireActivity()).get(ClassScheduleViewModel::class.java)//本地的ViewModel
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.nodeRecy.layoutManager = LinearLayoutManager(YsuSelfStudyApplication.context)
        binding.nodeRecy.adapter = WeekAdapter(viewModel.timeNode)

        //相连滚动
        val scrollListeners =
            arrayOfNulls<RecyclerView.OnScrollListener>(2)
        scrollListeners[0] = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                binding.nodeRecy.removeOnScrollListener(scrollListeners[1]!!)
                binding.nodeRecy.scrollBy(dx, dy)
                binding.nodeRecy.addOnScrollListener(scrollListeners[1]!!)
            }
        }
        scrollListeners[1] = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                binding.schedule.removeOnScrollListener(scrollListeners[0]!!)
                binding.schedule.scrollBy(dx, dy)
                binding.schedule.addOnScrollListener(scrollListeners[0]!!)
            }
        }
        binding.schedule.addOnScrollListener(scrollListeners[0]!!)
        binding.nodeRecy.addOnScrollListener(scrollListeners[1]!!)


        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        if (Dao.isCourseEmpty()) {
            mainViewModel.state.observe(
                viewLifecycleOwner,
                Observer { authenticationState ->

                    when (authenticationState) {
                        false -> {
                            binding.classLoginBtn.visibility = View.VISIBLE
                        }
                        true -> {
                            showUi()
                        }
                    }
                })
        } else {
            showUi()
        }


        //观察课程的返回情况。
        viewModel.nowWeekCourse.observe(this.viewLifecycleOwner, Observer { result ->
            if (result != null) {
                if (result is Int) {
                    navController.navigate(R.id.webFragment)
                } else if (result is ArrayList<*>) {
                    mData.clear()
                    mData.addAll(result as Collection<Course>)
                    adapter.notifyDataSetChanged()
                }
            }


        })


    }

    private fun showUi() {
        binding.classLoginBtn.visibility = View.GONE
        binding.classBackground.visibility = View.VISIBLE

        Glide.with(this)
            .asBitmap()
            .load(R.drawable.ic_pikaqiu)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_error)
            .into(binding.classBackground)
        viewModel.getCourse()
    }

}
