package io.github.guilhermegodoydev.greenhorizon.core.managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
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

// IMPORTAÇÕES DAS MOEDAS
import io.github.guilhermegodoydev.greenhorizon.core.ui.CoinsDisplay;
import io.github.guilhermegodoydev.greenhorizon.core.itens.CoinsManager;

public class ManagerUI implements TowerSelectionListener, Disposable {
    private final Stage stage;
    private final HealthDisplay healthDisplay;
    private final CoinsDisplay coinsDisplay;
    private final ConstructionMenu constructionMenu;
    private final TowerActionMenu actionMenu;
    private final GameEventListener eventListener;

    // Variáveis do Menu de Pausa
    private Table pauseTable;
    private GameScreen gameScreen;

    private TowerSlot slotAlvo;
    private TowerBase torreSelecionada;

    // CONSTRUTOR ATUALIZADO
    public ManagerUI(Viewport viewport, SpriteBatch batch, LifeManager lifeManager, CoinsManager coinsManager, GameEventListener listener, GameScreen gameScreen) {
        this.stage = new Stage(viewport, batch);
        this.eventListener = listener;
        this.gameScreen = gameScreen;

        float topo = viewport.getWorldHeight();

        this.healthDisplay = new HealthDisplay(lifeManager, 20, topo - 40);
        this.coinsDisplay = new CoinsDisplay(coinsManager, 20, topo - 75);

        this.actionMenu = new TowerActionMenu(this);
        this.constructionMenu = new ConstructionMenu(this);

        stage.addActor(healthDisplay);
        stage.addActor(coinsDisplay);
        stage.addActor(constructionMenu);
        stage.addActor(actionMenu);

        // CHAMADAS PARA CRIAR A UI DE PAUSA
        criarBotaoEngrenagem();
        criarMenuPausa();
    }

    // --- MÉTODOS DO MENU DE PAUSA ---

    private void criarBotaoEngrenagem() {
        TextButton.TextButtonStyle btnStyle = createProgrammerArtStyle(Color.GRAY);
        TextButton btnPause = new TextButton("||", btnStyle);

        // Posição no canto superior direito
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
    }

    private TextButton.TextButtonStyle createProgrammerArtStyle(Color cor) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(cor);
        pixmap.fill();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(new Texture(pixmap));
        style.font = new com.badlogic.gdx.graphics.g2d.BitmapFont();
        pixmap.dispose();
        return style;
    }

    // --- MÉTODOS ORIGINAIS DO MANAGER UI ---

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
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        healthDisplay.dispose();
        coinsDisplay.dispose();
        stage.dispose();
    }

    public boolean isVisivel() {
        return constructionMenu.isVisible() || actionMenu.isVisible();
    }

    public Stage getStage() { return stage; }
}
