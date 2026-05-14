package io.github.guilhermegodoydev.greenhorizon.core.ui.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import io.github.guilhermegodoydev.greenhorizon.core.entities.towers.TowerBase;
import io.github.guilhermegodoydev.greenhorizon.core.ui.TowerSelectionListener;

public class TowerActionMenu extends MenuBase {
    private Label lblUpgradeCost;
    private Label lblSellValue;

    public TowerActionMenu(TowerSelectionListener listener) {
        super(listener, "background_menu.png");

        Label.LabelStyle whiteStyle = new Label.LabelStyle(menuFont, Color.WHITE);
        Label.LabelStyle goldStyle = new Label.LabelStyle(menuFont, Color.GOLD);

        lblUpgradeCost = new Label("", whiteStyle);
        lblSellValue = new Label("", goldStyle);

        addButton("menuBtnUpgrade.png", "Upgrade").colspan(2).center();
        this.row();

        this.add(lblUpgradeCost).colspan(2).center().padBottom(2);
        this.row();

        addButton("menuBtnInfo.png", "Info").left().expandX();
        addButton("menuBtnVender.png", "Sell").right().expandX();
        this.row();

        this.add().expandX();
        this.add(lblSellValue).right().expandX().padRight(2);

        this.pack();
    }

    public void updateValues(TowerBase tower) {
        lblSellValue.setText("+$" + tower.getSellValue());

        if (tower.getLevel() < TowerBase.MAX_LEVEL) {
            lblUpgradeCost.setText("-$" + tower.getUpgradeCost());
            lblUpgradeCost.setColor(Color.WHITE);
        } else {
            lblUpgradeCost.setText("MAX");
            lblUpgradeCost.setColor(Color.GOLD);
        }
    }
}
