package mx.edu.growup.ui.viewmodel

import PlantRepository
import ShopRepository
import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import mx.edu.growup.data.network.model.Inventory
import mx.edu.growup.data.network.model.Plant
import mx.edu.growup.data.network.model.ShopItem
import mx.edu.growup.data.network.repository.InventoryRepository


class PlantViewModel(application: Application) : AndroidViewModel(application) {

    val plantRepository = PlantRepository(application.applicationContext)
    val inventoryRepository = InventoryRepository(application.applicationContext)
    val shopItemRepository = ShopRepository(application.applicationContext)

    val error = MutableStateFlow("")

    val plantasUsuario = MutableStateFlow<List<Plant>>(emptyList())
    val plantaActual = MutableStateFlow<Plant?>(null)
    val catalogo = MutableStateFlow<List<ShopItem>>(emptyList())
    val userId = mutableStateOf<Int?>(null)

    val energia = MutableStateFlow(50f)
    val agua = MutableStateFlow(0f)
    val nivelLuz = MutableStateFlow(0f)
    val nivelCrecimiento = MutableStateFlow(0)

    val estaMarchita = MutableStateFlow(false)
    val monedas = MutableStateFlow(0)
    val progresoFase = MutableStateFlow(0f)

    var ultimoRiego: Long = System.currentTimeMillis()

    fun setUsuarioId(id: Int) {
        userId.value = id
    }


    fun cargarCatalogo() {
        shopItemRepository.getAll(
            onSuccess = { items ->
                catalogo.value = items
            },
            onError = { message ->
                error.value = message
            }
        )
    }

    fun cambiarPlanta(idPlanta: Int) {
        plantRepository.get(
            idPlanta,
            onSuccess = { plant ->
                plantaActual.value = plant
                energia.value = plant.energia
                agua.value = plant.agua
                nivelLuz.value = 0f
                nivelCrecimiento.value = plant.nivelCrecimiento
                monedas.value = plant.monedasGeneradas
                ultimoRiego = plant.ultimoRiego
                estaMarchita.value = plant.estaMarchita
                progresoFase.value = plant.progresoFase
            },
            onError = { message ->
                error.value = message
            }
        )
    }


    fun cargarPlantas(usuarioId: Int) {
        plantRepository.getAllByUser(
            usuarioId,
            onSuccess = { list ->
                if (list.isNullOrEmpty()) {
                    plantasUsuario.value = emptyList()
                    plantaActual.value = null
                    return@getAllByUser
                }

                plantasUsuario.value = list
                val primera = list.first()

                plantaActual.value = primera
                energia.value = primera.energia
                agua.value = primera.agua
                nivelLuz.value = 0f
                nivelCrecimiento.value = primera.nivelCrecimiento
                monedas.value = primera.monedasGeneradas
                progresoFase.value = primera.progresoFase
                ultimoRiego = primera.ultimoRiego
                estaMarchita.value = primera.estaMarchita
            },
            onError = { message ->
                error.value = message
            }
        )
    }


    fun regar() {
        val p = plantaActual.value ?: return
        if (estaMarchita.value) return

        agua.value += 1f
        energia.value += 15f
        monedas.value += 1
        aumentarProgreso(0.2f)
        ultimoRiego = System.currentTimeMillis()
        calcularNivelCrecimiento()
        guardarCambios()
    }

    fun actualizarLuz(lux: Float) {
        val p = plantaActual.value ?: return
        if (estaMarchita.value) return

        nivelLuz.value = lux
        energia.value += (lux / 1000f) * 5f
        aumentarProgreso(0.1f)
        calcularNivelCrecimiento()
        guardarCambios()
    }

    fun revisarPlanta() {
        val p = plantaActual.value ?: return
        if (estaMarchita.value) return

        val horas = (System.currentTimeMillis() - ultimoRiego) / (1000L * 60L * 60L)
        if (horas >= 48) {
            estaMarchita.value = true
            guardarCambios()
        }
    }

    fun calcularNivelCrecimiento() {
        val resultado = energia.value + (agua.value * 10f)
        nivelCrecimiento.value = when {
            resultado >= 100f -> 4
            resultado >= 75f -> 3
            resultado >= 50f -> 2
            resultado >= 30f -> 1
            else -> 0
        }
    }

    fun reiniciarPlanta() {
        val p = plantaActual.value ?: return

        energia.value = 50f
        agua.value = 0f
        nivelLuz.value = 0f
        nivelCrecimiento.value = 0
        estaMarchita.value = false
        monedas.value = 0
        progresoFase.value = 0f
        ultimoRiego = System.currentTimeMillis()
    }


    fun aumentarProgreso(valor: Float) {
        progresoFase.value += valor
        if (progresoFase.value >= 1f) {
            progresoFase.value = 0f
            if (nivelCrecimiento.value < 4) nivelCrecimiento.value++
        }
    }


    fun usarObjeto(shopItemId: Int) {
        val uid = userId.value ?: return
        val planta = plantaActual.value ?: return

        // Paso 1: obtener inventario del usuario
        inventoryRepository.getByUserAndItem(
            uid,
            shopItemId,
            onSuccess = { inv ->

                if (inv == null || inv.cantidad <= 0) return@getByUserAndItem

                // Paso 2: obtener descripción del objeto
                shopItemRepository.get(
                    shopItemId,
                    onSuccess = { item ->
                        procesarObjeto(item, planta, inv)
                    },
                    onError = { message -> error.value = message }
                )
            },
            onError = { message ->
                error.value = message
            }
        )
    }

    private fun procesarObjeto(item: ShopItem, planta: Plant, inv: Inventory) {

        when (item.tipo.lowercase()) {

            "agua" -> {
                val updated = planta.copy(
                    agua = (planta.agua + 2f).coerceAtMost(100f),
                    energia = (planta.energia + 10f).coerceAtMost(100f)
                )
                actualizarPlantaYRestarObjeto(updated, inv)
            }

            "fertilizante" -> {
                var progreso = planta.progresoFase + 0.3f
                var nivel = planta.nivelCrecimiento

                if (progreso >= 1f) {
                    progreso -= 1f
                    nivel = (nivel + 1).coerceAtMost(4)
                }

                val updated = planta.copy(progresoFase = progreso, nivelCrecimiento = nivel)
                actualizarPlantaYRestarObjeto(updated, inv)
            }

            "revivir", "revive" -> {
                if (planta.estaMarchita) {
                    val updated = planta.copy(
                        estaMarchita = false,
                        energia = 30f,
                        agua = 1f
                    )
                    actualizarPlantaYRestarObjeto(updated, inv)
                }
            }

            else -> {}
        }
    }

    private fun actualizarPlantaYRestarObjeto(planta: Plant, inv: Inventory) {
        plantRepository.update(
            planta,
            onSuccess = {
                plantaActual.value = planta

                inventoryRepository.updateQuantity(
                    inv.id,
                    (inv.cantidad - 1).coerceAtLeast(0),
                    onSuccess = {},
                    onError = { message -> error.value = message }
                )

                userId.value?.let { cargarObjetosUsuario(it) }
            },
            onError = { message -> error.value = message }
        )
    }


    val inventarioUsuario = MutableStateFlow<List<Pair<ShopItem, Int>>>(emptyList())

    fun cargarObjetosUsuario(usuarioId: Int) {

        inventoryRepository.getByUser(
            usuarioId,
            onSuccess = { inventario ->

                if (inventario.isEmpty()) {
                    inventarioUsuario.value = emptyList()
                    return@getByUser
                }

                val resultado = mutableListOf<Pair<ShopItem, Int>>()
                var procesados = 0

                for (inv in inventario) {
                    shopItemRepository.get(
                        inv.shopItemId,
                        onSuccess = { item ->
                            if (item.tipo.lowercase() != "planta") {
                                resultado.add(item to inv.cantidad)
                            }
                            procesados++
                            if (procesados == inventario.size) {
                                inventarioUsuario.value = resultado
                            }
                        },
                        onError = {
                            procesados++
                            if (procesados == inventario.size) {
                                inventarioUsuario.value = resultado
                            }
                        }
                    )
                }
            },
            onError = {
                inventarioUsuario.value = emptyList()
            }
        )
    }


    fun comprarItem(item: ShopItem, onSuccess: () -> Unit = {}, onError: (String) -> Unit = {}) {
        val uid = userId.value ?: return onError("Usuario no establecido")

        if (monedas.value < item.precio) {
            onError("Monedas insuficientes")
            return
        }

        monedas.value -= item.precio


        if (item.tipo.lowercase() == "planta") {

            val nueva = Plant(
                id = 0,
                tipo = item.nombre,
                energia = 50f,
                agua = 0f,
                estaMarchita = false,
                monedasGeneradas = 0,
                estaActiva = true,
                nivelCrecimiento = 0,
                ultimoRiego = System.currentTimeMillis(),
                progresoFase = 0f,
                idUsuario = uid
            )

            plantRepository.create(
                nueva,
                onSuccess = {
                    cargarPlantas(uid)
                    onSuccess()
                },
                onError = { message -> onError(message) }
            )

            return
        }


        inventoryRepository.getByUserAndItem(
            uid,
            item.id,
            onSuccess = { inv ->

                if (inv != null) {
                    inventoryRepository.updateQuantity(
                        inv.id,
                        inv.cantidad + 1,
                        onSuccess = { onSuccess() },
                        onError = { message -> onError(message) }
                    )
                } else {
                    // No existe -> insertar
                    val nuevo = Inventory(
                        id = 0,
                        userId = uid,
                        shopItemId = item.id,
                        cantidad = 1
                    )
                    inventoryRepository.addItem(
                        nuevo,
                        onSuccess = { onSuccess() },
                        onError = { message -> onError(message) }
                    )
                }

            },
            onError = { message -> onError(message) }
        )
    }

    private fun guardarCambios() {
        val p = plantaActual.value ?: return

        val actualizado = p.copy(
            energia = energia.value,
            agua = agua.value,
            nivelCrecimiento = nivelCrecimiento.value,
            monedasGeneradas = monedas.value,
            ultimoRiego = ultimoRiego,
            estaMarchita = estaMarchita.value,
            progresoFase = progresoFase.value
        )

        plantaActual.value = actualizado

        plantRepository.update(
            actualizado,
            onSuccess = {},
            onError = { message -> error.value = message }
        )
    }
}
