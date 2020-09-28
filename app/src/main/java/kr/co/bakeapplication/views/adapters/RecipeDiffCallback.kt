package kr.co.bakeapplication.views.adapters

import androidx.recyclerview.widget.DiffUtil
import kr.co.bakeapplication.data.Recipe

class RecipesDiffCallback(
    private val oldItems: ArrayList<Recipe>,
    private val newItems: ArrayList<Recipe>
): DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = oldItems[oldItemPosition] == newItems[newItemPosition]

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = oldItems[oldItemPosition] == newItems[newItemPosition]
}