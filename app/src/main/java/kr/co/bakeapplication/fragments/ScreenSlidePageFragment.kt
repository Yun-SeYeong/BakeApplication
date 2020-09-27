package kr.co.bakeapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
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
        mBinding.scrollviewRecipeDescription.text = page.description
        return mBinding.root
    }
}