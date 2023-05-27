package com.example.tictoctoe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel(){                         //this class extends from ViewModel
    var state by mutableStateOf(GameState())               //put the GameState as an state is should be var to change any time

    //the first time every cell is empty
    val boardItems :MutableMap<Int,BoardCellValue> = mutableMapOf(
        1 to BoardCellValue.NONE,
        2 to BoardCellValue.NONE,
        3 to BoardCellValue.NONE,
        4 to BoardCellValue.NONE,
        5 to BoardCellValue.NONE,
        6 to BoardCellValue.NONE,
        7 to BoardCellValue.NONE,
        8 to BoardCellValue.NONE,
        9 to BoardCellValue.NONE,
    )

    fun onAction(action: UserAction){
    when(action){
        is UserAction.ButtonTapped -> {
            AddValueToBoard(action.cellNo)

        }
        UserAction.PlayAgainButtonClicked -> {
            gameReset()
        }
    }
    }

    private fun gameReset() {
        boardItems.forEach{(i,_)->
            boardItems[i]=BoardCellValue.NONE
        }
        state= state.copy(
            hintText = "Player 'O' turn",
            currentTurn = BoardCellValue.CIRCLE,
            victoryType = VictoryType.NONE,
            hasWon = false
        )
    }

    private fun AddValueToBoard(cellNo: Int) {
        if (boardItems[cellNo] != BoardCellValue.NONE){
            return
        }
        if(state.currentTurn== BoardCellValue.CIRCLE){
            boardItems[cellNo] = BoardCellValue.CIRCLE
            if(checkForVictory(BoardCellValue.CIRCLE)){
                state=state.copy(
                    hintText = " Player 'O' Won",
                    playerCircleCount = state.playerCircleCount+1,
                    currentTurn = BoardCellValue.NONE,             //when circle is won no more action allowed
                    hasWon = true
                )
            }
            //check if the board is full or not
            if (asBoardFull()){
                state= state.copy(
                    hintText = " Game Draw",
                    drawCount = state.drawCount+1
                )
            }
            else{
                state= state.copy(                     //change the state here
                    hintText = "Player 'X' turn",
                    currentTurn = BoardCellValue.CROSS
                )
            }

        } else if (state.currentTurn== BoardCellValue.CROSS){
            boardItems[cellNo]= BoardCellValue.CROSS
            if(checkForVictory(BoardCellValue.CROSS)){
                state=state.copy(
                    hintText = " Player 'X' Won",
                    playerCrossCount = state.playerCrossCount+1,
                    currentTurn = BoardCellValue.NONE,             //when circle is won no more action allowed
                    hasWon = true
                )
            } else if (asBoardFull()){
                state= state.copy(
                    hintText = " Game Draw",
                    drawCount = state.drawCount+1
                )
            }else{
                state= state.copy(
                    hintText = "Player 'O' turn",
                    currentTurn = BoardCellValue.CIRCLE
                )
            }
        }
    }

    private fun checkForVictory(boardValue: BoardCellValue): Boolean {
        if (boardItems[1]==boardValue && boardItems[2]== boardValue && boardItems[3]==boardValue){
            state =state.copy(victoryType = VictoryType.HORIZONTAL1)
            return true
        }
        if (boardItems[4]==boardValue && boardItems[5]== boardValue && boardItems[6]==boardValue){
            state =state.copy(victoryType = VictoryType.HORIZONTAL2)
            return true
        }
        if (boardItems[7]==boardValue && boardItems[8]== boardValue && boardItems[9]==boardValue){
            state =state.copy(victoryType = VictoryType.HORIZONTAL3)
            return true
        }
        if (boardItems[1]==boardValue && boardItems[4]== boardValue && boardItems[7]==boardValue){
            state =state.copy(victoryType = VictoryType.VERTICAL1)
            return true
        }
        if (boardItems[2]==boardValue && boardItems[5]== boardValue && boardItems[8]==boardValue){
            state =state.copy(victoryType = VictoryType.VERTICAL2)
            return true
        }
        if (boardItems[3]==boardValue && boardItems[6]== boardValue && boardItems[9]==boardValue){
            state =state.copy(victoryType = VictoryType.VERTICAL3)
            return true
        }
        if (boardItems[1]==boardValue && boardItems[5]== boardValue && boardItems[9]==boardValue){
            state =state.copy(victoryType = VictoryType.DIAGONAL1)
            return true
        }
        if (boardItems[3]==boardValue && boardItems[5]== boardValue && boardItems[7]==boardValue){
            state =state.copy(victoryType = VictoryType.DIAGONAL2)
            return true
        }
        else return false
    }


    private fun asBoardFull(): Boolean {
        if (boardItems.containsValue(BoardCellValue.NONE))return false
         return true

    }

}