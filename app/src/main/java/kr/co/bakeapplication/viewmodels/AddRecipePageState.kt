package kr.co.bakeapplication.viewmodels

sealed class AddRecipePageState {
    class init: AddRecipePageState()
    class startLoading: AddRecipePageState()
    class endLoading: AddRecipePageState()
    class refreshList: AddRecipePageState()
    class onError(val throwable: Throwable): AddRecipePageState()
}