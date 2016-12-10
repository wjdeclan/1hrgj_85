package com.quickjam3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by libri on 12/3/16.
 */
public class PlayerController extends VectorizedSprite {

	private float gravityAccel = -1500;
	private float friction = 100;

	public PlayerController(Texture img) {
		super(img);
	}

	public void update(float dt) {
		if (!(getY() < 0 || getY() > (568 - getHeight()))) {
			movement.add(0, gravityAccel * dt);
		} else {
			if (getY() < 0) {
				setPosition(getX(), 0);
				movement.y = 0;
			} else {
				setPosition(getX(), 568 - getHeight());
				movement.y = 0;
			}
		}

		if (movement.x > 0 && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			movement.x -= friction * dt;
		} else if (movement.x < 0 && !Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			movement.x += friction * dt;
		}

		if (getX() < 0) {
			movement.x = 0;
			setX(0);
		} else if (getX() > 768 - getWidth()) {
			movement.x = 0;
			setX(768 - getWidth());
		}

		super.update(dt);
	}

	public void flipGravity() {
		gravityAccel *= -1;
	}
	
	public boolean gravityDown() {
		return gravityAccel < 0;
	}

	public Vector2 getMovement() {
		return movement;
	}

	public void jump() {
		movement = new Vector2(super.movement.x, -500 * (Math.abs(gravityAccel) / gravityAccel));
	}

}