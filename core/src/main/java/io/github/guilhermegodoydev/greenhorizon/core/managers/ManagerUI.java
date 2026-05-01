package io.github.guilhermegodoydev.greenhorizon.core.managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

    // FONTE GLOBAL PARA CONTROLE DE TAMANHO
    private final BitmapFont uiFont;

    private Table pauseTable;
    private GameScreen gameScreen;
    private TowerSlot slotAlvo;
    private TowerBase torreSelecionada;

    public ManagerUI(Viewport viewport, SpriteBatch batch, LifeManager lifeManager, CoinsManager coinsManager, GameEventListener listener, GameScreen gameScreen, WaveManager waveManager) {
        this.waveManager = waveManager;
        this.stage = new Stage(viewport, batch);
        this.eventListener = listener;
        this.gameScreen = gameScreen;

        // INICIALIZA A FONTE E DEFINE A ESCALA (0.7f = 70% do tamanho original)
        this.uiFont = new BitmapFont();
        this.uiFont.getData().setScale(0.7f);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0, 0, 0, 0.6f)); // Preto com 60% de transparência
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

    private void criarBotaoStartWave() {
        TextButton.TextButtonStyle style = createProgrammerArtStyle(Color.ORANGE);
        btnStartWave = new TextButton("INICIAR WAVE", style);

        // Aumentei um pouco o tamanho do botão para caber o texto com a fonte menor
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
        TextButton.TextButtonStyle btnStyle = createProgrammerArtStyle(Color.GRAY);
        TextButton btnPause = new TextButton("||", btnStyle);

        btnPause.setPosition(stage.getViewport().getWorldWidth() - 50, stage.getViewport().getWorldHeight() - 40);
        btnPause.setSize(30, 30);

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

        TextButton.TextButtonStyle btnStyle = createProgrammerArtStyle(Color.DARK_GRAY);

        TextButton btnContinuar = new TextButton("CONTINUAR", btnStyle);
        btnContinuar.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.togglePause();
            }
        });

        TextButton btnConfig = new TextButton("CONFIGURACOES", btnStyle);
        btnConfig.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.getGame().setScreen(new SettingsScreen(gameScreen.getGame(), gameScreen));
            }
        });

        TextButton btnSair = new TextButton("SAIR PARA MENU", btnStyle);
        btnSair.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.getGame().setScreen(new MainMenuScreen(gameScreen.getGame()));
            }
        });

        pauseTable.add(btnContinuar).width(200).height(50).pad(10).row();
        pauseTable.add(btnConfig).width(200).height(50).pad(10).row();
        pauseTable.add(btnSair).width(200).height(50).pad(10);

        stage.addActor(pauseTable);
    }

    public void setPauseVisible(boolean visivel) {
        pauseTable.setVisible(visivel);
        if (visivel) {
            // Move a tabela de pausa para ser o último elemento do desenho (ficar no topo)
            pauseTable.toFront();
        }
    }

    private TextButton.TextButtonStyle createProgrammerArtStyle(Color cor) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(cor);
        pixmap.fill();

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(new Texture(pixmap));

        // USA A FONTE COM ESCALA REDUZIDA NO ESTILO DO BOTÃO
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

            // USA A FONTE GLOBAL JÁ ESCALADA PARA O DESENHO DO TIMER
            float x = stage.getViewport().getWorldWidth() / 2 - 80;
            float y = stage.getViewport().getWorldHeight() - 20;

            // DESENHA A MOLDURA (Fundo do texto)
            // O +100 e +20 são para cobrir a área do texto, ajuste se necessário
            stage.getBatch().draw(blackBackground, x - 5, y - 15, 170, 25);

            // DESENHA O TEXTO POR CIMA
            uiFont.draw(stage.getBatch(), texto, x, y);

            stage.getBatch().end();
        }
    }

    @Override
    public void dispose() {
        healthDisplay.dispose();
        coinsDisplay.dispose();
        uiFont.dispose(); // IMPORTANTE LIMPAR A FONTE
        stage.dispose();
        blackBackground.dispose();
    }

    public boolean isVisivel() {
        return constructionMenu.isVisible() || actionMenu.isVisible();
    }

    public Stage getStage() { return stage; }
}
