package io.github.guilhermegodoydev.greenhorizon.core.exceptions;

public class InsufficientFundsException extends Exception{
    private final int errorCode;

    /**
     * Exceção lançada quando o saldo de moedas é insuficiente.
     * @param mensagem Descrição do erro
     * @param errorCode Código de erro para identificação (ex: 1001)
     */
    public InsufficientFundsException(String mensagem, int errorCode) {
        super(mensagem);
        this.errorCode = errorCode;
    }

    /**
     * Retorna o código de erro para facilitar logging e debugging.
     */
    public int getErrorCode() {
        return errorCode;
    }
}

