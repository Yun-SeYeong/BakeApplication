package kr.co.bakeapplication.viewmodels

sealed class LoginState {
    class init: LoginState()
    class startLogin: LoginState()
    class endLogin: LoginState()
    class onError(val throwable: Throwable): LoginState()
}