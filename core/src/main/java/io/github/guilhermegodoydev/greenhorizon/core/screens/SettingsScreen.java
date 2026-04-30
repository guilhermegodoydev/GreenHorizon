package io.github.guilhermegodoydev.greenhorizon.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.guilhermegodoydev.greenhorizon.Main;
import io.github.guilhermegodoydev.greenhorizon.core.managers.SettingsManager;

public class SettingsScreen extends BaseScreen {
    private final Stage stage;
    private Label musicLabel;
    private Label sfxLabel;
    private final com.badlogic.gdx.Screen telaAnterior; // Variável final

    public SettingsScreen(final Main game, com.badlogic.gdx.Screen telaAnterior) {
        super(game);

        // --- AQUI ESTAVA O ERRO! VOCÊ PRECISA ATRIBUIR O VALOR ---
        this.telaAnterior = telaAnterior;

        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton.TextButtonStyle btnStyle = createProgrammerArtStyle();
        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        // Linha da MÚSICA
        TextButton btnMusicMinus = new TextButton("-", btnStyle);
        TextButton btnMusicPlus = new TextButton("+", btnStyle);
        musicLabel = new Label("Musica: " + (int) (SettingsManager.getMusicVolume() * 100) + "%", labelStyle);

        btnMusicMinus.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                SettingsManager.setMusicVolume(SettingsManager.getMusicVolume() - 0.1f);
                atualizarTextos();
            }
        });
        btnMusicPlus.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                SettingsManager.setMusicVolume(SettingsManager.getMusicVolume() + 0.1f);
                atualizarTextos();
            }
        });

        // Linha do SFX
        TextButton btnSfxMinus = new TextButton("-", btnStyle);
        TextButton btnSfxPlus = new TextButton("+", btnStyle);
        sfxLabel = new Label("Efeitos: " + (int) (SettingsManager.getSfxVolume() * 100) + "%", labelStyle);

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

        // Botão Voltar
        TextButton btnBack = new TextButton("VOLTAR", btnStyle);
        btnBack.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                // Agora o Java sabe quem é a telaAnterior!
                game.setScreen(telaAnterior);
            }
        });

        // Montando o Layout
        table.add(btnMusicMinus).size(50).pad(10);
        table.add(musicLabel).width(150).center();
        table.add(btnMusicPlus).size(50).pad(10).row();

        table.add(btnSfxMinus).size(50).pad(10);
        table.add(sfxLabel).width(150).center();
        table.add(btnSfxPlus).size(50).pad(10).row();

        table.add(btnBack).colspan(3).width(200).height(50).padTop(30);
    }

    private void atualizarTextos() {
        musicLabel.setText("Musica: " + (int) (SettingsManager.getMusicVolume() * 100) + "%");
        sfxLabel.setText("Efeitos: " + (int) (SettingsManager.getSfxVolume() * 100) + "%");
    }

    private TextButton.TextButtonStyle createProgrammerArtStyle() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.DARK_GRAY);
        pixmap.fill();
        TextureRegionDrawable drawableBg = new TextureRegionDrawable(new Texture(pixmap));
        pixmap.dispose();

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = drawableBg;
        style.font = new BitmapFont();
        return style;
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
