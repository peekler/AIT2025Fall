package hu.bme.ait.aittimeshow

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
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

        val desiredPaddingDp = 16
        val desiredPaddingPx = (desiredPaddingDp * resources.displayMetrics.density).toInt()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left + desiredPaddingPx,
                systemBars.top + desiredPaddingPx,
                systemBars.right + desiredPaddingPx,
                systemBars.bottom + desiredPaddingPx
            )
            insets // Return the original insets
        }

        binding.btnShow.setOnClickListener {
            var a = 5/0

            Log.d("TAG_MAIN","btnShow was pressed $a")

            try {
                if (binding.etNumA.text.isNotEmpty()) {
                    var numA = binding.etNumA.text.toString().toInt()
                    var numB = binding.etNumA.text.toString().toInt()
                    var sum = numA + numB

                    Log.d("TAG_MAIN","sum: $sum")

                    var currTime = Date(System.currentTimeMillis()).toString()

                    Toast.makeText(
                        this, "Time: $currTime", Toast.LENGTH_LONG
                    ).show()

                    binding.tvData.text = currTime

                    //binding.root.setBackgroundColor(Color.BLUE)
                    revealCard()
                } else {
                    binding.etNumA.error = getString(R.string.text_empty)
                }
            } catch (e: Exception) {
                binding.etNumA.error = "Error: ${e.message}"
            }
        }
    }

    fun revealCard() {
        val x = binding.cardView.getRight()
        val y = binding.cardView.getBottom()

        val startRadius = 0
        val endRadius = Math.hypot(binding.cardView.getWidth().toDouble(),
            binding.cardView.getHeight().toDouble()).toInt()

        val anim = ViewAnimationUtils.createCircularReveal(
            binding.cardView,
            x,
            y,
            startRadius.toFloat(),
            endRadius.toFloat()
        )
        anim.setDuration(5000)

        binding.cardView.setVisibility(View.VISIBLE)
        anim.start()
    }



}