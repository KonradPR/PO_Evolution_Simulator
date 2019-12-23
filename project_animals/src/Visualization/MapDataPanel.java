package Visualization;

import project_animals.Animal;
import project_animals.BasicMap;
import project_animals.IWorldMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MapDataPanel extends JPanel {
    IWorldMap map;
    Visualizer visualizer;
    JLabel avgAge;
    JLabel numberOfAnimals;
    JLabel numberOfPlants;
    JLabel avgEnergy;
    JLabel deadAnimals;
    JLabel definifgGenome;
    JLabel observedEnergy;
    JLabel observedGenome;
    JLabel observedChildren;

    private Animal observed;

    int num;

    public MapDataPanel(IWorldMap map, Visualizer visualizer, int num){
        this.map = map;
        this.visualizer= visualizer;
        this.avgAge = new JLabel("Average age is: " );
        this.setVisible(true);
        this.num = num;
        this.numberOfAnimals = new JLabel();
        this.numberOfPlants = new JLabel();
        this.avgEnergy = new JLabel();
        this.deadAnimals = new JLabel();
        this.definifgGenome = new JLabel();
        this.observedEnergy = new JLabel();
        this.observedChildren = new JLabel();
        this.observedGenome = new JLabel();
        this.observed =  ((BasicMap)map).getObserved();

    }

    public void initDataPanel(){
        this.setLayout(new GridLayout(6, 2));
        this.add(this.avgAge);
        JButton upadateButton = new JButton("Refresh Statistics");
        upadateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        this.add(numberOfAnimals);
        this.add(numberOfPlants);
        this.add(avgEnergy);
        this.add(deadAnimals);
        this.add(definifgGenome);
        this.add(observedEnergy);
        this.add(observedChildren);
        this.add(observedGenome);
        this.add(upadateButton);


    }

    public void paintComponent(Graphics g){
        //super.paintComponent(g);
        this.observed = ((BasicMap)map).getObserved();
        int energy = 0;
        int children = 0;
        int genome = -1;
        if(this.observed!=null){
            energy = observed.getEnergy();
            children = observed.getChildren();
            genome = observed.getDefiningGene();
        }
        this.setLocation((int)(num*visualizer.frame.getWidth()*0.5)+100, visualizer.frame.getHeight()-200);
        this.avgAge.setText("Average age is: " + map.getAvgAge());
        this.numberOfAnimals.setText("Number of animals: " + map.getAllAnimals().size());
        this.numberOfPlants.setText("Number of plants: " + map.getPlantPositions().size());
        this.avgEnergy.setText("Average energy is: " + map.getAvgEnergy());
        this.deadAnimals.setText("Already dead animals: " + map.deadAnimals());
        this.definifgGenome.setText("Defining genome: " + map.getDefiningGene());
        this.observedEnergy.setText("Energy: " + energy);
        this.observedChildren.setText("Children: " + children);
        this.observedGenome.setText("Genome: " + genome);


    }






}
