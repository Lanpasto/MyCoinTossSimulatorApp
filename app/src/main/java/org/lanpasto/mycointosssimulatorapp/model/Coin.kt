package org.lanpasto.mycointosssimulatorapp.model

enum class CoinSide {
    HEADS,
    TAILS
}

class Coin {
    var currentSide: CoinSide = CoinSide.HEADS
        private set

    fun flip() {
        currentSide = if (currentSide == CoinSide.HEADS) {
            CoinSide.TAILS
        } else {
            CoinSide.HEADS
        }
    }
}