package com.fionasiregar0032.agenda.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fionasiregar0032.agenda.model.Acara
import com.fionasiregar0032.agenda.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecycleBinScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val deletedAcara by viewModel.deletedAcara.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recycle Bin") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (deletedAcara.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Recycle Bin kosong.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(deletedAcara) { acara ->
                    RecycleBinItem(acara = acara, onRestore = {
                        viewModel.restoreAcara(acara)
                    })
                }
            }
        }
    }
}

@Composable
fun RecycleBinItem(acara: Acara, onRestore: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = acara.acaraName, style = MaterialTheme.typography.titleLarge)
            Text("Tanggal: ${acara.acaraDate}")
            Text("Waktu: ${acara.startTime} - ${acara.endTime}")
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onRestore,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Restore")
            }
        }
    }
}