import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.WritableIntegerValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

public class Collision {

    private Snake snake;
    private Blocks blocks;
    private ArrayList<Text> texts;

    Boolean intersect = false;
    Timeline timeline;

    private Pane pane;

    Collision(){
//        timeline = new Timeline();
    }

    public void collide(){

        timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("this is called every 5 seconds on UI thread");
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        boom();
    }

    public void increments(){

    }

    public void boom(){

        Circle ball = snake.getBalls().get(0);
        for(Rectangle rectangle : blocks.getRectangles()){
            if(ball.getBoundsInParent().intersects(rectangle.getBoundsInParent())){
                for(Text text: texts){
                    if(text.getBoundsInParent().intersects(rectangle.getBoundsInParent())){
                        if(intersect == false) {
                            PauseTransition wait = new PauseTransition(Duration.seconds(5));
                            wait.setOnFinished((e) -> {
                                /*YOUR METHOD*/
                                System.out.println("hi");
                                wait.playFromStart();
                            });
                            wait.play();
                            intersect = true;
                        }
                    }
                }
                //System.out.println("Isshapening");
            }
        }
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public Snake getSnake() {
        return snake;
    }

    public Blocks getBlocks() {
        return blocks;
    }


    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void setBlocks(Blocks blocks) {
        this.blocks = blocks;
    }
    public void setTexts(ArrayList<Text> texts) {
        this.texts = texts;
    }
}
