package com.nevrmd.tunes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nevrmd.tunes.ui.theme.TunesTheme
import com.nevrmd.tunes.presentation.search.SearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TunesTheme {
                SearchScreen()
            }
        }
    }
}