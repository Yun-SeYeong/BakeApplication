package kr.co.bakeapplication.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.bakeapplication.R
import kr.co.bakeapplication.data.RecipePage
import kr.co.bakeapplication.databinding.AddRecipePageListItemBinding
import kr.co.bakeapplication.viewmodels.AddPageViewModel
import kr.co.bakeapplication.views.AddRecipeActivity

class AddRecipePageListViewAdapter: RecyclerView.Adapter<AddRecipePageBindingViewHolder<AddRecipePageListItemBinding>>() {
    private val pages = ArrayList<RecipePage>()
    private lateinit var mContext: AddRecipeActivity

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddRecipePageBindingViewHolder<AddRecipePageListItemBinding> {
        mContext = parent.context as AddRecipeActivity
        val view = LayoutInflater.from(parent.context).inflate(R.layout.add_recipe_page_list_item, parent, false)
        return AddRecipePageBindingViewHolder(view)
    }

    override fun getItemCount(): Int = pages.size

    override fun onBindViewHolder(
        holder: AddRecipePageBindingViewHolder<AddRecipePageListItemBinding>,
        position: Int
    ) {
        holder.mBinding.page = pages[position]
        val viewmodal = AddPageViewModel(mContext)
        holder.mBinding.viewmodel = viewmodal
        viewmodal.pageImage.observe(mContext, Observer {
            if (it != null) {
                holder.mBinding.buttonRecipepageImagepicker.setImageURI(it)
                pages[position].imageUri = it.path!!
            }
        })
    }

    fun updatePages(pages: ArrayList<RecipePage>) {
        val callback = AddRecipePageDiffCallback(this.pages, pages)
        val result = DiffUtil.calculateDiff(callback)
        this.pages.clear()
        this.pages.addAll(pages)
        result.dispatchUpdatesTo(this)
    }

}