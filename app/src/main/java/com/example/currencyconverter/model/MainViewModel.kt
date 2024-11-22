import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.service.RetrofitClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _exchangeRates = MutableStateFlow<Map<String, Double>>(emptyMap())
    val exchangeRates: StateFlow<Map<String, Double>> = _exchangeRates

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchExchangeRates() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getExchangeRates("175f706551487f20cc75385ed5b6dc0a")
                if (response.isSuccessful && response.body()?.success == true) {
                    _exchangeRates.value = response.body()?.rates ?: emptyMap()
                } else {
                    _errorMessage.value = "Failed to fetch exchange rates"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Network error: ${e.message}"
            }
        }
    }
}
