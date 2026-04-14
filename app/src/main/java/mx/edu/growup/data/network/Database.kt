package mx.edu.growup.data.network

import mx.edu.growup.data.network.api.InventoryApi
import mx.edu.growup.data.network.api.PlantApi
import mx.edu.growup.data.network.api.ShopApi
import mx.edu.growup.data.network.api.UserApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Database {

    private const val BASE_URL = "http://10.0.2.2:3000/"


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val inventoryApi: InventoryApi by lazy {
        retrofit.create(InventoryApi::class.java)
    }

    val plantApi: PlantApi by lazy {
        retrofit.create(PlantApi::class.java)
    }

    val shopApi: ShopApi by lazy {
        retrofit.create(ShopApi::class.java)
    }

    val userApi: UserApi by lazy {
        retrofit.create(UserApi::class.java)
    }
}