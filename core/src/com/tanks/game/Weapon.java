package com.tanks.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Weapon {
    private boolean gravity;
    private boolean bouncing;
    private float damage;
    private WeaponType weaponType;
    private List<WeaponType> weaponTypeList;

    public Weapon(WeaponType weaponType) {
        this.weaponTypeList = Arrays.asList(WeaponType.values());
        setup(weaponType);
    }

    public void setup(WeaponType weaponType) {
        this.weaponType = weaponType;
        switch (weaponType) {
            case LASER:
                gravity = false;
                bouncing = false;
                damage = 15.0f;
                break;
            case ROCKET:
                gravity = true;
                bouncing = true;
                damage = 20.0f;
                break;
            default:
                this.weaponType = WeaponType.ROCKET;
                gravity = false;
                bouncing = false;
                damage = 15.0f;
        }

    }

    //получение настроек
    public boolean isGravity() {
        return gravity;
    }

    public boolean isBouncing() {
        return bouncing;
    }

    public float getDamage() {
        return damage;
    }

    public String getWeaponType() {
        return weaponType.toString();
    }

    //получить следующее оружие
    public WeaponType getNextWeapon() {
        int index = weaponTypeList.indexOf(weaponType);
        index++;
        if (index >= weaponTypeList.size())
            return weaponTypeList.get(0);
        else
            return weaponTypeList.get(index);
    }
}
