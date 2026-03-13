package io.github.guilhermegodoydev.greenhorizon.itens;

public class CoinsManager {
    private int balance = 100;

    public int getBalance() { return balance; }

    public void addCoins(int amount) {
        balance += amount;
    }

    public void removeCoins(int amount) {
        if (canAfford(amount)) {
            balance -= amount;
        }
    }

    public boolean canAfford(int cost) {
        return balance >= cost;
    }
}
