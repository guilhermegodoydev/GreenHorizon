package io.github.guilhermegodoydev.greenhorizon.core.features.economy;

import io.github.guilhermegodoydev.greenhorizon.core.exceptions.InsufficientFundsException;
import io.github.guilhermegodoydev.greenhorizon.core.utils.SettingsManager;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class CoinsManager {
    private int currentBalance;

    public CoinsManager(int initialCoins) {
        this.currentBalance = initialCoins;
        System.out.println("CoinsManager started with " + initialCoins + " coins");
    }

    public void add(int amount) {
        this.currentBalance += amount;
        Assets.getSound("sfx/getCoin.wav").play(SettingsManager.getSfxVolume());
        System.out.println("Coins added! New balance: " + currentBalance);
    }

    public void remove(int amount) throws InsufficientFundsException {
        if (currentBalance < amount) {
            throw new InsufficientFundsException(
                "Insufficient funds! Required: " + amount + ", Available: " + currentBalance,
                1001
            );
        }
        this.currentBalance -= amount;
        Assets.getSound("sfx/payCoin.wav").play(SettingsManager.getSfxVolume());
        System.out.println("Coins removed! New balance: " + currentBalance);
    }

    public int getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(int newBalance) {
        this.currentBalance = newBalance;
        System.out.println("Balance set to: " + currentBalance);
    }
}
