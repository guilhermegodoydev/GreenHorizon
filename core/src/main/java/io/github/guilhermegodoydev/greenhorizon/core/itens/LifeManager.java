package io.github.guilhermegodoydev.greenhorizon.core.itens;

public class LifeManager {
    private int vidasAtuais;
    private int vidasMaximas;

    // construtor que define com quantas  vidas a base começa
    public LifeManager(int vidasIniciais) {
        this.vidasMaximas = vidasIniciais;
        this.vidasAtuais = vidasMaximas;
    }

    // metodo para quando um inimigo passar pelas torres
    public void perderVida(int quantidade) {
        try {
            vidasAtuais -= quantidade;
            System.out.println("Passou um inimigo! Vidas restantes: " + vidasAtuais);

            if (vidasAtuais <= 0) {
                vidasAtuais = 0;
                darGameOver();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // metodo que vai ser chamado qunado zerar a vida
    public void darGameOver() {
        System.out.println("Game Over! A base foi destruida");
    }
    // metodo para a gente pegar a vida e desenhar na tela depois
    public int getVidasAtuais() {
        return vidasAtuais;
    }
}
