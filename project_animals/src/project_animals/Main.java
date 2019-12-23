package project_animals;


import Visualization.Visualizer;


public class Main {

    public static void main(String[] args) {
	JsonReader reader = new JsonReader();


	IWorldMap map = new BasicMap(reader.getMapWidth(),reader.getMapHeight(),reader.getPlantEnergy(),(float) reader.getJungleRatio(),reader.getDailyEnergyLoss());
	for(int i=0;i<30;i++)map.growPlants();
	map.seedMapCenterWithAnimals(reader.getNumberOfStartingAnimals(),reader.getStartingEnergy());
	Visualizer mapVisualizer = new Visualizer(map);
	mapVisualizer.run();
	mapVisualizer.simulate();
	//System.out.println(map.toString());
	//for(int i=0;i<3000;i++){

	//	if(i%10==0) System.out.println(map.toString());
	//	map.run();
	//}

	//System.out.println(map.toString());

    }
}
