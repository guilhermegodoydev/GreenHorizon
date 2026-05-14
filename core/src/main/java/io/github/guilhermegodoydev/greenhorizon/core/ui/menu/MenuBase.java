package io.github.guilhermegodoydev.greenhorizon.core.ui.menu;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    protected BitmapFont menuFont;

    public MenuBase(TowerSelectionListener listener, String backgroundPath) {
        this.listener = listener;
        this.setVisible(false);
        this.setBackground(new TextureRegionDrawable(Assets.getTexture(backgroundPath)));

        this.menuFont = new BitmapFont();
        this.menuFont.getData().setScale(0.40f);
    }

    protected Cell<ImageButton> addButton(String path, final String command) {
        ImageButton button = new ImageButton(new TextureRegionDrawable(Assets.getTexture(path)));

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (listener != null) {
                    listener.onTowerSelected(command);
                }
                setVisible(false);
            }
        });

        return this.add(button).size(16, 16);
    }
}
