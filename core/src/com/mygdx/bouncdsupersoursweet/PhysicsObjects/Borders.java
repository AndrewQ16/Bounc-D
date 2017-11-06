package com.mygdx.bouncdsupersoursweet.PhysicsObjects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by andrewquinonez on 2/24/17.
 */

public class Borders {

    //southWall
    private BodyDef sDef;
    private Body sBody;
    private PolygonShape sWalls;
    private FixtureDef sFixtureDef;
    private Fixture sFexture;
    //north wall
    private BodyDef nDef;
    private Body nBody;
    private PolygonShape nWall;
    private FixtureDef nFixtureDef;
    private Fixture nFixture;
    //west wall
    private BodyDef wDef;
    private Body wBody;
    private FixtureDef wFixtureDef;
    private Fixture wFixture;
    private PolygonShape wWalls;
    //east wall
    private BodyDef eDef;
    private Body eBody;
    private FixtureDef eFixtureDef;
    private Fixture eFixture;
    private PolygonShape eWall;





    public Borders(World world, OrthographicCamera camera) {
        westWall(world, camera);
        eastWall(world, camera);
        northWall(world, camera);
        southWall(world, camera);


    }


    public void westWall(World world, OrthographicCamera camera){
        //west
        wWalls = new PolygonShape();
        wWalls.setAsBox(0, camera.viewportHeight);
        wDef = new BodyDef();
        wDef.position.set(0, camera.viewportHeight/2);
        wDef.type = BodyDef.BodyType.StaticBody;
        wBody = world.createBody(wDef);
        wFixtureDef = new FixtureDef();
        wFixtureDef.shape = wWalls;
        wFixture = wBody.createFixture(wFixtureDef);
    }

    public void eastWall(World world, OrthographicCamera camera){
        eWall = new PolygonShape();
        eWall.setAsBox(0, camera.viewportHeight);
        eDef = new BodyDef();
        eDef.position.set(camera.viewportWidth, camera.viewportHeight/2);
        eDef.type = BodyDef.BodyType.StaticBody;
        eBody = world.createBody(eDef);
        eFixtureDef = new FixtureDef();
        eFixtureDef.shape = eWall;
        eFixture = eBody.createFixture(eFixtureDef);
    }

    public void northWall(World world, OrthographicCamera camera){

        nWall = new PolygonShape();
        nWall.setAsBox(camera.viewportWidth,0);
        nDef = new BodyDef();
        nDef.type = BodyDef.BodyType.StaticBody;
        nDef.position.set(camera.viewportWidth/2, camera.viewportHeight - 10);
        nBody = world.createBody(nDef);
        nFixtureDef = new FixtureDef();
        nFixtureDef.shape = nWall;
        nFixture = nBody.createFixture(nFixtureDef);
    }

    public void southWall(World world, OrthographicCamera camera){
        //south
        sDef = new BodyDef();
        sDef.type = BodyDef.BodyType.StaticBody;
        sDef.position.set(camera.viewportWidth/2,0);
        sBody = world.createBody(sDef);
        sWalls = new PolygonShape();
        sWalls.setAsBox(camera.viewportWidth,0);
        sFixtureDef = new FixtureDef();
        sFixtureDef.shape = sWalls;
        sFexture = sBody.createFixture(sFixtureDef);
        sFexture.setUserData("southWall");

    }



}
