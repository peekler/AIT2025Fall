package hu.bme.ait.genailocalmodel.ui.screen


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.ait.genailocalmodel.ui.ai.LlmInferenceHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val inferenceHelper = LlmInferenceHelper(application)

    private val _uiState = MutableStateFlow("Model Loading...")
    val uiState = _uiState.asStateFlow()

    private val _isGenerating = MutableStateFlow(false)
    val isGenerating = _isGenerating.asStateFlow()

    fun initializeModel() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                inferenceHelper.initModel()
                _uiState.value = "Model Ready. Type something!"
            } catch (e: Exception) {
                _uiState.value = "Error: ${e.message}"
            }
        }
    }

    fun sendPrompt(prompt: String) {
        if (prompt.isBlank()) return

        _isGenerating.value = true
        _uiState.value = "" // Clear previous text

        viewModelScope.launch(Dispatchers.IO) {
            var fullResponse = ""
            inferenceHelper.generateResponse(prompt).collect { partial ->
                fullResponse += partial
                _uiState.value = fullResponse
            }
            _isGenerating.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        inferenceHelper.close()
    }
}