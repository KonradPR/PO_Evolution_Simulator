package project_animals;

import java.util.Random;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public MapDirection next(){
        return  this.ordinal()< MapDirection.values().length -1 ? MapDirection.values()[this.ordinal()+1] : NORTH;
    }

    public MapDirection rotateBy(int i){
        MapDirection tmp = this;
        for(int j = 0; j<i;j++)tmp=tmp.next();

        return tmp;
    }

    static public MapDirection randomDirection(){
        Random rand = new Random();
        return MapDirection.values()[rand.nextInt(8)];
    }
}