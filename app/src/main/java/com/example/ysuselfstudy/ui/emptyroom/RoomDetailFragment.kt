package com.example.ysuselfstudy.ui.emptyroom

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.YsuSelfStudyApplication
import com.example.ysuselfstudy.adapter.RoomAdapter
import com.example.ysuselfstudy.data.EmptyRoom
import com.example.ysuselfstudy.databinding.RoomDetailFragmentBinding

class RoomDetailFragment : Fragment() {
    private val TAG = "RoomDetailFragment"

    companion object {
        fun newInstance() = RoomDetailFragment()
    }

    private lateinit var viewModel: RoomViewModel
    private lateinit var adapter: RoomAdapter
    private lateinit var amount: String
    private lateinit var detailFragmentBinding: RoomDetailFragmentBinding
    private var mData = ArrayList<EmptyRoom>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.room_detail_fragment, container, false)

        setHasOptionsMenu(true)

        amount = arguments?.getString("amount")!!

        return detailFragmentBinding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(RoomViewModel::class.java)

        mData = viewModel.getConditionRoom(amount)
        adapter = RoomAdapter(mData)
        detailFragmentBinding.roomRecycler.adapter = adapter
        detailFragmentBinding.roomRecycler.layoutManager =
            LinearLayoutManager(YsuSelfStudyApplication.context)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (amount.contains("里仁")) {
            inflater.inflate(R.menu.liren_menu, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> return super.onOptionsItemSelected(item)
            R.id.id_liren_a -> changeCondition("A")
            R.id.id_liren_b -> changeCondition("B")
            R.id.id_liren_c -> changeCondition("C")
            R.id.id_liren_d -> changeCondition("D")
        }


        return true
    }


    private fun changeCondition(contidion: String) {
        viewModel.emptyRoom.clear()
        viewModel.emptyRoom.addAll(viewModel.getConditionRoom(amount))
        mData.clear()
        adapter.notifyDataSetChanged()
        for (e in viewModel.emptyRoom) {
            if (e.room.contains(contidion)) {
                mData.add(e)
            }
        }
        adapter.notifyDataSetChanged()
    }
}
