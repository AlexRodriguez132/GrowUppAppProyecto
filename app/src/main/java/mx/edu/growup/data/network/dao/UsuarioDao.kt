package mx.edu.growup.data.network.dao

import kotlinx.coroutines.flow.Flow
import mx.edu.growup.data.network.model.User

interface UsuarioDao {

    fun getAll(
        onSuccess: (List<User>) -> Unit,
        onError: (String) -> Unit
    )

    fun get(
        id: Int,
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit
    )
    fun getByEmail(
        email : String,
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit
    )
    fun getByCredentials(
        username : String,
        password : String,
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit
    )
    fun create(
        user: User,
        onSuccess: (Int) -> Unit,
        onError: (String) -> Unit
    )
    fun update(
        user: User,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
    fun delete(
        id: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

}