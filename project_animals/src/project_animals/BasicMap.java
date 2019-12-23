package project_animals;

import javax.print.attribute.standard.JobHoldUntil;
import java.util.*;

import static java.lang.Math.sqrt;


public class BasicMap implements IWorldMap {
    private int dailyEnergyLoss;
    private int plantEnergyValue;
    private int height;
    private int width;
    private float jungleRatio;
    private Vector2d jungleLow;
    private Vector2d jungleTop;
    private Map<Vector2d,List<Animal>> animals = new HashMap<Vector2d, List<Animal>>();
    private List<Animal> corpses = new LinkedList<>();
    private List<Animal> younglings = new LinkedList<>();
    private Map<Vector2d,Plant> plants = new LinkedHashMap<>();
    private static Random rand = new Random();
    private int deadNum = 0;
    private int avgAge = 0;
    private int avgEnergy = 0;
    private int[] genomes = new int[8];
    private Animal observed = null;

    public BasicMap(int width, int height, int plantEnergyValue, float jungleRatio, int dailyEnergyLoss){
        this.width = width;
        this.height = height;
        this.dailyEnergyLoss = dailyEnergyLoss;
        this.plantEnergyValue = plantEnergyValue;
        this.jungleRatio = jungleRatio;

        int x = (int) (width*jungleRatio);
        int y = (int) (height*jungleRatio);

        jungleLow = new Vector2d((width-x)/2,(height-y)/2);
        jungleTop = jungleLow.add(new Vector2d(x,y));

    }

    public Vector2d mapPosition(Vector2d pos){
        int x = pos.getX();
        int y = pos.getY();

        x%=width;
        y%=height;

        if(x<0) x=width;
        if(y<0) y=height;

        return new Vector2d(x,y);
    }

    @Override
    public void placeAnimal(Animal animal) {
        if(animals.get(animal.getPosition())==null){
            List<Animal> li = new LinkedList<>();
            animals.put(animal.getPosition(),li);
        }
        animals.get(animal.getPosition()).add(animal);

    }

    @Override
    public void growPlants() {
        int tries = 0;
        Vector2d position = randomStepPosition();
        while(plants.containsKey(position)&&tries<300) {
            position = randomStepPosition();
            tries++;
        }
        if(!plants.containsKey(position))
            plants.put(position, new Plant(plantEnergyValue,position));

        tries = 0;
        position = randomJunglePosition();
        while(plants.containsKey(position)&&tries<300) {
            position = randomJunglePosition();
            tries++;
        }
        if(!plants.containsKey(position))
            plants.put(position, new Plant(plantEnergyValue,position));
    }


    private Vector2d randomJunglePosition(){
        return new Vector2d(random(jungleLow.getX(),jungleTop.getX()),random(jungleLow.getY(),jungleTop.getY()));
    }

    private Vector2d randomStepPosition(){

        int area = random(0,3);

        int x,y;
        switch (area) {
            case 0:
                x = random(0, 2 * jungleLow.getX());
                if (x >= jungleLow.getX()) {
                    x = jungleTop.getX() + x % jungleLow.getX()+1;
                }
                y = random(jungleLow.getY(), jungleTop.getY());
                break;
            case 1:
                x = random(jungleLow.getX(), jungleTop.getX());
                y = random(0, 2 * jungleLow.getX());
                if (y >= jungleLow.getY()) {
                    y = jungleTop.getY() + y % jungleLow.getY()+1;
                }
                break;
            default:
            x = random(0, 2 * jungleLow.getX());
            if (x >= jungleLow.getX()) {
                x = jungleTop.getX() + x % jungleLow.getX()+1;
            }

            y = random(0, 2 * jungleLow.getX());
            if (y >= jungleLow.getY()) {
                y = jungleTop.getY() + y % jungleLow.getY()+1;
            }
        }

        return new Vector2d(x,y);
    }

    static private int random(int low, int high){
        return low + rand.nextInt(high-low+1);
    }

    public Plant plantAt(Vector2d pos){
        if(!plants.containsKey(pos)) return null;
        return plants.get(pos);
    }

    public void notifyPlantEaten(Vector2d pos){
        plants.remove(pos);
    }

    public void notifyAnimalDead(Animal a){
        corpses.add(a);
    }

    public void notifyAnimalBorn(Animal a){
        younglings.add(a);
    }

    private void removeCorpses(){
        Animal tmp = null;
        Iterator<Animal> it = corpses.iterator();
        while(it.hasNext()){
            tmp = it.next();
            animals.get(tmp.getPosition()).remove(tmp);
            this.genomes[tmp.getDefiningGene()]--;
            it.remove();
            this.deadNum++;
        }
    }

    private void placeYounglings(){
        Animal tmp = null;
        Iterator<Animal> it = younglings.iterator();
        while(it.hasNext()){
            tmp = it.next();
            this.placeAnimal(tmp);
            this.genomes[tmp.getDefiningGene()]++;
            it.remove();
        }
    }

    @Override
    public void run() {
        removeCorpses();
        moveAnimals();
        eatAnimals();
        mateAnimals();
        takeEnergyFromAnimals();
        growPlants();
        placeYounglings();
    }

    private void moveAnimals(){
        int sum = 0;
        int eSum = 0;
        Iterator<Animal> it= getAllAnimals().iterator();
        Animal tmp = null;
        while(it.hasNext()){
            tmp = it.next();
            tmp.move();
            sum+=tmp.getAge();
            eSum+=tmp.getEnergy();
        }
        avgAge = sum / getAllAnimals().size();
        avgEnergy = eSum / getAllAnimals().size();
    }

    private void eatAnimals(){
        Iterator<Animal> it= getAllAnimals().iterator();
        Animal tmp = null;
        while(it.hasNext()){
            tmp = it.next();
            tmp.eat();
        }
    }

    private void mateAnimals(){
        Iterator<Animal> it= getAllAnimals().iterator();
        Animal tmp = null;
        while(it.hasNext()){
            tmp = it.next();
            tmp.mate();
        }
    }

    private void takeEnergyFromAnimals(){
        Iterator<Animal> it= getAllAnimals().iterator();
        Animal tmp = null;
        while(it.hasNext()){
            tmp = it.next();
            tmp.dayEnergyExpenditure(dailyEnergyLoss);
        }
    }

    @Override
    public List<Animal> animalsAt(Vector2d pos) {
        List<Animal> li = new LinkedList<Animal>();
        if(animals.get(pos)!=null)li.addAll(animals.get(pos));
        return li;
    }

    @Override
    public boolean isOccupied(Vector2d pos) {
        if(plantAt(pos)!=null) return true;
        if(animalsAt(pos).size()!=0) return true;
        return false;
    }

    @Override
    public Vector2d nearestFreePosition(Vector2d pos){
        for(int i=0;i<8;i++){
            Vector2d tmp = this.mapPosition(pos.postMove(MapDirection.values()[i]));
            if(this.animalsAt(tmp).size()==0) return tmp;
        }

        return pos;
    }

   public void seedMapCenterWithAnimals(int numOfAnimals, int startingEnergy){
        for(int i=0;i<numOfAnimals;i++){
            Animal tmp = new Animal(startingEnergy,Genome.radnomGenome().toArray(),randomJunglePosition(),this,MapDirection.randomDirection(),startingEnergy);
            placeAnimal(tmp);
            this.genomes[tmp.getDefiningGene()]++;
        }
   }

    @Override
    public Object objectAt(Vector2d pos) {
        if(plantAt(pos)!=null) return plantAt(pos);
        if(animalsAt(pos).size()==1) return animalsAt(pos).get(0);
        return Integer.valueOf(animalsAt(pos).size());
    }

    public String toString(){
        MapVisualizer tool = new MapVisualizer(this);
        System.out.println("Number of dead animals: "+this.deadAnimals());
        return tool.draw(new Vector2d(0,0), new Vector2d(width,height));
    }

    public List<Animal> healthiestAnimalsAt(Vector2d pos){
        List<Animal> animalList = this.animalsAt(pos);
        if(animalList.size()==0) return animalList;
        Iterator<Animal> it = animalList.iterator();
        Animal tmp;
        List<Animal> hAnimalsAt = new LinkedList<>();
        hAnimalsAt.add(it.next());
        while(it.hasNext()){
            tmp = it.next();
            if(hAnimalsAt.get(0).getEnergy()<tmp.getEnergy()){
                hAnimalsAt.clear();
                hAnimalsAt.add(tmp);
            }else if(hAnimalsAt.get(0).getEnergy()==tmp.getEnergy()){
                hAnimalsAt.add(tmp);
            }
        }
        return hAnimalsAt;
    }

    @Override
    public int deadAnimals() {
        return this.deadNum;
    }

    public List<Animal> getAllAnimals(){
        List<Animal> li  = new LinkedList<Animal>();
        for(List<Animal> tmpList : this.animals.values()){
            li.addAll(tmpList);
        }
        return li;
    }

    public void removeAnimal(Animal animal){
        animals.get(animal.getPosition()).remove(animal);
    }

    @Override
    public int getWidth() {
        return width;
    }

    public int getHeight(){
        return height;
    }

    public Vector2d getJungleLow(){
        return jungleLow;
    }

    public Vector2d getJungleTop(){
        return  jungleTop;
    }

    public Set<Vector2d> getPlantPositions(){
        return plants.keySet();
    }

    @Override
    public int getAvgAge() {
        return avgAge;
    }

    @Override
    public int getAvgEnergy() {
        return avgEnergy;
    }

    @Override
    public int getDefiningGene() {
        int tmp = genomes[0];
        int index = 0;
        for(int i=1;i<genomes.length;i++){
            if(tmp<genomes[i]){
                tmp = genomes[i];
                index = i;
            }
        }

        return index;
    }

    public void placePlant(Plant p){
        this.plants.put(p.getPosition(),p);
    }

    public void setObserved(Animal animal){
        this.observed = animal;
    }

    public Animal getObserved(){
        if(observed==null) return observed;
        if(observed.getEnergy()==0)observed = null;
        return observed;
    }

    public IWorldMap clone(){
        IWorldMap cloned = new BasicMap(width,height,plantEnergyValue,jungleRatio,dailyEnergyLoss);
        List<Animal> animals = this.getAllAnimals();
        for(Animal animal : animals){
            Animal tmp = animal.clone(cloned);
            cloned.placeAnimal(tmp);
        }

        Set<Vector2d> plants = this.getPlantPositions();
        for(Vector2d pos: plants){
            cloned.placePlant(new Plant(plantEnergyValue,pos));
        }

        return cloned;
    }
}
