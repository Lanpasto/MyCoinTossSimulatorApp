package org.lanpasto.mycointosssimulatorapp.view


import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import org.lanpasto.mycointosssimulatorapp.R
import org.lanpasto.mycointosssimulatorapp.viewmodel.CoinViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var coinViewModel: CoinViewModel
    private lateinit var coinImageView: ImageView
    private lateinit var cross1: ImageView
    private lateinit var cross2: ImageView
    private lateinit var cross3: ImageView
    private lateinit var flipButton: Button

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

        coinImageView.setOnClickListener {
            coinViewModel.onImageViewClick(it)
        }

        updateCoinImage()
    }

    private fun updateCoinImage() {
        coinViewModel.updateCoinImage(coinImageView, cross1, cross2, cross3, this)
    }
}



