package Visualization;

import project_animals.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class Visualizer implements ActionListener {
    public JFrame frame;
    public IWorldMap mapFirst;
    public IWorldMap mapSecond;
    MapPanel mPanelFirst;
    MapDataPanel mdPanelFirst;
    MapPanel mPanelSecond;
    MapDataPanel mdPanelSecond;
    private int delay = 80;
    private boolean paused = false;
    Graphics tool;
    JPanel container = new JPanel();

    public Visualizer(IWorldMap map){
        this.mapFirst = map;
        frame = new JFrame("Evolution Simulator");
        this.tool = this.frame.getGraphics();
        this.mapSecond = mapFirst.clone();
    }

    public void run(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setSize(1800,1000);
        frame.setLocationRelativeTo(null);
        mPanelFirst = new MapPanel(mapFirst,this,0);
        mdPanelFirst = new MapDataPanel(mapFirst,this,0);
        mdPanelFirst.initDataPanel();
        mPanelSecond = new MapPanel(mapSecond,this,1);
        mdPanelSecond = new MapDataPanel(mapSecond,this,1);
        mdPanelSecond.initDataPanel();
        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(this);



        container.add(mdPanelFirst);
        container.add(pauseButton);
        container.add(mdPanelSecond);
        container.add(mPanelFirst);
        container.add(mPanelSecond);


        frame.add(container);

    }

    public void simulate(){

                while(mapFirst.getAllAnimals().size()>1||mapSecond.getAllAnimals().size()>1){

                    while(!this.paused) {
                        mapFirst.run();
                        mapSecond.run();
                        mPanelSecond.repaint();
                        mPanelFirst.repaint();

                        try {
                            TimeUnit.MILLISECONDS.sleep(delay);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


    @Override
    public void actionPerformed(ActionEvent e) {
        this.paused = !this.paused;
    }
}
