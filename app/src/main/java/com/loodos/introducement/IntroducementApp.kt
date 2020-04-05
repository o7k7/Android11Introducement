package com.loodos.introducement

import android.app.Application
import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompat

/**
 * Created by orhunkupeli on 5.04.2020
 */

class IntroducementApp: Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}