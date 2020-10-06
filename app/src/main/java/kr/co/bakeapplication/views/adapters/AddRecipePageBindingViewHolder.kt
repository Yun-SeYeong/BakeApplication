package kr.co.bakeapplication.views.adapters

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class AddRecipePageBindingViewHolder<T: ViewDataBinding>: RecyclerView.ViewHolder{
    val mBinding: T

    constructor(itemView: View): super(itemView) {
        this.mBinding = DataBindingUtil.bind(itemView)!!
    }
}