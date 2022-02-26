package com.example.tictactoe

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.airbnb.lottie.LottieAnimationView
import java.util.*

class MainActivity : AppCompatActivity() {
    private val game = Game()
    private  lateinit var player1:Player
    private lateinit var player2: Player
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        iniPlayer()


    }



    private fun updateGame(lottieView: LottieAnimationView) {
        when (game.turn) {

            "X" -> {

                lottieView.clearAnimation()
                lottieView.setAnimation(R.raw.x)
                lottieView.playAnimation()
                lottieView.isClickable = false
                player1.fieldsPlayed.add(lottieView.tag.toString())
                game.turn = "O"
                proveriPobednika(player1)
            }
            "O" -> {
              
                lottieView.clearAnimation()
                lottieView.setAnimation(R.raw.o)
                lottieView.playAnimation()
                lottieView.isClickable = false
                player2.fieldsPlayed.add(lottieView.tag.toString())
                game.turn = "X"
                proveriPobednika(player2)
            }
        }
    }

    private fun proveriPobednika(player: Player) {


        for (lista in game.winCombinations) {

            var won = false
            var brojac = 0
            for (i in 0 until player.fieldsPlayed.size) {


                if (lista.contains(player.fieldsPlayed[i])) {

                    brojac++
                }
                if (brojac == 3) {
                    won = true
                    break
                }

            }

            if (won) {
                Toast.makeText(this, "Pobedio je znak --> " + player.type, Toast.LENGTH_LONG).show()
                game.isPlaying = false
                game.whoWon=player.type
                GameEnds()
                break
            }


        }
        if (player1.fieldsPlayed.size > 4 || player2.fieldsPlayed.size > 4) {
            Toast.makeText(this, "Nereseno", Toast.LENGTH_LONG).show()
            GameEnds()
        }

    }

    private fun GameEnds() {
        val reset = findViewById<ImageView>(R.id.reset)
        reset.visibility = View.VISIBLE

        AlertDialog.Builder(this).setTitle("Igrajte Opet?").setPositiveButton("Da") { dialog, which -> resetClicked(null) }.setNegativeButton("Ne") { dialog, which -> }.show()
    }

    private fun iniPlayer() {
        player1 = Player("X", LinkedList<String>())
        player2 = Player("O", LinkedList<String>())
        game.turn = "X"
    }

    fun FiledClicked(v: View) {
        if (game.isPlaying)
            updateGame(findViewById<LottieAnimationView>(v.id))
    }

    fun resetClicked(v: View?) {
       val intent= Intent(this,MainActivity::class.java)

        startActivity(intent)
        Runtime.getRuntime().exit(0)
    }

}


data class Player(val type:String, var fieldsPlayed:LinkedList<String>)
class Game(){
    var winCombinations= listOf(listOf("0","1","2"),listOf("3","4","5"),
            listOf("6","7","8"),listOf("0","4","8"),listOf("2","4","6"),listOf("0","3","6"),listOf("1","4","7"),listOf("2","5","8"))

    var turn="0"
    var isPlaying=true
    var whoWon=""
}

