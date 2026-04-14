package mx.edu.growup.data.network.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import mx.edu.growup.data.network.VolleySingleton
import mx.edu.growup.data.network.model.Plant
import mx.edu.growup.data.network.model.User
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

class PlantApi(val context: Context) {
    val baseURL = "http://10.0.2.2:3000"

    fun getAllByUser(
        userId: Int,
        onSuccess: (List<Plant>) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "$baseURL/planta/$userId"
        val method = Request.Method.GET

        val listener = Response.Listener<JSONObject> { response ->
            val plantList = mutableListOf<Plant>()

            if (response.has("id")) {
                plantList.add(parsePlant(response))
            }

            onSuccess(plantList)
        }

        val errorListener = Response.ErrorListener { error ->
            onError(error.message.toString())
        }

        val request = JsonObjectRequest(method, url, null, listener, errorListener)
        VolleySingleton.getInstance(context).add(request)
    }

    fun get(
        plantId : Int,
        onSuccess: (Plant) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "$baseURL/planta/$plantId"
        val method = Request.Method.GET

        val listener = Response.Listener<JSONObject> { response ->
            val plant = parsePlant(response)
            onSuccess(plant)
        }

        val errorListener = Response.ErrorListener { error ->
            onError(error.message.toString())
        }

        val request = JsonObjectRequest(method, url, null, listener, errorListener)
        VolleySingleton.getInstance(context).add(request)
    }

    fun create(
        plant: Plant,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "$baseURL/planta/"
        val body = JSONObject()

        body.put("tipo", plant.tipo)
        body.put("energia", plant.energia)
        body.put("id_usuario", plant.idUsuario)

        val listener = Response.Listener<JSONObject> { response ->
            if (response.optInt("affectedRows") == 1) {
                onSuccess()
            }
        }

        val errorListener = Response.ErrorListener { error ->
            onError(error.message.toString())
        }

        val request = JsonObjectRequest(Request.Method.POST, url, body, listener, errorListener)
        VolleySingleton.getInstance(context).add(request)
    }

    fun update(
        plant: Plant,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "$baseURL/planta/"
        val body = JSONObject()

        body.put("tipo", plant.tipo)
        body.put("energia", plant.energia)
        body.put("marchita", plant.estaMarchita)
        body.put("monedas_generadas", plant.monedasGeneradas)
        body.put("activa", plant.estaActiva)
        body.put("nivel", plant.nivelCrecimiento)
        body.put("ultimo_riego", plant.ultimoRiego)
        body.put("progreso_fase", plant.progresoFase)
        body.put("id_usuario", plant.idUsuario)

        val listener = Response.Listener<JSONObject> { response ->
            if (response.optInt("affectedRows") == 1) {
                onSuccess()
            }
        }

        val errorListener = Response.ErrorListener { error ->
            onError(error.message.toString())
        }

        val request = JsonObjectRequest(Request.Method.PUT, url, body, listener, errorListener)
        VolleySingleton.getInstance(context).add(request)
    }

    fun delete(
        plantId: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "$baseURL/planta/$plantId"

        val listener = Response.Listener<JSONObject> { response ->
            if (response.optInt("affectedRows") == 1) {
                onSuccess()
            }
        }

        val errorListener = Response.ErrorListener { error ->
            onError(error.message.toString())
        }

        val request = JsonObjectRequest(Request.Method.DELETE, url, null, listener, errorListener)
        VolleySingleton.getInstance(context).add(request)
    }

    private fun parsePlant(obj: JSONObject): Plant {
        return Plant(
            obj.getInt("id"),
            obj.getString("tipo"),
            obj.getDouble("energia").toFloat(),
            obj.getDouble("agua").toFloat(),
            obj.getBoolean("marchita"),
            obj.getInt("monedas_generadas"),
            obj.getBoolean("activa"),
            obj.getInt("nivel"),
            obj.getLong("ultimo_riego"),
            obj.getDouble("progreso_fase").toFloat(),
            obj.getInt("id_usuario")
        )
    }
}
