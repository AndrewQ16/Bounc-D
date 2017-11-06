package com.mygdx.bouncdsupersoursweet.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by andrewquinonez on 4/24/17.
 */

public class HUD implements Disposable {

    private Stage stage;

    private Table table;
    //Game over table*
    private Table gameOverTable;
    //Pause menu table
    private Table pauseTable;

    //Calling the HUD class elements
    private TextureAtlas UIatlas;
    //Hud labels
    private Label timerLabel;
    private Label scoreLabel;
    //High Score Label with background and font for the game over table
    private Label gameOverScoreLabel;

    //The backdrop for our pause and gameover tables
    private Drawable menuBackgroundDrawable;

    private Viewport viewport;

    //Will hold the time remaining in the game
    private int time;

    //These are used to update the timer method
    private float addOneSecond;
    public static boolean startTimer;


    //This boolean is to set the menu screen and reset the game screen once the time runs out
    public static boolean setMenu;
    public static boolean resetGame;
    private boolean stopGameOverMethod;

    //Set up the fonts for the Labels
    private BitmapFont font90;
    private BitmapFont font70;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter HUDparameter;
    private FreeTypeFontGenerator.FreeTypeFontParameter gameOverParameter;

    //Pause Button
    private Drawable pauseDrawable;
    private ImageButton pauseButton;

    //Play Button
    private Drawable playDrawable;
    private ImageButton playButton;

    //Replay button
    private Drawable replayDrawable;
    private ImageButton replayPauseMenuButton;
    private ImageButton replayGameOverButton;


    //Quit butten for both menus
    private Drawable quitDrawable;
    private ImageButton quitPauseMenuButton;
    private ImageButton quitGameOverButton;



    private Body bar;
    private int oldScore;

    public HUD(OrthographicCamera camera, SpriteBatch sb, Preferences pref, int testScore) {
        UIatlas = new TextureAtlas(Gdx.files.internal("UI.txt"));
        viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        stage = new Stage(viewport, sb);
        time = 30;
        oldScore = 0;
        setMenu = false;
        resetGame = false;
        addOneSecond = 0;
        startTimer = false;
        stopGameOverMethod = false;
        //Setup for the font
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Oh Whale - TTF.ttf"));
        HUDparameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        HUDparameter.size = 90;
        gameOverParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        gameOverParameter.size = 70;
        font90 = generator.generateFont(HUDparameter);
        font70 = generator.generateFont(gameOverParameter);
        generator.dispose();



        gameOverScoreLabel = new Label(highScore(pref, testScore ), new Label.LabelStyle(font70, Color.BLUE));

        setPauseButton();

        setPlayButton(camera);

        //Replay buttons
        setReplayButtons();

        //Quit buttons
        setQuitButtons();

        //table setup
        table = new Table();
        table.setSize(viewport.getScreenWidth(), 300);
        table.setPosition(0, 1620);

        //Labels -- check out
        timerLabel = new Label(String.format("Time: %02d", time), new Label.LabelStyle(font90, Color.ORANGE));
        scoreLabel = new Label(String.format("Score: %03d", testScore), new Label.LabelStyle(font90, Color.WHITE));


        //Top of the game HUD
        table.add(timerLabel).expandX();
        table.add(scoreLabel).expandX();
        table.row();
        table.add(pauseButton).colspan(2);

        table.right().top();
        stage.addActor(table);



        menuBackgroundDrawable = new TextureRegionDrawable(UIatlas.findRegion("pauseBlockMenu"));
        pauseTable = new Table();
        pauseTable.setSize(viewport.getScreenWidth()/2 + 150, viewport.getScreenHeight()/2 - 200);
        pauseTable.setPosition(viewport.getWorldWidth()/2 - pauseTable.getWidth()/2, viewport.getWorldHeight()/2 - pauseTable.getHeight()/2);
        pauseTable.setBackground(menuBackgroundDrawable);
        pauseTable.add(playButton).padBottom(15);
        pauseTable.row();
        pauseTable.add(replayPauseMenuButton).padBottom(15);
        pauseTable.row();
        pauseTable.add(quitPauseMenuButton).padBottom(15);
        stage.addActor(pauseTable);

        gameOverTable = new Table();
        gameOverTable.setSize(viewport.getScreenWidth()/2 + 300, viewport.getScreenHeight()/2 - 200);
        gameOverTable.setPosition(viewport.getWorldWidth()/2 - gameOverTable.getWidth()/2, viewport.getWorldHeight()/2 - gameOverTable.getHeight()/2);
        gameOverTable.setBackground(menuBackgroundDrawable);
        gameOverTable.add(replayGameOverButton.padBottom(15));
        gameOverTable.row();
        gameOverTable.add(quitGameOverButton);
        gameOverTable.row();
        gameOverTable.add(gameOverScoreLabel).padBottom(15);
        stage.addActor(gameOverTable);

        
        gameOverTable.setVisible(false);
        pauseTable.setVisible(false);
    }


    //Where I'll update  the score and time
    //This code works out with the 'testScore'
    public void update(float dt, Preferences pref, int testScore){
        float pauseTime = addOneSecond;
        //scoreLabel.setText(String.format("Score: %03d", com.mygdx.bouncdsupersoursweet.PhysicsObjects.Gameplay.score));
        scoreLabel.setText(String.format("Score: %03d", testScore));
        scoreLabel.setColor(setColor(pref, testScore));

        //int currentScore = com.mygdx.bouncdsupersoursweet.PhysicsObjects.Gameplay.score;
        int currentScore = testScore;
        if (currentScore > oldScore){
            time+=3;
            oldScore = currentScore;
        }

        if (startTimer){
            addOneSecond+= dt;
            if (addOneSecond >= 1){
                time--;
                timerLabel.setText(String.format("Time: %03d", time));
                addOneSecond = 0;
            }
        } else if (!startTimer){
            addOneSecond = pauseTime;
        }

    }
    public Color setColor(Preferences pref, int testScore){

        /*
        if ((float) com.mygdx.bouncdsupersoursweet.PhysicsObjects.Gameplay.score/pref.getInteger("highScore") > .7f){
            return Color.YELLOW;
        } else if ((float) com.mygdx.bouncdsupersoursweet.PhysicsObjects.Gameplay.score/pref.getInteger("highScore") > 1){
            return Color.RED;
        } else
            return Color.CYAN;
            */

        if ((float) testScore/pref.getInteger("highScore") > 1f){
            return Color.RED;
        } else if ((float) testScore/pref.getInteger("highScore") > .75f){
            return Color.YELLOW;
        } else
            return Color.GREEN;

    }

    //When time runs out and the round ends:

    public void gameOver(Preferences pref, int testScore){
        //testing out a new boolean that will only run the code once at the end of the game
        if (time <= 0f && !stopGameOverMethod){
            time = 0;
            startTimer = false;
            GameScreen.timeStep = 0;
            pauseButton.setDisabled(true);

            gameOverScoreLabel.setText(String.format(highScore(pref, testScore), checkScore(pref, testScore)));
            /*
            if (com.mygdx.bouncdsupersoursweet.PhysicsObjects.Gameplay.score > pref.getInteger("highScore") ){
                pref.putInteger("highScore", com.mygdx.bouncdsupersoursweet.PhysicsObjects.Gameplay.score);
                pref.flush();
            }
            */

            if (testScore > pref.getInteger("highScore") ){
                pref.putInteger("highScore", testScore);
                pref.flush();
            }

            gameOverTable.setVisible(true);
            stopGameOverMethod = true;
        }
    }

    public int getTime() {
        return time;
    }

    //THISSSSSSS********************************** NEW LABEL STRING IF THE SCORE IS NEW! change the color of the next if its new!
    private String highScore(Preferences pref, int testScore){
        int highScore = pref.getInteger("highScore");
        System.out.println("test score:" + testScore + ". pref score:" + pref.getInteger("highScore"));
        if ( testScore > highScore){
            System.out.println("new high score ran, test score:" + testScore + ". pref score:" + pref.getInteger("highScore"));
            return "New High Score: %d";
        } else{
            System.out.println("old high score ran");
            return "Same High Score: %d";
        }
    }

    private int checkScore(Preferences pref, int testScore){
        /*
        if (com.mygdx.bouncdsupersoursweet.PhysicsObjects.Gameplay.score > pref.getInteger("highsScore")){
            return com.mygdx.bouncdsupersoursweet.PhysicsObjects.Gameplay.score;
          */
        int highScore = pref.getInteger("highScore");
        if (testScore > highScore){
            System.out.println("Test score is greater");
            return testScore;
        } else {
            System.out.println("Old score is greater than new");
            return pref.getInteger("highScore");
        }

    }




    public void resetHUDobjects(Body bar, OrthographicCamera camera, int testScore){

        time = 30;
        timerLabel.setText(String.format("Time: %03d", time));
        com.mygdx.bouncdsupersoursweet.PhysicsObjects.Gameplay.score = 0;
        //gameOverScoreLabel.setText(String.format("Score: %03d", com.mygdx.bouncdsupersoursweet.PhysicsObjects.Gameplay.score));
        gameOverScoreLabel.setText(String.format("Score: %03d", testScore));
        resetGame = false;
        gameOverTable.setVisible(false);
        pauseButton.setDisabled(false);
        pauseTable.setVisible(false);
        stopGameOverMethod = false;
        this.bar = bar;
        this.bar.setTransform(camera.viewportWidth/2, 20, 0);
        startTimer = true;
        GameScreen.timeStep = 1/60f;
    }


    //Buttons:
    //Instantiate and set up Pause button
    public void setPauseButton(){

        pauseDrawable = new TextureRegionDrawable(UIatlas.findRegion("pausebtnRed"));
        pauseButton = new ImageButton(pauseDrawable);
        pauseButton.setSize(300,300);
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Paused state");
                GameScreen.timeStep = 0;
                pauseTable.setVisible(true);
                //pauseOn = true;
                startTimer = false;
            }
        });
    }

    //Instantiate and set up Play button
    public void setPlayButton(OrthographicCamera camera){

        playDrawable = new TextureRegionDrawable(UIatlas.findRegion("playbtnRed"));
        playButton = new ImageButton(playDrawable);
        playButton.setPosition(camera.viewportWidth, camera.viewportHeight/2);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pauseTable.setVisible(false);
                startTimer = true;
                GameScreen.timeStep = 1/60f;
            }
        });
    }

    //Insantiate and set up replay button for pause menu and game over menu
    public void setReplayButtons(){
        replayDrawable = new TextureRegionDrawable(UIatlas.findRegion("replayGreen"));
        replayPauseMenuButton = new ImageButton(replayDrawable);
        replayPauseMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stopGameOverMethod = false;
                resetGame = true;
            }
        });

        replayGameOverButton = new ImageButton(replayDrawable);
        replayGameOverButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stopGameOverMethod = false;
                resetGame = true;
            }
        });
    }

    //Insantiate and set up quit buttons for the pause menu and game over menu
    public void setQuitButtons(){
        quitDrawable = new TextureRegionDrawable(UIatlas.findRegion("quitGray"));
        quitPauseMenuButton = new ImageButton(quitDrawable);
        quitPauseMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setMenu = true;
            }
        });

        quitGameOverButton = new ImageButton(quitDrawable);
        quitGameOverButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setMenu = true;
            }
        });
    }

    @Override
    public void dispose() {
        stage.dispose();
        UIatlas.dispose();
    }

    public Stage getStage() {
        return stage;
    }
}

