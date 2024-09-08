package com.example.treasurehunt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.treasurehunt.ui.theme.TreasureHuntTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TreasureHuntTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("clue1") {
            ClueScreen(
                navController,
                "passa diante do sol e não faz sombra? Opções a) cabelo b) agua c)vento",
                "c",
                "clue2"
            )
        }
        composable("clue2") {
            ClueScreen(
                navController,
                "é cheia de furinhos, mas consegue reter água? Opções a) esponja b) peneira c) pele",
                "a",
                "clue3"
            )
        }
        composable("clue3") {
            ClueScreen(
                navController,
                "Qual é o queijo que mais sente dor? Opções a) muçarela b) parmesão c) ralado",
                "c",
                "treasure"
            )
        }
        composable("treasure") { TreasureScreen(navController) }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFC0CB)),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { navController.navigate("clue1") }) {
            Text("Iniciar caça ao tesouro")
        }
    }
}

@Composable
fun ClueScreen(navController: NavController, clue: String, correctAnswer: String, nextDestination: String) {
    var selectedAnswer by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFC0CB)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(text = clue, style = MaterialTheme.typography.h6, modifier = Modifier.padding(16.dp))
            Spacer(modifier = Modifier.height(16.dp))
            listOf("a", "b", "c").forEach { option ->
                Button(onClick = { selectedAnswer = option }) {
                    Text(option)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Button(onClick = { if (selectedAnswer == correctAnswer) navController.navigate(nextDestination) }) {
                    Text("Próxima Pista")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = { navController.popBackStack() }) {
                    Text("Voltar")
                }
            }
        }
    }
}

@Composable
fun TreasureScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFC0CB)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(text = "Parabéns! Você encontrou o tesouro!", style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack("home", false) }) {
                Text("Voltar à Tela Inicial")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TreasureHuntTheme {
        HomeScreen(rememberNavController())
    }
}
