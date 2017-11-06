package com.mygdx.bouncdsupersoursweet.PhysicsObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by andrewquinonez on 3/2/17.
 */

public class Gameplay {

    //Ball
    private BodyDef circleBodyDef;
    private Body circleBody;
    private CircleShape circle;
    private FixtureDef circleFixtureDef;
    private Fixture circleFixture;
    private Texture circleImage;
    public Sprite circleSprite;

    //Bar
    private BodyDef barDef;
    private Body barBody;
    private PolygonShape barShape;
    private FixtureDef barFixtureDef;
    private Fixture barFixture;
    private Texture barTexture;
    private Pixmap barOriginalSize;
    private Pixmap barScaleTwice;
    public Sprite barSprite;


    private Fixture obsFixture;
    private Texture obsTexture;
    private Sprite obsSprite;

    public static int timeLeft;
    public static float timeElapsed;
    public static int score;
    public static boolean setBarMovement;

    Body obstacle1;
    Body obstacle2;
    Body obstacle3;
    Body obstacle4;
    BodyDef bodyDef1;
    BodyDef bodyDef2;
    BodyDef bodyDef3;
    BodyDef bodyDef4;
    FixtureDef obstacleFixtureDef;

    PolygonShape obstacleShape;

    public static Obstacles contact;
    private boolean destroyOne;
    private boolean destroyTwo;
    private boolean destroyThree;

    private int testScore;


    public Gameplay(World world, OrthographicCamera camera){
        defineCircle(world, camera);
        defineBar(world, camera);
        contact = Obstacles.noContact;
        setBarMovement = false;
        createObstacles(world, camera);
        testScore = 0;

        //test with booleans
        destroyOne = true;
        destroyTwo = true;
        destroyThree = true;
        timeLeft = 8;
        timeElapsed = 0;
        //System.out.println("score: " + score);


    }

    private void defineCircle(World world, OrthographicCamera camera){
        circleImage = new Texture(Gdx.files.internal("bouncdstuff/circleImage.png"));
        circleSprite = new Sprite(circleImage);
        circleBodyDef = new BodyDef();
        circle = new CircleShape();

        circleBodyDef.type = BodyDef.BodyType.DynamicBody;
        circleBodyDef.position.set(camera.viewportWidth/2, camera.viewportHeight/2);
        circleBody = world.createBody(circleBodyDef);
        circle.setRadius(3f);

        circleFixtureDef = new FixtureDef();
        circleFixtureDef.shape = circle;
        circleFixtureDef.density = 0.5f;
        circleFixtureDef.friction = 0.4f;
        circleFixtureDef.restitution = 0.6f;
        circleBody.createFixture(circleFixtureDef).setUserData("circle");
    }

    private void defineBar(World world,OrthographicCamera camera){
        barDef = new BodyDef();
        barDef.type = BodyDef.BodyType.KinematicBody;
        barDef.position.set(camera.viewportWidth/2, 20);
        barBody = world.createBody(barDef);
        barShape = new PolygonShape();
        barShape.setAsBox(10, 0.5f);
        barFixtureDef = new FixtureDef();
        barFixtureDef.shape = barShape;
        barFixture = barBody.createFixture(barFixtureDef);
        barFixture.setUserData("bar");

        barTexture = new Texture(Gdx.files.internal("bouncdstuff/Bar.png"));
        barSprite = new Sprite(barTexture);
        barSprite.setScale(2,2);
    }

    public float randomX(OrthographicCamera camera){
        float randomX = MathUtils.random(0,camera.viewportWidth);
        return randomX;
    }

    public float randomY(OrthographicCamera camera){
        float randomY = MathUtils.random(0,camera.viewportHeight - 12);
        return randomY;
    }

    public void createObstacles(World world, OrthographicCamera camera){
        obsTexture = new Texture("bouncdstuff/boxImage.png");
        obsSprite = new Sprite(obsTexture);
        obstacleShape = new PolygonShape();
        obstacleShape.setAsBox(1,1);
        obstacleFixtureDef = new FixtureDef();
        obstacleFixtureDef.shape = obstacleShape;



        //*************************  Revamping the whole obstacle system    ******************************************

        bodyDef1 = new BodyDef();
        bodyDef1.type = BodyDef.BodyType.KinematicBody;
        //bodyDef1.position.set(randomX(camera), randomY(camera));
        //This below is the debug position for the obstacle********
        bodyDef1.position.set(1, 5);
        obstacle1 = world.createBody(bodyDef1);
        obstacle1.createFixture(obstacleFixtureDef).setUserData("box1");

        bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.KinematicBody;
        //bodyDef2.position.set(randomX(camera), randomY(camera));
        //This below is the debug position for the obstacle********
        bodyDef2.position.set(9, 5);
        obstacle2 = world.createBody(bodyDef2);
        obstacle2.createFixture(obstacleFixtureDef).setUserData("box2");

        bodyDef3 = new BodyDef();
        bodyDef3.type = BodyDef.BodyType.KinematicBody;
        //bodyDef3.position.set(randomX(camera), randomY(camera));
        //This below is the debug position for the obstacle********
        bodyDef3.position.set(16,5f);
        obstacle3 = world.createBody(bodyDef3);
        obstacle3.createFixture(obstacleFixtureDef).setUserData("box3");


        bodyDef4 = new BodyDef();
        bodyDef4.type = BodyDef.BodyType.KinematicBody;
        //bodyDef4.position.set(randomX(camera), randomY(camera));
        //This below is the debug position for the obstacle********
        bodyDef4.position.set(28, 5);
        obstacle4 = world.createBody(bodyDef4);
        obstacle4.createFixture(obstacleFixtureDef).setUserData("box4");

        obstacle1.setAngularVelocity(3f);
        obstacle2.setAngularVelocity(4f);
        obstacle3.setAngularVelocity(5f);
        obstacle4.setAngularVelocity(6f);
    }

    public void collisions(Obstacles obstacles, World world, OrthographicCamera camera){

        /*
        if (timeElapsed > 30 && destroyThree){
            world.destroyBody(body3);
            destroyThree = false;
        } else if (timeElapsed > 20 && destroyTwo){
            world.destroyBody(body2);
            destroyTwo = false;
        } else if (timeElapsed > 10 && destroyOne){
            world.destroyBody(body1);
            destroyOne = false;
        } else {
        */
            switch (obstacles){
                case contactObstacleOne:
                    score++;

                    testScore++;

                    obstacle1.setTransform(randomX(camera), randomY(camera), 0);
                    timeLeft =+5;
                    break;
                case contactObstacleTwo:
                    score++;

                    testScore++;

                    obstacle2.setTransform(randomX(camera), randomY(camera), 0);
                    timeLeft =+5;
                    break;
                case contactObstacleThree:
                    score++;

                    testScore++;

                    obstacle3.setTransform(randomX(camera), randomY(camera), 0);
                    timeLeft =+5;
                    break;
                case contactObstacleFour:
                    score++;

                    testScore++;

                    obstacle4.setTransform(randomX(camera), randomY(camera), 0);
                    break;
                case noContact:
                    break;

                default:
                    break;
            }
    }

    public int getTestScore() {
        return testScore;
    }

    public void setTestScore(int testScore) {
        this.testScore = testScore;
    }

    public void drawGameplaySprites(SpriteBatch sb){

        float degrees1 = MathUtils.radiansToDegrees * obstacle1.getAngle();
        float degrees2 = MathUtils.radiansToDegrees * obstacle2.getAngle();
        float degrees3 = MathUtils.radiansToDegrees * obstacle3.getAngle();
        float degrees4 = MathUtils.radiansToDegrees * obstacle4.getAngle();


        if (obstacle1.getFixtureList().get(0).getUserData().toString().equals("box1")){
            obsSprite.setPosition(obstacle1.getPosition().x * 30 - 35, obstacle1.getPosition().y * 30 - 35);
            obsSprite.setRotation(degrees1);
            obsSprite.draw(sb);
        }

        if (obstacle2.getFixtureList().get(0).getUserData().equals("box2")){
            obsSprite.setPosition(obstacle2.getPosition().x * 30 - 35, obstacle2.getPosition().y * 30 - 35);
            obsSprite.setRotation(degrees2);
            obsSprite.draw(sb);
        }

        if (obstacle3.getFixtureList().get(0).getUserData().equals("box3")){
            obsSprite.setPosition(obstacle3.getPosition().x * 30 - 35, obstacle3.getPosition().y * 30 - 35);
            obsSprite.setRotation(degrees3);
            obsSprite.draw(sb);
        }

        if (obstacle4.getFixtureList().get(0).getUserData().equals("box4")){
            obsSprite.setPosition(obstacle4.getPosition().x * 30 - 35, obstacle4.getPosition().y * 30 - 35);
            obsSprite.setRotation(degrees4);
            obsSprite.draw(sb);
        }

    }

    public void moveTheBar(World world,Vector3 touchPos, OrthographicCamera gameCamera){
        float barAngle = barBody.getAngle();
        float anglularVelocity = barBody.getAngularVelocity();
        //world.destroyBody(barBody);
        if (touchPos.y > 54){
            barDef.position.set(touchPos.x , 54);
            System.out.println("Move bar coordinates touch:" + touchPos.x + ", " + touchPos.y);
        } else
            barDef.position.set(touchPos.x, touchPos.y);
        //barBody = world.createBody(barDef);
        barBody.setTransform(new Vector2(touchPos.x, touchPos.y), barAngle );
        barBody.setAngularVelocity(anglularVelocity);
        //barFixture = barBody.createFixture(barFixtureDef);
        setBarMovement = false;

        //ADD A LOCAL PARAM TO KEEP TRACK OF THE ANGLE THE BAR IS ON SO IT KEEPS IT!!!!!!!!!!!!!!!*******************************
    }

    public void resetGameplayObjects(OrthographicCamera camera){
        circleBody.setAngularVelocity(0);
        circleBody.setTransform(camera.viewportWidth/2, camera.viewportHeight/2, 0);
        barBody.setTransform(camera.viewportWidth/2, 20, 0);

        obstacle1.setTransform(randomX(camera), randomY(camera), 0);
        obstacle2.setTransform(randomX(camera), randomY(camera), 0);
        obstacle3.setTransform(randomX(camera), randomY(camera), 0);
        obstacle4.setTransform(randomX(camera), randomY(camera), 0);
    }

    public Body getBarBody(){
        return barBody;
    }

    public Body getCircleBody(){
        return circleBody;
    }

    //test method for reseing the score
    public void gameOver(int time){
        if(time <= 0){
            testScore = 0;
        }
    }

    public void dispose(){
        obsTexture.dispose();
        circleImage.dispose();
    }

}