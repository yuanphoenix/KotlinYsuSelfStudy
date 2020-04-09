package com.example.ysuselfstudy.ui.classschedule

import ExpandAdapte
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahyer.recyclerviewexpandable.UltimateAdapter
import com.example.ysuselfstudy.R
import java.util.*

class ClassScheduleFragment : Fragment() {
    companion object {
        fun newInstance() = ClassScheduleFragment()
    }

    private lateinit var viewModel: ClassScheduleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.class_schedule_fragment, container, false)
        var recy: RecyclerView = view.findViewById(R.id.my_recycler)
        val dirs: MutableList<ExpandAdapte.UnitData<String, String>> =
            ArrayList<ExpandAdapte.UnitData<String, String>>()
        var uini1: ExpandAdapte.UnitData<String, String> =
            ExpandAdapte.UnitData("哈利波特", listOf("罗恩", "赫敏"))
        var uini2: ExpandAdapte.UnitData<String, String> =
            ExpandAdapte.UnitData("伏地魔", listOf("马尔福", "莱斯特兰奇"))

        dirs.add(0, uini1)
        dirs.add(1, uini2)

        var controller = findNavController()


        var adapter = UltimateAdapter(dirs,controller)
        var layout = LinearLayoutManager(view.context)
        recy.adapter = adapter
        recy.layoutManager=layout
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ClassScheduleViewModel::class.java)

    }

}
