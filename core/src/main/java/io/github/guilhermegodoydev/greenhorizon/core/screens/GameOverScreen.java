package io.github.guilhermegodoydev.greenhorizon.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import io.github.guilhermegodoydev.greenhorizon.Main;
import io.github.guilhermegodoydev.greenhorizon.core.utils.AnimatedImage;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;
import io.github.guilhermegodoydev.greenhorizon.core.utils.ButtonFactory;

public class GameOverScreen extends BaseScreen {
    private final Stage stage;

    public GameOverScreen(final Main game) {
        super(game);
        this.stage = new Stage(viewport, game.batch);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Texture animTexture = Assets.getTexture("animacao_fim_game.png");
        int frameCols = 6;

        TextureRegion[][] tmp = TextureRegion.split(animTexture, animTexture.getWidth() / frameCols, animTexture.getHeight());

        TextureRegion[] frames = new TextureRegion[frameCols];
        System.arraycopy(tmp[0], 0, frames, 0, frameCols);

        Animation<TextureRegion> gameOverAnimation = new Animation<>(0.15f, frames);
        gameOverAnimation.setPlayMode(Animation.PlayMode.LOOP);

        AnimatedImage animatedGameOver = new AnimatedImage(gameOverAnimation);
        animatedGameOver.setScaling(Scaling.fit);

        ImageButton btnRestart = ButtonFactory.createHoverButton("botao_novamente.png", "botao_novamente_hover.png");
        btnRestart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        ImageButton btnMenu = ButtonFactory.createHoverButton("botao_menuprincipal.png", "botao_menuprincipal_hover.png");
        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        table.add(animatedGameOver).padBottom(50).row();
        table.add(btnRestart).padBottom(10).row();
        table.add(btnMenu);
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
