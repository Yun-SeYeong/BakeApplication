package kr.co.bakeapplication.viewmodels

sealed class MyRecipeState {
    class init: MyRecipeState()
    class startLoading: MyRecipeState()
    class endLoading: MyRecipeState()
    class onError(val throwable: Throwable): MyRecipeState()
}