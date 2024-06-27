package com.example.tipcalculator.ui.theme.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


val IconButtonSizeModifier = Modifier.size(40.dp)
@Composable
fun RoundIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    imageVector: ImageVector,
    tint: Color = Color.Black.copy(alpha = 0.8f),
    elevation: CardElevation = CardDefaults.cardElevation(),
    backgroundColor: Color = MaterialTheme.colorScheme.background
    ){
    Card(modifier = modifier
        .padding(4.dp)
        .clickable { onClick.invoke() }
        .then(IconButtonSizeModifier),
        shape = CircleShape,
        elevation = elevation
    ){
            Icon(imageVector = imageVector,
                contentDescription = "Plus or Minus Icon",
                tint = tint)
    }
}