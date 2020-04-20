package com.example.ysuselfstudy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ysuselfstudy.R;
import com.example.ysuselfstudy.data.Grade;
import com.example.ysuselfstudy.databinding.GradeItemLayoutBinding;

import java.util.ArrayList;

/**
 * @author Ahyer
 * @version 1.0
 * @date 2020/4/20 12:13
 */
public class GradeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Grade> mData = new ArrayList<>();

    public GradeAdapter(ArrayList<Grade> list) {
        this.mData = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grade_semester_layout, parent, false);
            return new SemesterHolder(view);
        } else {
            GradeItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.grade_item_layout, parent, false);
            return new ItemViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SemesterHolder) {
            SemesterHolder mHolder = (SemesterHolder) holder;
            mHolder.textView.setText(mData.get(position).getDate() + "第" + mData.get(position).getSemester() + "学期");
        } else {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            Grade temp = mData.get(position);
            itemViewHolder.binding.setGradeBean(temp);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getProject().equals("")) {
            return 0;
        }
        return 1;
    }

    class SemesterHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public SemesterHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.semeter);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private GradeItemLayoutBinding binding;

        public ItemViewHolder(@NonNull GradeItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
