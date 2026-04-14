package mx.edu.growup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.growup.navigation.AppNavigation
import mx.edu.growup.ui.navigation.HomeContainer
import mx.edu.growup.ui.screens.HomeScreen
import mx.edu.growup.ui.screens.LoginScreen
import mx.edu.growup.ui.screens.RegisterScreen
import mx.edu.growup.ui.theme.GrowUpTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            AppNavigation()
        }
    }
}