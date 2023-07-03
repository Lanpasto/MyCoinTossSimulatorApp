package org.lanpasto.mycointosssimulatorapp.viewmodel

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import org.lanpasto.mycointosssimulatorapp.R

import org.lanpasto.mycointosssimulatorapp.model.Coin
import org.lanpasto.mycointosssimulatorapp.model.CoinSide
import java.util.Random


class CoinViewModel : ViewModel() {
    private val coin = Coin()
    private var currentCoinSide: CoinSide = CoinSide.HEADS
    private var counter: Int = 0
    private var isAnimationRunning = false

    private fun getCurrentCoinSide(): CoinSide {
        return currentCoinSide
    }

    private fun setCurrentCoinSide(coinSide: CoinSide) {
        currentCoinSide = coinSide
    }

    fun flipCoin() {
        coin.flip()
        Log.d("CoinViewModel", "Coin flipped")
    }

    @SuppressLint("ResourceType")
    fun updateCoinImage(
        coinImageView: ImageView,
        cross1: ImageView,
        cross2: ImageView,
        cross3: ImageView,
        context: Context
    ) {
        if (isAnimationRunning) {
            return
        }

        val random = Random()
        val coinSide = if (random.nextBoolean()) CoinSide.HEADS else CoinSide.TAILS

        val imageRes = when (coinSide) {
            CoinSide.HEADS -> R.drawable.first
            CoinSide.TAILS -> R.drawable.second
        }

        val rotationAnimator1 = ObjectAnimator.ofFloat(coinImageView, "rotationY", 0f, -90f)
        rotationAnimator1.duration = 500

        val rotationAnimator2 = ObjectAnimator.ofFloat(coinImageView, "rotationY", 90f, 0f)
        rotationAnimator2.duration = 500

        rotationAnimator1.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                coinImageView.setImageResource(imageRes)
                rotationAnimator2.start()
            }
        })

        rotationAnimator2.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                isAnimationRunning = true
            }

            override fun onAnimationEnd(animation: Animator) {
                isAnimationRunning = false
            }
        })

        rotationAnimator1.start()

        if (coinSide != getCurrentCoinSide()) {
            counter = 0
        } else {
            counter++
        }

        setCurrentCoinSide(coinSide)

        cross1.visibility = if (counter >= 1) View.VISIBLE else View.INVISIBLE
        cross2.visibility = if (counter >= 2) View.VISIBLE else View.INVISIBLE
        cross3.visibility = if (counter >= 3) View.VISIBLE else View.INVISIBLE

        Log.d("CoinViewModel", "Counter: $counter")

        if (counter > 3) {
            if (coinSide == CoinSide.TAILS) {
                Toast.makeText(context, "Сьогодні пиво з твого друга", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Ну ти голова гони сотигу...", Toast.LENGTH_SHORT).show()
            }
        }
        if (counter == 3) {
            if (coinSide == CoinSide.TAILS) {
                Toast.makeText(context, "Випало 3 рази поспіль TAILS!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Випало 3 рази поспіль HEADS!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("ResourceType")
    fun onImageViewClick(view: View) {
        val animatorSet = AnimatorInflater.loadAnimator(view.context, R.anim.flip_animation) as AnimatorSet
        animatorSet.setTarget(view)
        animatorSet.start()

        Log.d("CoinViewModel", "ImageView clicked")
    }
}
