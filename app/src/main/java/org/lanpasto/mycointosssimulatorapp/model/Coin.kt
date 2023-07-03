package org.lanpasto.mycointosssimulatorapp.model

import android.util.Log

enum class CoinSide {
    HEADS,
    TAILS
}

class Coin {
    var currentSide: CoinSide = CoinSide.HEADS
        private set

    fun flip() {
        val previousSide = currentSide
        currentSide = if (currentSide == CoinSide.HEADS) {
            CoinSide.TAILS
        } else {
            CoinSide.HEADS
        }
        Log.d("Coin", "Coin flipped: $previousSide -> $currentSide")
    }
}
