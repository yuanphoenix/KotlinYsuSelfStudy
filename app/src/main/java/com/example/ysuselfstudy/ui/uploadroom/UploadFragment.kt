package com.example.ysuselfstudy.ui.uploadroom

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.databinding.UploadFragmentBinding

class UploadFragment : Fragment() {

    companion object {
        fun newInstance() = UploadFragment()
    }

    private lateinit var viewModel: UploadViewModel
    private lateinit var binding: UploadFragmentBinding
    private lateinit var toolbar: Toolbar
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.upload_fragment, container, false)
        toolbar = requireActivity().findViewById(R.id.toolbar)
        toolbar.visibility = View.GONE
        navController = findNavController()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UploadViewModel::class.java)
        viewModel.process.observe(viewLifecycleOwner, Observer {
            if (it == -1) {
                binding.showTip.text = "更新失败，即将退出页面〒▽〒"
                quitFragment()
            } else
                binding.progressBar1.progress = it
        })
        viewModel.getCrad()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            //拦截返回键，显式弹窗
        }

        binding.progressBar1.setOnProgressListener {
            binding.showTip.text = "更新成功，Thanks♪(･ω･)ﾉ，3秒后退出"
            quitFragment()
        }

    }


    private fun quitFragment() {
        val handler = Handler()
        handler.postDelayed(
            Runnable {
                navController.popBackStack()
            }, 3000
        ); //3秒后执行Runnable中的run方法

    }

}
