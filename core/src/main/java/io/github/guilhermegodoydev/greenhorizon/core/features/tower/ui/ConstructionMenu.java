package io.github.guilhermegodoydev.greenhorizon.core.features.tower.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ConstructionMenu extends MenuBase {
    public ConstructionMenu(TowerSelectionListener listener) {
        super(listener, "background_menu.png");

        Label.LabelStyle estiloVerde = new Label.LabelStyle(menuFont, Color.LIME);

        // BOTÃO ÁRVORE
        addButton("menuBtnArvore.png", "Tree").colspan(2).center();
        this.row();
        // PREÇO ÁRVORE (Exatamente embaixo)
        this.add(new Label("$50", estiloVerde)).colspan(2).center().padBottom(2);
        this.row();

        // BOTÕES EÓLICA E SOLAR
        addButton("menuBtnEolica.png", "Wind").left().expandX();
        addButton("menuBtnSolar.png", "Solar").right().expandX();
        this.row();

        // PREÇOS EÓLICA E SOLAR
        this.add(new Label("$100", estiloVerde)).left().expandX().padLeft(2);
        this.add(new Label("$150", estiloVerde)).right().expandX().padRight(2);

        this.pack();
    }
}
