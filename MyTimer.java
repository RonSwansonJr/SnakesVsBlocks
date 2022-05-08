import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;
import javafx.concurrent.*;
import javafx.util.Duration;

import javax.swing.text.html.ImageView;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MyTimer extends AnimationTimer {

    @Override
    public void handle(long now){
        if(snake.getLengthofsnake()<=0) return;
        dohandle();
    }

    private Pane pane;
    private Blocks blocks;
    private ArrayList<Text> texts;
    private Spawn spawn;
    private Integer index;
    private ArrayList<Line> lines;

    private Database database;

    private Snake snake;
    private Score score;
    private ArrayList<Token> tokens;

    boolean intersect = false;
    Timeline timeline;
    AnimationTimer antimer = null;

    private boolean isMagnet = false;
    private boolean isShield = false;
    private boolean isDestroy = false;

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public void setLines(ArrayList<Line> lines) {
        this.lines = lines;
    }

    public void setSpawn(Spawn spawn) {
        this.spawn = spawn;
    }

    public void setTexts(ArrayList<Text> texts) {
        this.texts = texts;
    }

    public void setDatabase(Database database) { this.database = database; }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public Blocks getBlocks() {
        return blocks;
    }

    public void setBlocks(Blocks blocks) {
        this.blocks = blocks;
    }

    public void setScore(Score score){
        this.score = score;
    }


    public void dohandle(){

        if(snake.getLengthofsnake()<=0) return;

        Bounds bounds = pane.getBoundsInLocal();
        if(snake.getBalls().get(0).getLayoutX() > (bounds.getMaxX() - 25)){
            putonright();
        }

        Text text = boom();
        Token token = boom2();
        if(token!=null){
            if(token.getClass()==Coin.class){
                System.out.println("COIN");
                addCoin( token );

                hit();
            }
            else usepower( token );
        }
        if(text==null)move();
        else reduce(text);

    }

    private void hit() {
        Random obj = new Random();
        int y = -150;
        boolean cond = true;
        while(cond){
            int val = obj.nextInt(500);
            for(Line line : lines){
                if(line.getStartX() == val){
                    continue;
                }
            }
            Coin coin = new Coin(val,y,obj.nextInt(10)+1);
            cond=false;
            tokens.add(coin);
            coin.getImageView().setLayoutX(coin.posx);
            coin.getImageView().setLayoutY(coin.posy);
            pane.getChildren().add(coin.getImageView());
        }


    }

    public Text boom(){

        /** The Collision detection*/
        Circle ball = snake.getBalls().get(0);
        intersect = false;
        int temp=0;
        for(Rectangle rectangle : blocks.getRectangles()){
            if(intersect) break;
            if(ball.getBoundsInParent().intersects(rectangle.getBoundsInParent()) && rectangle.isVisible() == true){
                for(Text text: texts){
                    if(text.getBoundsInParent().intersects(rectangle.getBoundsInParent())){
                        intersect = true;
                        this.index=temp;
                        return text;

                    }
                    temp++;
                }
            }
        }
        this.index=-1;
        return null;
    }

    public Token boom2(){

        Circle ballcheck = snake.getBalls().get(0);
        boolean inters = false;
        for(Token token: tokens){
            if(inters) break;
            if(token.getImageView().getBoundsInParent().intersects(ballcheck.getBoundsInParent())){
                return token;
            }
        }
        return null;
    }

    private void reduce(Text text) {

        if(Integer.parseInt(text.getText()) >=1){
            if(isShield != true && isDestroy != true) snake.reducelengthofsnake();
            text.setText(Integer.toString(Integer.parseInt(text.getText()) - 1));
            score.increase_score(1);
            Task<Void> sleeper = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }
                    return null;
                }
            };
        }
        else{
            int temp=0;
            for(Rectangle rectangle : blocks.getRectangles()){//I think the tempp++ == index is bullshit. ~Shahid
                if(temp++ == index){rectangle.setVisible(false); text.setVisible(false);}
            }
        }
    }

    private void move() {

        /** The movement of blocks*/
//        database.writeblocks(blocks);
        database.writepscore(score);
//        database.writetokens(tokens);
//        database.writelines(lines);
//        database.writetexts(texts);

        if(isDestroy == true){
            for(Rectangle rectangle: blocks.getRectangles()){
                if(rectangle.getLayoutY() > 250 && rectangle.getLayoutY() < 600){
                    Bounds bounds = pane.getBoundsInLocal();
                    rectangle.setVisible(false);
                }
            }
            for(Text text : texts){
                if(text.getLayoutY()> 250 && text.getLayoutY() < 600) {
//                    addCoin(Integer.parseInt(text.getText()));
                    text.setVisible(false);
                }
            }
        }

        if(isMagnet == true){
            for(int i = 0; i<tokens.size();i++){
                Token token = tokens.get(i);
                if(token.getClass() == Coin.class){
                    if(token.getImageView().getLayoutY() > 250 && token.getImageView().getLayoutY() < 600) {
                        addCoin(token);
                    }
                }
            }
        }

        Circle ball = snake.getBalls().get(0);

        Random obj = new Random();
        int number = obj.nextInt(4) + 1;

        if(lines.size()==0){
            Line line2 = new Line();
            Line line1 = new Line();
            lines.add(line1);
            lines.add(line2);
            pane.getChildren().add(line1);
            pane.getChildren().add(line2);
            System.out.println("ADDED");
            for(Line line : lines) {
                randy(line,number);
                number++;
            }
        }

        for (Node node : pane.getChildren()) {
            if (node.getClass() != Circle.class && node != score.getScore_text() && node != score.getText()) {
                node.setLayoutY(node.getLayoutY() + 3);
            }
        }

        if (blocks.getRectangles().get(0).getLayoutY() > 600 + 180) {


            for(int j =0;j<tokens.size();j++){
                Token tokk = tokens.get(j);
                pane.getChildren().remove(tokk.getImageView());
                tokens.remove(tokk);
            }

            ArrayList<Integer> randomx = spawn.randomx();
            ArrayList<Integer> randomy = spawn.randomy();

            for(int i = 0; i < 3;i++){

                int numb = spawn.spawn_token();
                Token token;
                if(numb == 9){
                    token = new Shield(randomx.get(i),randomy.get(i));
                }
                else if(numb == 8){
                    token = new Destroy(randomx.get(i),randomy.get(i));
                }
                else if(numb == 7){
                    token = new Magnet(randomx.get(i),randomy.get(i));
                }
                else{
                    token = new Coin(randomx.get(i),randomy.get(i),numb);
                }
                tokens.add(token);
                token.getImageView().setLayoutX(token.getPosx());
                token.getImageView().setLayoutY(token.getPosy());
                pane.getChildren().add(token.getImageView());
            }
//                Coin ct = new Coin(130,50,2);
//                tokens.add(ct);
//                ct.getImageView().setLayoutX(ct.getPosx());
//                ct.getImageView().setLayoutY(ct.getImageView().getLayoutX() - 500);


            for (Rectangle rectangle : blocks.getRectangles()) {
                spawn.randomize_rect(rectangle);
                rectangle.setLayoutY(rectangle.getY());
                rectangle.setVisible(true);
            }
            for (Text text : texts) {
                spawn.randomize_text(text);
                text.setLayoutY(text.getY() - 30);
                text.setVisible(true);
            }

            number = obj.nextInt(4) + 1;
            for(Line line : lines) {
                randy(line,number);
                number++;
                line.setLayoutY(line.getStartY());
            }
        }
    }

    private void randy(Line line,int number) {

        line.setStartX(number * 100);
        line.setEndX(number * 100);
        line.setStartY(-75);
        line.setEndY(50);
        // line2.setEndY(-50);
        line.setStrokeWidth(10);
        line.setFill(Color.WHITE);
        line.setStroke(Color.WHITE);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setVisible(true);
        //line.setLayoutX(line.getStartX());
        //line.setLayoutY(line.getStartY());
    }

    public void addCoin(Token token){
        Coin coin = (Coin) token;
        for(int i = 0;i < coin.getValue(); i++){
            snake.addball();
            Circle balldisplay = snake.getBalls().get(snake.getLengthofsnake() - 1);
            balldisplay.relocate(snake.getposx().get(snake.getLengthofsnake() - 1),snake.getPosy().get(snake.getLengthofsnake() - 1));
            pane.getChildren().add(balldisplay);
        }
        pane.getChildren().remove(token.getImageView());
        tokens.remove(token);
//        coin.getImageView().imageProperty().setValue(null);
    }

    public void usepower(Token token){
        if(token.getClass() == Magnet.class){
            final Timer timer = new Timer();
            pane.getChildren().remove(token.getImageView());
            tokens.remove(token);
            isMagnet = true;
            snake.getBalls().get(0).setFill(Color.WHITE);
            class RemindMagnet extends TimerTask {
                public void run() {
                    snake.getBalls().get(0).setFill(Color.GREEN);
                    isMagnet = false;
                    timer.cancel();
                }
            }
            timer.schedule(new RemindMagnet(),9000);
        }
        else if(token.getClass() == Shield.class){
            final Timer timer = new Timer();
            pane.getChildren().remove(token.getImageView());
            tokens.remove(token);
            isShield = true;
            snake.getBalls().get(0).setFill(Color.BLUE);
            class RemindMagnet extends TimerTask {
                public void run() {
                    snake.getBalls().get(0).setFill(Color.GREEN);
                    isShield = false;
                    timer.cancel();
                }
            }
            timer.schedule(new RemindMagnet(),9000);
        }
        else if(token.getClass() == Destroy.class){
            final Timer timer = new Timer();
            pane.getChildren().remove(token.getImageView());
            tokens.remove(token);
            isDestroy = true;
            snake.getBalls().get(0).setFill(Color.RED);
            class RemindMagnet extends TimerTask {
                public void run() {
                    snake.getBalls().get(0).setFill(Color.GREEN);
                    isDestroy = false;
                    timer.cancel();
                }
            }
            timer.schedule(new RemindMagnet(),9000);
        }
    }

    public void putonright(){
        for(int i = 0; i < snake.getLengthofsnake(); i++){
            snake.getBalls().get(i).setLayoutX(snake.getposx().get(i));
        }
    }

}






//        PauseTransition wait = new PauseTransition(Duration.seconds(0.3));
//        wait.setOnFinished((e) -> {
//            collide();
//        });
//        wait.play();

//        antimer = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//               // boom();
//                move();
//            }
//        };
//        antimer.start();


//        timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//
//                boom();
//                collide();
//            }
//        }));
//        timeline.setAutoReverse(true);
//        timeline.setRate(timeline.getCycleDuration().toSeconds());
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.play();

