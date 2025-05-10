@file:Suppress("KotlinConstantConditions")

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fionasiregar0032.agenda.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcaraFormScreen(navController: NavController, acaraId: Long?) {
    var acaraName by remember { mutableStateOf("") }
    var acaraDate by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val acaraTypes = listOf("Meeting", "Seminar", "Workshop")
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
                label = { Text(stringResource(R.string.nama_acara)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = acaraDate,
                onValueChange = { acaraDate = it },
                label = { Text(stringResource(R.string.tanggal_acara)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = startTime,
                onValueChange = { startTime = it },
                label = { Text(stringResource(R.string.waktu_mulai)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = endTime,
                onValueChange = { endTime = it },
                label = { Text(stringResource(R.string.waktu_selesai)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text(stringResource(R.string.lokasi)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(stringResource(R.string.deskripsi)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            Spacer(modifier = Modifier.height(16.dp))


            if (acaraTypes.isNotEmpty()) {
                Text(stringResource(R.string.jenis_kegiatan), style = MaterialTheme.typography.titleMedium)
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
            } else {

                Text(stringResource((R.string.tidak_ada_jenis_kegiatan)), style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(72.dp))
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.konfirmasi_hapus)) },
        text = { Text(stringResource(R.string.pesan_konfirmasi)) },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(stringResource(R.string.hapus))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.batal))
            }
        }
    )
}