package com.mayouf.chucknorristest.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mayouf.chucknorristest.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChuckNorrisActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragmentContainer,
                ChuckNorrisFragment.newInstance(),
                ChuckNorrisFragment.FRAGMENT_NAME
            )
            .commitAllowingStateLoss()
    }
}