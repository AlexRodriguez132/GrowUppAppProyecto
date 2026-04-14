package mx.edu.growup.data.network.repository

import android.content.Context
import mx.edu.growup.data.network.api.InventoryApi
import mx.edu.growup.data.network.dao.InventarioDao
import mx.edu.growup.data.network.dao.UsuarioDao
import mx.edu.growup.data.network.model.Inventory

class InventoryRepository(context: Context) : InventarioDao {
    val api = InventoryApi(context)

    /*suspend fun obtenerInventarioPorUsuario(userId: Int): List<Inventory> {
        return api.obtenerInventarioPorUsuario(userId)
    }

    suspend fun agregarItem(item: Inventory): Int {
        return api.agregarItem(item)
    }

    suspend fun actualizarCantidad(id: Int, nuevaCantidad: Int) {
        api.actualizarCantidad(id, nuevaCantidad)
    }

    suspend fun eliminarItem(id: Int) {
        api.eliminarItem(id)
    }

    suspend fun obtenerInventarioPorObjeto(userId: Int, shopItemId: Int): Inventory? {
        return api.obtenerInventarioPorObjeto(userId, shopItemId)
    }*/
    override fun getByUser(
        userId: Int,
        onSuccess: (List<Inventory>) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getByUser(userId, onSuccess, onError)
    }

    override fun getByUserAndItem(
        userId: Int,
        itemId: Int,
        onSuccess: (Inventory) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getByUserAndItem(userId, itemId, onSuccess, onError)
    }

    override fun get(
        inventoryId: Int,
        onSuccess: (Inventory) -> Unit,
        onError: (String) -> Unit
    ) {
        api.get(inventoryId, onSuccess, onError)
    }

    override fun addItem(
        item: Inventory,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        api.addItem(item, onSuccess, onError)
    }

    override fun updateQuantity(
        inventoryId: Int,
        quantity: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        api.updateQuantity(inventoryId, quantity, onSuccess, onError)
    }

    override fun delete(
        inventoryId: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        api.delete(inventoryId, onSuccess, onError)
    }
}
