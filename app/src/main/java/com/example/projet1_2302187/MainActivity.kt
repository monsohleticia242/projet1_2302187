package com.example.projet1_2302187

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projet1_2302187.ui.theme.Projet1_2302187Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Projet1_2302187Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    LanceurDes()
                }
            }
        }
    }
}

@Composable
fun LanceurDes() {
    var numberOfDice by remember { mutableStateOf(1) } //Stock le nombre de Dés
    var selectedDiceType by remember { mutableStateOf("d12") } //Type de Dés actuel
    var sortOption by remember { mutableStateOf("Aucun tri") }

    val staticResults = listOf(3, 5, 2, 6, 1) // Liste de resultat fictive

    //Organisation verticalement des composants *
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp), //marge
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Titre de l'application
        Text(
            text = " Lanceur de Dés ",
            fontSize = 24.sp, //Taille de police
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp) //Espace en dessous du titre
        )

        // ========== PREMIÈRE CARD : Configuration ==========
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)  // Espace entre les deux cards
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 3. Configuration du nombre de dés
                Text(
                    text = "Nombre de dés",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Dés sélectionnés: $numberOfDice",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Spacer(modifier = Modifier.height(24.dp)) //Espace les textes

                // 4. Configuration du nombre de faces par dé
                Text(
                    text = "Type de dé",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                val maListe = listOf("d4", "d6", "d8", "d10", "d12", "d20") //Contient 6 types de Dés

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    maListe.chunked(3).forEach { chunk ->  //Chunked divise la liste en groupe de 3.
                        Column {
                            chunk.forEach { Liste ->
                                Row(
                                    modifier = Modifier
                                        .selectable( //Rendre chaque row  cliquable
                                            selected = selectedDiceType == Liste,
                                            onClick = {
                                                selectedDiceType = Liste
                                                Log.i("DiceLauncher", "Type de dé sélectionné: $Liste") // Message de débug
                                            }
                                        )
                                        .padding(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = selectedDiceType == Liste,
                                        onClick = null
                                    )
                                    Text(
                                        text = Liste,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp)) //Espacement

                // 5. Option de tri des résultats
                Text(
                    text = "Tri des résultats",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                val sortOptions = listOf("Aucun tri", "Tri croissant", "Tri décroissant") //Liste des 3 options demandé.

                Column(modifier = Modifier.selectableGroup()) {
                    sortOptions.forEach { option -> //forEach créer un RadioButton pour chaque option.
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = sortOption == option,
                                    onClick = {
                                        sortOption = option
                                        Log.i("DiceLauncher", "Option de tri sélectionnée: $option") // Message
                                    }
                                )
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = sortOption == option,
                                onClick = null
                            )
                            Text(
                                text = option,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp)) //Espacement

                // 2. Bouton principal "Lancer les dés"
                Button(
                    onClick = {
                        Log.d("LanceurDes", "Bouton 'Lancer les dés' cliqué!") //Ajoute une action et la configuration actuelle
                        Log.d("LanceurDes", "Configuration: $numberOfDice dés de type $selectedDiceType, tri: $sortOption")
                    },
                ) {
                    Text(
                        text = " LANCER LES DÉS ",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }


        // ========== DEUXIÈME CARD : Résultats ==========
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 6. Zone d'affichage des résultats
                Text(
                    text = " RÉSULTATS ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Affichage des valeurs des dés
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Colonne pour les valeurs
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Valeurs obtenues:",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = staticResults.take(numberOfDice).joinToString(" "), //Prend seulement le nombre de Dés sélectionné.
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    // Colonne pour la somme
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Somme totale:",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = "${staticResults.take(numberOfDice).sum()}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LanceurDesPreview() {
    Projet1_2302187Theme {
        LanceurDes()
    }
}