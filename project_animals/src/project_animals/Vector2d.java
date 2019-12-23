package project_animals;

public class Vector2d implements Comparable<Vector2d>{
    public int x;
    public int y;

    public Vector2d(int x, int y){
        this.x=x;
        this.y=y;
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }


    public String toString(){
        return "(" + x + "," + y + ")";
    }

    public boolean precedes(Vector2d other){
        if(x<=other.x && y<=other.y) return true;
        return false;
    }

    public boolean follows(Vector2d other){
        if(x>=other.x && y>=other.y) return true;
        return false;
    }

    public Vector2d upperRight(Vector2d other){
        return new Vector2d(x>other.x?x:other.x,y>other.y?y:other.y);
    }

    public Vector2d lowerLeft(Vector2d other){
        return new Vector2d(x<other.x?x:other.x,y<other.y?y:other.y);
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(x+other.x,y+other.y);
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(x-other.x,y-other.y);
    }

    @Override
    public int hashCode() {
        int tmp =11;
        tmp += x*13;
        tmp += y*17;
        return tmp;
    }

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return (x==that.x && y==that.y);
    }

    public Vector2d opposite(){
        return new Vector2d(-x,-y);
    }

    public int compareTo(Vector2d other){
        if(x<other.x) return -1;
        if(x>other.x) return 1;
        return y>other.y? 1 : -1;
    }

    public Vector2d postMove(MapDirection dir){
        switch(dir){
            case NORTH:
                return this.add(new Vector2d(0,1));
            case NORTHEAST:
                return this.add(new Vector2d(1,1));
            case EAST:
                return this.add(new Vector2d(1,0));
            case SOUTHEAST:
                return this.add(new Vector2d(1,-1));
            case SOUTH:
                return this.add(new Vector2d(0,-1));
            case SOUTHWEST:
                return this.add(new Vector2d(-1,-1));
            case WEST:
                return this.add(new Vector2d(-1,0));
            case NORTHWEST:
                return this.add(new Vector2d(-1,1));
        }
        return this;
    }



}
