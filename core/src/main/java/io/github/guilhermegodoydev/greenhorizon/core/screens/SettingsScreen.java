package io.github.guilhermegodoydev.greenhorizon.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import io.github.guilhermegodoydev.greenhorizon.Main;
import io.github.guilhermegodoydev.greenhorizon.core.managers.SettingsManager;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class SettingsScreen extends BaseScreen {
    private final Stage stage;
    private Label musicLabel;
    private Label sfxLabel;
    private final com.badlogic.gdx.Screen telaAnterior;

    public SettingsScreen(final Main game, com.badlogic.gdx.Screen telaAnterior) {
        super(game);
        this.telaAnterior = telaAnterior;

        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        ImageButton btnMusicMinus = criarBotaoComHover("botao_diminuir.png", "botao_diminuir.png");
        ImageButton btnMusicPlus = criarBotaoComHover("botao_aumentar.png", "botao_aumentar.png");

        musicLabel = new Label("Musica: " + Math.round(SettingsManager.getMusicVolume() * 100) + "%", labelStyle);

        btnMusicMinus.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                SettingsManager.setMusicVolume(SettingsManager.getMusicVolume() - 0.1f);
                atualizarTextos();
                game.syncConfigVolume();
            }
        });

        btnMusicPlus.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                SettingsManager.setMusicVolume(SettingsManager.getMusicVolume() + 0.1f);
                atualizarTextos();
                game.syncConfigVolume();
            }
        });

        ImageButton btnSfxMinus = criarBotaoComHover("botao_diminuir.png", "botao_diminuir.png");
        ImageButton btnSfxPlus = criarBotaoComHover("botao_aumentar.png", "botao_aumentar.png");

        sfxLabel = new Label("Efeitos: " + Math.round(SettingsManager.getSfxVolume() * 100) + "%", labelStyle);

        btnSfxMinus.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                SettingsManager.setSfxVolume(SettingsManager.getSfxVolume() - 0.1f);
                atualizarTextos();
            }
        });

        btnSfxPlus.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                SettingsManager.setSfxVolume(SettingsManager.getSfxVolume() + 0.1f);
                atualizarTextos();
            }
        });

        ImageButton btnBack = criarBotaoComHover("botao_voltar.png", "botao_voltar_hover.png");
        btnBack.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.graphics.setCursor(Main.cursorPadrao); // Segurança extra
                game.setScreen(telaAnterior);
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

    // MÉTODO UTILITÁRIO PARA CRIAR BOTÕES COM HOVER E CLIQUE
    private ImageButton criarBotaoComHover(String imgNormal, String imgHover) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(Assets.getTexture(imgNormal));
        style.over = new TextureRegionDrawable(Assets.getTexture(imgHover));

        final ImageButton btn = new ImageButton(style);

        btn.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1 && btn.isTouchable()) {
                    Gdx.graphics.setCursor(Main.cursorClick); // Troca para a mão
                    Assets.getSound("sfx/menubuttonhover.wav").play(SettingsManager.getSfxVolume());
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    Gdx.graphics.setCursor(Main.cursorPadrao); // Volta ao normal
                }
            }
        });

        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Assets.getSound("sfx/clickbuttonUI.wav").play(SettingsManager.getSfxVolume());
            }
        });

        return btn;
    }

    private void atualizarTextos() {
        musicLabel.setText("Musica: " + Math.round(SettingsManager.getMusicVolume() * 100) + "%");
        sfxLabel.setText("Efeitos: " + Math.round(SettingsManager.getSfxVolume() * 100) + "%");
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
