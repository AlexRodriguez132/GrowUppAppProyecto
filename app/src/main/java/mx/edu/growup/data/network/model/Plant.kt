package mx.edu.growup.data.network.model

data class Plant(
    val id: Int = 0,
    val tipo: String,
    val energia: Float = 0f,
    val agua: Float = 0f,
    val estaMarchita: Boolean = false,
    val monedasGeneradas: Int = 0,
    val estaActiva: Boolean = true,
    val nivelCrecimiento: Int = 0,
    val ultimoRiego: Long = 0,
    val progresoFase: Float = 0f,
    val idUsuario : Int
)