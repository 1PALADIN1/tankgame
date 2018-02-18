package com.tanks.game;

import com.badlogic.gdx.math.Vector2;

public class PlayerTank extends Tank {
    public enum Action {
        IDLE, MOVE_LEFT, MOVE_RIGHT, TURRET_UP, TURRET_DOWN, FIRE, NEXT_WEAPON, PREV_WEAPON
    }

    private Action currentAction;
    private BulletEmitter.BulletType currentWeapon;

    public void setCurrentAction(Action currentAction) {
        this.currentAction = currentAction;
    }

    public PlayerTank(GameScreen game, Vector2 position) {
        super(game, position);
        this.currentWeapon = getCurrentWeapon();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (game.isMyTurn(this) && !makeTurn) {
            if (currentAction == Action.TURRET_UP) {
                rotateTurret(1, dt);
            }
            if (currentAction == Action.TURRET_DOWN) {
                rotateTurret(-1, dt);
            }
            if (currentAction == Action.MOVE_LEFT) {
                move(-1, dt);
            }
            if (currentAction == Action.MOVE_RIGHT) {
                move(1, dt);
            }

            if (currentAction == Action.NEXT_WEAPON) {
                currentWeapon = getNextWeapon();
                currentAction = Action.IDLE;
            }
            if (currentAction == Action.PREV_WEAPON) {
                currentWeapon = getPreviousWeapon();
                currentAction = Action.IDLE;
            }

            if (currentAction == Action.FIRE) {
                if (power < MINIMAL_POWER) {
                    power = MINIMAL_POWER + 1.0f;
                } else {
                    power += 400.0f * dt;
                    if (power > maxPower) {
                        power = maxPower;
                    }
                }
            } else {
                if (power > MINIMAL_POWER) {
                    float ammoPosX = weaponPosition.x + 4 + 28 * (float) Math.cos(Math.toRadians(turretAngle));
                    float ammoPosY = weaponPosition.y + 6 + 28 * (float) Math.sin(Math.toRadians(turretAngle));

                    float ammoVelX = power * (float) Math.cos(Math.toRadians(turretAngle));
                    float ammoVelY = power * (float) Math.sin(Math.toRadians(turretAngle));

                    game.getBulletEmitter().setup(currentWeapon, ammoPosX, ammoPosY, ammoVelX, ammoVelY);

                    power = 0.0f;

                    makeTurn = true;
                    currentAction = Action.IDLE;
                }
            }
//            currentAction = Action.IDLE;
        }
    }
}
