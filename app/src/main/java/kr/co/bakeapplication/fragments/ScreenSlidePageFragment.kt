package kr.co.bakeapplication.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kr.co.bakeapplication.R
import kr.co.bakeapplication.data.RecipePage
import kr.co.bakeapplication.databinding.FragmentRecipePageBinding

class ScreenSlidePageFragment(private val page: RecipePage): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mBinding = inflate<FragmentRecipePageBinding>(inflater, R.layout.fragment_recipe_page, container, false)
        mBinding.scrollviewRecipeTitle.text = page.title
        Log.d("BaseActivity", "uri: " + page.imageUri)
        Glide.with(this).load(page.imageUri).into(mBinding.imageviewRecipeImage)
        mBinding.scrollviewRecipeDescription.text = page.description
        return mBinding.root
    }
}