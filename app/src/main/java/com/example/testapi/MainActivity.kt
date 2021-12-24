package com.example.testapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.testapi.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            job = CoroutineScope(Dispatchers.IO).launch {
                val request = ApiUtils.createRetrofit()
                    .getRates(
                        mapOf(
                            "uid" to "563B4852-6D4B-49D6-A86E-B273DD520FD2",
                            "type" to "ExchangeRates",
                            "rid" to "BEYkZbmV"
                        )
                    ).execute()

                if (request.isSuccessful)
                    withContext(Dispatchers.Main) {
                        request.body()?.let { it -> showToast(it) }
                    }
            }
        }
    }

    private fun showToast(data: ApiResponse) {
        Toast.makeText(this, "${data.code}, ${data.message}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}
