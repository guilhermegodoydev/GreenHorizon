package io.github.guilhermegodoydev.greenhorizon.core.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.guilhermegodoydev.greenhorizon.Main;
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
    private ImageButton btnStartWave;
    private Texture blackBackground;

    private final BitmapFont uiFont;

    private Table pauseTable;
    private GameScreen gameScreen;
    private TowerSlot slotAlvo;
    private TowerBase torreSelecionada;

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

        // REPOSICIONAMENTO E ESCALA DO HUD
        // X = 15 para afastar da borda.
        // Y ajustado considerando o tamanho real e a origem da escala.
        this.healthDisplay = new HealthDisplay(lifeManager, 15, topo - 30);
        this.healthDisplay.setScale(0.7f);

        this.coinsDisplay = new CoinsDisplay(coinsManager, 15, topo - 55);
        this.coinsDisplay.setScale(0.7f);

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
                // pointer == -1 garante que só mude no PC (mouse), ignorando toques no celular
                if (pointer == -1 && btn.isTouchable()) {
                    Gdx.graphics.setCursor(Main.cursorClick);
                    Assets.getSound("sfx/menubuttonhover.wav").play(SettingsManager.getSfxVolume());
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    Gdx.graphics.setCursor(Main.cursorPadrao);
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

    private void criarBotaoStartWave() {
        btnStartWave = criarBotaoComHover("botao_start.png", "botao_start_hover.png");

        // NOVO POSICIONAMENTO: Canto inferior direito.
        // Pega a largura da tela, subtrai a largura do botão e afasta um pouco (15px) da borda.
        float btnWidth = btnStartWave.getWidth() > 0 ? btnStartWave.getWidth() : 100; // fallback caso não tenha width ainda
        btnStartWave.setPosition(stage.getViewport().getWorldWidth() - btnWidth - 15, 15);

        btnStartWave.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                waveManager.startNextWave();
            }
        });

        stage.addActor(btnStartWave);
    }

    private void criarBotaoEngrenagem() {
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
            btnPause.setTouchable(Touchable.disabled);
        } else {
            btnPause.setTouchable(Touchable.enabled);
        }
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

            // TIMER EM FORMATO DE PÍLULA
            int timerValue = (int)waveManager.getWaveTimer();
            String texto = "WAVE EM: " + timerValue + "s";

            // Define a cor de tensão se faltarem menos de 5 segundos
            if (timerValue <= 5) {
                uiFont.setColor(Color.RED);
            } else {
                uiFont.setColor(Color.WHITE);
            }

            // Largura e Altura fixas para a "pílula"
            float bgWidth = 140;
            float bgHeight = 22;

            // Centralizando no topo da tela (y = topo - margem de 10)
            float centerX = stage.getViewport().getWorldWidth() / 2f;
            float bgX = Math.round(centerX - (bgWidth / 2f));
            float bgY = Math.round(stage.getViewport().getWorldHeight() - bgHeight - 10);

            // Desenha a tarja preta pílula
            stage.getBatch().draw(blackBackground, bgX, bgY, bgWidth, bgHeight);

            // Desenha o texto (o texto da fonte é desenhado de cima para baixo, por isso yText difere)
            float textX = Math.round(centerX - 40);
            float textY = Math.round(bgY + 16);

            uiFont.draw(stage.getBatch(), texto, textX, textY);

            // Retorna a cor para branco para não bugar outras fontes no futuro
            uiFont.setColor(Color.WHITE);

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
