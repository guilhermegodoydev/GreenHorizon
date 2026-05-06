package io.github.guilhermegodoydev.greenhorizon.core.managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable; // Importante para desligar o clique
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.guilhermegodoydev.greenhorizon.core.entities.towers.TowerBase;
import io.github.guilhermegodoydev.greenhorizon.core.events.GameEvent;
import io.github.guilhermegodoydev.greenhorizon.core.events.GameEventListener;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;
import io.github.guilhermegodoydev.greenhorizon.core.screens.GameScreen;
import io.github.guilhermegodoydev.greenhorizon.core.screens.MainMenuScreen;
import io.github.guilhermegodoydev.greenhorizon.core.screens.SettingsScreen;
import io.github.guilhermegodoydev.greenhorizon.core.ui.menu.ConstructionMenu;
import io.github.guilhermegodoydev.greenhorizon.core.ui.HealthDisplay;
import io.github.guilhermegodoydev.greenhorizon.core.ui.menu.TowerActionMenu;
import io.github.guilhermegodoydev.greenhorizon.core.ui.TowerSelectionListener;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Utils;
import io.github.guilhermegodoydev.greenhorizon.core.itens.LifeManager;
import io.github.guilhermegodoydev.greenhorizon.core.ui.CoinsDisplay;
import io.github.guilhermegodoydev.greenhorizon.core.itens.CoinsManager;

public class ManagerUI implements TowerSelectionListener, Disposable {
    private final Stage stage;
    private final HealthDisplay healthDisplay;
    private final CoinsDisplay coinsDisplay;
    private final ConstructionMenu constructionMenu;
    private final TowerActionMenu actionMenu;
    private final GameEventListener eventListener;
    private WaveManager waveManager;
    private TextButton btnStartWave;
    private Texture blackBackground;

    private final BitmapFont uiFont;

    private Table pauseTable;
    private GameScreen gameScreen;
    private TowerSlot slotAlvo;
    private TowerBase torreSelecionada;

    // Transformado em atributo da classe para podermos manipular depois
    private ImageButton btnPause;

    public ManagerUI(Viewport viewport, SpriteBatch batch, LifeManager lifeManager, CoinsManager coinsManager, GameEventListener listener, GameScreen gameScreen, WaveManager waveManager) {
        this.waveManager = waveManager;
        this.stage = new Stage(viewport, batch);
        this.eventListener = listener;
        this.gameScreen = gameScreen;

        this.uiFont = new BitmapFont();
        this.uiFont.getData().setScale(0.7f);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0, 0, 0, 0.6f));
        pixmap.fill();
        this.blackBackground = new Texture(pixmap);
        pixmap.dispose();

        float topo = viewport.getWorldHeight();

        this.healthDisplay = new HealthDisplay(lifeManager, 20, topo - 40);
        this.coinsDisplay = new CoinsDisplay(coinsManager, 20, topo - 75);

        this.actionMenu = new TowerActionMenu(this);
        this.constructionMenu = new ConstructionMenu(this);

        stage.addActor(healthDisplay);
        stage.addActor(coinsDisplay);
        stage.addActor(constructionMenu);
        stage.addActor(actionMenu);

        criarBotaoEngrenagem();
        criarMenuPausa();
        criarBotaoStartWave();
    }

    private ImageButton criarBotaoComHover(String imgNormal, String imgHover) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(Assets.getTexture(imgNormal));
        style.over = new TextureRegionDrawable(Assets.getTexture(imgHover));
        return new ImageButton(style);
    }

    private void criarBotaoStartWave() {
        TextButton.TextButtonStyle style = createProgrammerArtStyle(Color.ORANGE);
        btnStartWave = new TextButton("INICIAR WAVE", style);

        btnStartWave.setSize(100, 30);
        btnStartWave.setPosition(20, 20);

        btnStartWave.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                waveManager.startNextWave();
            }
        });

        stage.addActor(btnStartWave);
    }

    private void criarBotaoEngrenagem() {
        // Agora inicializamos o atributo da classe em vez de uma variável local
        btnPause = criarBotaoComHover("botao_pause.png", "botao_pause_hover.png");
        btnPause.setPosition(stage.getViewport().getWorldWidth() - 50, stage.getViewport().getWorldHeight() - 40);

        btnPause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.togglePause();
            }
        });
        stage.addActor(btnPause);
    }

    private void criarMenuPausa() {
        pauseTable = new Table();
        pauseTable.setFillParent(true);
        pauseTable.setVisible(false);

        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(new Color(0, 0, 0, 0.7f));
        bgPixmap.fill();
        pauseTable.setBackground(new TextureRegionDrawable(new Texture(bgPixmap)));
        bgPixmap.dispose();

        ImageButton btnContinuar = criarBotaoComHover("botao_continuar.png", "botao_continuar_hover.png");
        btnContinuar.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.togglePause();
            }
        });

        ImageButton btnConfig = criarBotaoComHover("botao_configuracoes.png", "botao_configuracoes_hover.png");
        btnConfig.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.getGame().setScreen(new SettingsScreen(gameScreen.getGame(), gameScreen));
            }
        });

        ImageButton btnSair = criarBotaoComHover("botao_sair.png", "botao_sair_hover.png");
        btnSair.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.getGame().setScreen(new MainMenuScreen(gameScreen.getGame()));
            }
        });

        pauseTable.add(btnContinuar).pad(5).row();
        pauseTable.add(btnConfig).pad(5).row();
        pauseTable.add(btnSair).pad(5);

        stage.addActor(pauseTable);
    }

    public void setPauseVisible(boolean visivel) {
        pauseTable.setVisible(visivel);
        if (visivel) {
            pauseTable.toFront();
            // Desliga a sensibilidade do botão de pause (tira hover e clique)
            btnPause.setTouchable(Touchable.disabled);
        } else {
            // Liga a sensibilidade de volta quando o jogo continua
            btnPause.setTouchable(Touchable.enabled);
        }
    }

    private TextButton.TextButtonStyle createProgrammerArtStyle(Color cor) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(cor);
        pixmap.fill();

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(new Texture(pixmap));
        style.font = uiFont;

        pixmap.dispose();
        return style;
    }

    @Override
    public void onTowerSelected(String tipo) {
        if (tipo.equals("Vender")) {
            eventListener.onEvent(new GameEvent(GameEvent.Type.SELL_TOWER, torreSelecionada));
        } else if (tipo.equals("Upgrade")) {
            eventListener.onEvent(new GameEvent(GameEvent.Type.UPGRADE_TOWER, torreSelecionada));
        } else {
            Object[] dadosConstrucao = { slotAlvo, tipo };
            eventListener.onEvent(new GameEvent(GameEvent.Type.BUILD_TOWER, dadosConstrucao));
        }
        fecharTodosMenus();
    }

    public void abrirMenuAcao(TowerBase torre) {
        this.torreSelecionada = torre;
        fecharTodosMenus();
        Utils.setCenteredPosition(actionMenu, torre.getPosition().x, torre.getPosition().y + 8);
        actionMenu.setVisible(true);
    }

    public void abrirMenu(TowerSlot slot) {
        this.slotAlvo = slot;
        fecharTodosMenus();
        Utils.setCenteredPosition(constructionMenu, slot.getCenterX(), slot.getBounds().y + slot.getBounds().height);
        constructionMenu.setVisible(true);
    }

    public void fecharTodosMenus() {
        constructionMenu.setVisible(false);
        actionMenu.setVisible(false);
    }

    public void render(float delta) {
        boolean pausado = pauseTable.isVisible();

        if (btnStartWave != null) {
            btnStartWave.setVisible(!waveManager.isWaveActive() && !pausado);
        }

        stage.act(delta);
        stage.draw();

        if (!waveManager.isWaveActive() && !pausado) {
            stage.getBatch().begin();
            String texto = "PROXIMA WAVE EM: " + (int)waveManager.getWaveTimer() + "s";

            float x = stage.getViewport().getWorldWidth() / 2 - 80;
            float y = stage.getViewport().getWorldHeight() - 20;

            stage.getBatch().draw(blackBackground, x - 5, y - 15, 170, 25);
            uiFont.draw(stage.getBatch(), texto, x, y);

            stage.getBatch().end();
        }
    }

    @Override
    public void dispose() {
        healthDisplay.dispose();
        coinsDisplay.dispose();
        uiFont.dispose();
        stage.dispose();
        blackBackground.dispose();
    }

    public boolean isVisivel() {
        return constructionMenu.isVisible() || actionMenu.isVisible();
    }

    public Stage getStage() { return stage; }
}
