package kr.co.bakeapplication.views

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kr.co.bakeapplication.R
import kr.co.bakeapplication.data.RecipePage
import kr.co.bakeapplication.databinding.ActivityAddRecipeBinding
import kr.co.bakeapplication.viewhandlers.AddRecipePageActivityHandler
import kr.co.bakeapplication.viewmodels.AddRecipeViewModel
import kr.co.bakeapplication.views.adapters.AddRecipePageListViewAdapter

class AddRecipeActivity : BaseActivity(), AddRecipePageActivityHandler {
    private val mBinding: ActivityAddRecipeBinding by lazy { DataBindingUtil.setContentView<ActivityAddRecipeBinding>(this, R.layout.activity_add_recipe) }
    private val mViewModel: AddRecipeViewModel by lazy {
        AddRecipeViewModel(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding.viewmodel = mViewModel
        mViewModel.thumbNailImage.observe(this, Observer {
            mBinding.imageviewAddrecipepageThumbnail.setImageURI(it)
        })

        mBinding.nestedscrollviewAddrecipeRecipe.setOnScrollChangeListener(View.OnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > oldScrollY) {
                mBinding.floatingActionButton.visibility = View.GONE
                mBinding.buttonAddrecipeAddpage.visibility = View.GONE
            }
            if (scrollY < oldScrollY) {
                mBinding.floatingActionButton.visibility = View.VISIBLE
                mBinding.buttonAddrecipeAddpage.visibility = View.VISIBLE
            }
        })
    }

    companion object{
        @BindingAdapter("items")
        @JvmStatic
        fun setItems(recyclerView: RecyclerView, pages: ObservableArrayList<RecipePage>) {
            var adapter: AddRecipePageListViewAdapter
            if (recyclerView.adapter == null) {
                adapter = AddRecipePageListViewAdapter()
                recyclerView.adapter = adapter
            } else {
                adapter = recyclerView.adapter as AddRecipePageListViewAdapter
            }
            adapter.updatePages(pages)
        }
    }

    override fun refreshList() {
        mBinding.recyclerviewAddrecipePages.adapter!!.notifyDataSetChanged()
    }
}