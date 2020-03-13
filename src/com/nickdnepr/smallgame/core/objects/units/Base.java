package com.nickdnepr.smallgame.core.objects.units;

public abstract class Base {

    private long id;
    private String name;

    private static long genId = 0L;

    public Base(String name) {
        this.id = genId();
        this.name = name;
    }

    private static long genId() {
        return genId++;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
