@file:Suppress("KotlinConstantConditions")

package com.fionasiregar0032.agenda.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fionasiregar0032.agenda.R
import com.fionasiregar0032.agenda.viewmodel.AcaraFormViewModel
import com.fionasiregar0032.agenda.model.Acara
import com.fionasiregar0032.agenda.model.acaraTypes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcaraFormScreen(
    navController: NavController,
    acaraId: Long?,
    viewModel: AcaraFormViewModel
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var acaraName by remember { mutableStateOf("") }
    var acaraDate by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedActivityType by remember { mutableStateOf(acaraTypes.firstOrNull() ?: "") }

    val currentAcaraFromDb by viewModel.currentAcara.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var acaraBeingEdited by remember { mutableStateOf<Acara?>(null) }

    LaunchedEffect(acaraId) {
        if (acaraId != null) {
            viewModel.loadAcara(acaraId)
        } else {
            viewModel.prepareNewAcara()
            acaraName = ""
            acaraDate = ""
            startTime = ""
            endTime = ""
            location = ""
            description = ""
            selectedActivityType = acaraTypes.firstOrNull() ?: ""
            acaraBeingEdited = null
        }
    }

    LaunchedEffect(currentAcaraFromDb) {
        currentAcaraFromDb?.let { acara ->
            acaraBeingEdited = acara
            acaraName = acara.acaraName
            acaraDate = acara.acaraDate
            startTime = acara.startTime
            endTime = acara.endTime
            location = acara.location
            description = acara.description
            selectedActivityType = acara.activityType
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (acaraId == null) "Tambah Acara Baru" else "Edit Acara") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    if (acaraId != null && acaraBeingEdited != null) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Hapus Acara",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFFFF69B4),
                    titleContentColor = Color.Black
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (acaraName.isBlank() || acaraDate.isBlank() || startTime.isBlank() ||
                    endTime.isBlank() || location.isBlank() || description.isBlank() || selectedActivityType.isBlank()
                ) {
                    Toast.makeText(context, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                    return@FloatingActionButton
                }

                val acaraToSave = Acara(
                    id = acaraId ?: 0L,
                    acaraName = acaraName.trim(),
                    acaraDate = acaraDate.trim(),
                    startTime = startTime.trim(),
                    endTime = endTime.trim(),
                    location = location.trim(),
                    description = description.trim(),
                    activityType = selectedActivityType
                )

                viewModel.saveAcara(acaraToSave) { success ->
                    coroutineScope.launch {
                        if (success) {
                            Toast.makeText(context, "Acara berhasil disimpan", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        } else {
                            Toast.makeText(context, "Gagal menyimpan acara", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }) {
                Icon(Icons.Filled.Check, contentDescription = "Simpan Acara")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = Color(0xFFFFF5E1)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    value = acaraName,
                    onValueChange = { acaraName = it },
                    label = { Text(stringResource(R.string.nama_acara)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = acaraDate,
                        onValueChange = { acaraDate = it },
                        label = { Text(stringResource(R.string.tanggal_acara)) },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = startTime,
                        onValueChange = { startTime = it },
                        label = { Text(stringResource(R.string.waktu_mulai)) },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = endTime,
                        onValueChange = { endTime = it },
                        label = { Text(stringResource(R.string.waktu_selesai)) },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text(stringResource(R.string.lokasi)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.deskripsi)) },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 5
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(stringResource(R.string.jenis_kegiatan), style = MaterialTheme.typography.titleMedium)

                Column {
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
                }

                Spacer(modifier = Modifier.height(72.dp))
            }
        }
    }

    if (showDeleteDialog && acaraBeingEdited != null) {
        DeleteConfirmationDialog(
            acaraToDelete = acaraBeingEdited!!,
            onConfirm = {
                viewModel.deleteAcara(acaraBeingEdited!!) { success ->
                    coroutineScope.launch {
                        if (success) {
                            Toast.makeText(
                                context,
                                "Acara '${acaraBeingEdited!!.acaraName}' dihapus",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.popBackStack()
                        } else {
                            Toast.makeText(context, "Gagal menghapus acara", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )
    }
}

@Composable
fun DeleteConfirmationDialog(
    acaraToDelete: Acara,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.konfirmasi_hapus)) },
        text = { Text(stringResource(R.string.pesan_konfirmasi, acaraToDelete.acaraName)) },
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
