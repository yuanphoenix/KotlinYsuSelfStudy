package com.example.ysuselfstudy.ui.collectinformation

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.YsuSelfStudyApplication
import com.example.ysuselfstudy.adapter.InformationAdapter
import com.example.ysuselfstudy.data.Information

class CollectFragment : Fragment() {

    companion object {
        fun newInstance() = CollectFragment()
    }

    private lateinit var viewModel: CollectViewModel
    private lateinit var recyclerView: RecyclerView
    private var mData = ArrayList<Information>()
    private lateinit var adapter: InformationAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.collect_fragment, container, false)
        recyclerView = view.findViewById(R.id.collect_recycler)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CollectViewModel::class.java)

        adapter = InformationAdapter(mData, findNavController())
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                YsuSelfStudyApplication.context,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerView.layoutManager = LinearLayoutManager(YsuSelfStudyApplication.context)
        viewModel.getInformationList()
        viewModel.inforList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                mData.clear()
                for (a in it) {
                    mData.add(Information(a.title, a.url, a.time))
                }
                adapter.notifyDataSetChanged()
            }
        })

    }

}