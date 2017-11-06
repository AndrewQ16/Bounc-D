package com.mygdx.bouncdsupersoursweet.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by andrewquinonez on 2/24/17.
 */


public class MenuScreen implements Screen {
    public com.mygdx.bouncdsupersoursweet.SmartBounces game;
    private OrthographicCamera camera;
    private Stage stage;
    private Viewport viewport;
    private TextureAtlas UIatlas;
    //Background
    private Texture background;
    private Sprite backgroundSprite;
    //Table
    private Table table;
    //Title picture
    private Texture titleTexture;
    private Sprite titleSprite;

    //Play Button
    private ImageButton playButton;
    private Drawable playDrawable;

    //Help Button
    private ImageButton questionButton;
    private Drawable questionDrawable;

    public static boolean setGame;

    //High Score Label
    private Label highScoreLabel;
    private BitmapFont menuFont;
    private FreeTypeFontGenerator menuGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter menuParameter;

    private Texture instructionOneTexture;
    private Texture instructionTwoTexture;
    private Sprite instructionOneSprite;
    private Sprite instructionTwoSprite;

    private int imageScroll;
    private float timeInbetweenClick;
    private boolean qButtonClicked;


    public MenuScreen(com.mygdx.bouncdsupersoursweet.SmartBounces game) {
        this.game = game;
        setGame = false;
        System.out.println("Width: " + Gdx.graphics.getWidth() + ", Height:" + Gdx.graphics.getHeight());
        camera = new OrthographicCamera(1080, 1920);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
        camera.setToOrtho(false);
        camera.update();
        viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        stage = new Stage(viewport, game.batch);
        UIatlas = new TextureAtlas(Gdx.files.internal("UI.txt"));
        table = new Table();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(stage);
        timeInbetweenClick = 0;
        qButtonClicked = false;

        imageScroll = 0;
        instructionOneTexture = new Texture("bouncdstuff/instructionsOne.png");
        instructionTwoTexture = new Texture("bouncdstuff/instructionTwo.png");
        instructionOneSprite = new Sprite(instructionOneTexture);
        instructionTwoSprite = new Sprite(instructionTwoTexture);
        instructionOneSprite.setScale(.75f);
        instructionTwoSprite.setScale(.75f);
        instructionOneSprite.setPosition(-.1668f * camera.viewportWidth, -.1668f * camera.viewportHeight);
        instructionTwoSprite.setPosition(-.1668f * camera.viewportWidth, -.1668f * camera.viewportHeight);

        titleTexture = new Texture("bouncdstuff/bouncdTitle.png");
        titleSprite = new Sprite(titleTexture);
        titleSprite.setPosition(camera.viewportWidth/2 - titleTexture.getWidth()/2, camera.viewportHeight/2 + titleTexture.getHeight()/6);

        background = new Texture("bouncdstuff/background.jpeg");
        backgroundSprite = new Sprite(background);

        //Play button
        playDrawable = new TextureRegionDrawable(UIatlas.findRegion("playbtnRed"));
        playButton = new ImageButton(playDrawable);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //System.out.println("GameScreen almost set");
                setGame = true;
                //System.out.println("GameScreen set");
                setGame();
                setGame = false;
            }
        });

        questionDrawable = new TextureRegionDrawable(UIatlas.findRegion("questionGreen"));
        questionButton = new ImageButton(questionDrawable);
        questionButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                imageScroll++;
            }
        });


        //Labels and fonts
        menuGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Oh Whale - TTF.ttf"));
        menuParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        menuParameter.size = 90;
        menuFont = menuGenerator.generateFont(menuParameter);
        menuGenerator.dispose();
        highScoreLabel = new Label(String.format("Current High Score: %d", this.game.prefs.getInteger("highScore")), new Label.LabelStyle(menuFont, Color.CYAN));

        table.add(playButton).padBottom(50);
        table.row();
        table.add(questionButton).padBottom(50);
        table.row();
        table.add(highScoreLabel);
        stage.addActor(table);
    }

    private void instructionImages(SpriteBatch batch, float dt){

        timeInbetweenClick += dt;

        if (questionButton.getClickListener().isPressed()){
            timeInbetweenClick = 0;
            qButtonClicked = true;
        }


        if (imageScroll == 1){
            instructionOneSprite.draw(batch);
            table.setVisible(false);
            if (Gdx.input.isTouched() && timeInbetweenClick >= .5f){
                imageScroll++;
                timeInbetweenClick = 0;
                //System.out.println("Clicked");
            }
            //System.out.println("Click:" + imageScroll);
        } else if (imageScroll == 2){
            instructionTwoSprite.draw(batch);
            if (Gdx.input.isTouched() && timeInbetweenClick >= .5f){
                imageScroll++;
                timeInbetweenClick = 0;
            }
            //System.out.println("Click:" + imageScroll);
        } else if (imageScroll == 3 || imageScroll == 0){
            imageScroll = 0;
            backgroundSprite.draw(batch);
            titleSprite.draw(batch);
            table.setVisible(true);
            qButtonClicked = false;
        }



    }

    private void setGame(){
        if (setGame){
            game.getScreen().dispose();
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        instructionImages(game.batch, delta);
        game.batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        UIatlas.dispose();
        stage.dispose();
    }
}
