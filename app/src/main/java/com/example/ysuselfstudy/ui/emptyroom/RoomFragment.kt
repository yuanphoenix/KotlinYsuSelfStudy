package com.example.ysuselfstudy.ui.emptyroom

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController

import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.databinding.RoomFragmentBinding

class RoomFragment : Fragment() {

    companion object {
        fun newInstance() = RoomFragment()
    }

    private lateinit var viewModel: RoomViewModel
    private lateinit var roomDataBing:RoomFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // RoomFragmentBinding.inflate(layoutInflater)
        var navController =findNavController()


        roomDataBing = DataBindingUtil.inflate(inflater, R.layout.room_fragment, container, false)

        return roomDataBing.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RoomViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
