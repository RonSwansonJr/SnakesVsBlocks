import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Blocks implements Serializable {

    private ArrayList< Integer > rect_posx;
    private ArrayList<Rectangle> rectangles;
    private int startx,starty;

    public int getStartx() {
        return startx;
    }

    public int getStarty() {
        return starty;
    }

    public ArrayList<Integer> getRect_posx() {
        return rect_posx;
    }

    public ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }

    public Blocks() {
        this.rect_posx = new ArrayList<Integer>();
        this.rectangles = new ArrayList<Rectangle>();
        this.startx=0;
        this.starty=-169;
        make_row();
        design_rect();

    }

    private void design_rect() {
        for(int i=0;i<rectangles.size();i++) {
            rectangles.get(i).setArcHeight(10.0d);
            rectangles.get(i).setArcWidth(10.0d);
            rectangles.get(i).setX(startx);
            rect_posx.add(startx);
            rectangles.get(i).setY(starty);
            startx += 100;
        }
        startx=0;
    }

    private void make_row() {
        for(int i=0;i<5;i++){
            rectangles.add(spawn_rect());
        }
    }

    private Rectangle spawn_rect() {
        Random obj = new Random();
        int val = obj.nextInt(5)+1;
        Rectangle rectangle = new Rectangle(100,65);
        if(val==1)rectangle.setFill(Color.GRAY);
        else if(val==2)rectangle.setFill(Color.TAN);
        else if(val==3)rectangle.setFill(Color.BLUE);
        else if(val==4)rectangle.setFill(Color.RED);
        else rectangle.setFill(Color.GREEN);
        return  rectangle;
    }
}