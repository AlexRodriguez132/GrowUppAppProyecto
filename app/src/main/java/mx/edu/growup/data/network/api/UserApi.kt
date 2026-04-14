package mx.edu.growup.data.network.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import mx.edu.growup.data.network.VolleySingleton
import mx.edu.growup.data.network.model.User
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

class UserApi(val context: Context) {
    val baseURL = "http://10.0.2.2:3000"

    fun getAll(
        onSuccess: (List<User>) -> Unit,
        onError: (String) -> Unit
    ){
        val url = "$baseURL/usuario"
        val method = Request.Method.GET
        val listener = Response.Listener<JSONArray>{ response ->
            val userList = mutableListOf<User>()
            for(i in 0 until response.length()){
                val jsonObject = response.getJSONObject(i)
                userList.add(
                    User(
                        jsonObject.getInt("id"),
                        jsonObject.getString("nombre"),
                        jsonObject.getString("email"),
                        jsonObject.getString("pass"),
                        jsonObject.getInt("monedas_totales"),
                    )
                )
            }
            onSuccess(userList)
        }
        val errorListener = Response.ErrorListener{ error ->
            onError(error.message.toString())
        }
        val request = JsonArrayRequest(method, url, null, listener, errorListener)
        VolleySingleton.getInstance(context).add(request)
    }
    fun get(
        userId : Int,
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit){
        val url = "$baseURL/usuario/$userId"
        val method = Request.Method.GET
        val listener = Response.Listener<JSONArray>{ response ->
            val jsonObject = response.getJSONObject(0)
            val user = User(
                jsonObject.getInt("id"),
                jsonObject.getString("nombre"),
                jsonObject.getString("email"),
                jsonObject.getString("pass"),
                jsonObject.getInt("monedas_totales"),
            )
            onSuccess(user)
        }
        val errorListener = Response.ErrorListener{ error ->
            onError(error.message.toString())
        }
        val request = JsonArrayRequest(method, url, null, listener, errorListener)
        VolleySingleton.getInstance(context).add(request)
    }
    fun getByCredentials(
        username : String,
        password : String,
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit){
        val url = "$baseURL/usuario/$username/$password"
        val method = Request.Method.GET
        val listener = Response.Listener<JSONArray>{ response ->
            val jsonObject = response.getJSONObject(0)
            val user = User(
                jsonObject.getInt("id"),
                jsonObject.getString("nombre"),
                jsonObject.getString("email"),
                jsonObject.getString("pass"),
                jsonObject.getInt("monedas_totales"),
            )
            onSuccess(user)
        }
        val errorListener = Response.ErrorListener{ error ->
            onError(error.message.toString())
        }
        val request = JsonArrayRequest(method, url, null, listener, errorListener)
        VolleySingleton.getInstance(context).add(request)
    }
    fun getByEmail(
        email : String,
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit){
        val url = "$baseURL/usuario/$email"
        val method = Request.Method.GET
        val listener = Response.Listener<JSONArray>{ response ->
            val jsonObject = response.getJSONObject(0)
            val user = User(
                jsonObject.getInt("id"),
                jsonObject.getString("nombre"),
                jsonObject.getString("email"),
                jsonObject.getString("pass"),
                jsonObject.getInt("monedas_totales"),
            )
            onSuccess(user)
        }
        val errorListener = Response.ErrorListener{ error ->
            onError(error.message.toString())
        }
        val request = JsonArrayRequest(method, url, null, listener, errorListener)
        VolleySingleton.getInstance(context).add(request)
    }
    fun create(
        user: User,
        onSuccess: (Int) -> Unit,
        onError: (String) -> Unit
    ){
        val url = "$baseURL/usuario/"
        val method = Request.Method.POST
        val body = JSONObject()
        body.put("nombre", user.nombre)
        body.put("email", user.correo)
        body.put("pass", user.password)

        val listener = Response.Listener<JSONObject>{ response ->
            if(response.optInt("affectedRows") == 1){
                onSuccess(response.getInt("insertId"))
            }
        }
        val errorListener = Response.ErrorListener{ error ->
            onError(error.message.toString())
        }
        val request = JsonObjectRequest(method, url, body, listener, errorListener)
        VolleySingleton.getInstance(context).add(request)
    }
    fun update(
        user: User,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        val url = "$baseURL/usuario/"
        val method = Request.Method.PUT
        val body = JSONObject()
        body.put("id", user.id)
        body.put("nombre", user.nombre)
        body.put("email", user.correo)
        body.put("pass", user.password)

        val listener = Response.Listener<JSONObject>{ response ->
            if(response.optInt("affectedRows") == 1){
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
        userId: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        val url = "$baseURL/usuario/$userId"
        val method = Request.Method.DELETE

        val listener = Response.Listener<JSONObject>{ response ->
            if(response.optInt("affectedRows") == 1){
                onSuccess()
            }
        }
        val errorListener = Response.ErrorListener{ error ->
            onError(error.message.toString())
        }
        val request = JsonObjectRequest(method, url, null, listener, errorListener)
        VolleySingleton.getInstance(context).add(request)
    }


    /*@GET("usuarios/{id}")
    suspend fun obtenerUsuarioPorId(
        @Path("id") id: Int
    ): User?

    @GET("usuarios/correo/{correo}")
    suspend fun obtenerUsuarioPorCorreo(
        @Path("correo") correo: String
    ): User?

    @POST("usuarios")
    suspend fun crearUsuario(
        @Body usuario: User
    ): Int

    @PUT("usuarios")
    suspend fun actualizarUsuario(
        @Body usuario: User
    )

    @DELETE("usuarios/{id}")
    suspend fun eliminarUsuario(
        @Path("id") id: Int
    )

    @GET("usuarios")
    suspend fun obtenerTodosUsuarios(): List<User>*/
}
