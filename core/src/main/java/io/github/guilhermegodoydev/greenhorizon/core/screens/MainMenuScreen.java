package io.github.guilhermegodoydev.greenhorizon.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.guilhermegodoydev.greenhorizon.Main;

public class MainMenuScreen extends BaseScreen {
    private final Stage stage;

    public MainMenuScreen(final Main game) {
        super(game);
        this.stage = new Stage(viewport, game.batch);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Estilo de botão usando Programmer Art
        TextButton.TextButtonStyle buttonStyle = createProgrammerArtStyle();

        // Botão JOGAR
        TextButton btnPlay = new TextButton("JOGAR", buttonStyle);
        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        // Botão CONFIGURAÇÕES (Passando 'this' como tela anterior)
        TextButton btnSettings = new TextButton("CONFIGURACOES", buttonStyle);
        btnSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsScreen(game, MainMenuScreen.this));
            }
        });

        table.add(btnPlay).width(200).height(50).pad(10).row();
        table.add(btnSettings).width(200).height(50).pad(10);
    }

    // --- A SOLUÇÃO ESTÁ AQUI ---
    @Override
    public void show() {
        super.show();
        // Avisa ao LibGDX que o mouse agora deve obedecer ao menu novamente
        Gdx.input.setInputProcessor(stage);
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
        Gdx.gl.glClearColor(0.1f, 0.3f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
