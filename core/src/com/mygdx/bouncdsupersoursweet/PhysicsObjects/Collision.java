package com.mygdx.bouncdsupersoursweet.PhysicsObjects;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.bouncdsupersoursweet.Screens.HUD;

/**
 * Created by andrewquinonez on 3/25/17.
 */

public class Collision implements ContactListener {


    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if ("circle".equals(fixtureA.getUserData()) && "box1".equals(fixtureB.getUserData()) || "circle".equals(fixtureB.getUserData()) && "box1".equals(fixtureA.getUserData())) {
            Fixture circle = "circle".equals(fixtureA.getUserData()) ? fixtureA : fixtureB;
            Fixture box = circle == fixtureA ? fixtureB : fixtureA;
            System.out.println(circle.getUserData());
            System.out.println(box.getUserData());
            Gameplay.contact = Obstacles.contactObstacleOne;


        } else if ("circle".equals(fixtureA.getUserData()) && "box2".equals(fixtureB.getUserData()) || "circle".equals(fixtureB.getUserData()) && "box2".equals(fixtureA.getUserData())) {
            Fixture circle = "circle".equals(fixtureA.getUserData()) ? fixtureA : fixtureB;
            Fixture box = circle == fixtureA ? fixtureB : fixtureA;
            System.out.println(circle.getUserData());
            System.out.println(box.getUserData());
            Gameplay.contact = Obstacles.contactObstacleTwo;


        } else if ("circle".equals(fixtureA.getUserData()) && "box3".equals(fixtureB.getUserData()) || "circle".equals(fixtureB.getUserData()) && "box3".equals(fixtureA.getUserData())) {
            Fixture circle = "circle".equals(fixtureA.getUserData()) ? fixtureA : fixtureB;
            Fixture box = circle == fixtureA ? fixtureB : fixtureA;
            System.out.println(circle.getUserData());
            System.out.println(box.getUserData());
            Gameplay.contact = Obstacles.contactObstacleThree;



        } else if ("circle".equals(fixtureA.getUserData()) && "box4".equals(fixtureB.getUserData()) || "circle".equals(fixtureB.getUserData()) && "box4".equals(fixtureA.getUserData())) {
            Fixture circle = "circle".equals(fixtureA.getUserData()) ? fixtureA : fixtureB;
            Fixture box = circle == fixtureA ? fixtureB : fixtureA;
            System.out.println(circle.getUserData());
            System.out.println(box.getUserData());
            Gameplay.contact = Obstacles.contactObstacleFour;


        } else if ("circle".equals(fixtureA.getUserData()) && "bar".equals(fixtureB.getUserData()) || "circle".equals(fixtureB.getUserData()) && "bar".equals(fixtureA.getUserData())) {
            Fixture circle = "circle".equals(fixtureA.getUserData()) ? fixtureA : fixtureB;
            Fixture box = circle == fixtureA ? fixtureB : fixtureA; //box is just the name
            HUD.startTimer = true;
        }
    }




    @Override
    public void endContact(Contact contact) {
        com.mygdx.bouncdsupersoursweet.PhysicsObjects.Gameplay.contact = Obstacles.noContact;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
