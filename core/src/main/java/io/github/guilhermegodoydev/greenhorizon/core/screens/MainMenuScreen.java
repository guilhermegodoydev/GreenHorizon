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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import io.github.guilhermegodoydev.greenhorizon.Main;
import io.github.guilhermegodoydev.greenhorizon.core.utils.AnimatedImage;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class MainMenuScreen extends BaseScreen {
    private final Stage stage;

    private Texture layerSky, layerRoad, layerBushes, layerAllBuildings;
    private float currentOffsetX = 0;
    private float currentOffsetY = 0;

    public MainMenuScreen(final Main game) {
        super(game);
        this.stage = new Stage(viewport, game.batch);

        // Carregamento
        layerSky = Assets.getTexture("fundo/ceu.png");
        layerRoad = Assets.getTexture("fundo/chao_rua.png");
        layerBushes = Assets.getTexture("fundo/arbustos.png");
        layerAllBuildings = Assets.getTexture("fundo/predios.png");

        // UI (Mantive sua lógica original de botões)
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

        animatedTitle.setScaling(Scaling.fit);
        table.add(animatedTitle).row();
        table.add(btnPlay).pad(5).row();
    }

    @Override
    public void render(float delta) {
        // Limpa a tela
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 1. Captura a posição do mouse e normaliza (-1 a 1)
        float relX = (Gdx.input.getX() / (float)Gdx.graphics.getWidth()) * 2 - 1;
        float relY = ((Gdx.graphics.getHeight() - Gdx.input.getY()) / (float)Gdx.graphics.getHeight()) * 2 - 1;

        // 2. Suavização (Lerp) para o movimento ser fluido
        currentOffsetX += (relX - currentOffsetX) * 0.05f;
        currentOffsetY += (relY - currentOffsetY) * 0.05f;

        // 3. Define a linha do horizonte (onde a rua termina e os prédios começam)
        float horizonY = viewport.getWorldHeight() * 0.45f;
        float skyHeight = viewport.getWorldHeight() - horizonY;



        game.batch.begin();

        // --- CAMADA 1: CÉU (Estático) ---
        game.batch.draw(layerSky, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // --- CAMADA 2: PRÉDIOS UNIFICADOS (Estático) ---
        // Como agora é uma imagem só, ela fica travada no horizonte
        game.batch.draw(layerAllBuildings, 0, horizonY, viewport.getWorldWidth(), skyHeight);

        // --- CAMADA 3: ESTRADA (Estática) ---
        // Desenhada depois dos prédios para dar o acabamento na base
        game.batch.draw(layerRoad, 0, 0, viewport.getWorldWidth(), horizonY);

        // --- CAMADA 4: ARBUSTOS (Com "sangria" para baixo) ---
        // Em vez de começar no Y = 0, começamos um pouco abaixo (ex: -15)
        // Assim, quando o currentOffsetY empurrar para cima, ainda teremos desenho ali.

        float margemSegurancaY = -15; // O quanto a imagem fica "enterrada" no chão
        float forcaMovimentoY = 8f;   // O quanto ela sobe com o mouse

        float arbustoPosX = Math.round(-50 + (currentOffsetX * 20f));
        float arbustoPosY = Math.round(margemSegurancaY + (currentOffsetY * forcaMovimentoY));

        game.batch.draw(layerBushes,
            arbustoPosX,
            arbustoPosY,
            viewport.getWorldWidth() + 100,
            horizonY + 25 // Aumentamos um pouco a altura total para compensar o que foi enterrado
        );

        game.batch.end();

        // Desenha os botões e interface por cima de tudo
        stage.act(delta);
        stage.draw();
    }

    // Função drawParallax atualizada para aceitar o Y variável dos arbustos
    private void drawMouseParallax(Texture texture, float xOffset, float yOffset, float height) {
        game.batch.draw(texture,
            Math.round(-50 + xOffset),
            Math.round(yOffset), // Aqui o yOffset pode ser o horizonY fixo ou o cálculo com o mouse
            viewport.getWorldWidth() + 100,
            height
        );
    }

    @Override
    public void show() { Gdx.input.setInputProcessor(stage); }

    @Override
    public void dispose() { stage.dispose(); }
}
