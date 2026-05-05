package io.github.guilhermegodoydev.greenhorizon.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import io.github.guilhermegodoydev.greenhorizon.Main;
import io.github.guilhermegodoydev.greenhorizon.core.managers.SettingsManager;
import io.github.guilhermegodoydev.greenhorizon.core.utils.AnimatedImage;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class MainMenuScreen extends BaseScreen {
    private final Stage stage;

    private Texture layerSky, layerRoad, layerBushes, layerAllBuildings;
    private float currentOffsetX = 0;
    private float currentOffsetY = 0;

    // Flag para controlar se o som de introdução já tocou
    private boolean introPlayed = false;

    public MainMenuScreen(final Main game) {
        super(game);
        this.stage = new Stage(viewport, game.batch);

        layerSky = Assets.getTexture("fundo/ceu.png");
        layerRoad = Assets.getTexture("fundo/chao_rua.png");
        layerBushes = Assets.getTexture("fundo/arbustos.png");
        layerAllBuildings = Assets.getTexture("fundo/predios.png");

        setupUI();
    }

    private void setupUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Texture tituloSpriteSheet = Assets.getTexture("titulo_animado.png");
        int FRAME_COLS = 8;
        TextureRegion[][] tmp = TextureRegion.split(tituloSpriteSheet,
            tituloSpriteSheet.getWidth() / FRAME_COLS, tituloSpriteSheet.getHeight());

        TextureRegion[] titleFrames = new TextureRegion[FRAME_COLS];
        for (int j = 0; j < FRAME_COLS; j++) titleFrames[j] = tmp[0][j];

        Animation<TextureRegion> titleAnimation = new Animation<>(0.35f, titleFrames);
        AnimatedImage animatedTitle = new AnimatedImage(titleAnimation);

        ImageButton btnPlay = new ImageButton(new TextureRegionDrawable(Assets.getTexture("botao_jogar.png")));
        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        ImageButton btnConfig = new ImageButton(new TextureRegionDrawable(Assets.getTexture("botao_configuracoes.png")));
        btnConfig.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsScreen(game, MainMenuScreen.this));
            }
        });

        animatedTitle.setScaling(Scaling.fit);
        table.add(animatedTitle).row();
        table.add(btnPlay).padBottom(5).row();
        table.add(btnConfig).row();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float relX = (Gdx.input.getX() / (float)Gdx.graphics.getWidth()) * 2 - 1;
        float relY = ((Gdx.graphics.getHeight() - Gdx.input.getY()) / (float)Gdx.graphics.getHeight()) * 2 - 1;

        currentOffsetX += (relX - currentOffsetX) * 0.05f;
        currentOffsetY += (relY - currentOffsetY) * 0.05f;

        float horizonY = viewport.getWorldHeight() * 0.45f;
        float skyHeight = viewport.getWorldHeight() - horizonY;

        game.batch.begin();
        game.batch.draw(layerSky, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        game.batch.draw(layerAllBuildings, 0, horizonY, viewport.getWorldWidth(), skyHeight);
        game.batch.draw(layerRoad, 0, 0, viewport.getWorldWidth(), horizonY);

        float margemSegurancaY = -15;
        float forcaMovimentoY = 8f;

        float arbustoPosX = Math.round(-50 + (currentOffsetX * 20f));
        float arbustoPosY = Math.round(margemSegurancaY + (currentOffsetY * forcaMovimentoY));

        game.batch.draw(layerBushes,
            arbustoPosX,
            arbustoPosY,
            viewport.getWorldWidth() + 100,
            horizonY + 25
        );
        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // Toca apenas na primeira vez que a tela é exibida nesta instância
        if (!introPlayed) {
            Assets.getSound("sfx/mainmenurootsgrowingsound.wav").play(SettingsManager.getSfxVolume());
            introPlayed = true;
        }

        game.fadeToMusic("sfx/mainmenumusicloop.mp3");
    }

    @Override
    public void dispose() { stage.dispose(); }
}
