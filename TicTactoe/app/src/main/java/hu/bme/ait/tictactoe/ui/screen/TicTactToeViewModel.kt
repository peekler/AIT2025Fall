package hu.bme.ait.tictactoe.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

enum class Player { X, O }

data class BoardCell(val row: Int, val col: Int)

class TicTactToeViewModel : ViewModel() {

    var board by mutableStateOf(
        Array(3) { Array(3) { null as Player? } })
        private set

    var currentPlayer by mutableStateOf(Player.X)
        private set

    var winner by mutableStateOf<Player?>(null)
        private set

    fun onCellClicked(cell: BoardCell) {
        // if the cell is empty and the game is not finished
        if (board[cell.row][cell.col] != null || winner != null) return

        board[cell.row][cell.col] = currentPlayer

        currentPlayer =
            if (currentPlayer == Player.X) Player.O else Player.X
    }

}