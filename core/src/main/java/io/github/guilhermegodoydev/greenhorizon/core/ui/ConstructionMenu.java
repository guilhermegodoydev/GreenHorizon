package io.github.guilhermegodoydev.greenhorizon.core.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ConstructionMenu extends Table {
    private TowerSelectionListener listener;

    public ConstructionMenu(TowerSelectionListener listener) {
        this.listener = listener;
        this.setVisible(false);

        Texture bgTex = new Texture("background_menu.png");
        bgTex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        this.setBackground(new TextureRegionDrawable(bgTex));

        adicionarBotao("menuBtnArvore.png", "Arvore").colspan(2).center();

        this.row();

        adicionarBotao("menuBtnEolica.png", "Eolica").left().expandX();
        adicionarBotao("menuBtnSolar.png", "Solar").right().expandX();

        this.pack();
    }


    private Cell<ImageButton> adicionarBotao(String path, final String nome) {
        Texture tex = new Texture(path);
        tex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        ImageButton botao = new ImageButton(new TextureRegionDrawable(tex));

        botao.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (listener != null) listener.onTowerSelected(nome);
                setVisible(false);
            }
        });

        return this.add(botao).size(16, 16);
    }
}
