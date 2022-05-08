import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.Serializable;
import java.util.ArrayList;

public class Snake implements Serializable {

    private ArrayList<Integer> posx,posy;
    private ArrayList<Circle> balls;
    private int lengthofsnake;

    Pane pane;
    Scene scene;

    public ArrayList<Integer> getposx() {
        return posx;
    }

    public ArrayList<Integer> getPosy() {
        return posy;
    }

    public void setPosy(ArrayList<Integer> posy) {
        this.posy = posy;
    }

    public void setPosx(ArrayList<Integer> posx) {
        this.posx = posx;
    }

    public ArrayList<Circle> getBalls() {
        return balls;
    }

    public int getLengthofsnake() {
        return lengthofsnake;
    }

    public void setLengthofsnake(int lengthofsnake) {
        this.lengthofsnake = lengthofsnake;
    }

    public void reducelengthofsnake(){
        if(this.getLengthofsnake()<=0){
            return;
        }
        this.setLengthofsnake(this.getLengthofsnake() - 1);


        Circle ballremove = balls.get(this.getLengthofsnake());
        pane.getChildren().remove(ballremove);

        balls.remove(this.getLengthofsnake());
        posx.remove(this.getLengthofsnake());
        posy.remove(this.getLengthofsnake());
    }

    public void addball(){
        Circle balltemp = new Circle(10, Color.GREEN);
        balls.add(balltemp);
        int ballpx = this.getposx().get(this.getLengthofsnake() - 1);
        int ballpy = this.getPosy().get(this.getLengthofsnake() - 1);
        posx.add(ballpx);
        posy.add(ballpy + 20);
        this.setLengthofsnake(this.getLengthofsnake() + 1);
    }

    public Snake() {
        this.setLengthofsnake(12);
        this.posx = new ArrayList<Integer>();
        this.posy = new ArrayList<Integer>();
        this.balls = new ArrayList<Circle>();
        for(int i = 0;i < this.getLengthofsnake(); i++) posx.add(250);
        for(int i = 0; i < this.getLengthofsnake(); i++) posy.add(350 + i*20);
        make_snake();
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public void makeagain(){
        this.posx = new ArrayList<Integer>();
        this.posy = new ArrayList<Integer>();
        this.balls = new ArrayList<Circle>();
        for(int i = 0;i < this.getLengthofsnake(); i++) posx.add(250);
        for(int i = 0; i < this.getLengthofsnake(); i++) posy.add(350 + i*20);
        make_snake();
    }

    private void make_snake() {
        for(int i = 0; i < this.getLengthofsnake(); i++){
            Circle balltemp = new Circle(10, Color.GREEN);
            balls.add(balltemp);
        }
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}