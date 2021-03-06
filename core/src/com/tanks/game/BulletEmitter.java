package com.tanks.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class BulletEmitter extends ObjectPool<Bullet> {
    public enum BulletType {
        LIGHT_AMMO(ParticleEmitter.BulletEffectType.FIRE, true, true, 32, 5.0f, 40, "FIRE GUN"),
        LASER(ParticleEmitter.BulletEffectType.LASER, false, true, 1, 10.0f, 20, "LASER"),
        ICE_AMMO(ParticleEmitter.BulletEffectType.ICE_FIRE, true, true, 15, 5.0f, 35, "ICE GUN");

        private ParticleEmitter.BulletEffectType effect;
        private boolean gravity;
        private boolean bouncing;
        private int groundClearingSize;
        private float maxTime;
        private int damage;
        private String ammoName;

        public ParticleEmitter.BulletEffectType getEffect() {
            return effect;
        }

        public boolean isGravity() {
            return gravity;
        }

        public boolean isBouncing() {
            return bouncing;
        }

        public int getGroundClearingSize() {
            return groundClearingSize;
        }

        public float getMaxTime() {
            return maxTime;
        }

        public int getDamage() {
            return damage;
        }

        public String getAmmoName() {
            return ammoName;
        }

        BulletType(ParticleEmitter.BulletEffectType effect, boolean gravity, boolean bouncing, int groundClearingSize, float maxTime, int damage, String ammoName) {
            this.effect = effect;
            this.gravity = gravity;
            this.bouncing = bouncing;
            this.groundClearingSize = groundClearingSize;
            this.maxTime = maxTime;
            this.damage = damage;
            this.ammoName = ammoName;
        }
    }

    private GameScreen game;
    private TextureRegion bulletTexture;

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }

    public BulletEmitter(GameScreen game, int size) {
        super(size);
        this.game = game;
        bulletTexture = Assets.getInstance().getAtlas().findRegion("ammo");
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            batch.draw(bulletTexture, activeList.get(i).getPosition().x - 8, activeList.get(i).getPosition().y - 8);
        }
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            updateBullet(activeList.get(i), dt, false);
        }
    }

    Vector2 v2tmp = new Vector2(0, 0);

    public void updateBullet(Bullet b, float dt, boolean virtual) {
        b.addTime(dt);
        if (b.getType().isGravity()) {
            b.getVelocity().y -= GameScreen.GLOBAL_GRAVITY * dt;
        }

        v2tmp.set(b.getVelocity()).scl(dt);
        float len = v2tmp.len();
        v2tmp.nor();
        for (int i = 0; i < len; i++) {
            b.getPosition().add(v2tmp);
            if (!virtual && b.getType().getEffect() != ParticleEmitter.BulletEffectType.NONE) {
                // if (b.getType().getEffect() != ParticleEmitter.BulletEffectType.NONE) {
                game.getParticleEmitter().makeBulletEffect(b.getType().getEffect(), b.getPosition().x, b.getPosition().y);
            }
        }

//        v2tmp.set(b.getPosition());
//        b.getPosition().mulAdd(b.getVelocity(), dt);
//        int length = (int) Vector2.dst(v2tmp.x, v2tmp.y, b.getPosition().x, b.getPosition().y);
//        v2tmp.nor();
//        if (!virtual && b.getType().getEffect() != ParticleEmitter.BulletEffectType.NONE) {
//            for (int i = 0; i < length; i++) {
//                game.getParticleEmitter().makeBulletEffect(b.getType().getEffect(), b.getPosition().x, b.getPosition().y);
//            }
//        }
    }

    public boolean empty() {
        return getActiveList().size() == 0;
    }

    public Bullet setup(BulletType type, float x, float y, float vx, float vy) {
        Bullet b = getActiveElement();
        b.activate(type, x, y, vx, vy);
        return b;
    }
}
