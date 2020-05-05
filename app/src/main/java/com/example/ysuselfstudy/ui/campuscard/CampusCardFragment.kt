package com.example.ysuselfstudy.ui.campuscard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.databinding.CampusCardFragmentBinding

class CampusCardFragment : Fragment() {
    private val TAG = "CampusCardFragment"

    companion object {
        fun newInstance() =
            CampusCardFragment()
    }

    private lateinit var viewModel: CampusCardViewModel
    private lateinit var binding: CampusCardFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.campus_card_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CampusCardViewModel::class.java)
        viewModel.surplus.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onActivityCreated: " + it);
        })
        binding.test.setOnClickListener { viewModel.getCrad() }
    }

}
