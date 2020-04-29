package com.example.ysuselfstudy.ui.classschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.ysuselfstudy.R

/**
 * @author  Ahyer
 * @date  2020/4/22 11:17
 * @version 1.0
 */
class CourseDetailDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.course_detail_pop_layout,container)
    }
}