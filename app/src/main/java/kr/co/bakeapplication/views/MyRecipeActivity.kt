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
import kr.co.bakeapplication.views.adapters.RecipeListViewAdapter

class MyRecipeActivity : AppCompatActivity() {
    private val mBinding: ActivityMyRecipeBinding by lazy { DataBindingUtil.setContentView<ActivityMyRecipeBinding>(this, R.layout.activity_my_recipe) }
    private val mViewModel: MyRecipeViewModel by lazy {
        ViewModelProvider(this, RecipeViewModelFactory(this))
            .get(MyRecipeViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.viewmodel = mViewModel

        val pages = ArrayList<RecipePage>()
        pages.add(RecipePage("1","des1"))
        pages.add(RecipePage("2","des2"))
        mViewModel.addRecipe(Recipe("test1", pages))
        mViewModel.addRecipe(Recipe("test2", pages))
        mViewModel.addRecipe(Recipe("test3", pages))
    }

    companion object{
        @BindingAdapter("items")
        @JvmStatic
        fun setItems(recyclerView: RecyclerView, recipes: ObservableArrayList<Recipe>) {
            var adapter: RecipeListViewAdapter
            if (recyclerView.adapter == null) {
                adapter = RecipeListViewAdapter()
                recyclerView.adapter = adapter
            } else {
                adapter = recyclerView.adapter as RecipeListViewAdapter
            }

            adapter.updateRecipes(recipes)
        }
    }



}