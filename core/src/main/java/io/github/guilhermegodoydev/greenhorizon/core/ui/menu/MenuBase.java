package io.github.guilhermegodoydev.greenhorizon.core.ui.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.guilhermegodoydev.greenhorizon.core.ui.TowerSelectionListener;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public abstract class MenuBase extends Table {
    protected TowerSelectionListener listener;

    public MenuBase(TowerSelectionListener listener, String backgroundPath) {
        this.listener = listener;
        this.setVisible(false);

        this.setBackground(new TextureRegionDrawable(Assets.getTexture(backgroundPath)));
    }

    protected Cell<ImageButton> adicionarBotao(String path, final String comando) {
        ImageButton botao = new ImageButton(new TextureRegionDrawable(Assets.getTexture(path)));

        botao.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (listener != null) {
                    listener.onTowerSelected(comando);
                }
                setVisible(false);
            }
        });

        return this.add(botao).size(16, 16);
    }
}
