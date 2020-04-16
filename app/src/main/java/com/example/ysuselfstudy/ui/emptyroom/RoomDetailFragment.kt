package com.example.ysuselfstudy.ui.emptyroom

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.databinding.RoomDetailFragmentBinding

class RoomDetailFragment : Fragment() {

    companion object {
        fun newInstance() = RoomDetailFragment()
    }

    private lateinit var viewModel: RoomDetailViewModel
    private lateinit var detailFragmentBinding: RoomDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.room_detail_fragment, container, false)

        var amount: String? = arguments?.getString("amount")

        detailFragmentBinding.textView3.setText(amount)
        return detailFragmentBinding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RoomDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
