package com.example.tipcalculator.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelID: String,
    enabled: Boolean,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Number,
    onAction: KeyboardActions = KeyboardActions.Default,
    isSingleLine: Boolean
){
    OutlinedTextField(value = valueState.value, onValueChange = {valueState.value= it},
    label = { Text(text = labelID)},
    leadingIcon = { Icon(imageVector = Icons.Rounded.AccountBox, contentDescription = "MoneyIcon")},
    singleLine = isSingleLine,
    textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground),
    keyboardActions = onAction,
    keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
    enabled = enabled,
    modifier = Modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
        .fillMaxWidth())
}


