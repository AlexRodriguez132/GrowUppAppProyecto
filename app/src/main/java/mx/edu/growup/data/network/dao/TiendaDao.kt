package mx.edu.growup.data.network.dao

import mx.edu.growup.data.network.model.ShopItem
import kotlinx.coroutines.flow.Flow
import mx.edu.growup.data.network.model.User


interface TiendaDao {
    fun getAll(
        onSuccess: (List<ShopItem>) -> Unit,
        onError: (String) -> Unit
    )

    fun get(
        itemId: Int,
        onSuccess: (ShopItem) -> Unit,
        onError: (String) -> Unit
    )
    fun create(
        shopItem: ShopItem,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
    fun update(
        shopItem: ShopItem,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
    fun delete(
        itemId: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    /*suspend fun obtenerTodosObjetos(): List<ShopItem>

    suspend fun obtenerObjetoPorId(id: Int): ShopItem?

    suspend fun crearObjeto(objeto: ShopItem): Int

    suspend fun actualizarObjeto(objeto: ShopItem)

    suspend fun eliminarObjeto(id: Int)

    fun observarCatalogo(): Flow<List<ShopItem>>*/
}