package mx.edu.growup.data.network.dao

import kotlinx.coroutines.flow.Flow
import mx.edu.growup.data.network.model.Plant
import mx.edu.growup.data.network.model.User

interface PlantaDao {

    fun getAllByUser(
        userId : Int,
        onSuccess: (List<Plant>) -> Unit,
        onError: (String) -> Unit
    )

    fun get(
        plantId: Int,
        onSuccess: (Plant) -> Unit,
        onError: (String) -> Unit
    )
    fun create(
        plant: Plant,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
    fun update(
        plant: Plant,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
    fun delete(
        plantId: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    /*suspend fun obtenerPlantaPorUsuario(userId: Int): Plant?

    suspend fun obtenerPlantasPorUsuario(userId: Int): List<Plant>

    suspend fun crearPlanta(planta: Plant): Int

    suspend fun actualizarPlanta(planta: Plant)

    suspend fun reiniciarPlanta(plantaId: Int)

    fun observarPlantaPorUsuario(userId: Int): Flow<Plant?>*/
}