package kr.co.bakeapplication.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import kr.co.bakeapplication.R
import kr.co.bakeapplication.data.Recipe
import kr.co.bakeapplication.data.RecipePage
import kr.co.bakeapplication.databinding.ActivityMyRecipeBinding
import kr.co.bakeapplication.repositorys.RecipeViewModelFactory
import kr.co.bakeapplication.viewmodels.MyRecipeViewModel
import kr.co.bakeapplication.viewmodels.RecipeViewModel
import kr.co.bakeapplication.views.adapters.MyRecipeListViewAdapter
import kr.co.bakeapplication.views.adapters.RecipeListViewAdapter

class MyRecipeActivity : BaseActivity() {
    private val mBinding: ActivityMyRecipeBinding by lazy { DataBindingUtil.setContentView<ActivityMyRecipeBinding>(this, R.layout.activity_my_recipe) }
    private val mViewModel: MyRecipeViewModel by lazy {
        ViewModelProvider(this, RecipeViewModelFactory(this))
            .get(MyRecipeViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.viewmodel = mViewModel

        mBinding.swiperefreshlayoutMyrecipeRecipes.setOnRefreshListener {
            mViewModel.syncRecipes()
            endLoading()
        }
    }

    companion object{
        @BindingAdapter("items")
        @JvmStatic
        fun setItems(recyclerView: RecyclerView, viewModel: MyRecipeViewModel) {
            var adapter: MyRecipeListViewAdapter
            if (recyclerView.adapter == null) {
                adapter = MyRecipeListViewAdapter(viewModel)
                recyclerView.adapter = adapter
            } else {
                adapter = recyclerView.adapter as MyRecipeListViewAdapter
            }

            adapter.updateRecipes(viewModel.recipeList)
        }
        @BindingAdapter("recipes")
        @JvmStatic
        fun setRecipes(recyclerView: RecyclerView, recipes: ObservableArrayList<Recipe>) {
            Log.d("BaseActivity",  "items")
            var adapter: MyRecipeListViewAdapter
            if (recyclerView.adapter != null) {
                adapter = recyclerView.adapter as MyRecipeListViewAdapter
                adapter.updateRecipes(recipes)
            }
        }
    }

    override fun startLoading() {
        super.startLoading()
    }

    override fun endLoading() {
        super.endLoading()
        mBinding.swiperefreshlayoutMyrecipeRecipes.isRefreshing = false
    }

}