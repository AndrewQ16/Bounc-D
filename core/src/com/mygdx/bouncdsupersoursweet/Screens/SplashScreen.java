package com.mygdx.bouncdsupersoursweet.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by andrewquinonez on 8/6/17.
 */

public class SplashScreen implements Screen {
    public com.mygdx.bouncdsupersoursweet.SmartBounces game;
    private Sprite sprite;
    private Texture backgroundTexture;
    private OrthographicCamera camera;
    private float splashTimer;
    private boolean loadingGame;

    public SplashScreen(com.mygdx.bouncdsupersoursweet.SmartBounces game) {
        this.game = game;
        camera = new OrthographicCamera(1080,1920);
        camera.update();
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
        splashTimer = 0;
        loadingGame = true;

    }

    @Override
    public void show() {
        backgroundTexture = new Texture(Gdx.files.internal("bouncdstuff/background.jpeg"));
        sprite = new Sprite(backgroundTexture);
    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        sprite.draw(game.batch);
        game.batch.end();
        setSplashTimer(delta);
    }

    public void setSplashTimer(float dt){
        splashTimer += dt;
        //System.out.println("Splash timer: " + splashTimer);
        if (splashTimer > 0.2f && loadingGame){
            game.getScreen().dispose();
            game.setScreen(new MenuScreen(game));
            loadingGame = false;
        }
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
        backgroundTexture.dispose();
    }
}
