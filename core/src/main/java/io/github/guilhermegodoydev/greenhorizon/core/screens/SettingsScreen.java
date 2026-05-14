package io.github.guilhermegodoydev.greenhorizon.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import io.github.guilhermegodoydev.greenhorizon.Main;
import io.github.guilhermegodoydev.greenhorizon.core.managers.SettingsManager;
import io.github.guilhermegodoydev.greenhorizon.core.utils.ButtonFactory;

public class SettingsScreen extends BaseScreen {
    private final Stage stage;
    private Label musicLabel;
    private Label sfxLabel;
    private final com.badlogic.gdx.Screen previousScreen;

    public SettingsScreen(final Main game, com.badlogic.gdx.Screen previousScreen) {
        super(game);
        this.previousScreen = previousScreen;

        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        ImageButton btnMusicMinus = ButtonFactory.createHoverButton("botao_diminuir.png", "botao_diminuir_hover.png");
        ImageButton btnMusicPlus = ButtonFactory.createHoverButton("botao_aumentar.png", "botao_aumentar_hover.png");

        musicLabel = new Label("Music: " + Math.round(SettingsManager.getMusicVolume() * 100) + "%", labelStyle);

        btnMusicMinus.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                SettingsManager.setMusicVolume(SettingsManager.getMusicVolume() - 0.1f);
                updateTexts();
                game.syncConfigVolume();
            }
        });

        btnMusicPlus.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                SettingsManager.setMusicVolume(SettingsManager.getMusicVolume() + 0.1f);
                updateTexts();
                game.syncConfigVolume();
            }
        });

        ImageButton btnSfxMinus = ButtonFactory.createHoverButton("botao_diminuir.png", "botao_diminuir_hover.png");
        ImageButton btnSfxPlus = ButtonFactory.createHoverButton("botao_aumentar.png", "botao_aumentar_hover.png");

        sfxLabel = new Label("SFX: " + Math.round(SettingsManager.getSfxVolume() * 100) + "%", labelStyle);

        btnSfxMinus.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                SettingsManager.setSfxVolume(SettingsManager.getSfxVolume() - 0.1f);
                updateTexts();
            }
        });

        btnSfxPlus.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                SettingsManager.setSfxVolume(SettingsManager.getSfxVolume() + 0.1f);
                updateTexts();
            }
        });

        ImageButton btnBack = ButtonFactory.createHoverButton("botao_voltar.png", "botao_voltar_hover.png");
        btnBack.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.graphics.setCursor(Main.defaultCursor);
                game.setScreen(previousScreen);
            }
        });

        table.add(btnMusicMinus).pad(10);
        table.add(musicLabel).center();
        table.add(btnMusicPlus).pad(10).row();

        table.add(btnSfxMinus).pad(10);
        table.add(sfxLabel).center();
        table.add(btnSfxPlus).pad(10).row();

        table.add(btnBack).colspan(3).padTop(30);
    }

    private void updateTexts() {
        musicLabel.setText("Music: " + Math.round(SettingsManager.getMusicVolume() * 100) + "%");
        sfxLabel.setText("SFX: " + Math.round(SettingsManager.getSfxVolume() * 100) + "%");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
