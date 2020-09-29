package kr.co.bakeapplication.data

data class Recipe(
    val recipename: String,
    val creatorname: String,
    val pages: ArrayList<RecipePage>
)