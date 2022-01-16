package com.example.moor

import android.os.Bundle
import android.content.pm.ActivityInfo
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.children
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    lateinit var random: Random
    lateinit var hLayout1: LinearLayout
    lateinit var hLayout2: LinearLayout
    lateinit var lastCard: ImageView
    lateinit var statText: TextView
    var deck : MutableList<Int> = mutableListOf(0,1,2,3,4,5,6,7,8,
                                                9,10,11,12,13,14,15,16,17,
                                                18,19,20,21,22,23,24,25,26,
                                                27,28,29,30,31,32,33,34,35)
    var playerCards : MutableList<Int> = mutableListOf()
    var aiCards : MutableList<Int> = mutableListOf()
    var cards : MutableList<Int> = mutableListOf()
    var playerSkips : Boolean = false
    var aiSkips : Boolean = false
    var awaitPlayer : Boolean = false


    var cardsArray = intArrayOf(
        R.drawable.clubs1,
        R.drawable.clubs2,
        R.drawable.clubs3,
        R.drawable.clubs4,
        R.drawable.clubs5,
        R.drawable.clubs6,
        R.drawable.clubs7,
        R.drawable.clubs8,
        R.drawable.clubs9,
        R.drawable.diamonds1,
        R.drawable.diamonds2,
        R.drawable.diamonds3,
        R.drawable.diamonds4,
        R.drawable.diamonds5,
        R.drawable.diamonds6,
        R.drawable.diamonds7,
        R.drawable.diamonds8,
        R.drawable.diamonds9,
        R.drawable.hearts1,
        R.drawable.hearts2,
        R.drawable.hearts3,
        R.drawable.hearts4,
        R.drawable.hearts5,
        R.drawable.hearts6,
        R.drawable.hearts7,
        R.drawable.hearts8,
        R.drawable.hearts9,
        R.drawable.spades1,
        R.drawable.spades2,
        R.drawable.spades3,
        R.drawable.spades4,
        R.drawable.spades5,
        R.drawable.spades6,
        R.drawable.spades7,
        R.drawable.spades8,
        R.drawable.spades9
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar()?.hide();
        setContentView(R.layout.activity_main)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_main);
        var scroll1 : HorizontalScrollView = findViewById(R.id.playerHand)
        var scroll2 : HorizontalScrollView = findViewById(R.id.aiHand)
        statText = findViewById(R.id.Status)
        lastCard = findViewById(R.id.lastPlayed)
        hLayout1 = findViewById<LinearLayout>(R.id.hLayout1)
        hLayout2 = findViewById<LinearLayout>(R.id.hLayout2)
        random = Random

        deck.shuffle()
        for (i in 0..4) {
            addCardToPlayer(deck.last())
            playerCards.add(deck.last())
            deck.remove(deck.last())
        }
        for (i in 0..4)
        {
            addCardtoAI(deck.last())
            aiCards.add(deck.last())
            deck.remove(deck.last())
        }
        //tryToPlay(2,aiCards.last())
        playCard(2,aiCards.last())
        hLayout2.removeAllViews()
        for (i in aiCards)
            addCardtoAI(i)
        if(cards.last()%9!=0 && cards.last()%9!=1 && cards.last()%9!=8)
            awaitPlayer=true
    }


    private fun addCardToPlayer(number: Int) {
        val imageButton = ImageButton(this)
        imageButton.layoutParams = LinearLayout.LayoutParams(
            250,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageButton.scaleType = ImageView.ScaleType.CENTER_CROP;
        imageButton.id= number
        imageButton.setImageResource(cardsArray[number])
        imageButton.setOnClickListener {
            tryToPlay(0,number)
        }
        // Add ImageButton to LinearLayout
        hLayout1.addView(imageButton)
    }
    private fun addCardtoAI(number: Int) {
        val imageButton = ImageButton(this)
        imageButton.layoutParams = LinearLayout.LayoutParams(
            250,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageButton.scaleType = ImageView.ScaleType.CENTER_CROP;
        imageButton.id= number
        imageButton.setImageResource(cardsArray[number])
        imageButton.setOnClickListener {
            tryToPlay(1,number)
        }
        // Add ImageButton to LinearLayout
        hLayout2.addView(imageButton)
    }
    private fun playCard(mode: Int, number: Int){
        if(mode==2)//первый ход ai
        {
            playerSkips = false
            if (number % 9 == 0)
            {
                addCardToPlayer(deck.last())
                playerCards.add(deck.last())
                deck.remove(deck.last())
                playerSkips=true
            }
            if (number % 9 == 1)
            {
                addCardToPlayer(deck.last())
                playerCards.add(deck.last())
                deck.remove(deck.last())
                addCardToPlayer(deck.last())
                playerCards.add(deck.last())
                deck.remove(deck.last())
                playerSkips=true
            }
            if(number%9==8)
                playerSkips=true
            cards.add(number)
            aiCards.remove(number)
            setCardImage(number,lastCard)
            if(playerSkips){
                awaitPlayer = false
                statText.text = "It Is AI's Turn"
            }
            else statText.text = "It Is Your Turn"
        }
        if(mode==1)//ходит ai
        {
            awaitPlayer = true
            playerSkips = false
            if (number % 9 == 0) {
                addCardToPlayer(deck.last())
                playerCards.add(deck.last())
                deck.remove(deck.last())
                playerSkips = true
            }
            if (number % 9 == 1) {
                addCardToPlayer(deck.last())
                playerCards.add(deck.last())
                deck.remove(deck.last())
                addCardToPlayer(deck.last())
                playerCards.add(deck.last())
                deck.remove(deck.last())
                playerSkips = true
            }
            if (number % 9 == 8)
                playerSkips = true
            cards.add(number)
            aiCards.remove(number)
            setCardImage(number, lastCard)
            if (playerSkips){
                awaitPlayer = false
            statText.text = "It Is AI's Turn"
            }
            else statText.text="It Is Your Turn"
        }
        if(mode==0)//ходит игрок
        {
            awaitPlayer=false
            aiSkips = false
            if (number % 9 == 0)
            {
                addCardtoAI(deck.last())
                aiCards.add(deck.last())
                deck.remove(deck.last())
                aiSkips=true
            }
            if (number % 9 == 1)
            {
                addCardtoAI(deck.last())
                aiCards.add(deck.last())
                deck.remove(deck.last())
                addCardtoAI(deck.last())
                aiCards.add(deck.last())
                deck.remove(deck.last())
                aiSkips=true
            }
            if(number%9==8)
                aiSkips=true
            cards.add(number)
            playerCards.remove(number)
            setCardImage(number,lastCard)
            if(aiSkips){
                awaitPlayer=true
                statText.text="It Is Your Turn"
            }
            else statText.text = "It Is AI's Turn"
        }
    }
    private fun tryToPlay(mode:Int,number: Int){
        var flag : Boolean = false
        if(mode==0)//player
        {
            if(awaitPlayer)
            {
                for(i in playerCards)
                    if(canMove(i))
                        flag=true
                if(!flag) {
                    Toast.makeText(
                        this@MainActivity,
                        "You Draw a Card",
                        Toast.LENGTH_SHORT
                    ).show()
                    addCardToPlayer(deck.last())
                    playerCards.add(deck.last())
                    deck.remove(deck.last())
                }
                for(i in playerCards)
                    if(canMove(i))
                        flag=true
                if(flag){
                    if(canMove(number))
                    {
                        playCard(0,number)
                        hLayout1.removeAllViews()
                        for (i in playerCards)
                            addCardToPlayer(i)
                        if(playerCards.isEmpty()){
                            statText.text = "You Won!"
                            endGame()
                        }
                    }
                    else
                        Toast.makeText(
                        this@MainActivity,
                        R.string.cant_play,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else {
                Toast.makeText(
                    this@MainActivity,
                    R.string.wait_turn,
                    Toast.LENGTH_SHORT
                ).show()
            }
            if(!flag){
                awaitPlayer = false
                statText.text = "It Is AI's Turn"
            }
        }
        if(mode==1)//ai
        {
            for(i in aiCards)
                if(canMove(i))
                    flag=true
            if(!flag) {
                Toast.makeText(
                    this@MainActivity,
                    "AI Draws a Card",
                    Toast.LENGTH_SHORT
                ).show()
                addCardtoAI(deck.last())
                aiCards.add(deck.last())
                deck.remove(deck.last())
            }
            for(i in aiCards)
                if(canMove(i))
                    flag=true
            if(flag){
                if(!awaitPlayer)
                {
                if(canMove(number))
                    {
                        playCard(1,number)
                        hLayout2.removeAllViews()
                        for (i in aiCards)
                            addCardtoAI(i)
                        if(aiCards.isEmpty()){
                            statText.text = "You Lost!"
                            endGame()
                        }
                    }
                    else
                        Toast.makeText(
                            this@MainActivity,
                            R.string.cant_play,
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }
            else {
                Toast.makeText(
                    this@MainActivity,
                    R.string.wait_turn,
                    Toast.LENGTH_SHORT
                ).show()
            }
            if(!flag){
                awaitPlayer=true
                statText.text="It Is Your Turn"
            }
        }
    }
    private fun aiTurn(){
        var flag : Boolean = false
        for(i in hLayout1.children)
            i.setEnabled(false)
        for(i in hLayout2.children)
            i.setEnabled(true)
        statText.text="It Is AI's Turn"
        for(i in aiCards)
            if(canMove(i)){
                flag=true
                tryToPlay(1, i)
            }
        if(!flag) {
            Toast.makeText(
                this@MainActivity,
                "AI Draws a Card",
                Toast.LENGTH_SHORT
            ).show()
            tryToPlay(1,aiCards[0])
        }
        for(i in aiCards)
            if(canMove(i)){
                tryToPlay(1,i)
                flag=true
            }
        if(!flag)
            tryToPlay(1,aiCards[0])
    }
    private fun endGame() {
        for(i in hLayout2.children)
            i.setEnabled(false)
        for(i in hLayout1.children)
            i.setEnabled(false)
    }
    private fun canMove(number:Int): Boolean {
        if(cards.last()/9==number/9 || cards.last()%9==number%9 || number%9==6)
            return true
        return false
    }
    private fun setCardImage(number: Int, image: ImageView) {
        image.setImageResource(cardsArray[number])
    }
}