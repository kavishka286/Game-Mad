package com.example.mycargame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
class Gameview (var c : Context,var gameTask: GameArea):View(c){
    private var gamePaint:Paint?=null
    private var gamespeed= 1
    private var gametime = 0
    private var gamescore = 0
    private var gameMousePosition = 0
    private val lion = ArrayList<HashMap<String,Any>>()

    var viewWidth = 0
    var viewHeight = 0
    init{
        gamePaint = Paint()

    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        if(gametime%700<10 +gamespeed){
            val map = HashMap<String,Any>()
            map["lane"]=(0..2).random()
            map["startTime"] = gametime
            lion.add(map)
        }
        gametime = gametime+10+gamespeed
        val catWidth = viewWidth/5
        val catHeight = catWidth+10
        gamePaint !!. style = Paint.Style.FILL
        val d = resources.getDrawable(R.drawable.rabbit,null)
        d.setBounds(
            gameMousePosition * viewWidth / 3 +viewWidth/15+25,
            viewHeight-2 - catHeight,
            gameMousePosition*viewWidth/3+ viewWidth/15+catWidth-25,
            viewHeight-2
        )
        d.draw(canvas!!)
        gamePaint!!.color = Color.GREEN
        var highScore = 0
        for (i in lion.indices){
            try{
                val bomX = lion[i]["lane"] as Int *viewWidth / 3 +viewWidth /15
                var bomY = gametime- lion[i]["startTime"] as Int
                val d2 = resources.getDrawable(R.drawable.loin,null)

                d2.setBounds(
                    bomX + 25 , bomY - catHeight , bomX + catWidth - 25 , bomY

                )
                d2.draw(canvas)
                if(lion[i]["lane"] as Int == gameMousePosition){
                    if(bomY> viewHeight - 2 - catHeight && bomY < viewHeight - 2){

                        gameTask.closeGame(gamescore)
                    }
                }
                if(bomY > viewHeight + catHeight)
                {
                    lion.removeAt(i)
                    gamescore++
                    gamespeed = 1 + Math.abs(gamescore /8)
                    if(gamescore > highScore){
                        highScore = gamescore
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
        gamePaint!!.color = Color.WHITE
        gamePaint!!.textSize = 40f
        canvas.drawText("Score : $gamescore",80f, 80f, gamePaint!!)
        canvas.drawText("Speed : $gamespeed",380f, 80f, gamePaint!!)
        invalidate()

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_DOWN ->{
                val x1 = event.x
                if(x1< viewWidth/2){
                    if(gameMousePosition>0){
                        gameMousePosition--
                    }
                }
                if(x1>viewWidth / 2){
                    if(gameMousePosition<2){
                        gameMousePosition++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP ->{

            }
        }
        return true
    }
}