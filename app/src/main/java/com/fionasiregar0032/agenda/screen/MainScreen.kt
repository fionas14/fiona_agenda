package com.fionasiregar0032.agenda.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fionasiregar0032.agenda.model.Acara
import com.fionasiregar0032.agenda.ui.theme.AgendaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = viewModel(),
    onNavigateToForm: (Long?) -> Unit ){
    val acara by mainViewModel.acara.collectAsState()
    Scaffold (
        containerColor = Color(0xFFFFF5E1),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Agenda")
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFFFF69B4),
                    titleContentColor = Color.Black,
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onNavigateToForm(null)
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Tambah Acara")
            }

}
    ) { paddingValues ->
        if (acara.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Belum ada acara.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                items(acara) { item ->
                    AcaraListItem(acara = item, onClick = {  })
                    Divider()
                }
            }
        }
    }
}

@Composable
fun AcaraListItem(acara: Acara, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp)
    ) {
        Text(text = acara.acaraName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Tanggal: ${acara.acaraDate} (${acara.startTime} - ${acara.endTime})")
        Text(text = "Lokasi: ${acara.location}")
        Text(text = "Jenis: ${acara.activityType}")
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AgendaTheme {

    }
}
