package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.component.InputField
import com.example.tipcalculator.ui.theme.CustomAppTheme
import com.example.tipcalculator.ui.theme.widgets.RoundIconButton
import com.example.tipcalculator.util.calculateTip
import com.example.tipcalculator.util.calculateTotalPerPerson

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppScreen {
                TotalAmountSection()
                UserInputSection()
            }
        }
    }
}


@Composable
fun AppScreen(content: @Composable () -> Unit) {
    CustomAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            content()
        }
    }
}

@Composable
fun TotalAmountSection(totalAmount: Double = 0.0) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .height(100.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))),
        color = Color.LightGray
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val total = "%.2f".format(totalAmount)
            Text(
                text = "Total bill per person",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "₹$total", style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold
            )

        }

    }
}

@Composable
fun UserInputSection(
    modifier: Modifier = Modifier,
    onValChange: (String) -> Unit = { }
) {
    // creating state values for observing percentage change.
    val totalBillState = remember {
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    val sliderState = remember {
        mutableFloatStateOf(0f)
    }

    val splitByState = remember {
        mutableIntStateOf(1)
    }

    val tipAmountState = remember {
        mutableDoubleStateOf(0.0)
    }

    val totalState = remember {
        mutableDoubleStateOf(0.0)
    }
    val tipPercent = (sliderState.floatValue * 100).toInt()
    Surface(
        modifier = modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Column {
            TotalAmountSection(totalAmount = totalState.doubleValue)
            InputField(
                valueState = totalBillState,
                labelID = stringResource(id = R.string.enter_amt),
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    onValChange(totalBillState.value.trim())

                    keyboardController?.hide()
                }
            )
            if (validState) {
                Row(
                    modifier = Modifier.padding(3.dp),
                    verticalAlignment = CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.split_title),
                        modifier = Modifier.align(
                            alignment = CenterVertically
                        )
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Row(
                        modifier = Modifier.padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        RoundIconButton(
                            onClick = {
                                splitByState.intValue =
                                    if (splitByState.intValue > 1) splitByState.intValue - 1 else 1
                                totalState.doubleValue =
                                    calculateTotalPerPerson(
                                        totalBill = totalBillState.value.toDouble(),
                                        tipPercent = tipPercent,
                                        splitBy = splitByState.intValue
                                    )
                            },
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            backgroundColor = Color.Transparent,
                            modifier = Modifier
                                .background(Color.Transparent)
                                .padding(top = 10.dp)
                        )
                        Text(
                            text = "${splitByState.intValue}", modifier = Modifier
                                .align(CenterVertically)
                                .padding(start = 9.dp, end = 9.dp)
                        )
                        RoundIconButton(
                            onClick = {
                                splitByState.intValue += 1
                                totalState.doubleValue =
                                    calculateTotalPerPerson(
                                        totalBill = totalBillState.value.toDouble(),
                                        tipPercent = tipPercent,
                                        splitBy = splitByState.intValue
                                    )
                            },
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                            backgroundColor = Color.Transparent,
                            modifier = Modifier
                                .background(Color.Transparent)
                                .padding(top = 10.dp)
                        )
                    }
                }

                Row(modifier = Modifier.padding(horizontal = 3.dp, vertical = 12.dp)) {
                    Text(
                        text = stringResource(id = R.string.tip_title),
                        modifier = Modifier.align(alignment = CenterVertically),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        text = "₹${tipAmountState.doubleValue}",
                        modifier = Modifier.align(alignment = CenterVertically),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = " ($tipPercent%)",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                //Tip Percentage
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(14.dp))

                    Slider(
                        value = sliderState.floatValue,
                        onValueChange = { newVal ->
                            sliderState.floatValue = newVal
                            tipAmountState.doubleValue =
                                calculateTip(
                                    totalBill = totalBillState.value.toDouble(),
                                    tipPercent = tipPercent
                                )
                            totalState.doubleValue =
                                calculateTotalPerPerson(
                                    totalBill = totalBillState.value.toDouble(),
                                    tipPercent = tipPercent,
                                    splitBy = splitByState.intValue
                                )
                        },
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        steps = 100
                        )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = if (tipPercent <= 0) stringResource(id = R.string.empty)
                        else if (tipPercent <= 10) stringResource(id = R.string.thanks)
                        else stringResource(id = R.string.gratitude),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Magenta
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    FilledIconButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        onClick = {}
                    ) {
                        Text(
                            text = stringResource(id = R.string.continue_to_payment),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }

            }
        }
    }
}
