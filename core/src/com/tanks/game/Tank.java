package com.tanks.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Tank {
    private Texture textureBase;
    private Texture textureWeapon;
    private Texture textureBar;
    private Vector2 position;
    private Vector2 weaponPosition;
    private TanksGame game;
    private float turretAngle;
    private int hp;
    private int maxHp;

    //сила выстрела
    private float deltaPower; //увеличение силы в ед. времени
    private float minPower;
    private float maxPower;
    private float currentPower;

    public Tank(TanksGame game, Vector2 position) {
        this.game = game;
        this.position = position;
        this.weaponPosition = new Vector2(position).add(20, 26);
        this.textureBase = new Texture("tank.png");
        this.textureWeapon = new Texture("weapon.png");
        this.textureBar = new Texture("grass.png");
        this.turretAngle = 0.0f;
        this.maxHp = 100;
        this.hp = maxHp;
        this.deltaPower = 200;
        this.minPower = 200;
        this.maxPower = 600;
        this.currentPower = minPower;
    }

    public void render(SpriteBatch batch) {
        batch.draw(textureWeapon, weaponPosition.x, weaponPosition.y, 12, 16, 64, 32, 1, 1, turretAngle, 0, 0, 64, 32, false, false);
        batch.draw(textureBase, position.x, position.y);

        //отрисовка силы выстрела
        if (currentPower > minPower)
            batch.draw(textureBar, position.x, position.y + 60, 0, 0, 4, 4, currentPower/maxPower * 16, 1, 0, 4, 0, 4, 4, false, false);
    }

    public void rotateTurret(int n, float dt) {
        turretAngle += n * 90.0f * dt;
    }

    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            rotateTurret(1, dt);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            rotateTurret(-1, dt);
        }

        if (!checkOnGroung()) {
            position.y -= TanksGame.GLOBAL_GRAVITY * dt;
            weaponPosition.set(position).add(20, 26);
        }

        //сила выстрела
        boolean spaceUp = false;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (currentPower < maxPower) currentPower += deltaPower * dt;
            spaceUp = true;
        }

        if (currentPower > minPower && !spaceUp) {
            float ammoPosX = weaponPosition.x + 12 + 28 * (float)Math.cos(Math.toRadians(turretAngle));
            float ammoPosY = weaponPosition.y + 16 + 28 * (float)Math.sin(Math.toRadians(turretAngle));
            float ammoVelX = currentPower * (float)Math.cos(Math.toRadians(turretAngle));
            float ammoVelY = currentPower * (float)Math.sin(Math.toRadians(turretAngle));

            game.getBulletEmitter().setup(ammoPosX, ammoPosY, ammoVelX, ammoVelY);
            currentPower = minPower;
        }
    }

    public boolean checkOnGroung() {
        for (int i = 25; i < 75; i+=10) {
            if (game.getMap().isGround(position.x + i, position.y + 25))
                return true;
        }
        return false;
    }

}
