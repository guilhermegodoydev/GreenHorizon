package io.github.guilhermegodoydev.greenhorizon.core.ui.menu;

import io.github.guilhermegodoydev.greenhorizon.core.ui.TowerSelectionListener;

public class TowerActionMenu extends MenuBase {
    public TowerActionMenu(TowerSelectionListener listener) {
        super(listener, "background_menu.png");


        adicionarBotao("menuBtnUpgrade.png", "Upgrade").colspan(2).center();

        this.row();

        adicionarBotao("menuBtnInfo.png", "Info").left().expandX();
        adicionarBotao("menuBtnVender.png", "Vender").right().expandX();

        this.pack();
    }
}
