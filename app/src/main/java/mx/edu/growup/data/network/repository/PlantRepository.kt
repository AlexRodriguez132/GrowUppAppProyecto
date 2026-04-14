import android.content.Context
import mx.edu.growup.data.network.api.PlantApi
import mx.edu.growup.data.network.dao.PlantaDao
import mx.edu.growup.data.network.model.Plant

class PlantRepository(context: Context) : PlantaDao {
    private val api = PlantApi(context)
    override fun getAllByUser(
        userId: Int,
        onSuccess: (List<Plant>) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getAllByUser(userId, onSuccess, onError)
    }

    override fun get(
        plantId: Int,
        onSuccess: (Plant) -> Unit,
        onError: (String) -> Unit
    ) {
        api.get(plantId, onSuccess, onError)
    }

    override fun create(
        plant: Plant,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        api.create(plant, onSuccess, onError)
    }

    override fun update(
        plant: Plant,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        api.update(plant, onSuccess, onError)
    }

    override fun delete(
        plantId: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        api.delete(plantId, onSuccess, onError)
    }
}
