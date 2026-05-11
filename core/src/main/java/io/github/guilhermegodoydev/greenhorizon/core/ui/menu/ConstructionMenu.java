package io.github.guilhermegodoydev.greenhorizon.core.ui.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import io.github.guilhermegodoydev.greenhorizon.core.ui.TowerSelectionListener;

public class ConstructionMenu extends MenuBase {
    public ConstructionMenu(TowerSelectionListener listener) {
        super(listener, "background_menu.png");

        Label.LabelStyle estiloVerde = new Label.LabelStyle(menuFont, Color.LIME);

        // BOTÃO ÁRVORE
        adicionarBotao("menuBtnArvore.png", "Arvore").colspan(2).center();
        this.row();
        // PREÇO ÁRVORE (Exatamente embaixo)
        this.add(new Label("$50", estiloVerde)).colspan(2).center().padBottom(2);
        this.row();

        // BOTÕES EÓLICA E SOLAR
        adicionarBotao("menuBtnEolica.png", "Eolica").left().expandX();
        adicionarBotao("menuBtnSolar.png", "Solar").right().expandX();
        this.row();

        // PREÇOS EÓLICA E SOLAR
        this.add(new Label("$100", estiloVerde)).left().expandX().padLeft(2);
        this.add(new Label("$150", estiloVerde)).right().expandX().padRight(2);

        this.pack();
    }
}
