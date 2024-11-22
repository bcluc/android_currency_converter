import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverter.ui.components.CustomDialog
import com.example.currencyconverter.ui.theme.fontFamily
import com.example.currencyconverter.util.bgColor
import com.example.currencyconverter.util.mainColor
import kotlinx.coroutines.launch
import kotlin.math.log

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CurrencyConverterApp(viewModel: MainViewModel) {
    val exchangeRates by viewModel.exchangeRates.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val openDialog = remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    var amount by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf("EUR") }
    var toCurrency by remember { mutableStateOf("USD") }

    Log.d("ALL CURRENCY", exchangeRates.keys.toString())
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                viewModel.fetchExchangeRates()
            } catch (_: Exception) {
            } finally {
                isLoading = false
            }
        }
    }
    CustomDialog(
        openDialogCustom = openDialog,
        errorMessage = "Cant call the convert api",
        headerMessage = "Try to connect to your next word"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(paddingValues = PaddingValues(vertical = 10.dp)),
                title = {
                    Text(
                        "Currency Converter",
                        style = MaterialTheme.typography.h5,
                        fontFamily = fontFamily,
                        color = Color.Black
                    )
                },
                backgroundColor = Color.White,
                elevation = 0.dp
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                when {
                    isLoading -> CircularProgressIndicator()
                    errorMessage != null -> Text(text = "Error: $errorMessage")
                    else -> Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        CurrencyInputRow(
                            currencyList = exchangeRates.keys.toList()
                                .filter { it != toCurrency },
                            selectedCurrency = fromCurrency,
                            onCurrencyChange = { fromCurrency = it },
                            amount = amount,
                            label = "You send",
                            isEnable = true,
                            onAmountChange = { input ->
                                if (input.isEmpty() || input.matches(Regex("^\\d*\\.?\\d*\$"))) {
                                    amount = input
                                }
                            }
                        )
//                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Button(
                                onClick = {
                                    val tmpCurrency = fromCurrency
                                    fromCurrency = toCurrency
                                    toCurrency = tmpCurrency
                                },
                                shape = RoundedCornerShape(50),
                                colors = ButtonDefaults.buttonColors(backgroundColor = bgColor),
                                modifier = Modifier
                                    .padding(start = 20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Trade currency",
                                    tint = mainColor
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Inverted currency",
                                style = MaterialTheme.typography.h5.copy(
                                    fontSize = 14.sp
                                ),
                                fontFamily = fontFamily,
                                color = Color.Gray,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        CurrencyInputRow(
                            currencyList = exchangeRates.keys.toList()
                                .filter { it != fromCurrency },
                            selectedCurrency = toCurrency,
                            onCurrencyChange = { toCurrency = it },
                            amount = result,
                            label = "You get",
                            isEnable = false,
                            onAmountChange = { {} }
                        )
//
                        errorMessage?.let {
                            Text("Error: $it", color = MaterialTheme.colors.error)
                        }
                    }
                }
            }
        },
        bottomBar = {

            Button(
                onClick = {
                    val fromRate = exchangeRates[fromCurrency] ?: 1.0
                    val toRate = exchangeRates[toCurrency] ?: 1.0
                    result =
                        amount.toDoubleOrNull()?.let { it * toRate / fromRate }
                            .toString()
                    if (result == "null") {
                        result = "0"
                        openDialog.value = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 40.dp).height(60.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(backgroundColor = mainColor)
            ) {
                Text(
                    "Convert",
                    color = Color.White,
                    style = MaterialTheme.typography.h5.copy(
                        fontSize = 20.sp
                    ),
                    fontFamily = fontFamily,
                )
            }
        })
}


