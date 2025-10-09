package hu.bme.ait.todoapp.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import hu.bme.ait.todoapp.data.TodoItem
import hu.bme.ait.todoapp.data.TodoPriority

class TodoViewModel : ViewModel() {

    private var _todoList =
        mutableStateListOf<TodoItem>()

    init {
        _todoList.add(
            TodoItem(
                "0",
                "Demo1",
                "Desc...",
                "2025. 10. 09.",
                TodoPriority.HIGH,
                isDone = false
            )
        )
    }

    fun getAllToDoList(): List<TodoItem> {
        return _todoList;
    }

    fun addTodoList(todoItem: TodoItem) {
        _todoList.add(todoItem)
    }

    fun removeTodoItem(todoItem: TodoItem) {
        _todoList.remove(todoItem)
    }

    fun changeTodoState(todoItem: TodoItem, value: Boolean) {
        val index = _todoList.indexOf(todoItem)

        val newTodo = todoItem.copy(
            title = todoItem.title,
            description = todoItem.description,
            createDate = todoItem.createDate,
            priority = todoItem.priority,
            isDone = value
        )

        _todoList[index] = newTodo
    }
}