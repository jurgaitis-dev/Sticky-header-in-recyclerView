package com.z9.stickyheaderinrecyclerview.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.z9.stickyheaderinrecyclerview.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CountriesFragment())
                .commit()
        }
    }
}