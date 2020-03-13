package com.nickdnepr.smallgame.core.objects.units;

import com.nickdnepr.smallgame.core.objects.BaseEntity;
import com.nickdnepr.smallgame.core.objects.EntityType;
import com.nickdnepr.smallgame.core.objects.UnitClass;
import com.nickdnepr.smallgame.core.objects.units.weapons.Weapon;

import java.util.ArrayList;

public class Unit extends BaseEntity {

    private UnitClass unitClass;
    private UnitType unitType;
    private double actionPoints;
    private double maxActionPoints;
    private int transportWeight;
    private boolean reinforced;
    private ArrayList<Weapon> weapons;

    public Unit(String name, int hp, int maxHp, int armor, int maxArmor, int defence, int maxDefence, int visionRadius, int maxVisionRadius, int visibilityCoefficient, int maxVisibilityCoefficient, UnitClass unitClass, UnitType unitType, double actionPoints, double maxActionPoints, int transportWeight, ArrayList<Weapon> weapons) {
        super(name, EntityType.UNIT, hp, maxHp, armor, maxArmor, defence, maxDefence, visionRadius, maxVisionRadius, visibilityCoefficient, maxVisibilityCoefficient);
        this.unitClass = unitClass;
        this.unitType = unitType;
        this.actionPoints = actionPoints;
        this.maxActionPoints = maxActionPoints;
        this.transportWeight = transportWeight;
        this.weapons = weapons;
    }

    @Override
    public void endTurn() {
        this.actionPoints = maxActionPoints;
    }

    public void reinforce(){

    }
}
