package com.example.hesabyar.ui.stats

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

data class PieChartData(val name: String, val value: Float, val color: Color)

@Composable
fun PieChart(data: List<PieChartData>, modifier: Modifier = Modifier) {
    val total = data.sumOf { it.value.toDouble() }.toFloat()
    
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            var startAngle = -90f
            data.forEach { pieData ->
                val sweepAngle = (pieData.value / total) * 360f
                drawArc(
                    color = pieData.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    size = Size(size.width, size.height)
                )
                startAngle += sweepAngle
            }
        }
    }
}
