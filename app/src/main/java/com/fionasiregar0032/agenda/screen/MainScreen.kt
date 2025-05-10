package com.fionasiregar0032.agenda.screen

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fionasiregar0032.agenda.R
import com.fionasiregar0032.agenda.model.Acara
import com.fionasiregar0032.agenda.ui.theme.AgendaTheme
import com.fionasiregar0032.agenda.util.SettingsDataStore
import com.fionasiregar0032.agenda.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    onNavigateToForm: (Long?) -> Unit,
    onNavigateToRecycleBin: () -> Unit
) {
    val context = LocalContext.current
    val acaraList by mainViewModel.activeAcara.collectAsState(initial = emptyList())
    val settingsDataStore = remember { SettingsDataStore(context) }
    val layoutMode by settingsDataStore.layoutModeFlow.collectAsState(initial = true)
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color(0xFFFFF5E1),
        topBar = {
            TopAppBar(
                title = { Text("Agenda") },
                actions = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            settingsDataStore.saveLayoutMode(!layoutMode)
                        }
                    }) {
                        Icon(
                            painter = painterResource(
                                id = if (layoutMode) R.drawable.baseline_grid_view_24 else R.drawable.baseline_view_list_24
                            ),
                            contentDescription = if (layoutMode) "Grid View" else "List View"
                        )
                    }
                    IconButton(onClick = onNavigateToRecycleBin) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_delete_24),
                            contentDescription = "Recycle Bin"
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFFFF69B4),
                    titleContentColor = Color.Black,
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToForm(null) }) {
                Icon(Icons.Filled.Add, contentDescription = "Tambah Acara")
            }
        }
    ) { paddingValues ->
        if (acaraList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Belum ada acara.", color = Color.Black)
            }
        } else {
            if (layoutMode) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(all = 8.dp)
                ) {
                    items(acaraList, key = { it.id }) { acaraItem ->
                        AcaraListItem(
                            acara = acaraItem,
                            onClick = { onNavigateToForm(acaraItem.id) },
                            onDelete = { deletedItem ->
                                coroutineScope.launch {
                                    mainViewModel.softDeleteAcara(deletedItem)
                                    val result = snackbarHostState.showSnackbar(
                                        message = "Acara dihapus",
                                        actionLabel = "Undo"
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        mainViewModel.restoreAcara(deletedItem)
                                    }
                                }
                            }
                        )
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(150.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(all = 8.dp)
                ) {
                    items(acaraList, key = { it.id }) { acaraItem ->
                        AcaraGridItem(
                            acara = acaraItem,
                            onClick = { onNavigateToForm(acaraItem.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun AcaraListItem(acara: Acara, onClick: () -> Unit, onDelete: (Acara) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = acara.acaraName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            IconButton(onClick = { onDelete(acara) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
        InfoRow("Tanggal:", "${acara.acaraDate} (${acara.startTime} - ${acara.endTime})")
        InfoRow("Lokasi:", acara.location)
        InfoRow("Jenis:", acara.activityType)
        if (acara.description.isNotBlank()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = acara.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun AcaraGridItem(acara: Acara, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(acara.acaraName, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            InfoRow("Tanggal:", "${acara.acaraDate} (${acara.startTime} - ${acara.endTime})")
            InfoRow("Lokasi:", acara.location)
            InfoRow("Jenis:", acara.activityType)
            if (acara.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = acara.description,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row {
        Text(
            text = "$label ",
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(text = value, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreview() {
    AgendaTheme {
    }
}