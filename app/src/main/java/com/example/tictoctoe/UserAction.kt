package com.example.tictoctoe
//save the actions of user in this game to a sealed class
sealed class UserAction{
    object PlayAgainButtonClicked : UserAction()                  //user can click to button to start the game
    data class ButtonTapped(val cellNo:Int): UserAction()         //user can tapped to any cell
}
