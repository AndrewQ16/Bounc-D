package com.mygdx.bouncdsupersoursweet.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.bouncdsupersoursweet.PhysicsObjects.Borders;

import com.mygdx.bouncdsupersoursweet.PhysicsObjects.MyGestureListener;

/**
 * Created by andrewquinonez on 2/24/17.
 */

public class GameScreen implements Screen{
    private Texture background;
    public com.mygdx.bouncdsupersoursweet.SmartBounces game;
    private Borders borders;
    private com.mygdx.bouncdsupersoursweet.PhysicsObjects.Gameplay gameplay;
    private HUD hud;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthographicCamera gameCam; //creation of cam for gameplay
    private OrthographicCamera camera; //camera for dealing with HUD and Pause options
    private Vector3 touchPos;
    private float dt;
    private com.mygdx.bouncdsupersoursweet.PhysicsObjects.Collision collision;
    private InputMultiplexer inputMultiplexer;
    private float circleAngleDegrees;
    private float barAngleDegrees;
    private float doubleTapTimer;
    private GestureDetector testDetector;
    private MyGestureListener myGestureListener;
    private int scoreHolder;


    protected static float timeStep;

    public GameScreen(com.mygdx.bouncdsupersoursweet.SmartBounces game) {
        this.game = game;
        background = new Texture(Gdx.files.internal("bouncdstuff/background.jpeg"));
        timeStep = 1/60f;
        //gives me 36 pixels x 64 pixels -- looks shit for images and fonts -- might have to add another camera*
        gameCam = new OrthographicCamera(36f,64f ); //this makes the circles look like real circles haha
        camera = new OrthographicCamera(1080f,1920f);
        gameCam.update();
        camera.update();
        dt = 1;
        doubleTapTimer = 0;

        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
        gameCam.position.set(gameCam.viewportWidth/2, gameCam.viewportHeight/2, 0); //centers the camera 
        world = new World(new Vector2(0, -10f), true);

        box2DDebugRenderer = new Box2DDebugRenderer();
        touchPos = new Vector3();
        borders = new Borders(world, gameCam);
        gameplay = new com.mygdx.bouncdsupersoursweet.PhysicsObjects.Gameplay(world, gameCam);

        myGestureListener = new MyGestureListener(gameplay.getBarBody(), barAngleDegrees, gameCam);
        testDetector = new GestureDetector(myGestureListener);
        testDetector.setTapCountInterval(.2f);
        testDetector.setTapSquareSize(Gdx.graphics.getBackBufferWidth()/10f);

        hud = new HUD(camera, game.batch, game.prefs, gameplay.getTestScore());
        collision = new com.mygdx.bouncdsupersoursweet.PhysicsObjects.Collision();
        inputMultiplexer = new InputMultiplexer(hud.getStage(), testDetector);
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    //Bar reverses too slowly
    private void handleInput(float delta) {
        if(Gdx.input.isTouched() && HUD.startTimer){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            gameCam.unproject(touchPos); //translates screen coordinates to worldspace
            //System.out.println("Touch pos:" + touchPos.x);
            if(touchPos.x < gameCam.viewportWidth/2 && touchPos.y < 54){
                dt += delta;

                if(dt <= 5){
                    gameplay.getBarBody().setAngularVelocity(dt);
                } else if (dt > 5){
                    gameplay.getBarBody().setAngularVelocity(5f);
                    dt = 5;
                }

                //gameplay.getBarBody().setAngularVelocity(3.5f);


            } else if (touchPos.x > gameCam.viewportWidth/2 && touchPos.y < 54){
                dt+= delta;

                if(dt <= 5){
                    gameplay.getBarBody().setAngularVelocity(-dt);
                } else if (dt > 5){
                    gameplay.getBarBody().setAngularVelocity(-5f);
                    dt = 5;
                }
            }
        } else if(HUD.startTimer) {
            if(dt > 0){
                dt -= Gdx.graphics.getDeltaTime();
                if (touchPos.x < gameCam.viewportWidth/2){
                    gameplay.getBarBody().setAngularVelocity(dt);
                } else
                    gameplay.getBarBody().setAngularVelocity(-dt);
            } else if(dt < 0){
                dt+=Gdx.graphics.getDeltaTime();
            }
        }

    }

    //Method to set our menu screen or reset the game screen objects to where they started
    private void changeScreens(){
        if (HUD.setMenu){
            game.getScreen().dispose();
            game.setScreen(new MenuScreen(this.game));
            HUD.setMenu = false;
        } else if (HUD.resetGame){

            gameplay.setTestScore(0);
            gameplay.resetGameplayObjects(gameCam);
            hud.resetHUDobjects(gameplay.getBarBody(), gameCam, gameplay.getTestScore());
        }
    }

    @Override
    public void show() {
        world.setContactListener(collision);
    }

    @Override
    public void render(float delta) {
        handleInput(delta);
        gameCam.update();
        camera.update();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //box2DDebugRenderer.render(world, gameCam.combined);
        game.batch.setProjectionMatrix(camera.combined);
        gameplay.collisions(com.mygdx.bouncdsupersoursweet.PhysicsObjects.Gameplay.contact, world, gameCam);


        game.batch.begin();
        game.batch.draw(background,0,0);
        //Sets the position of the Circle and Bar in the game
        gameplay.circleSprite.setPosition(gameplay.getCircleBody().getPosition().x * 30 - 91, gameplay.getCircleBody().getPosition().y * 30 - 91);
        gameplay.barSprite.setPosition(gameplay.getBarBody().getPosition().x * 30 - 150, gameplay.getBarBody().getPosition().y * 30 - 7.5f);
        //Calculates the angles on which to draw the sprites associated to circle and bar's respective box2d representation
        circleAngleDegrees = MathUtils.radiansToDegrees * gameplay.getCircleBody().getAngle();
        barAngleDegrees = MathUtils.radiansToDegrees * gameplay.getBarBody().getAngle();
        //Uses the previous calculation to set the rotation of the circle and bar
        gameplay.circleSprite.setRotation(circleAngleDegrees);//The angle in degrees
        gameplay.barSprite.setRotation(barAngleDegrees);
        //Draws the game sprites according to the location of their box2d represenation
        gameplay.drawGameplaySprites(game.batch);
        gameplay.circleSprite.draw(game.batch);
        gameplay.barSprite.draw(game.batch);
        game.batch.end();

        hud.getStage().draw();

        //testing the score in between these methods
        hud.update(delta, game.prefs, gameplay.getTestScore()); //the update method checks out


        hud.gameOver(game.prefs, gameplay.getTestScore());
        //gameplay.gameOver(hud.getTime());
        changeScreens();
        world.step(timeStep, 6, 2);
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
        world.dispose();
        gameplay.dispose();
        hud.dispose();
    }
}
