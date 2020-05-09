package com.example.ysuselfstudy.ui.classschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.data.Course
import com.example.ysuselfstudy.databinding.CourseDetailPopLayoutBinding


/**
 * @author  Ahyer
 * @date  2020/4/22 11:17
 * @version 1.0
 */
class CourseDetailDialogFragment(val course: Course) : DialogFragment() {
    private lateinit var binding: CourseDetailPopLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.course_detail_pop_layout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.titleText.text = course.courseName
        binding.teacherText.text = course.teacher
        binding.locationText.text = course.position
        binding.weekText.text = "第${course.startWeek}-${course.endWeek}周"
        binding.nodeText.text =
            "周${course.dayOfWeek} 第${course.beginNode}-${course.beginNode + course.continued - 1}节"


    }

    override fun onResume() {
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
        super.onResume()
    }
}