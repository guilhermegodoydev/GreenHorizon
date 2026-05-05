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

public class GameOverScreen extends BaseScreen {
    private final Stage stage;

    public GameOverScreen(final Main game) {
        super(game);
        this.stage = new Stage(viewport, game.batch);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label.LabelStyle titleStyle = new Label.LabelStyle(new BitmapFont(), Color.RED);
        Label lblGameOver = new Label("FIM DE JOGO", titleStyle);
        lblGameOver.setFontScale(2.0f);

        TextButton.TextButtonStyle buttonStyle = createButtonStyle();

        TextButton btnRestart = new TextButton("TENTAR NOVAMENTE", buttonStyle);
        btnRestart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        TextButton btnMenu = new TextButton("MENU PRINCIPAL", buttonStyle);
        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        table.add(lblGameOver).padBottom(50).row();
        table.add(btnRestart).width(250).height(50).padBottom(10).row();
        table.add(btnMenu).width(250).height(50);
    }

    private TextButton.TextButtonStyle createButtonStyle() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.DARK_GRAY);
        pixmap.fill();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(new Texture(pixmap));
        style.font = new BitmapFont();
        pixmap.dispose();
        return style;
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
