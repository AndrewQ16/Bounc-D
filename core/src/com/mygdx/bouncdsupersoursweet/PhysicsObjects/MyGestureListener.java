package com.mygdx.bouncdsupersoursweet.PhysicsObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;


/**
 * Created by andrewquinonez on 8/26/17.
 */

public class MyGestureListener  implements GestureDetector.GestureListener {
    private Body bar;
    private float barAngle;
    private OrthographicCamera camera;
    private int tapCounter;

    public MyGestureListener(Body bar, float barAngle, OrthographicCamera camera) {
        super();
        this.bar = bar;
        this.barAngle = barAngle;
        this.camera = camera;
        tapCounter = 0;

    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        //System.out.println("tap");
        //System.out.println("touch x:" + x + ". y: + " + y);
        Vector3 translate = new Vector3();
        System.out.println("Click:" + count);
        if (count > 1){
            translate.set(x,y, 0);
            camera.unproject(translate);
            if(translate.y < 54){
                System.out.println("double tap");
                bar.setTransform(translate.x, translate.y, bar.getAngle());
                count = 0;
            }
        }
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

}
