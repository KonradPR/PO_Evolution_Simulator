package project_animals;
import org.json.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonReader {

    private int mapWidth = 0;
    private int mapHeight = 0;
    private double jungleRatio = 0;
    private int dailyEnergyLoss = 0;
    private int numberOfStartingAnimals = 0;
    private int plantEnergy = 0;
    private int startingEnergy = 0;

    public JsonReader(){

        String path = "src/project_animals/settings.json";
        String arguments= null;
        try {
            arguments = new String(Files.readAllBytes(Paths.get(path)));
            JSONObject jObject = new JSONObject(arguments);
            this.startingEnergy = jObject.getInt("startingEnergy");
            this.plantEnergy = jObject.getInt("plantEnergy");
            this.numberOfStartingAnimals = jObject.getInt("numberOfStartingAnimals");
            this.dailyEnergyLoss = jObject.getInt("dailyEnergyLoss");
            this.jungleRatio = jObject.getDouble("jungleRatio");
            this.mapWidth = jObject.getInt("mapWidth");
            this.mapHeight = jObject.getInt("mapHeight");

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        if(this.mapHeight<=0 || this.mapWidth<=0 || this.numberOfStartingAnimals<=0 || this.jungleRatio<=0 || this.startingEnergy<=0 || this.plantEnergy<=0 ||this.dailyEnergyLoss<=0){
            throw new IllegalArgumentException("Wrong value for some of the settings!");
        }
    }

    public int getDailyEnergyLoss() {
        return dailyEnergyLoss;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public double getJungleRatio() {
        return jungleRatio;
    };

    public int getNumberOfStartingAnimals() {
        return numberOfStartingAnimals;
    }

    public int getPlantEnergy() {
        return plantEnergy;
    }

    public int getStartingEnergy() {
        return startingEnergy;
    }
}
