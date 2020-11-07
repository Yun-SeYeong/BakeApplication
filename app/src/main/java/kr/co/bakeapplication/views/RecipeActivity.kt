package kr.co.bakeapplication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.bumptech.glide.Glide
import kr.co.bakeapplication.R
import kr.co.bakeapplication.data.Recipe
import kr.co.bakeapplication.data.RecipePage
import kr.co.bakeapplication.databinding.ActivityLoginBinding
import kr.co.bakeapplication.databinding.ActivityRecipeBinding
import kr.co.bakeapplication.fragments.ScreenSlidePageFragment
import kr.co.bakeapplication.repositorys.FirebaseAuthFactory
import kr.co.bakeapplication.viewmodels.LoginViewModel
import kr.co.bakeapplication.viewmodels.RecipeViewModel

class RecipeActivity : AppCompatActivity() {
    private val mBinding: ActivityRecipeBinding by lazy { DataBindingUtil.setContentView<ActivityRecipeBinding>(this, R.layout.activity_recipe) }
    private val mViewModel: RecipeViewModel by lazy { RecipeViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val recipe = intent.getSerializableExtra("RECIPE") as Recipe

        val pageAdapter = ScreenSlidePagerAdapter(recipe.pages!!, supportFragmentManager)
        mBinding.layoutRecipeViewpager.adapter = pageAdapter

        Glide.with(this).load(recipe.thumbNailUri).into(mBinding.imageviewRecipeThumbnailBackground)

        mBinding.buttonRecipeStartrecipe.setOnClickListener {
            mBinding.layoutRecipeStartpage.visibility = View.GONE
        }
    }

    private inner class ScreenSlidePagerAdapter(private val recipePages: ArrayList<RecipePage>,fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = recipePages.size

        override fun getItem(position: Int): Fragment = ScreenSlidePageFragment(recipePages[position])
    }
}