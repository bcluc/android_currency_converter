import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverter.ui.theme.fontFamily
import com.example.currencyconverter.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyInputRow(
    isEnable: Boolean,
    label: String,
    modifier: Modifier = Modifier,
    currencyList: List<String>,
    selectedCurrency: String,
    onCurrencyChange: (String) -> Unit,
    amount: String,
    onAmountChange: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .padding(vertical = 16.dp)
            .border(
                width = 2.dp,
                color = if (isFocused) mainColor else unFocusColor,
                shape = RoundedCornerShape(12.dp)
            )
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                    fontFamily = fontFamily,
                    color = Color.Gray
                )
                TextField(
                    value = amount,
                    onValueChange = onAmountChange,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    enabled = isEnable,
                    label = {
                        when {
                            !isEnable && amount.isEmpty()-> Text("Convert below")
                            !isFocused && amount.isEmpty() -> Text("Enter here")
                            else -> null
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Black,
                        containerColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .onFocusChanged { isFocused = it.isFocused }
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
            ) {
                Button(
                    onClick = { isDropdownExpanded = !isDropdownExpanded },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = selectedCurrency, color = Color.Black)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Arrow",
                            tint = Color.Gray
                        )
                    }
                }

                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false },
                    modifier = Modifier
                        .heightIn(max = 200.dp)
                        .background(color = Color.White)
                ) {
                    currencyList.forEach { currency ->
                        DropdownMenuItem(
                            onClick = {
                                onCurrencyChange(currency)
                                isDropdownExpanded = false
                            },
                            text = { Text(text = currency) }
                        )
                    }
                }
            }
        }
    }
}
