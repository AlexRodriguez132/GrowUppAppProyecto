package mx.edu.growup.ui.viewmodel

import PlantRepository
import UserRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import mx.edu.growup.data.network.model.Plant
import mx.edu.growup.data.network.model.User
import kotlin.Float

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    val userRepository = UserRepository(application.applicationContext)
    val plantRepository = PlantRepository(application.applicationContext)
    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()
    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()
    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()
    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()
    private val _passwordVisibility = MutableStateFlow(false)
    val passwordVisibility = _passwordVisibility.asStateFlow()

    fun onUsernameChanged(newUsername : String){
        _username.value = newUsername
    }
    fun onPasswordChanged(newPassword : String){
        _password.value = newPassword
    }
    fun onConfirmPasswordChanged(newConfirmPassword : String){
        _confirmPassword.value = newConfirmPassword
    }
    fun togglePasswordVisibility(){
        _passwordVisibility.value = !_passwordVisibility.value
    }
    fun onRegister(){
        if(_password.value == "" || _confirmPassword.value == "" || _username.value == "") return
        if(_password.value != _confirmPassword.value) return
        val user = User(
            nombre = _username.value,
            password = _password.value
        )
        userRepository.create(user,
            onSuccess = { insertId ->
                val plant = Plant(
                    tipo = "girasol",
                    idUsuario = insertId,
                )
                plantRepository.create(
                    plant,
                    onSuccess = {},
                    onError = { message ->
                        _error.value = message
                    }
                )
            },
            onError = { message ->
                _error.value = message
            }
        )
    }
}