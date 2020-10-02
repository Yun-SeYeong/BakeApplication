package kr.co.bakeapplication.viewmodels

sealed class DashboardState {
    class init: DashboardState()
    class startCheckLogin: DashboardState()
    class endCheckLogin: DashboardState()
    class startLoading: DashboardState()
    class endLoading: DashboardState()
    class onError(val throwable: Throwable): DashboardState()
}