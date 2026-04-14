package mx.edu.growup.data.network.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import mx.edu.growup.data.network.VolleySingleton
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Path
import retrofit2.http.Body
import mx.edu.growup.data.network.model.Inventory
import mx.edu.growup.data.network.model.User
import org.json.JSONArray
import org.json.JSONObject

class InventoryApi(val context: Context) {
    val baseURL = "http://10.0.2.2:3000"
    fun getByUser(
        userId: Int,
        onSuccess: (List<Inventory>) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "$baseURL/inventario/$userId"
        val method = Request.Method.GET
        val listener = Response.Listener<JSONArray>{ response ->
            val inventories = mutableListOf<Inventory>()
            for(i in 0 until response.length()){
                val jsonObject = response.getJSONObject(i)
                inventories.add(
                    Inventory(
                        jsonObject.getInt("id"),
                        jsonObject.getInt("id_usuario"),
                        jsonObject.getInt("id_objeto"),
                        jsonObject.getInt("cantidad")
                    )
                )
            }
            onSuccess(inventories)
        }
        val errorListener = Response.ErrorListener{ error ->
            onError(error.message.toString())
        }
        val request = JsonArrayRequest(method, url, null, listener, errorListener)
        VolleySingleton.getInstance(context).add(request)
    }
    fun getByUserAndItem(
        userId: Int,
        itemId: Int,
        onSuccess: (Inventory) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "$baseURL/inventario/$userId/$itemId"
        val method = Request.Method.GET
        val listener = Response.Listener<JSONArray>{ response ->
            val jsonObject = response.getJSONObject(0)
            val inventory = Inventory(
                jsonObject.getInt("id"),
                jsonObject.getInt("id_usuario"),
                jsonObject.getInt("id_objeto"),
                jsonObject.getInt("cantidad")
            )
            onSuccess(inventory)
        }
        val errorListener = Response.ErrorListener{ error ->
            onError(error.message.toString())
        }
        val request = JsonArrayRequest(method, url, null, listener, errorListener)
        VolleySingleton.getInstance(context).add(request)
    }
    fun get(
        inventoryId: Int,
        onSuccess: (Inventory) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "$baseURL/inventario/$inventoryId"
        val method = Request.Method.GET
        val listener = Response.Listener<JSONArray>{ response ->
            val jsonObject = response.getJSONObject(0)
            val inventory = Inventory(
                jsonObject.getInt("id"),
                jsonObject.getInt("id_usuario"),
                jsonObject.getInt("id_objeto"),
                jsonObject.getInt("cantidad")
            )
            onSuccess(inventory)
        }
        val errorListener = Response.ErrorListener{ error ->
            onError(error.message.toString())
        }
        val request = JsonArrayRequest(method, url, null, listener, errorListener)
        VolleySingleton.getInstance(context).add(request)
    }
    fun addItem(
        item: Inventory,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        val url = "$baseURL/inventario/"
        val method = Request.Method.POST
        val body = JSONObject()
        body.put("id", item.id)
        body.put("id_usuario", item.userId)
        body.put("id_objeto", item.shopItemId)
        body.put("cantidad", item.cantidad)

        val listener = Response.Listener<JSONObject>{ response ->
            if(response.getInt("afectedRows") == 1){
                onSuccess()
            }
        }
        val errorListener = Response.ErrorListener{ error ->
            onError(error.message.toString())
        }
        val request = JsonObjectRequest(method, url, body, listener, errorListener)
        VolleySingleton.getInstance(context).add(request)
    }
    fun updateQuantity(
        inventoryId: Int,
        quantity: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        val url = "$baseURL/inventario/$inventoryId/$quantity"
        val method = Request.Method.PUT

        val listener = Response.Listener<JSONObject>{ response ->
            if(response.getInt("afectedRows") == 1){
                onSuccess()
            }
        }
        val errorListener = Response.ErrorListener{ error ->
            onError(error.message.toString())
        }
        val request = JsonObjectRequest(method, url, null, listener, errorListener)
        VolleySingleton.getInstance(context).add(request)
    }
    fun delete(
        inventoryId: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        val url = "$baseURL/inventario/$inventoryId"
        val method = Request.Method.DELETE

        val listener = Response.Listener<JSONObject>{ response ->
            if(response.getInt("afectedRows") == 1){
                onSuccess()
            }
        }
        val errorListener = Response.ErrorListener{ error ->
            onError(error.message.toString())
        }
        val request = JsonObjectRequest(method, url, null, listener, errorListener)
        VolleySingleton.getInstance(context).add(request)
    }


    /*@GET("inventory/usuario/{userId}")
    suspend fun obtenerInventarioPorUsuario(
        @Path("userId") userId: Int
    ): List<Inventory>

    @GET("inventory/{id}")
    suspend fun obtenerItemPorId(
        @Path("id") id: Int
    ): Inventory?

    @POST("inventory")
    suspend fun agregarItem(
        @Body item: Inventory
    ): Int

    @PUT("inventory/{id}/{cantidad}")
    suspend fun actualizarCantidad(
        @Path("id") id: Int,
        @Path("cantidad") cantidad: Int
    )

    @DELETE("inventory/{id}")
    suspend fun eliminarItem(
        @Path("id") id: Int
    )

    @GET("inventory/usuario/{userId}/objeto/{shopItemId}")
    suspend fun obtenerInventarioPorObjeto(
        @Path("userId") userId: Int,
        @Path("shopItemId") shopItemId: Int
    ): Inventory?*/
}
