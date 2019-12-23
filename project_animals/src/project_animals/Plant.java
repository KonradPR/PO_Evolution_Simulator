package project_animals;

public class Plant {
    private Vector2d position;
    private int energyValue;

    public Plant(int energy, Vector2d position){
        this.energyValue = energy;
        this.position = position;
    }

    public Vector2d getPosition(){
        return position;
    }

    public int getEnergyValue() {
        return energyValue;
    }

    public String toString(){
        return "W";
    }

}
