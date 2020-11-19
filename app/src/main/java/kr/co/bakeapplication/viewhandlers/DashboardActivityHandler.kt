package kr.co.bakeapplication.viewhandlers

import kr.co.bakeapplication.data.Profile

interface DashboardActivityHandler: BaseActivityHandler{
    fun startSyncProfile()
    fun endSyncProfile(profile: Profile?)
}