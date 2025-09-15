package hu.bme.ait.dynamicxmllayoutdemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import hu.bme.ait.dynamicxmllayoutdemo.databinding.ActivityMainBinding
import hu.bme.ait.dynamicxmllayoutdemo.databinding.TodoRowItemBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSave.setOnClickListener {
            addNewTodoToTheList()
        }
    }

    private fun addNewTodoToTheList() {
        // create a view instance from todo_row_item.xml
        val bindingTodoRow = TodoRowItemBinding.inflate(layoutInflater)

        bindingTodoRow.tvTodoTitle.text = binding.etTodo.text.toString()
        bindingTodoRow.btnDeleteTodo.setOnClickListener {
            binding.main.removeView(bindingTodoRow.root)
        }

        binding.main.addView(bindingTodoRow.root, 0)
    }


}

