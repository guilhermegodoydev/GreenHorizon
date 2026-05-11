package io.github.guilhermegodoydev.greenhorizon.core.ui.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import io.github.guilhermegodoydev.greenhorizon.core.entities.towers.TowerBase;
import io.github.guilhermegodoydev.greenhorizon.core.ui.TowerSelectionListener;

public class TowerActionMenu extends MenuBase {
    private Label lblCustoUpgrade;
    private Label lblValorVenda;

    public TowerActionMenu(TowerSelectionListener listener) {
        super(listener, "background_menu.png");

        Label.LabelStyle estiloBranco = new Label.LabelStyle(menuFont, Color.WHITE);
        Label.LabelStyle estiloOuro = new Label.LabelStyle(menuFont, Color.GOLD);

        lblCustoUpgrade = new Label("", estiloBranco);
        lblValorVenda = new Label("", estiloOuro);

        // BOTÃO UPGRADE
        adicionarBotao("menuBtnUpgrade.png", "Upgrade").colspan(2).center();
        this.row();
        // LABEL UPGRADE/MAX
        this.add(lblCustoUpgrade).colspan(2).center().padBottom(2);
        this.row();

        // BOTÕES INFO E VENDER (O "i" voltou!)
        adicionarBotao("menuBtnInfo.png", "Info").left().expandX();
        adicionarBotao("menuBtnVender.png", "Vender").right().expandX();
        this.row();

        // VALOR DE VENDA (Embaixo do botão de lixo)
        this.add().expandX(); // Espaço vazio embaixo do info
        this.add(lblValorVenda).right().expandX().padRight(2);

        this.pack();
    }

    public void atualizarValores(TowerBase torre) {
        // Atualiza a venda
        lblValorVenda.setText("+$" + torre.getValorVenda());

        // Lógica do Upgrade + MAX que você gostou
        if (torre.getNivel() < TowerBase.NIVEL_MAXIMO) {
            lblCustoUpgrade.setText("-$" + torre.getCustoUpgrade());
            lblCustoUpgrade.setColor(Color.WHITE);
        } else {
            lblCustoUpgrade.setText("MAX");
            lblCustoUpgrade.setColor(Color.GOLD);
        }
    }
}
