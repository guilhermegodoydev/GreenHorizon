package io.github.guilhermegodoydev.greenhorizon.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import io.github.guilhermegodoydev.greenhorizon.Main;
import io.github.guilhermegodoydev.greenhorizon.core.managers.SettingsManager;
import io.github.guilhermegodoydev.greenhorizon.core.utils.AnimatedImage;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class GameOverScreen extends BaseScreen {
    private final Stage stage;

    public GameOverScreen(final Main game) {
        super(game);
        this.stage = new Stage(viewport, game.batch);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // --- CARREGAMENTO DA ANIMAÇÃO DE FIM DE JOGO ---
        Texture animTexture = Assets.getTexture("animacao_fim_game.png");
        int FRAME_COLS = 6;

        // Fatia a imagem (1 linha, 6 colunas)
        TextureRegion[][] tmp = TextureRegion.split(animTexture, animTexture.getWidth() / FRAME_COLS, animTexture.getHeight());

        // Converte a matriz em um array simples para a Animation
        TextureRegion[] frames = new TextureRegion[FRAME_COLS];
        for (int i = 0; i < FRAME_COLS; i++) {
            frames[i] = tmp[0][i];
        }

        // Cria a animação com 0.15 segundos por frame e configura para repetir infinitamente
        Animation<TextureRegion> gameOverAnimation = new Animation<>(0.15f, frames);
        gameOverAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // Instancia o nosso envelope de UI para a animação
        AnimatedImage animatedGameOver = new AnimatedImage(gameOverAnimation);
        animatedGameOver.setScaling(Scaling.fit);

        // --- BOTÕES ---
        ImageButton btnRestart = criarBotaoComHover("botao_novamente.png", "botao_novamente_hover.png");
        btnRestart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        ImageButton btnMenu = criarBotaoComHover("botao_menuprincipal.png", "botao_menuprincipal_hover.png");
        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        // Adiciona a animação na tabela (no exato lugar onde ficava o Label antigo)
        table.add(animatedGameOver).padBottom(50).row();
        table.add(btnRestart).padBottom(10).row();
        table.add(btnMenu);
    }

    // MÉTODO UTILITÁRIO PARA CRIAR BOTÕES COM HOVER E CLIQUE
    private ImageButton criarBotaoComHover(String imgNormal, String imgHover) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(Assets.getTexture(imgNormal));
        style.over = new TextureRegionDrawable(Assets.getTexture(imgHover));

        final ImageButton btn = new ImageButton(style);

        // Listener do Hover
        btn.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1 && btn.isTouchable()) {
                    Assets.getSound("sfx/menubuttonhover.wav").play(SettingsManager.getSfxVolume());
                }
            }
        });

        // Listener do Clique
        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Assets.getSound("sfx/clickbuttonUI.wav").play(SettingsManager.getSfxVolume());
            }
        });

        return btn;
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
