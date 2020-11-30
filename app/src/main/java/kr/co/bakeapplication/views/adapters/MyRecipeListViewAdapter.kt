package kr.co.bakeapplication.views.adapters

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kr.co.bakeapplication.R
import kr.co.bakeapplication.data.Profile
import kr.co.bakeapplication.data.Recipe
import kr.co.bakeapplication.databinding.RecipeListItemBinding
import kr.co.bakeapplication.viewmodels.DashboardViewModel
import kr.co.bakeapplication.viewmodels.MyRecipeViewModel
import kr.co.bakeapplication.views.DashboardActivity
import kr.co.bakeapplication.views.RecipeActivity

class MyRecipeListViewAdapter(private val viewModel: MyRecipeViewModel): RecyclerView.Adapter<RecipeBindingViewHolder<RecipeListItemBinding>>() {
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
        Glide.with(mContext).load(recipes[position].thumbNailUri).into(holder.mBinding.imageviewRecipeThumbnail)
        holder.mBinding.cardviewRecipeItem.setOnClickListener {
            val intent = Intent(mContext, RecipeActivity::class.java)
            intent.putExtra("RECIPE", recipes[position])
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            mContext.startActivity(intent)
        }
        viewModel.findProfile(recipes[position].creatoruid, object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = snapshot.getValue<Map<String, Any>>()
                Log.d("BaseActivity", "profile list: " + list)
                if (list != null) {
                    var p = Profile("", "", "")
                    for ((k, v) in list) {
                        p.toObj(v as Map<String, Any?>)
                    }
                    if (p.firebasetoken != "") {
                        holder.mBinding.textviewRecipeCreatorid.text = p.profilename
                        holder.mBinding.imageviewRecipeCreatorimg.background = ShapeDrawable(OvalShape())
                        holder.mBinding.imageviewRecipeCreatorimg.clipToOutline = true
                        Glide.with(mContext).load(p.profileImg).into(holder.mBinding.imageviewRecipeCreatorimg)
                    }
                }
            }
        })
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