package io.github.guilhermegodoydev.greenhorizon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.guilhermegodoydev.greenhorizon.core.input.InputHandler;
import io.github.guilhermegodoydev.greenhorizon.core.map.MapHandler;
import io.github.guilhermegodoydev.greenhorizon.core.managers.ManagerUI;
import io.github.guilhermegodoydev.greenhorizon.itens.LifeManager;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private ManagerUI ManagerUI;
    private MapHandler mapHandler;

    // PARTE DO MAPA
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private FitViewport viewport;

    // SISTEMA DE VIDA E HUD (HUD = Interface)
    private SpriteBatch batch;
    private BitmapFont fonte;
    private LifeManager lifeManager;

    // Imagens e Animação
    private Texture imgBarra;
    private Texture spriteSheetAnimacao;
    private TextureRegion[] framesCoracao = new TextureRegion[4]; // 4 corações na tira

    // Controle da Animação
    private float tempoAnimacao = 0;
    private boolean estaSofrendoDano = false;

    @Override
    public void create() {
        batch = new SpriteBatch();
        ManagerUI = new ManagerUI();
        mapHandler = new MapHandler("mapa.tmx");
        renderer = new OrthogonalTiledMapRenderer(mapHandler.getTiledMap());
        // --- Setup do Mapa ---
        mapa = new TmxMapLoader().load("mapa.tmx");
        renderer = new OrthogonalTiledMapRenderer(mapa);
        camera = new OrthographicCamera();
        viewport = new FitViewport(320, 240, camera);

        InputHandler inputHandler = new InputHandler(viewport, mapHandler, ManagerUI);
        Gdx.input.setInputProcessor(inputHandler);

        // Setup do Sistema de Vida
        batch = new SpriteBatch();
        fonte = new BitmapFont();
        lifeManager = new LifeManager(10);

        // Carregar imagens
        imgBarra = new Texture("apenas_barra.png");
        spriteSheetAnimacao = new Texture("animacao_perdendo_vida.png");

        // Tirar o borrão das imagens (Pixel Art nítida)
        imgBarra.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        spriteSheetAnimacao.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        // Cortar a tira vertical em 4 pedaços iguais
        int larguraFrame = spriteSheetAnimacao.getWidth() / 4;
        int alturaFrame = spriteSheetAnimacao.getHeight();
        for (int i = 0; i < 4; i++) {
            framesCoracao[i] = new TextureRegion(spriteSheetAnimacao,i * larguraFrame, 0, larguraFrame, alturaFrame);
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);

        renderer.setView(camera);
        renderer.render();

        // Lógica e Desenho da Vida
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // 1. Desenha a barra de fundo
        batch.draw(imgBarra, 250, 210, 60, 25);

        // 2. Desenha o coração (Animado ou Parado)
        int frameAtual = 0;
        if (estaSofrendoDano) {
            tempoAnimacao += Gdx.graphics.getDeltaTime();
            frameAtual = (int)(tempoAnimacao / 0.1f); // Troca frame a cada 0.1s

            if (frameAtual > 3) { // Acabou os 4 frames
                estaSofrendoDano = false;
                tempoAnimacao = 0;
                frameAtual = 0;
            }
        }
        batch.draw(framesCoracao[frameAtual], 255, 216, 16, 16);

        // 3. Desenha o contador de vidas
        fonte.draw(batch, "X " + lifeManager.getVidasAtuais(), 275, 228);

        batch.end();

        // --- Teste de Dano ---
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            lifeManager.perderVida(1);
            estaSofrendoDano = true;
            tempoAnimacao = 0;
        }
    }

        camera.update();
        viewport.apply();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        ManagerUI.render(batch);
        batch.end();
    @Override
    public void dispose() {
        // Limpar tudo
        mapa.dispose();
        renderer.dispose();
        batch.dispose();
        imgBarra.dispose();
        spriteSheetAnimacao.dispose();
        fonte.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        ManagerUI.dispose();
        batch.dispose();
        mapHandler.dispose();
        renderer.dispose();
    }
}
