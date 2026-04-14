package mx.edu.growup.ui.navigation

import BottomNavBar
import TiendaScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import mx.edu.growup.data.network.model.ShopItem
import mx.edu.growup.ui.screens.AjustesScreen
import mx.edu.growup.ui.screens.HomeScreen
import mx.edu.growup.ui.viewmodel.PlantViewModel


@Composable
fun HomeContainer(navController: NavController, userId: Int) {
    // Si usas el AndroidViewModel que ya tienes:
    val plantViewModel: PlantViewModel = viewModel()

    LaunchedEffect(userId) {
        plantViewModel.setUsuarioId(userId)
        plantViewModel.cargarCatalogo()
        plantViewModel.cargarObjetosUsuario(userId)
        plantViewModel.cargarPlantas(userId)
    }

    // Si HomeScreen espera plantViewModel como parámetro:
    HomeScreen(plantViewModel = plantViewModel)
}

