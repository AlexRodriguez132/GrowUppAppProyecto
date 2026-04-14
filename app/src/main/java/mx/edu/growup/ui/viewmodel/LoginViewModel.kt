package mx.edu.growup.ui.viewmodel

import UserRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import mx.edu.growup.data.network.model.User

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val userRepository = UserRepository(application.applicationContext)
    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()
    private val _username = MutableStateFlow<String>("")
    val username = _username.asStateFlow()
    private val _password = MutableStateFlow<String>("")
    val password = _password.asStateFlow()
    private val _passwordVisibility = MutableStateFlow(false)
    val passwordVisibility = _passwordVisibility.asStateFlow()
    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginState = _loginState.asStateFlow()

    fun onUsernameChanged(newUsername : String){
        _username.value = newUsername
    }
    fun onPasswordChanged(newPassword : String){
        _password.value = newPassword
    }
    fun togglePasswordVisibility(){
        _passwordVisibility.value = !_passwordVisibility.value
    }
    fun onLogin() {
        if(_password.value == "" || _username.value == "") return
        _loginState.value = LoginUiState.Loading
        userRepository.getByCredentials(
            _username.value, _password.value,
            onSuccess = { user ->
                _loginState.value = LoginUiState.Success(user)
            },
            onError = { message ->
                _loginState.value = LoginUiState.Error(message);
                _error.value = message
            }
        )
    }
    fun resetLoginState() {
        _loginState.value = LoginUiState.Idle
    }
}
sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val user: User) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}