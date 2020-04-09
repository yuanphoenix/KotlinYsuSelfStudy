package com.example.ysuselfstudy.ui.emptyroom

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.YsuSelfStudyApplication
import com.example.ysuselfstudy.databinding.RoomFragmentBinding
import com.example.ysuselfstudy.ui.classschedule.ExpandAdapte
import com.example.ysuselfstudy.ui.classschedule.UltimateAdapter
import kotlinx.android.synthetic.main.room_fragment.*
import java.util.ArrayList

class RoomFragment : Fragment() {

    companion object {
        fun newInstance() = RoomFragment()
    }

    private lateinit var viewModel: RoomViewModel
    private lateinit var roomDataBing: RoomFragmentBinding
    private lateinit var timePopWindow: TimePopWindow
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // RoomFragmentBinding.inflate(layoutInflater)
        var navController = findNavController()
        //初始化ViewModel，里面有本Fragment的所有data
        viewModel =  ViewModelProvider(requireActivity()).get(RoomViewModel::class.java)
        roomDataBing = DataBindingUtil.inflate(inflater, R.layout.room_fragment, container, false)
        roomDataBing.viewmodel = viewModel

        var adapter = UltimateAdapter(viewModel.dirs, navController)
        var layout = LinearLayoutManager(roomDataBing.root.context)
        roomDataBing.myRecycler.adapter = adapter
        roomDataBing.myRecycler.layoutManager = layout
        timePopWindow = TimePopWindow(roomDataBing.root.context)

        return roomDataBing.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roomDataBing.timeChoose.setOnClickListener {
            timePopWindow.showPopupWindow()
        }

       timePopWindow.setOnCertainClickListener(object :TimePopWindow.OnCertainClickListener{
           override fun clickCertain() {
               roomDataBing.timeChoose.setText("好了好了")
           }
       })
    }
}
