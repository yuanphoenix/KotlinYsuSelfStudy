package com.example.ysuselfstudy.ui.emptyroom

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.YsuSelfStudyApplication
import com.example.ysuselfstudy.adapter.UltimateAdapter
import com.example.ysuselfstudy.databinding.RoomFragmentBinding
import com.example.ysuselfstudy.logic.showToast
import com.example.ysuselfstudy.ui.TimePopWindow
import com.example.ysuselfstudy.viewmodelfactory.MyViewModelFactory

class RoomFragment : Fragment() {
    private val TAG = "RoomFragment"

    companion object {
        fun newInstance() = RoomFragment()
    }

    lateinit var viewModel: RoomViewModel
    private lateinit var roomDataBing: RoomFragmentBinding
    private lateinit var timePopWindow: TimePopWindow
    private lateinit var navController: NavController
    private var isAgree: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        //初始化ViewModel，里面有本Fragment的所有data
        viewModel = ViewModelProvider(
            requireActivity(),
            MyViewModelFactory()
        ).get(RoomViewModel::class.java)
        viewModel.getBiying()
        roomDataBing = DataBindingUtil.inflate(inflater, R.layout.room_fragment, container, false)
        roomDataBing.viewmodel = viewModel

        roomDataBing.lifecycleOwner = this

        var adapter = UltimateAdapter(
            viewModel.dirs,
            navController
        )
        var layout = LinearLayoutManager(roomDataBing.root.context)
        roomDataBing.myRecycler.adapter = adapter
        roomDataBing.myRecycler.layoutManager = layout
        timePopWindow = TimePopWindow(this)
        roomDataBing.swipeRefresh.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        roomDataBing.swipeRefresh.setOnRefreshListener {
            refreshRoom()
        }
        return roomDataBing.root
    }

    fun refreshRoom() {
        roomDataBing.swipeRefresh.isRefreshing = true;
        isAgree = !isAgree
        viewModel.getRoom()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roomDataBing.timeChoose.setOnClickListener {
            timePopWindow.showPopupWindow()
        }

        //观察textview的数据变化
        viewModel.time.observe(requireActivity(), Observer { ti ->
            roomDataBing.timeChoose.text = ti.toString()
        })

        viewModel.emptyRoomLiveData.observe(viewLifecycleOwner, Observer { result ->
            roomDataBing.swipeRefresh.isRefreshing = false
            if (result != null && isAgree) {
                isAgree = !isAgree
                if (result.equals("false")) {
                    //要更新空教室
                    showDialog()
                } else {
                    "空教室由 ${result} 提供".showToast()
                }

            }
            viewModel.emptyRoomLiveData.removeObservers(this)

        })
    }

    /**
     * 弹出更新教室的弹窗
     */
    private fun showDialog() {
        val dialog =
            AlertDialog.Builder(
                requireContext(),
                R.style.Theme_MaterialComponents_Light_Dialog_Alert
            )
        dialog.setTitle("愿意花费一些时间来为大家同步吗？")
        dialog.setMessage("我们发现你可以同步空教室给大家，你愿意这么做吗？")
        dialog.setPositiveButton(
            "可以啊"
        ) { dialogInterface, i ->
            navController.navigate(R.id.uploadFragment)
            dialogInterface.dismiss()
        }
        dialog.setNegativeButton(
            "不愿意"
        ) { dialogInterface, i -> dialogInterface.dismiss() }

        dialog.show()
    }

}
