package project_animals;

import java.util.List;
import java.util.Set;

public interface IWorldMap {


    /*This method takes position Animal wants to move to and returns position after move
    * i.e. it should wrap moves around map and check for obstacles acording to specification*/
    public Vector2d mapPosition(Vector2d pos);

    /*This method returns array of objects in a postion null if no objects are in particular position*/
    public Plant plantAt(Vector2d pos);

    public List<Animal> animalsAt(Vector2d pos);
    public List<Animal> healthiestAnimalsAt(Vector2d pos);

    /*This method places animal on map*/
    public void placeAnimal(Animal animal);

    public void removeAnimal(Animal animal);

    /*This method returns nearest position on which there are no animals if such position thoes not exist it
    * just returns back position give as argument*/
    public Vector2d nearestFreePosition(Vector2d pos);

    /*This method places plants on map*/
    public void growPlants();

    /*This method runs a day on a map*/
    public void run();

    /*This method removes plant from map after it has been eaten*/
    public void notifyPlantEaten(Vector2d pos);

    /*This method notifyes map that animal has died*/
    public void notifyAnimalDead(Animal a);

    /*This method notifies map taht an animal has been born*/
    public void notifyAnimalBorn(Animal a);

    /*This method puts random animals near map center*/
    public void seedMapCenterWithAnimals(int numOfAnimals, int startingEnergy);

    /*Methods used only with MapVisalizer to draw map*/
    public boolean isOccupied(Vector2d pos);
    public Object objectAt(Vector2d pos);

    public int deadAnimals();

    public String toString();

    public int getWidth();
    public int getHeight();
    public Vector2d getJungleLow();
    public Vector2d getJungleTop();
    public List<Animal> getAllAnimals();
    public Set<Vector2d> getPlantPositions();
    public int getAvgAge();
    public int getAvgEnergy();
    public int getDefiningGene();

    public IWorldMap clone();
    public void placePlant(Plant p);


}
