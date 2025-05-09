package com.fionasiregar0032.agenda.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fionasiregar0032.agenda.model.acaraTypes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventFormScreen(navController: NavController, acaraId: Long?) {
    var acaraName by remember { mutableStateOf("") }
    var acaraDate by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedActivityType by remember { mutableStateOf(acaraTypes.firstOrNull() ?: "") }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (acaraId == null) "Tambah Acara Baru" else "Edit Acara") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.Check, if (acaraId == null) "Simpan Acara" else "Update Acara")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .then(Modifier.padding(16.dp))
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = acaraName,
                onValueChange = { acaraName = it },
                label = { Text("Nama Acara") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = acaraDate,
                onValueChange = { acaraDate = it },
                label = { Text("Tanggal Acara (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = startTime,
                onValueChange = { startTime = it },
                label = { Text("Waktu Mulai (HH:MM)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = endTime,
                onValueChange = { endTime = it },
                label = { Text("Waktu Selesai (HH:MM)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Lokasi") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Deskripsi Kegiatan") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text("Jenis Kegiatan:", style = MaterialTheme.typography.titleMedium)
            acaraTypes.forEach { type ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (type == selectedActivityType),
                            onClick = { selectedActivityType = type }
                        )
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (type == selectedActivityType),
                        onClick = { selectedActivityType = type }
                    )
                    Text(
                        text = type,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(72.dp)) // Spacer untuk FAB
        }
    }
}