import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.growup.R
import mx.edu.growup.data.network.model.ShopItem
import mx.edu.growup.ui.viewmodel.PlantViewModel

@Composable
fun TiendaScreen(
    modifier: Modifier = Modifier,
    plantViewModel: PlantViewModel = viewModel(),
    onItemPurchased: (ShopItem) -> Unit = {}
) {
    val monedas by plantViewModel.monedas.collectAsStateWithLifecycle()

    // Lista fija de items
    val items = listOf(
        ShopItem(1, "Agua Extra", "agua", 5, "Riega instantáneamente la planta.", true),
        ShopItem(2, "Fertilizante", "fertilizante", 10, "Aumenta el progreso de fase.", true),
        ShopItem(3, "Revive", "revive", 20, "Revive una planta marchita.", true),
        ShopItem(4, "Tulipán", "planta", 12, "Planta decorativa: tulipán.", true),
        ShopItem(5, "Cactus", "planta", 15, "Planta con poca agua necesaria.", true)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA)) // Fondo bonito para la tienda
            .padding(16.dp)
    ) {
        Column(
            Modifier.fillMaxSize(), Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Tienda", modifier = Modifier.padding(start = 4.dp), fontSize = 24.sp)
            Text(text = "Monedas: $monedas", modifier = Modifier.padding(start = 4.dp, bottom = 8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(96.dp)
                            .background(Color(0xFFF1F1F1), RoundedCornerShape(12.dp))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val imagenRes = when (item.tipo.lowercase()) {
                            "agua" -> R.drawable.agua_extra
                            "fertilizante" -> R.drawable.fertilizante
                            "revive", "revivir" -> R.drawable.revive
                            "planta" -> when (item.nombre.lowercase()) {
                                "cactus" -> R.drawable.cactus
                                "tulipán", "tulipan" -> R.drawable.tulipan
                                else -> R.drawable.girasolfaseuno
                            }
                            else -> R.drawable.girasolfaseuno
                        }

                        Image(
                            painter = painterResource(id = imagenRes),
                            contentDescription = item.nombre,
                            modifier = Modifier.size(64.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = item.nombre)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = item.descripcion, fontSize = 12.sp, color = Color.DarkGray)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = "Precio: ${item.precio} 💰")
                        }


                        Button(
                            onClick = {
                                plantViewModel.comprarItem(item,
                                    onSuccess = { onItemPurchased(item) }
                                )
                            },
                            enabled = monedas >= item.precio
                        ) {
                            Text(text = if (monedas >= item.precio) "Comprar" else "Sin monedas")
                        }
                    }
                }
            }
        }
    }
}