package com.quickjam3;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class QuickJam extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Texture enemyTexture, playerTexture, bombTexture, explosionTexture;

	PlayerController player;

	Sprite explosion;

	ArrayList<WallBounceSprite> enemies;

	//BitmapFont font = new BitmapFont();

	float timeToExplode;

	@Override
	public void create() {
		batch = new SpriteBatch();

		enemyTexture = new Texture("enemy.png");
		playerTexture = new Texture("player.png");
		bombTexture = new Texture("explode.png");

		explosionTexture = new Texture("explosion.png");

		explosion = new Sprite(explosionTexture);
		explosion.setSize(128, 128);

		player = new PlayerController(playerTexture);

		player.setPosition((768 - player.getWidth()) / 2, (576 - player.getHeight()) / 2);

		timeToExplode = 5 + (float) (Math.random() * 5);

		populate();

		Gdx.input.setInputProcessor(this);

		//font.setColor(Color.BLACK);
	}

	public void populate() {
		enemies = new ArrayList<WallBounceSprite>();

		for (int i = 0; i < 50; i++) {
			WallBounceSprite wbs = new WallBounceSprite(enemyTexture);

			wbs.setPosition((float) (Math.random() * 768 - wbs.getWidth()),
					(float) (Math.random() * 576 - wbs.getHeight()));

			Vector2 movement = new Vector2((float) (Math.random() - 0.5), (float) (Math.random() - 0.5));
			movement.nor().scl(50);

			wbs.setMovementVector(movement);

			enemies.add(wbs);
		}
	}

	public void reset() {
		player.setPosition((768 - player.getWidth()) / 2, (576 - player.getHeight()) / 2);
		player.setTexture(playerTexture);

		timeToExplode = 5 + (float) (Math.random() * 5);

		populate();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float dt = Gdx.graphics.getDeltaTime();

		timeToExplode -= dt;

		if ((timeToExplode < 3 && timeToExplode > 2.75) || (timeToExplode < 2 && timeToExplode > 1.75)
				|| (timeToExplode < 1.5 && timeToExplode > 1.25) || (timeToExplode < 1)) {
			player.setTexture(bombTexture);
		} else if ((timeToExplode < 2.75 && timeToExplode > 2) || (timeToExplode < 1.75 && timeToExplode > 1.5)
				|| (timeToExplode < 1.25 && timeToExplode > 1)) {
			player.setTexture(playerTexture);
		}

		if (timeToExplode > 0) {
			for (WallBounceSprite enemy : enemies) {
				enemy.update(dt);
			}

			player.update(dt);
		} else {
			Circle explosion = new Circle(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2,
					64);
		}

		if (timeToExplode < 0) {
			explosion.setPosition(player.getX() + player.getWidth() / 2 - 64,
					player.getY() + player.getHeight() / 2 - 64);
		}

		batch.begin();

		for (WallBounceSprite enemy : enemies) {
			enemy.draw(batch);
		}

		player.draw(batch);

		
		if (timeToExplode < 0) {
			explosion.draw(batch);
			
			//font.draw(batch, "You killed " + numDead, 0, 0);
		}

		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();

		enemyTexture.dispose();
		playerTexture.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.LEFT) {
			player.getMovement().set(new Vector2(-100, player.movement.y));
		} else if (keycode == Input.Keys.RIGHT) {
			player.getMovement().set(new Vector2(100, player.movement.y));
		} else if (keycode == Input.Keys.UP) {
			player.getMovement().set(new Vector2(player.movement.x, 100));
		} else if (keycode == Input.Keys.DOWN) {
			player.getMovement().set(new Vector2(player.movement.x, -100));
		} else if (keycode == Input.Keys.SPACE) {
			reset();
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
