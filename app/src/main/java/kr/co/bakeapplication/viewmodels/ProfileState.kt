package kr.co.bakeapplication.viewmodels

import kr.co.bakeapplication.data.Profile

sealed class ProfileState {
    class init: ProfileState()
    class startCheckProfile: ProfileState()
    class endCheckProfile(val profile: Profile?): ProfileState()
    class startLoading: ProfileState()
    class endLoading: ProfileState()
    class onError(val throwable: Throwable): ProfileState()
}