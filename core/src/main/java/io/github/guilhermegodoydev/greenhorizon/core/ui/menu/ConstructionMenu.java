package io.github.guilhermegodoydev.greenhorizon.core.ui.menu;

import io.github.guilhermegodoydev.greenhorizon.core.ui.TowerSelectionListener;

public class ConstructionMenu extends MenuBase {
    public ConstructionMenu(TowerSelectionListener listener) {
        super(listener, "background_menu.png");

        adicionarBotao("menuBtnArvore.png", "Arvore").colspan(2).center();
        this.row();
        adicionarBotao("menuBtnEolica.png", "Eolica").left().expandX();
        adicionarBotao("menuBtnSolar.png", "Solar").right().expandX();

        this.pack();
    }
}
