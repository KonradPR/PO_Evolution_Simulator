package Visualization;

import project_animals.Animal;
import project_animals.BasicMap;
import project_animals.IWorldMap;
import project_animals.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Set;

public class MapPanel  extends JPanel implements MouseListener {

    IWorldMap map;
    Visualizer visualizer;
    int num;
    Animal observed = null;

    public MapPanel(IWorldMap map, Visualizer visualizer, int num){
        this.map = map;
        this.visualizer= visualizer;
        addMouseListener(this);
        this.setVisible(true);
        this.num = num;

    }

    public void paintComponent(Graphics tool){
        if(observed!=null&&observed.getEnergy()==0) observed = null;
        this.setVisible(true);
        this.setSize((int) (visualizer.frame.getWidth()*0.4) ,(int)(visualizer.frame.getHeight()*0.8));
        //this.setLocation((int)(num*visualizer.frame.getWidth()*0.45),0);
        this.setLocation((int)(num*visualizer.frame.getWidth()*0.59),0);
        int x = this.getX();
        int y = this.getY();
        int scaleH = this.getHeight() / map.getHeight();
        int scaleW = this.getWidth() / map.getWidth();
        int jungleLowX = map.getJungleLow().getX();
        int jungleLowY = map.getJungleLow().getY();
        int jungleWidth =  map.getJungleTop().getX() - jungleLowX;
        int jungleHeight =  map.getJungleTop().getY() - jungleLowY;
        tool.setColor(new Color(200,200,80));
        tool.fillRect(0,0,this.getWidth(),this.getHeight());

        tool.setColor(new Color(0,100,70));
        tool.fillRect(jungleLowX*scaleW,
                      jungleLowY*scaleW,
                   jungleWidth*scaleW,
                   jungleHeight*scaleH);

        Set<Vector2d> plants = map.getPlantPositions();
        tool.setColor(new Color(0,240,100));
        for(Vector2d pos : plants){
            tool.fillRect(pos.getX()*scaleW,pos.getY()*scaleH,scaleW,scaleH);
        }

        List<Animal> animals = map.getAllAnimals();
        for(Animal animal : animals){
            tool.setColor(animal.getColor());
            tool.fillOval(animal.getPosition().getX()*scaleW,animal.getPosition().getY()*scaleH,scaleW,scaleH);
        }

        if(observed!=null){
            tool.setColor(new Color(0,250,250));
            tool.fillOval(observed.getPosition().getX()*scaleW,observed.getPosition().getY()*scaleH,scaleW,scaleH);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX() / (this.getWidth() / map.getWidth());
        int y = e.getY() / (this.getHeight() / map.getHeight());

        if(map.healthiestAnimalsAt(new Vector2d(x,y)).size()>0) {
            Animal toObserve = map.healthiestAnimalsAt(new Vector2d(x, y)).get(0);
            observed = toObserve;
            ((BasicMap)map).setObserved(toObserve);
            System.out.print(toObserve.toString());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
