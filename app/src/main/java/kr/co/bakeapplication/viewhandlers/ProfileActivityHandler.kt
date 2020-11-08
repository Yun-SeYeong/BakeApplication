package kr.co.bakeapplication.viewhandlers

import kr.co.bakeapplication.data.Profile

interface ProfileActivityHandler {
    fun startCheckProfile()
    fun endCheckProfile(profile: Profile?)
}