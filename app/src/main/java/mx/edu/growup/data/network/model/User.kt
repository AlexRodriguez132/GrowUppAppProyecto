package mx.edu.growup.data.network.model

data class User(
    val id: Int = 0,
    val nombre: String,
    val correo: String? = null,
    val password: String,
    val monedasTotales: Int = 0,
)