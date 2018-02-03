package com.tanks.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Tank {
    private Texture textureBase;
    private Texture textureTurret;
    private Texture textureTrack;
    private Texture textureBar;
    private Vector2 position;
    private Vector2 weaponPosition;
    private TanksGame game;
    private float turretAngle;
    private int hp;
    private int maxHp;
    private Circle hitArea;

    //сила выстрела
    private float deltaPower; //увеличение силы в ед. времени
    private float minPower;
    private float maxPower;
    private float currentPower;

    public Circle getHitArea() {
        return hitArea;
    }

    public Tank(TanksGame game, Vector2 position) {
        this.game = game;
        this.position = position;
        this.weaponPosition = new Vector2(position).add(0, 0);
        this.textureBase = new Texture("tankBody.png");
        this.textureTurret = new Texture("tankTurret.png");
        this.textureTrack = new Texture("tankTrack.png");
        this.textureBar = new Texture("grass.png");
        this.turretAngle = 0.0f;
        this.maxHp = 100;
        this.hp = maxHp;
        this.deltaPower = 200;
        this.minPower = 200;
        this.maxPower = 600;
        this.currentPower = minPower;
        this.hitArea = new Circle(new Vector2(0, 0), textureBase.getWidth() * 0.4f);
    }

    public void render(SpriteBatch batch) {
        batch.draw(textureTurret, weaponPosition.x, weaponPosition.y, textureTurret.getWidth() / 10, textureTurret.getHeight() / 2, textureTurret.getWidth(), textureTurret.getHeight(), 1, 1, turretAngle, 0, 0, textureTurret.getWidth(), textureTurret.getHeight(), false, false);
        batch.draw(textureTrack, position.x + 4, position.y);
        batch.draw(textureBase, position.x, position.y + textureTrack.getHeight() / 3);

        //отрисовка силы выстрела
        if (currentPower > minPower)
            batch.draw(textureBar, position.x, position.y + 60, 0, 0, 4, 4, (currentPower-minPower)/(maxPower-minPower) * 16, 1, 0, 12, 0, 4, 4, false, false);
        //отрисовка здоровья
        if (hp > 0) {
            batch.setColor(1, 0, 0, 1);
            batch.draw(textureBar, position.x, position.y + 64, 0, 0, 4, 4, (float) hp / maxHp * 16, 1, 0, 12, 0, 4, 4, false, false);
            batch.setColor(1, 1, 1, 1);
        }
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
        }
        weaponPosition.set(position).add(35, 45);
        this.hitArea.x = position.x + textureBase.getWidth() / 2;
        this.hitArea.y = position.y + textureBase.getHeight() / 2;

        //сила выстрела
        boolean spaceUp = false;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (currentPower < maxPower) currentPower += deltaPower * dt;
            else
                currentPower = maxPower;
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

    public boolean takeDamage(int dmg) {
        hp -= dmg;
        if (hp <= 0) return true;
        return false;
    }

    public boolean checkOnGroung() {
        for (int i = 25; i < 75; i+=10) {
            if (game.getMap().isGround(position.x + i, position.y + 25))
                return true;
        }
        return false;
    }

    public Vector2 getPosition() {
        return position;
    }


}
