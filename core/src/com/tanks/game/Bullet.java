package com.tanks.game;

import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private Vector2 position;
    private Vector2 velocity;
    private boolean active;
    private float angle;
    private int damage;

    public Vector2 getPosition() {
        return position;
    }

    public boolean isActive() {
        return active;
    }

    public float getAngle() {
        return angle;
    }

    public Bullet() {
        this.position = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
        this.active = false;
        this.angle = 0.0f;
        this.damage = 15;
    }

    public void deactivate() {
        active = false;
    }

    public void activate(float x, float y, float vx, float vy) {
        position.set(x, y);
        velocity.set(vx, vy);
        active = true;
    }

    public void update(float dt) {
        velocity.y -= TanksGame.GLOBAL_GRAVITY * dt;
        angle = velocity.angle();
        position.mulAdd(velocity, dt);
    }

    //проверка на столкновение с танком
    public boolean checkTankCollision(Tank tank) {
        if (Math.abs(position.x - tank.getPosition().x - 32) <= (8 + 20) && Math.abs(position.y - tank.getPosition().y - 40) <= (8 + 15))
            return true;
        return false;
    }

    public int getDamage() {
        return damage;
    }
}
