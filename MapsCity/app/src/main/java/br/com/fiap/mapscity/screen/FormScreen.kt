package br.com.fiap.mapscity.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FormScreen(navController: NavHostController) {
    var location by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var evidencias by remember { mutableStateOf("") }

    // Estado do Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Scaffold para o Snackbar
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFECEFF1), RoundedCornerShape(8.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "FORMULÁRIO DE CONTATO",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                )

                Spacer(modifier = Modifier.height(24.dp))

                CustomOutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = "Endereço do Local:"
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomOutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = "Data:"
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomOutlinedTextField(
                    value = type,
                    onValueChange = { type = it },
                    label = "Tipo da Anomalia:"
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomOutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = "Descrição:",
                    height = 70.dp
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomOutlinedTextField(
                    value = evidencias,
                    onValueChange = { evidencias = it },
                    label = "Evidencias:"
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Chamado aberto com sucesso!",
                                duration = SnackbarDuration.Short
                            )
                            delay(1000)
                            navController.navigate("mapScreen")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF64B5F6))
                ) {
                    Text("Enviar", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    height: Dp = 40.dp
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = TextStyle(fontSize = 16.sp, color = Color.DarkGray),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 1.dp),
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            cursorBrush = SolidColor(Color.Black)
        )
    }
}
