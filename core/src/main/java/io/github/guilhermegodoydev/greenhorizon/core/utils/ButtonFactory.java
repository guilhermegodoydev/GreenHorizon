package io.github.guilhermegodoydev.greenhorizon.core.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.guilhermegodoydev.greenhorizon.Main;

public class ButtonFactory {

    public static ImageButton createHoverButton(String imgNormal, String imgHover) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(Assets.getTexture(imgNormal));
        style.over = new TextureRegionDrawable(Assets.getTexture(imgHover));

        final ImageButton button = new ImageButton(style);

        button.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1 && button.isTouchable()) {
                    Gdx.graphics.setCursor(Main.clickCursor);
                    Assets.getSound("sfx/menubuttonhover.wav").play(SettingsManager.getSfxVolume());
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    Gdx.graphics.setCursor(Main.defaultCursor);
                }
            }
        });

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.graphics.setCursor(Main.defaultCursor);
                Assets.getSound("sfx/clickbuttonUI.wav").play(SettingsManager.getSfxVolume());
            }
        });

        return button;
    }
}
