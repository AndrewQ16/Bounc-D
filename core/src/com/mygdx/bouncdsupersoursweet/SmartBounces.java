package com.mygdx.bouncdsupersoursweet;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.bouncdsupersoursweet.Screens.SplashScreen;

public class SmartBounces extends Game {

	public SpriteBatch batch;
	public Preferences prefs;

	@Override
	public void create () {
		batch = new SpriteBatch();
		prefs = Gdx.app.getPreferences("My Preferences");
        prefs.putInteger("highScore", checkHighScore());
		setScreen(new SplashScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		super.dispose();
	}

	private int checkHighScore (){
		if (prefs.getInteger("highScore") > 0){
			return prefs.getInteger("highScore");
		} else
			return 0;
	}
}
