package kr.co.bakeapplication.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.bakeapplication.R
import kr.co.bakeapplication.data.RecipePage
import kr.co.bakeapplication.databinding.AddRecipePageListItemBinding

class AddRecipePageListViewAdapter: RecyclerView.Adapter<AddRecipePageBindingViewHolder<AddRecipePageListItemBinding>>() {
    private val pages = ArrayList<RecipePage>()
    private lateinit var mContext: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddRecipePageBindingViewHolder<AddRecipePageListItemBinding> {
        mContext = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.add_recipe_page_list_item, parent, false)
        return AddRecipePageBindingViewHolder(view)
    }

    override fun getItemCount(): Int = pages.size

    override fun onBindViewHolder(
        holder: AddRecipePageBindingViewHolder<AddRecipePageListItemBinding>,
        position: Int
    ) {
        holder.mBinding.page = pages[position]
    }

    fun updatePages(pages: ArrayList<RecipePage>) {
        val callback = AddRecipePageDiffCallback(this.pages, pages)
        val result = DiffUtil.calculateDiff(callback)
        this.pages.clear()
        this.pages.addAll(pages)
        result.dispatchUpdatesTo(this)
    }

}