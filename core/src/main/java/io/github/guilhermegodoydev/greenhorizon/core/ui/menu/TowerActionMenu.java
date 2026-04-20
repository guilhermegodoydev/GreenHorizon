package io.github.guilhermegodoydev.greenhorizon.core.ui.menu;

import io.github.guilhermegodoydev.greenhorizon.core.ui.TowerSelectionListener;

public class TowerActionMenu extends MenuBase {
    public TowerActionMenu(TowerSelectionListener listener) {
        super(listener, "background_menu.png");

        adicionarBotao("menuBtnVender.png", "Vender");
        adicionarBotao("menuBtnUpgrade.png", "Upgrade");

        this.pack();
    }
}
