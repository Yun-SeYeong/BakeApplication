package kr.co.bakeapplication.views.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.bakeapplication.R
import kr.co.bakeapplication.data.Recipe
import kr.co.bakeapplication.databinding.RecipeListItemBinding
import kr.co.bakeapplication.views.RecipeActivity

class RecipeListViewAdapter: RecyclerView.Adapter<RecipeBindingViewHolder<RecipeListItemBinding>>() {
    private val recipes = ArrayList<Recipe>()
    private lateinit var mContext: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeBindingViewHolder<RecipeListItemBinding> {
        mContext = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_list_item, parent, false)
        return RecipeBindingViewHolder(view)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(
        holder: RecipeBindingViewHolder<RecipeListItemBinding>,
        position: Int
    ) {
        holder.mBinding.recipe = recipes[position]
        holder.mBinding.layoutRecipeItem.setOnClickListener {
            val intent = Intent(mContext, RecipeActivity::class.java)
            intent.putExtra("RECIPE", recipes[position])
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            mContext.startActivity(intent)
        }
    }

    fun updateRecipes(recipes: ArrayList<Recipe>) {
        Log.d("BaseActivity", "updateRecipes")
        val callback = RecipesDiffCallback(this.recipes, recipes)
        val result = DiffUtil.calculateDiff(callback)

        this.recipes.clear()
        this.recipes.addAll(recipes)
        result.dispatchUpdatesTo(this)
    }
}