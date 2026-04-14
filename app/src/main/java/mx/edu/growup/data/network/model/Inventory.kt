package mx.edu.growup.data.network.model

data class Inventory(
    val id: Int = 0,
    val userId: Int,
    val shopItemId: Int,
    val cantidad: Int
)