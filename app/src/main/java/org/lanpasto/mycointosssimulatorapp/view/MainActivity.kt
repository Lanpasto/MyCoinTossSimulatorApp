package org.lanpasto.mycointosssimulatorapp.view


import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import org.lanpasto.mycointosssimulatorapp.R
import org.lanpasto.mycointosssimulatorapp.model.CoinSide
import org.lanpasto.mycointosssimulatorapp.viewmodel.CoinViewModel
import java.util.Random


class MainActivity : AppCompatActivity() {
    private lateinit var coinViewModel: CoinViewModel
    private lateinit var coinImageView: ImageView
    private lateinit var cross1: ImageView
    private lateinit var cross2: ImageView
    private lateinit var cross3: ImageView
    private lateinit var flipButton: Button
    private var isAnimationRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cross1 = findViewById(R.id.cross1)
        cross2 = findViewById(R.id.cross2)
        cross3 = findViewById(R.id.cross3)
        coinViewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        coinImageView = findViewById(R.id.coin_image_view)
        flipButton = findViewById(R.id.flip_button)

        flipButton.setOnClickListener {
            coinViewModel.flipCoin()
            updateCoinImage()
        }

        updateCoinImage()
    }

    private var counter: Int = 0


    @SuppressLint("ResourceType")
    private fun updateCoinImage() {
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

        // Перевіряємо, чи випадає протилежне значення
        if (coinSide != coinViewModel.getCurrentCoinSide()) {
            counter = 0
        } else {
            counter++
        }

        // Оновлюємо значення coinViewModel.getCurrentCoinSide()
        coinViewModel.setCurrentCoinSide(coinSide)

        // Керуємо видимістю ImageView елементів
        cross1.visibility =
            if (counter >= 1) View.VISIBLE else View.INVISIBLE
        cross2.visibility =
            if (counter >= 2) View.VISIBLE else View.INVISIBLE
        cross3.visibility =
            if (counter >= 3) View.VISIBLE else View.INVISIBLE

        // Логуємо значення counter
        Log.d("Check", "counter: $counter")

        // Перевіряємо, чи counter досягло 3 і виводимо повідомлення Toast
        if (counter > 3) {
            if (coinSide == CoinSide.TAILS) {
                Toast.makeText(this, "Сьогодні пиво з твого друга", Toast.LENGTH_SHORT).show()
            } else if (coinSide == CoinSide.HEADS) {
                Toast.makeText(this, "Ну ти голова гони сотигу...", Toast.LENGTH_SHORT).show()
            }
        }
            if (counter == 3) {
            if (coinSide == CoinSide.TAILS) {
                Toast.makeText(this, "Випало 3 рази поспіль TAILS!", Toast.LENGTH_SHORT).show()
            } else if (coinSide == CoinSide.HEADS) {
                Toast.makeText(this, "Випало 3 рази поспіль HEADS!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    @SuppressLint("ResourceType")
    fun onFlipButtonClick(view: View) {
        val animatorSet = AnimatorInflater.loadAnimator(this, R.anim.flip_button_animation) as AnimatorSet
        animatorSet.setTarget(view)
        animatorSet.start()
    }





    @SuppressLint("ResourceType")
    fun onImageViewClick(@Suppress("UNUSED_PARAMETER") view: View) {
        val animatorSet = AnimatorInflater.loadAnimator(this, R.anim.flip_animation) as AnimatorSet
        animatorSet.setTarget(coinImageView)
        animatorSet.start()
    }
}
