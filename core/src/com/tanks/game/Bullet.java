package com.tanks.game;

import com.badlogic.gdx.math.Vector2;

public class Bullet implements Poolable {
    private Vector2 position;
    private Vector2 velocity;
    private boolean active;
    private float time;
    private boolean gravity;
    private boolean bouncing;

    public boolean isGravity() {
        return gravity;
    }

    public boolean isBouncing() {
        return bouncing;
    }

    public Vector2 getPosition() {
        return position;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public boolean isArmed() {
        return time > 0.2f;
    }

    public Bullet() {
        position = new Vector2(0, 0);
        velocity = new Vector2(0, 0);
        active = false;
        time = 0.0f;
        gravity = false;
        bouncing = false;
    }

    public void deactivate() {
        active = false;
    }

    public void activate(float x, float y, float vx, float vy, boolean gravity, boolean bouncing) {
        position.set(x, y);
        velocity.set(vx, vy);
        active = true;
        time = 0.0f;
        this.gravity = gravity;
        this.bouncing = bouncing;
    }

    public void addTime(float dt) {
        time += dt;
    }
}
