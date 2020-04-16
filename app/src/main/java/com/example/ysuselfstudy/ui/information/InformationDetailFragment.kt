package com.example.ysuselfstudy.ui.information

import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.databinding.InformationDetailFragmentBinding

class InformationDetailFragment : Fragment() {

    companion object {
        fun newInstance() = InformationDetailFragment()
    }

    private lateinit var viewModel: InformationDetailViewModel
    private lateinit var binding: InformationDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.information_detail_fragment,
            container,
            false
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var amount: String? = arguments?.getString("amount")

        viewModel = ViewModelProvider(this).get(InformationDetailViewModel::class.java)
        viewModel.getInformation(amount)
        viewModel.html.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                //报错提示
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    binding.htmlText.text = (Html.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY))
                } else {
                    binding.htmlText.text = (Html.fromHtml(it))
                }
            }
        })


    }

}
