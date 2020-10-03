package kr.co.bakeapplication.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
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

//        val pages = ArrayList<RecipePage>()
//        pages.add(RecipePage("1. 계란풀기","계란을 까서 보울에 담아 휘퍼로 잘 풀어준다(너무 쎄게 빠르게 저으면 거품이 많이 생기니 주의)\n"))
//        pages.add(RecipePage("2. 설탕물 풀기","설탕과 꿀을 1에 넣고 섞어준다\n"))
//        pages.add(RecipePage("3. 계란풀기","박력분과 BP(베이킹파우더)를 체쳐서 2에 넣고 섞어준다\n"))
//        pages.add(RecipePage("4. 계란풀기","버터를 중탕하거나 전자레인지에 녹여 넣고 섞어준다\n" +
//                "(너무 적게 섞으면 구울때 반죽이 배꼽으로 흐르고 너무 많이 적으면 글루텐이 많이 생겨 식감이 바뀌므로 버터가 다 섞이고 나서부터 15~25회 정도 더 섞어준다)\n"))
//        pages.add(RecipePage("5. 계란풀기","냉장고에 반죽을 최소 30분 길게는 24시간동안 숙성시켜둔다\n"))
//        pages.add(RecipePage("6. 계란풀기","마들렌팬에 실온 버터를 얇게 펴 발라준 뒤 밀가루를 체쳐서 고루 뭍여준 후 과도한 밀가루는 잘 털어준다\n" +
//                "(밀가루와 버터가 너무 두껍지 않게 하는것이 중요)\n"))
//        pages.add(RecipePage("7. 계란풀기","반죽을 한번 가볍게 섞어준 후 짤주머니에 담아 코팅해준 팬에 반죽을 짜서 넣어준다\n" +
//                "(꽉 채우지 말고 70~80% 만 짜준다 너무 팬닝량이 많으면 굽다가 넘치고 적으면 탈수 있으니 주의)\n"))
//        pages.add(RecipePage("8. 계란풀기","190도로 예열하고 온도가 다 오르면 180도로 온도를 내려 15분 구워준다\n" +
//                "(상황을 봐가면 시간과 온도는 조금씩 조절한다 윗면과 아랫면의 굽기차이가 심하면 판 위치를 올리고 내리거나 추가로 판을 닷대어준다)\n"))
//        pages.add(RecipePage("9. 계란풀기","구워진 마들렌을 팬과 분리후 살짝 기울여 식힌다\n"))
//
//        val recipe = Recipe("마들렌", "Creator1", pages)

        val recipe = intent.getSerializableExtra("RECIPE") as Recipe

        if(recipe.pages == null) {
            Log.d("BaseActivity", "recipe.pages is null")
        } else {
            Log.d("BaseActivity", "recipe.pages is not null" + recipe.pages)
        }

        val pageAdapter = ScreenSlidePagerAdapter(recipe.pages!!, supportFragmentManager)
        mBinding.layoutRecipeViewpager.adapter = pageAdapter
    }

    private inner class ScreenSlidePagerAdapter(private val recipePages: ArrayList<RecipePage>,fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = recipePages.size

        override fun getItem(position: Int): Fragment = ScreenSlidePageFragment(recipePages[position])
    }
}