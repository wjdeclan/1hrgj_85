package com.quickjam3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class WallBounceSprite extends VectorizedSprite {

	public WallBounceSprite(Texture img) {
		super(img);
	}

	public void update(float dt) {
		this.setPosition(this.getX() + movement.x * dt, this.getY() + movement.y * dt);
		if (this.getX() > 768 - this.getWidth()) {
			this.setPosition(768 - this.getWidth(), this.getY());
			setMovementVector(new Vector2(-1 * movement.x, movement.y));
		} else if (this.getX() < 0) {
			this.setPosition(0, this.getY());
			setMovementVector(new Vector2(-1 * movement.x, movement.y));
		}
		if (this.getY() > 576 - this.getHeight()) {
			this.setPosition(this.getX(), 576 - this.getHeight());
			setMovementVector(new Vector2(movement.x, -1 * movement.y));
		} else if (this.getY() < 0) {
			this.setPosition(this.getX(), 0);
			setMovementVector(new Vector2(movement.x, -1 * movement.y));
		}
	}

}
