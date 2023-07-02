package org.lanpasto.mycointosssimulatorapp.viewmodel

import androidx.lifecycle.ViewModel

import org.lanpasto.mycointosssimulatorapp.model.Coin
import org.lanpasto.mycointosssimulatorapp.model.CoinSide

    class CoinViewModel : ViewModel() {
    private val coin = Coin()
    private var currentCoinSide: CoinSide = CoinSide.HEADS

    fun getCurrentCoinSide(): CoinSide {
        return currentCoinSide
    }
    fun setCurrentCoinSide(coinSide: CoinSide) {
        currentCoinSide = coinSide
    }
    fun flipCoin() {
        coin.flip()
    }
        
}