package hu.bme.ait.aittimeshow

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import hu.bme.ait.aittimeshow.databinding.ActivityMainBinding
import java.util.Date

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnShow.setOnClickListener {
            //var numA = binding.etNumA.text.toString().toInt()
            //var numB = binding.etNumA.text.toString().toInt()
            //var sum = numA+numB

            var currTime = Date(System.currentTimeMillis()).toString()

            Toast.makeText(
                this, "Time: $currTime", Toast.LENGTH_LONG).show()

            binding.tvData.text = currTime

            //binding.root.setBackgroundColor(Color.BLUE)

        }

    }
}