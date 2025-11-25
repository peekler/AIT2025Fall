package hu.bme.ait.genailocalmodel.ui.ai


import android.content.Context
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import hu.bme.ait.genailocalmodel.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class LlmInferenceHelper(private val context: Context) {

    private var llmInference: LlmInference? = null
    private val modelFileName = "gemma_model.bin"

    suspend fun initModel() {
        // Switch to IO thread for heavy file operations
        withContext(Dispatchers.IO) {
            // 1. Get a reference to where we want the file to live in internal storage
            val targetFile = File(context.filesDir, modelFileName)

            // 2. Check if it already exists so we don't copy it every time the app opens
            if (!targetFile.exists()) {
                // This might take a few seconds depending on model size!
                copyModelFromRaw(targetFile)
            }

            // 3. Initialize the model using the path to the INTERNAL file
            val options = LlmInference.LlmInferenceOptions.builder()
                .setModelPath(targetFile.absolutePath)
                // .setMaxTokens(512) // Uncomment if crashing on Emulator
                .build()

            llmInference = LlmInference.createFromOptions(context, options)
        }
    }

    private fun copyModelFromRaw(targetFile: File) {
        try {
            // Open the file from res/raw/gemma_model.bin
            // Note: R.raw.gemma_model must match your actual filename
            val inputStream = context.resources.openRawResource(R.raw.gemma_model)

            FileOutputStream(targetFile).use { outputStream ->
                val buffer = ByteArray(4 * 1024) // 4KB buffer
                var read: Int
                while (inputStream.read(buffer).also { read = it } != -1) {
                    outputStream.write(buffer, 0, read)
                }
                outputStream.flush()
            }
            inputStream.close()
        } catch (e: Exception) {
            throw RuntimeException("Failed to copy model from resources", e)
        }
    }

    fun generateResponse(prompt: String): Flow<String> = callbackFlow {
        if (llmInference == null) {
            trySend("Error: Model not initialized.")
            close()
            return@callbackFlow
        }

        llmInference?.generateResponseAsync(prompt) { partialResult, done ->
            if (partialResult != null) {
                trySend(partialResult)
            }
            if (done) {
                close()
            }
        }

        awaitClose { }
    }

    fun close() {
        llmInference = null
    }
}