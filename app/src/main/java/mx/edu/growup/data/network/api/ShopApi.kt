package mx.edu.growup.data.network.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import mx.edu.growup.data.network.VolleySingleton
import mx.edu.growup.data.network.model.ShopItem
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

class ShopApi(val context: Context) {
    val baseURL = "http://10.0.2.2:3000"

    fun getAll(
        onSuccess: (List<ShopItem>) -> Unit,
        onError: (String) -> Unit
    ){
        val url = "$baseURL/shop/items"
        val method = Request.Method.GET
        val listener = Response.Listener<JSONArray>{ response ->
            val items = mutableListOf<ShopItem>()
            for(i in 0 until response.length()){
                val jsonObject = response.getJSONObject(i)
                items.add(
                    ShopItem(
                        jsonObject.getInt("id"),
                        jsonObject.getString("nombre"),
                        jsonObject.getString("tipo"),
                        jsonObject.getInt("precio"),
                        jsonObject.getString("descripcion"),
                        jsonObject.getBoolean("disponible")
                    )
                )
            }
            onSuccess(items)
        }
        val errorListener = Response.ErrorListener{ error ->
            onError(error.message.toString())
        }
        val request = JsonArrayRequest(method, url, null, listener, errorListener)
        VolleySingleton.getInstance(context).add(request)
    }
    fun get(
        itemId: Int,
        onSuccess: (ShopItem) -> Unit,
        onError: (String) -> Unit
    ){
        val url = "$baseURL/shop/items/$itemId"
        val method = Request.Method.GET
        val listener = Response.Listener<JSONArray>{ response ->
            val jsonObject = response.getJSONObject(0)
            val item = ShopItem(
                        jsonObject.getInt("id"),
                        jsonObject.getString("nombre"),
                        jsonObject.getString("tipo"),
                        jsonObject.getInt("precio"),
                        jsonObject.getString("descripcion"),
                        jsonObject.getBoolean("disponible")
            )
            onSuccess(item)
        }
        val errorListener = Response.ErrorListener{ error ->
            onError(error.message.toString())
        }
        val request = JsonArrayRequest(method, url, null, listener, errorListener)
        VolleySingleton.getInstance(context).add(request)
    }
    fun create(
        shopItem: ShopItem,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        val url = "$baseURL/shop/items/"
        val method = Request.Method.POST
        val body = JSONObject()
        body.put("nombre", shopItem.nombre)
        body.put("tipo", shopItem.tipo)
        body.put("precio", shopItem.precio)
        body.put("descripcion", shopItem.descripcion)
        body.put("disponible", shopItem.disponible)

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
    fun update(
        shopItem: ShopItem,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        val url = "$baseURL/shop/items/"
        val method = Request.Method.PUT
        val body = JSONObject()
        body.put("id", shopItem.id)
        body.put("nombre", shopItem.nombre)
        body.put("tipo", shopItem.tipo)
        body.put("precio", shopItem.precio)
        body.put("descripcion", shopItem.descripcion)
        body.put("disponible", shopItem.disponible)

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
    fun delete(
        itemId: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        val url = "$baseURL/shop/items/$itemId"
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
    /*@GET("tienda/objetos")
    suspend fun obtenerTodosObjetos(): List<ShopItem>

    @GET("tienda/objetos/{id}")
    suspend fun obtenerObjetoPorId(
        @Path("id") id: Int
    ): ShopItem?

    @POST("tienda/objetos")
    suspend fun crearObjeto(
        @Body objeto: ShopItem
    ): Int

    @PUT("tienda/objetos")
    suspend fun actualizarObjeto(
        @Body objeto: ShopItem
    )

    @DELETE("tienda/objetos/{id}")
    suspend fun eliminarObjeto(
        @Path("id") id: Int
    )*/
}
