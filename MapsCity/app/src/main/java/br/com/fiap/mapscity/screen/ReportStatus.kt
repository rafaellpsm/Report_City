package br.com.fiap.mapscity.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
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
import androidx.navigation.NavHostController
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

@Composable
fun ReportStatus(navController: NavHostController) {
    val anomalies = listOf(
        Anomaly("Endereço 1", "01/09/2024", "Tipo 1", "Completo", Color.Green),
        Anomaly("Endereço 2", "02/09/2024", "Tipo 2", "Sendo feito", Color.Yellow),
        Anomaly("Endereço 3", "03/09/2024", "Tipo 3", "Sendo feito", Color.Yellow)
    )

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Registros de Anomalias",
                onBackClick = { navController.navigate("mapScreen") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            anomalies.forEach { anomaly ->
                AnomalyCard(anomaly = anomaly)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Botão para acessar a página de registro
            Button(
                onClick = { navController.navigate("mapScreen") },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Ir para Registro")
            }
        }
    }
}

@Composable
fun CustomTopAppBar(
    title: String,
    onBackClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF64B5F6))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        onBackClick?.let {
            IconButton(onClick = it) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )

    }
}

@Composable
fun AnomalyCard(anomaly: Anomaly) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFECEFF1))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(anomaly.statusColor, shape = CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "Endereço: ${anomaly.location}",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                )
                Text(
                    text = "Data: ${anomaly.date}",
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                )
                Text(
                    text = "Tipo: ${anomaly.type}",
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                )
                Text(
                    text = "Status: ${anomaly.status}",
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, color = anomaly.statusColor)
                )
            }
        }
    }
}

data class Anomaly(
    val location: String,
    val date: String,
    val type: String,
    val status: String,
    val statusColor: Color
)
