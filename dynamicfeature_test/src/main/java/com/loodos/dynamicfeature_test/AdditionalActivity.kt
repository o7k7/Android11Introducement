package com.loodos.dynamicfeature_test

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitcompat.SplitCompat

/**
 * Created by orhunkupeli on 5.04.2020
 */
class AdditionalActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additional)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}