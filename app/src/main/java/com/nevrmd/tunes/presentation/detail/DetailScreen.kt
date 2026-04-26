package com.nevrmd.tunes.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.nevrmd.tunes.presentation.detail.components.InfoCard
import com.nevrmd.tunes.presentation.detail.components.MediaControls
import com.nevrmd.tunes.presentation.navigation.Screen

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    args: Screen.Detail,
    viewModel: DetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    LaunchedEffect(args.id) {
        viewModel.loadItem(args.id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.stopAndClear()
                        onBackClick()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        state.mediaItem?.let { item ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 20.dp)
            ) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(24.dp))
                        .shadow(8.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = item.title,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = item.artist,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                item.collectionName?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                MediaControls(
                    viewModel = viewModel,
                    isLoading = state.isLoadingAudio,
                    isPlaying = state.isPlaying,
                    progress = state.progress
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Information",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    maxItemsInEachRow = 2
                ) {
                    val itemModifier = Modifier.weight(1f).minimumInteractiveComponentSize()

                    item.primaryGenreName?.let {
                        InfoCard(label = "Genre", value = it, modifier = itemModifier)
                    }

                    if (item.trackPrice != null && item.trackPrice > 0) {
                        InfoCard(
                            label = "Price",
                            value = "${item.trackPrice} ${item.currency}",
                            modifier = itemModifier
                        )
                    }

                    if (item.trackTimeMillis != null && item.trackTimeMillis > 0) {
                        val minutes = item.trackTimeMillis / 1000 / 60
                        val seconds = (item.trackTimeMillis / 1000) % 60
                        InfoCard(
                            label = "Duration",
                            value = "${minutes}m ${seconds}s",
                            modifier = itemModifier
                        )
                    }

                    item.releaseDate?.let { date ->
                        if (date.length >= 4) {
                            InfoCard(label = "Released", value = date.take(4), modifier = itemModifier)
                        }
                    }

                    item.country?.let {
                        InfoCard(label = "Region", value = it, modifier = itemModifier)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}