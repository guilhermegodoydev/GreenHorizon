package io.github.guilhermegodoydev.greenhorizon.core.itens;

import io.github.guilhermegodoydev.greenhorizon.core.exceptions.InsufficientFundsException;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;


public class CoinsManager {
    private int saldoAtual;

    /**
     * Construtor que define o saldo inicial de moedas.
     * @param moedainiciais Quantidade de moedas com que o jogo começa
     */
    public CoinsManager(int moedainiciais) {
        this.saldoAtual = moedainiciais;
        System.out.println("CoinsManager iniciado com " + moedainiciais + " moedas");
    }

    /**
     * Adiciona moedas ao saldo (quando inimigos são derrotados, torres vendidas, etc).
     * @param valor Quantidade de moedas a adicionar
     */
    public void acrescentar(int valor) {
        this.saldoAtual += valor;
        Assets.getSound("getCoin.wav").play();

        System.out.println("Moedas adicionadas! Novo saldo: " + saldoAtual);
    }

    /**
     * Remove moedas do saldo (quando torres são construídas).
     * Lança InsufficientFundsException se não houver saldo suficiente.
     * @param valor Quantidade de moedas a remover
     * @throws InsufficientFundsException Se o saldo for insuficiente
     */
    public void remover(int valor) throws InsufficientFundsException {
        if (saldoAtual < valor) {
            throw new InsufficientFundsException(
                "Saldo insuficiente! Necessário: " + valor + ", Disponível: " + saldoAtual,
                1001
            );
        }
        this.saldoAtual -= valor;
        Assets.getSound("payCoin.wav").play();

        System.out.println("Moedas removidas! Novo saldo: " + saldoAtual);
    }

    /**
     * Retorna o saldo atual de moedas.
     */
    public int getSaldoAtual() {
        return saldoAtual;
    }

    /**
     * Define manualmente o saldo de moedas (útil para testes ou eventos especiais).
     * @param novoSaldo O novo saldo
     */
    public void setSaldoAtual(int novoSaldo) {
        this.saldoAtual = novoSaldo;
        System.out.println("Saldo definido para: " + saldoAtual);
    }
}
