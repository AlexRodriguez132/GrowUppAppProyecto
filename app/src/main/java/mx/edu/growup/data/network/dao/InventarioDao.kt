package mx.edu.growup.data.network.dao

import kotlinx.coroutines.flow.Flow
import mx.edu.growup.data.network.model.Inventory

interface InventarioDao {
    fun getByUser(
        userId: Int,
        onSuccess: (List<Inventory>) -> Unit,
        onError: (String) -> Unit
    )
    fun getByUserAndItem(
        userId: Int,
        itemId: Int,
        onSuccess: (Inventory) -> Unit,
        onError: (String) -> Unit
    )
    fun get(
        inventoryId: Int,
        onSuccess: (Inventory) -> Unit,
        onError: (String) -> Unit
    )
    fun addItem(
        item: Inventory,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
    fun updateQuantity(
        inventoryId: Int,
        quantity: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
    fun delete(
        inventoryId: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
    /*suspend fun obtenerInventarioPorUsuario(userId: Int): List<Inventory>

    suspend fun obtenerItemInventarioPorId(id: Int): Inventory?

    suspend fun agregarItem(inventory: Inventory): Int

    suspend fun actualizarCantidad(itemId: Int, nuevaCantidad: Int)

    suspend fun eliminarItem(itemId: Int)

    fun observarInventario(userId: Int): Flow<List<Inventory>>*/
}