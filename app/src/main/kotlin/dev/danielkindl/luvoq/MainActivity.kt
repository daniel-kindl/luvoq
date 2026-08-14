package dev.danielkindl.luvoq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import dev.danielkindl.luvoq.ui.theme.LuvoqTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LuvoqTheme {
                LuvoqApp()
            }
        }
    }
}
