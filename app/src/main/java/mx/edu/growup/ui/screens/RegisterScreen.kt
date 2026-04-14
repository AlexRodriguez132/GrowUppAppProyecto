package mx.edu.growup.ui.screens

import android.R.id.icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import mx.edu.growup.R
import mx.edu.growup.ui.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    navController: NavController
) {
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val passwordVisibility by viewModel.passwordVisibility.collectAsState()
    val error by viewModel.error.collectAsState()

    Column {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Fondo de pantalla
            Image(
                painter = painterResource(id = R.drawable.fondoregistro),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Card central
            Card(
                modifier = Modifier
                    .padding(50.dp)
                    .align(Alignment.Center),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logogrowup),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                    Text(
                        text = "GrowUp",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF8C4B20)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Registro",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFFE2B15D)
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = username,
                        onValueChange = { newText ->
                            viewModel.onUsernameChanged(newText)
                        },
                        label = { Text("Usuario") },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xFFE2B15D),
                            unfocusedIndicatorColor = Color(0xFFE2B15D),
                            focusedLabelColor = Color(0xFFE2B15D),
                            unfocusedLabelColor = Color.DarkGray,
                            cursorColor = Color(0xFFE2B15D),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { newText ->
                            viewModel.onPasswordChanged(newText)
                        },
                        label = { Text("Contraseña") },
                        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            val image = if (passwordVisibility)
                                Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                                Icon(imageVector = image, contentDescription = null, tint = Color(0xFFE2B15D))
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xFFE2B15D),
                            unfocusedIndicatorColor = Color(0xFFE2B15D),
                            focusedLabelColor = Color(0xFFE2B15D),
                            unfocusedLabelColor = Color.DarkGray,
                            cursorColor = Color(0xFFE2B15D),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { newText ->
                            viewModel.onConfirmPasswordChanged(newText)
                        },
                        label = { Text("Confirmar contraseña") },
                        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            val image = if (passwordVisibility)
                                Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                                Icon(imageVector = image, contentDescription = null, tint = Color(0xFFE2B15D))
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xFFE2B15D),
                            unfocusedIndicatorColor = Color(0xFFE2B15D),
                            focusedLabelColor = Color(0xFFE2B15D),
                            unfocusedLabelColor = Color.DarkGray,
                            cursorColor = Color(0xFFE2B15D),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { navController.navigate("login")},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF5F5F5),
                                contentColor = Color(0xFFE2B15D)
                            )
                        ) {
                            Text("Ir a login")
                        }

                        Button(
                            onClick = {
                                viewModel.onRegister()
                                if(error.isEmpty()){
                                    navController.navigate("login")
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE2B15D),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Registrarse")
                        }
                    }
                }
            }
        }
    }
}