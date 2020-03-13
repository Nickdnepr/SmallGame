package com.nickdnepr.smallgame.core.objects.units.weapons;

import com.nickdnepr.smallgame.core.objects.units.UnitType;

import java.util.ArrayList;
import java.util.HashMap;

public class Weapon {
    private ArrayList<UnitType> targetTypes;
    private HashMap<UnitType, Double> attackRanges;
    private double actionPointsCost;
    private HashMap<Shell, Integer> ammo;
    private int maxShells;
    private int accuracy;


}
