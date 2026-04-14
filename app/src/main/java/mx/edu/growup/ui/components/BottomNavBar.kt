import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavBar(
    selectedScreen: String,
    onScreenSelected: (String) -> Unit
) {
    NavigationBar(
        containerColor = Color(0xFF1A1A1A)
    ) {
        NavigationBarItem(
            selected = selectedScreen == "inicio",
            onClick = { onScreenSelected("inicio") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") }
        )

        NavigationBarItem(
            selected = selectedScreen == "tienda",
            onClick = { onScreenSelected("tienda") },
            icon = { Icon(Icons.Default.Store, contentDescription = "Tienda") }
        )

        NavigationBarItem(
            selected = selectedScreen == "ajustes",
            onClick = { onScreenSelected("ajustes") },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Ajustes") }
        )
    }
}
