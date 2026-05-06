package io.github.guilhermegodoydev.greenhorizon.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.guilhermegodoydev.greenhorizon.Main;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

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

        // BOTÃO NOVAMENTE COM HOVER
        ImageButton btnRestart = criarBotaoComHover("botao_novamente.png", "botao_novamente_hover.png");
        btnRestart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        // BOTÃO MENU PRINCIPAL COM HOVER
        ImageButton btnMenu = criarBotaoComHover("botao_menuprincipal.png", "botao_menuprincipal_hover.png");
        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        table.add(lblGameOver).padBottom(50).row();

        // Removemos o width() e height() manuais porque a imagem original da sua UI já dá o tamanho perfeito
        table.add(btnRestart).padBottom(10).row();
        table.add(btnMenu);
    }

    // MÉTODO UTILITÁRIO PARA CRIAR BOTÕES COM HOVER
    private ImageButton criarBotaoComHover(String imgNormal, String imgHover) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(Assets.getTexture(imgNormal));
        style.over = new TextureRegionDrawable(Assets.getTexture(imgHover));
        return new ImageButton(style);
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
