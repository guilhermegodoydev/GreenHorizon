package io.github.guilhermegodoydev.greenhorizon.core.managers;

public class LifeManager {
    private int vidasAtuais;
    private int vidasMaximas;

    public LifeManager(int vidasIniciais) {
        this.vidasAtuais = vidasIniciais;
        this.vidasMaximas = vidasIniciais;
    }

    public void perderVida(int quantidade) {
        vidasAtuais -= quantidade;
        if (vidasAtuais < 0) vidasAtuais = 0;
    }

    public boolean estaMorto() {
        return vidasAtuais <= 0;
    }

    public int getVidasAtuais() {
        return vidasAtuais;
    }
}
