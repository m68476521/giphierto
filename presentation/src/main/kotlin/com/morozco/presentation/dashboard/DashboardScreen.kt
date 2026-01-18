package com.morozco.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlin.getValue

@Composable
fun DashboardScreen() {
    Text(modifier = Modifier.background(Color.Red), text = "Dashboard")

//    private val trendingModel: TrendingViewModel by viewModels()
}