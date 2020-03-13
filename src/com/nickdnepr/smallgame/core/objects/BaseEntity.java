package com.nickdnepr.smallgame.core.objects;

import com.nickdnepr.smallgame.core.objects.units.Base;
import com.nickdnepr.smallgame.core.objects.units.weapons.Shell;

public abstract class BaseEntity extends Base {

    private EntityType entityType;
    private int hp;
    private int maxHp;
    private int armor;
    private int maxArmor;
    private int defence;
    private int maxDefence;
    private int visionRadius;
    private int maxVisionRadius;
    private int visibilityCoefficient;
    private int maxVisibilityCoefficient;

    public BaseEntity(String name, EntityType entityType, int hp, int maxHp, int armor, int maxArmor, int defence, int maxDefence, int visionRadius, int maxVisionRadius, int visibilityCoefficient, int maxVisibilityCoefficient) {
        super(name);
        this.entityType = entityType;
        this.hp = hp;
        this.maxHp = maxHp;
        this.armor = armor;
        this.maxArmor = maxArmor;
        this.defence = defence;
        this.maxDefence = maxDefence;
        this.visionRadius = visionRadius;
        this.maxVisionRadius = maxVisionRadius;
        this.visibilityCoefficient = visibilityCoefficient;
        this.maxVisibilityCoefficient = maxVisibilityCoefficient;
    }

    public abstract void endTurn();

    public void takeDamage(Shell shell) {

    }

    public boolean isDead() {
        return hp <= 0;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getMaxArmor() {
        return maxArmor;
    }

    public void setMaxArmor(int maxArmor) {
        this.maxArmor = maxArmor;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getMaxDefence() {
        return maxDefence;
    }

    public void setMaxDefence(int maxDefence) {
        this.maxDefence = maxDefence;
    }

    public int getVisionRadius() {
        return visionRadius;
    }

    public void setVisionRadius(int visionRadius) {
        this.visionRadius = visionRadius;
    }

    public int getMaxVisionRadius() {
        return maxVisionRadius;
    }

    public void setMaxVisionRadius(int maxVisionRadius) {
        this.maxVisionRadius = maxVisionRadius;
    }

    public int getVisibilityCoefficient() {
        return visibilityCoefficient;
    }

    public void setVisibilityCoefficient(int visibilityCoefficient) {
        this.visibilityCoefficient = visibilityCoefficient;
    }

    public int getMaxVisibilityCoefficient() {
        return maxVisibilityCoefficient;
    }

    public void setMaxVisibilityCoefficient(int maxVisibilityCoefficient) {
        this.maxVisibilityCoefficient = maxVisibilityCoefficient;
    }
}
