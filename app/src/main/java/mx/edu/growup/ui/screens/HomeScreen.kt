package mx.edu.growup.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.growup.R
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import mx.edu.growup.ui.viewmodel.PlantViewModel
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import mx.edu.growup.data.network.model.ShopItem

@Composable
fun HomeScreen(plantViewModel: PlantViewModel = viewModel()) {
    val plantaActual by plantViewModel.plantaActual.collectAsState(initial = null)
    val nivelCrecimiento by plantViewModel.nivelCrecimiento.collectAsState(initial = 0)
    val estaMarchita by plantViewModel.estaMarchita.collectAsState(initial = false)
    val nivelLuz by plantViewModel.nivelLuz.collectAsState(initial = 0f)
    val progresoBarra by plantViewModel.progresoFase.collectAsState(initial = 0f)
    val monedas by plantViewModel.monedas.collectAsState(initial = 0)

    val plantas by plantViewModel.plantasUsuario.collectAsState(initial = emptyList())
    val inventario by plantViewModel.inventarioUsuario.collectAsState(initial = emptyList())

    val userId = plantViewModel.userId.value
    LaunchedEffect(userId) {
        if (userId != null) {
            plantViewModel.cargarObjetosUsuario(userId)
            plantViewModel.cargarCatalogo()
            plantViewModel.cargarPlantas(userId)
        }
    }

    val esDeDia = nivelLuz > 2000f


    val currentIndex = remember(plantaActual, plantas) {
        plantaActual?.let { p -> plantas.indexOfFirst { it.id == p.id } } ?: -1
    }

    Box(modifier = Modifier.fillMaxSize()) {

        HomeScreenBackground(Dia = esDeDia)

        MonedaCounterSimple(
            monedas = monedas,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 20.dp, top = 35.dp)
        )

        InventoryPanel(
            inventario = inventario,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 12.dp, top = 30.dp)
                .width(160.dp)
                .height(160.dp)
        )


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {

                val showButtons = plantas.size > 1 && currentIndex >= 0
                val canPrev = showButtons && currentIndex > 0
                androidx.compose.material3.Button(
                    onClick = {
                        if (canPrev) {
                            val prevId = plantas[currentIndex - 1].id
                            plantViewModel.cambiarPlanta(prevId)
                        }
                    },
                    enabled = canPrev,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(48.dp)
                ) {
                    Text(text = "<", fontSize = 20.sp)
                }

                val imagenPlantaRes = when {
                    estaMarchita -> R.drawable.girasolmarchito
                    nivelCrecimiento == 0 -> R.drawable.girasolfaseuno
                    nivelCrecimiento == 1 -> R.drawable.girasolfasedos
                    nivelCrecimiento == 2 -> R.drawable.girasolfasetres
                    nivelCrecimiento == 3 -> R.drawable.girasolfasecuatro
                    nivelCrecimiento == 4 -> R.drawable.girasolfasecinco
                    else -> R.drawable.girasolfaseuno
                }

                Crossfade(targetState = imagenPlantaRes) { resId ->
                    Image(
                        painter = painterResource(id = resId),
                        contentDescription = "Planta",
                        modifier = Modifier
                            .sizeIn(minWidth = 150.dp, maxWidth = 300.dp)
                            .padding(horizontal = 12.dp),
                        contentScale = ContentScale.Fit
                    )
                }


                val canNext = showButtons && currentIndex < plantas.lastIndex
                androidx.compose.material3.Button(
                    onClick = {
                        if (canNext) {
                            val nextId = plantas[currentIndex + 1].id
                            plantViewModel.cambiarPlanta(nextId)
                        }
                    },
                    enabled = canNext,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(48.dp)
                ) {
                    Text(text = ">", fontSize = 20.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))


            BarraProgresoFase(
                progresoFase = progresoBarra,
                nivelActual = nivelCrecimiento,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(horizontal = 8.dp)
            )
        }
    }
}




@Composable
fun InventoryPanel(
    inventario: List<Pair<ShopItem, Int>>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Inventario",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            if (inventario.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Vacío", style = MaterialTheme.typography.bodySmall)
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(inventario) { pair ->
                        val item = pair.first
                        val qty = pair.second
                        InventoryRow(itemName = item.nombre, cantidad = qty)
                    }
                }
            }
        }
    }
}

@Composable
fun InventoryRow(itemName: String, cantidad: Int, iconSize: Dp = 28.dp) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.moneda),
            contentDescription = itemName,
            modifier = Modifier.size(iconSize)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = itemName, fontSize = 13.sp, modifier = Modifier.weight(1f))
        Text(text = "x$cantidad", fontSize = 13.sp, color = Color.DarkGray)
    }
}


@Composable
fun MonedaCounterSimple(monedas: Int, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                color = Color.White.copy(alpha = 0.95f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.moneda),
            contentDescription = "Moneda",
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = monedas.toString(),
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun HomeScreenBackground(Dia: Boolean) {
    val imagen = if (Dia) R.drawable.fondodia else R.drawable.fondonoche
    Image(
        painter = painterResource(id = imagen),
        contentDescription = "Imagen de fondo",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun BarraProgresoFase(
    progresoFase: Float,
    nivelActual: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = "Nivel $nivelActual",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
        LinearProgressIndicator(
            progress = progresoFase.coerceIn(0f, 1f),
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(8.dp)),
            color = Color(0xFF2196F3),
            trackColor = Color(0xFF00BCD4)
        )
    }
}

