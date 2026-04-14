package mx.edu.growup.data.network.model

data class ShopItem(
    val id: Int = 0,
    val nombre: String,
    val tipo: String,
    val precio: Int,
    val descripcion: String,
    val disponible: Boolean
)