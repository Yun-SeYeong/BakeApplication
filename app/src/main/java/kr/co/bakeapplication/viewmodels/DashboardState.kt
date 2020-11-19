package kr.co.bakeapplication.viewmodels

import kr.co.bakeapplication.data.Profile

sealed class DashboardState {
    class init: DashboardState()
    class startCheckLogin: DashboardState()
    class endCheckLogin: DashboardState()
    class startSyncProfile: DashboardState()
    class endSyncProfile(val profile: Profile?): DashboardState()
    class startLoading: DashboardState()
    class endLoading: DashboardState()
    class onError(val throwable: Throwable): DashboardState()
}