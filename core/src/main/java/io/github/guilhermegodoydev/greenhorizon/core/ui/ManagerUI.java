package io.github.guilhermegodoydev.greenhorizon.core.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.guilhermegodoydev.greenhorizon.core.features.enemy.WaveManager;
import io.github.guilhermegodoydev.greenhorizon.core.features.tower.entities.TowerBase;
import io.github.guilhermegodoydev.greenhorizon.core.events.GameEvent;
import io.github.guilhermegodoydev.greenhorizon.core.events.GameEventListener;
import io.github.guilhermegodoydev.greenhorizon.core.features.tower.ui.TowerSelectionListener;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;
import io.github.guilhermegodoydev.greenhorizon.core.screens.GameScreen;
import io.github.guilhermegodoydev.greenhorizon.core.screens.MainMenuScreen;
import io.github.guilhermegodoydev.greenhorizon.core.screens.SettingsScreen;
import io.github.guilhermegodoydev.greenhorizon.core.features.tower.ui.ConstructionMenu;
import io.github.guilhermegodoydev.greenhorizon.core.features.tower.ui.TowerActionMenu;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Utils;
import io.github.guilhermegodoydev.greenhorizon.core.features.economy.LifeManager;
import io.github.guilhermegodoydev.greenhorizon.core.features.economy.CoinsManager;
import io.github.guilhermegodoydev.greenhorizon.core.utils.ButtonFactory;

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
    private final GlyphLayout layout = new GlyphLayout();

    private Table pauseTable;
    private GameScreen gameScreen;
    private TowerSlot targetSlot;
    private TowerBase selectedTower;

    private ImageButton btnPause;

    public ManagerUI(Viewport viewport, SpriteBatch batch, LifeManager lifeManager, CoinsManager coinsManager, GameEventListener listener, GameScreen gameScreen, WaveManager waveManager) {
        this.waveManager = waveManager;
        this.stage = new Stage(viewport, batch);
        this.eventListener = listener;
        this.gameScreen = gameScreen;

        this.uiFont = new BitmapFont();
        this.uiFont.getData().setScale(0.45f);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0, 0, 0, 0.6f));
        pixmap.fill();
        this.blackBackground = new Texture(pixmap);
        pixmap.dispose();

        float top = viewport.getWorldHeight();

        this.healthDisplay = new HealthDisplay(lifeManager, 15, top - 30);
        this.healthDisplay.setScale(0.7f);

        this.coinsDisplay = new CoinsDisplay(coinsManager, 15, top - 55);
        this.coinsDisplay.setScale(0.7f);

        this.actionMenu = new TowerActionMenu(this);
        this.constructionMenu = new ConstructionMenu(this);

        stage.addActor(healthDisplay);
        stage.addActor(coinsDisplay);
        stage.addActor(constructionMenu);
        stage.addActor(actionMenu);

        createPauseButton();
        createPauseMenu();
        createStartWaveButton();
    }

    private void createStartWaveButton() {
        btnStartWave = ButtonFactory.createHoverButton("botao_start.png", "botao_start_hover.png");

        float btnWidth = btnStartWave.getWidth() > 0 ? btnStartWave.getWidth() : 100;
        btnStartWave.setPosition(stage.getViewport().getWorldWidth() - btnWidth - 98, 216);

        btnStartWave.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                waveManager.startNextWave();
            }
        });

        stage.addActor(btnStartWave);
    }

    private void createPauseButton() {
        btnPause = ButtonFactory.createHoverButton("botao_pause.png", "botao_pause_hover.png");
        btnPause.setPosition(stage.getViewport().getWorldWidth() - 51, stage.getViewport().getWorldHeight() - 33);

        btnPause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.togglePause();
            }
        });
        stage.addActor(btnPause);
    }

    private void createPauseMenu() {
        pauseTable = new Table();
        pauseTable.setFillParent(true);
        pauseTable.setVisible(false);

        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(new Color(0, 0, 0, 0.7f));
        bgPixmap.fill();
        pauseTable.setBackground(new TextureRegionDrawable(new Texture(bgPixmap)));
        bgPixmap.dispose();

        ImageButton btnContinue = ButtonFactory.createHoverButton("botao_continuar.png", "botao_continuar_hover.png");
        btnContinue.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.togglePause();
            }
        });

        ImageButton btnConfig = ButtonFactory.createHoverButton("botao_configuracoes.png", "botao_configuracoes_hover.png");
        btnConfig.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.getGame().setScreen(new SettingsScreen(gameScreen.getGame(), gameScreen));
            }
        });

        ImageButton btnExit = ButtonFactory.createHoverButton("botao_sair.png", "botao_sair_hover.png");
        btnExit.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.getGame().setScreen(new MainMenuScreen(gameScreen.getGame()));
            }
        });

        pauseTable.add(btnContinue).pad(5).row();
        pauseTable.add(btnConfig).pad(5).row();
        pauseTable.add(btnExit).pad(5);

        stage.addActor(pauseTable);
    }

    public void setPauseVisible(boolean visible) {
        pauseTable.setVisible(visible);
        if (visible) {
            pauseTable.toFront();
            btnPause.setTouchable(Touchable.disabled);
        } else {
            btnPause.setTouchable(Touchable.enabled);
        }
    }

    @Override
    public void onTowerSelected(String type) {
        if (type.equals("Sell")) {
            eventListener.onEvent(new GameEvent(GameEvent.Type.SELL_TOWER, selectedTower));
        } else if (type.equals("Upgrade")) {
            eventListener.onEvent(new GameEvent(GameEvent.Type.UPGRADE_TOWER, selectedTower));
        } else {
            Object[] buildData = { targetSlot, type };
            eventListener.onEvent(new GameEvent(GameEvent.Type.BUILD_TOWER, buildData));
        }
        closeAllMenus();
    }

    public void openActionMenu(TowerBase tower) {
        this.selectedTower = tower;
        actionMenu.updateValues(tower);
        closeAllMenus();
        Utils.setCenteredPosition(actionMenu, tower.getPosition().x, tower.getPosition().y + 8);
        actionMenu.setVisible(true);
    }

    public void openMenu(TowerSlot slot) {
        this.targetSlot = slot;
        closeAllMenus();
        Utils.setCenteredPosition(constructionMenu, slot.getCenterX(), slot.getBounds().y + slot.getBounds().height);
        constructionMenu.setVisible(true);
    }

    public void closeAllMenus() {
        constructionMenu.setVisible(false);
        actionMenu.setVisible(false);
    }

    public void render(float delta) {
        boolean paused = pauseTable.isVisible();

        if (btnStartWave != null) {
            btnStartWave.setVisible(!waveManager.isWaveActive() && !paused);
        }

        stage.act(delta);
        stage.draw();

        if (!paused) {
            stage.getBatch().begin();

            boolean isWaveActive = waveManager.isWaveActive();
            int displayWave = isWaveActive ? waveManager.getCurrentWave() : waveManager.getCurrentWave() + 1;
            displayWave = Math.min(displayWave, waveManager.getTotalWaves());
            String waveText = "WAVE " + displayWave + "/" + waveManager.getTotalWaves();

            float centerX = stage.getViewport().getWorldWidth() / 2f;

            float bgWidth = 90f;
            float bgHeight = isWaveActive ? 16f : 30f;

            float bgX = Math.round(centerX - (bgWidth / 2f));
            float bgY = Math.round(stage.getViewport().getWorldHeight() - bgHeight - 5f);

            stage.getBatch().draw(blackBackground, bgX, bgY, bgWidth, bgHeight);

            layout.setText(uiFont, waveText);
            float waveTextX = Math.round(centerX - (layout.width / 2f));
            float waveTextY = isWaveActive ? Math.round(bgY + 12) : Math.round(bgY + bgHeight - 4);
            uiFont.draw(stage.getBatch(), waveText, waveTextX, waveTextY);

            if (!isWaveActive) {
                int timerValue = (int)waveManager.getWaveTimer();
                String timerText = "STARTS IN: " + timerValue + "s";

                if (timerValue <= 5) {
                    uiFont.setColor(Color.RED);
                } else {
                    uiFont.setColor(Color.WHITE);
                }

                layout.setText(uiFont, timerText);
                float timerTextX = Math.round(centerX - (layout.width / 2f));
                float timerTextY = Math.round(bgY + 11);

                uiFont.draw(stage.getBatch(), timerText, timerTextX, timerTextY);
                uiFont.setColor(Color.WHITE);
            }

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

    public boolean isVisible() {
        return constructionMenu.isVisible() || actionMenu.isVisible();
    }

    public Stage getStage() { return stage; }
}
