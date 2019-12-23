package project_animals;

import java.awt.*;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Animal {
    private int energy;
    private Genome genome;
    private Vector2d position;
    private IWorldMap map;
    private MapDirection orientation;
    private static Random rand = new Random();
    private int startingEnergy;
    private int age = 1;
    private int children = 0;

    public Animal(int energy, int[] genome, Vector2d position, IWorldMap map, MapDirection orientation, int startingEnergy){
        this.energy = energy;
        this.position = position;
        this.genome = Genome.fromArray(genome);
        this.map = map;
        this.orientation = orientation;
        this.startingEnergy = startingEnergy;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void move(){
        rotate();
        Vector2d tmp = this.getPosition();
        map.removeAnimal(this);
        this.position = map.mapPosition(this.getPosition().postMove(orientation));
        map.placeAnimal(this);
    }

    public void eat(){

            Plant toEat = map.plantAt(this.getPosition());
            if (toEat != null) {
                List<Animal> healthiestanimalsAt = this.map.healthiestAnimalsAt(this.getPosition());
                int energyToAssign = toEat.getEnergyValue() / healthiestanimalsAt.size();
                Iterator<Animal> it = healthiestanimalsAt.iterator();
                Animal tmp;
                this.map.notifyPlantEaten(toEat.getPosition());
                while (it.hasNext()) {
                    tmp = it.next();
                    tmp.energy = tmp.getEnergy() + energyToAssign;
                }
            }
    }

    public void dayEnergyExpenditure(int expended){
        this.age++;
        this.energy -= expended;
        if(this.energy<=0) this.map.notifyAnimalDead(this);
    }

    public void mate(){
        Animal bestPartner = chosePartner();
        if(bestPartner!=null &&this.canMate() && bestPartner.canMate()){
            int tmp_energy = bestPartner.mateEnergyExpenditure() + this.mateEnergyExpenditure();
            Genome tmp_genome = this.genome.combine(bestPartner.genome);
            map.notifyAnimalBorn(new Animal(tmp_energy,tmp_genome.toArray(),map.nearestFreePosition(this.getPosition()),map,orientation,this.startingEnergy));
            children++;
        }
    }

    public String toString(){
        return "@";
    }

    private void rotate(){
        int chosen_rotation = rand.nextInt(32);
        orientation = orientation.rotateBy(genome.getGene(chosen_rotation));
    }

    private Animal chosePartner(){

        List<Animal> matingPartners = map.animalsAt(position);
        matingPartners.remove(this);
        Iterator<Animal> it= matingPartners.iterator();
        Animal bestPartner = null;
        Animal potentialPartner = null;
        while(it.hasNext()){
            potentialPartner = it.next();
            if(bestPartner!=null){
                if(bestPartner.energy<=potentialPartner.energy) bestPartner = potentialPartner;
            }else{
                bestPartner = potentialPartner;
            }
        }

        return bestPartner;
    }

    private boolean canMate(){
        return energy/startingEnergy >=1;
    }

    private  int mateEnergyExpenditure(){
        int tmp = energy/4;
        energy-= tmp;
        return tmp;
    }

    public int getEnergy(){
        return this.energy;
    }
    public Color getColor(){
        if(this.energy/startingEnergy>=3) return new Color(0,200,0);
        if(this.energy/startingEnergy>=2) return new Color(160,180,0);
        if(this.energy/startingEnergy>=1) return  new Color(140,120,0);
        return new Color(200,20,0);
    }

    public int getAge(){
        return this.age;
    }

    public int getChildren(){
        return this.children;
    }

    public int getDefiningGene(){
        return this.genome.getDefiningGene();
    }

    public String showGenome(){
        return this.genome.toString();
    }

    public Animal clone(IWorldMap map){
        Animal tmp = new Animal(energy,genome.toArray(),position,map,orientation,startingEnergy);
        return tmp;
    }
}
