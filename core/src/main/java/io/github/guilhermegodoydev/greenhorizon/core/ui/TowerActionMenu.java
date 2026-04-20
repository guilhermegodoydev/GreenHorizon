package io.github.guilhermegodoydev.greenhorizon.core.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TowerActionMenu extends Table {
    private TowerSelectionListener listener;

    public TowerActionMenu(TowerSelectionListener listener) {
        this.listener = listener;
        this.setVisible(false);

        // Fundo do menu de ação (pode ser uma cor ou arte diferente)
        Texture bg = new Texture("background_menu.png");
        bg.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        this.setBackground(new TextureRegionDrawable(bg));




        this.row();

        adicionarBotao("menuBtnVender.png", "Vender").left().expandX();;
        adicionarBotao("menuBtnUpgrade.png", "Upgrade").right().expandX();

        this.pack();
    }

    private Cell<ImageButton> adicionarBotao(String path, final String acao) {
        Texture tex = new Texture(path);
        tex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        ImageButton btn = new ImageButton(new TextureRegionDrawable(tex));

        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (listener != null) listener.onTowerSelected(acao);
                setVisible(false);
            }
        });
        return this.add(btn).size(16, 16).pad(5);
    }
}
