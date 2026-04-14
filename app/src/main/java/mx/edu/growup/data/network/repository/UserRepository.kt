import android.content.Context
import mx.edu.growup.data.network.api.UserApi
import mx.edu.growup.data.network.dao.UsuarioDao
import mx.edu.growup.data.network.model.User


class UserRepository(context: Context) : UsuarioDao {
    private val api = UserApi(context)
    override fun getAll(
        onSuccess: (List<User>) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getAll(onSuccess, onError)
    }

    override fun get(
        id: Int,
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit
    ) {
        api.get(id, onSuccess, onError)
    }

    override fun getByEmail(
        email: String,
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getByEmail(email, onSuccess, onError)
    }

    override fun getByCredentials(
        username : String,
        password : String,
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit
    ){
        api.getByCredentials(username, password, onSuccess, onError)
    }

    override fun create(
        user: User,
        onSuccess: (Int) -> Unit,
        onError: (String) -> Unit
    ) {
        api.create(user, onSuccess, onError)
    }

    override fun update(
        user: User,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        api.update(user, onSuccess, onError)
    }

    override fun delete(id: Int, onSuccess: () -> Unit ,onError: (String) -> Unit) {
        api.delete(id, onSuccess,onError)
    }


}