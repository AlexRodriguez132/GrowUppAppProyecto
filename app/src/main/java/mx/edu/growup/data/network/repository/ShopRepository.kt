import android.content.Context
import mx.edu.growup.data.network.api.ShopApi
import mx.edu.growup.data.network.dao.TiendaDao
import mx.edu.growup.data.network.model.ShopItem

class ShopRepository(context: Context) : TiendaDao {
    private val api = ShopApi(context)
    override fun getAll(
        onSuccess: (List<ShopItem>) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getAll(onSuccess, onError)
    }

    override fun get(
        itemId: Int,
        onSuccess: (ShopItem) -> Unit,
        onError: (String) -> Unit
    ) {
        api.get(itemId, onSuccess, onError)
    }

    override fun create(
        shopItem: ShopItem,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        api.create(shopItem, onSuccess, onError)
    }

    override fun update(
        shopItem: ShopItem,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        api.update(shopItem, onSuccess, onError)
    }

    override fun delete(
        itemId: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        api.delete(itemId, onSuccess, onError)
    }


    /*suspend fun obtenerTodosObjetos(): List<ShopItem> {
        return api.obtenerTodosObjetos()
    }

    suspend fun obtenerObjetoPorId(id: Int): ShopItem? {
        return api.obtenerObjetoPorId(id)
    }

    suspend fun crearObjeto(objeto: ShopItem): Int {
        return api.crearObjeto(objeto)
    }

    suspend fun actualizarObjeto(objeto: ShopItem) {
        api.actualizarObjeto(objeto)
    }

    suspend fun eliminarObjeto(id: Int) {
        api.eliminarObjeto(id)
    }

    suspend fun obtenerItemPorId(id: Int): ShopItem? {
        return api.obtenerObjetoPorId(id)
    }*/
}
