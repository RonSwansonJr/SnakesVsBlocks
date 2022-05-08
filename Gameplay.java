import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Gameplay  extends Application implements EventHandler<KeyEvent>  {


    private TableView<Products> tableView;

    private ArrayList<Line> lines;
    private ArrayList<Text> texts;

    private Scene scene;
    private Pane pane;
    private Spawn spawn;

    private Snake snake;
    private Blocks blocks;
    boolean cond;

    //variables below this are Shahid's

    private AnimationTimer atimer;
    private Timeline timeline;

    private static Database database;

    private ArrayList<Token> tokens;
    private static Leaderboard leaderboard;

    Token coin;
    Token shield;
    Token magnet;
    Token destroy;

    Score score;

    Stage window;

    @Override
    public void start(Stage primarystage) throws IOException,ClassNotFoundException {

        //scene1
        mainScene(primarystage);

    }

    private void mainScene(Stage primarystage) throws IOException,ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene1.fxml"));
        Stage Primarystage = primarystage;
        Parent root = loader.load();
        Primarystage.setTitle("Start");
        Scene scene = new Scene(root,Color.BLACK);
        scene.setFill(Color.BLACK);

        Primarystage.setScene(scene);
        Primarystage.show();

        boolean issaved = false;

        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new FileReader("issave.txt"));
            String ch;
            if((ch = in.readLine()) != null){
                if(ch.equalsIgnoreCase("ser")){
                    issaved = true;
                    System.out.println(ch);
                }
                else{
                    System.out.println("nots    " + ch);
                }
            }
            else{
                System.out.println("no");
            }
            in.close();
        }catch(IOException ioe){
            System.out.println("caught");
            issaved = false;
        }


        Button startgame = (Button) loader.getNamespace().get("A");
        Button resume = (Button) loader.getNamespace().get("B");
        Button leader = (Button) loader.getNamespace().get("C");
        Button exit = (Button) loader.getNamespace().get("D");
        startgame.setOnAction(event -> {
            Primarystage.close();
            try {
                scene2(primarystage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //scene2(Primarystage);
        if(issaved == true) {
            resume.setOnAction(event -> {
                Primarystage.close();
                try {
                    scene4(primarystage);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
        exit.setOnAction(event -> {
            Primarystage.close();
            System.exit(0);
        });
        leader.setOnAction(event -> {
            Primarystage.close();
            try {
                scene3(primarystage);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void scene3(Stage primarystage) throws IOException,ClassNotFoundException{

        /** The leaderboardpage*/
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("Table.fxml"));

        TableColumn<Products,Long> tableColumn = new TableColumn<>("Score");
        tableColumn.setMinWidth(200);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        tableView = new TableView<>();
        tableView.setItems(getProduct());
        Button button = new Button("Back");
        tableView.getColumns().addAll(tableColumn);

        VBox root = new VBox();
        root.getChildren().addAll(tableView,button);

        Scene scene = new Scene(root);
        primarystage.setScene(scene);
        primarystage.show();

        button.setOnAction(event -> {
            primarystage.close();
            try {
                mainScene(primarystage);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }


    public ObservableList<Products> getProduct(){
        ObservableList<Products> products = FXCollections.observableArrayList();
        for(int i: leaderboard.getScores()){
            products.add(new Products(i));
        }
        return products;
    }

    private void scene4(Stage primarystage)throws IOException,ClassNotFoundException{


        /** The game page in case of resume*/

        database.setissavetrue();
        tokens = new ArrayList<Token>();
//        ObjectInputStream tokenin = null;
//        try{
//            tokenin = new ObjectInputStream((new FileInputStream("tokens.txt")));
//            tokens = (ArrayList<Token>) tokenin.readObject();
//            tokenin.close();
//        }catch(IOException ioe){
//            ioe.printStackTrace();
//        }catch (ClassNotFoundException cnf){
//            cnf.printStackTrace();
//        }



        snake = new Snake();
        ObjectInputStream snakein = null;
        try{
            snakein = new ObjectInputStream((new FileInputStream("snake.txt")));
            int snakel = (int) snakein.readObject();
            snake.setLengthofsnake(snakel);
            snake.makeagain();
            snakein.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }catch (ClassNotFoundException cnf){
            cnf.printStackTrace();
        }
        cond=false;


        window = primarystage;


        score = new Score();
        ObjectInputStream scorein = null;
        try{
            scorein = new ObjectInputStream((new FileInputStream("score.txt")));
            long scorel = (long) scorein.readObject();
            score.increase_score(scorel);
            scorein.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }catch (ClassNotFoundException cnf){
            cnf.printStackTrace();
        }


        pane = new Pane();
        blocks = new Blocks();

//        ObjectInputStream blockin = null;
//        try{
//            blockin = new ObjectInputStream((new FileInputStream("blocks.txt")));
//            blocks = (Blocks) blockin.readObject();
//            blockin.close();
//        }catch(IOException ioe){
//            ioe.printStackTrace();
//        }catch (ClassNotFoundException cnf){
//            cnf.printStackTrace();
//        }

        pane.setStyle("-fx-background-color: #000000");
        texts = new ArrayList<>();

        ObjectInputStream textin = null;
        try{
            textin = new ObjectInputStream((new FileInputStream("texts.txt")));
            texts = (ArrayList<Text>) textin.readObject();
            textin.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }catch (ClassNotFoundException cnf){
            cnf.printStackTrace();
        }

        lines = new ArrayList<Line>();
//        ObjectInputStream linein = null;
//        try{
//            linein = new ObjectInputStream((new FileInputStream("lines.txt")));
//            lines = (ArrayList<Line>) linein.readObject();
//            linein.close();
//        }catch(IOException ioe){
//            ioe.printStackTrace();
//        }catch (ClassNotFoundException cnf){
//            cnf.printStackTrace();
//        }

        scene = new Scene(pane,500,600, Color.BLACK);

        snake.setPane(pane);
        snake.setScene(scene);

        int startx=0,starty=-169;
        for(int i=0;i<blocks.getRectangles().size();i++){
            startx+=100;
            Random obj = new Random();
            Text text = new Text(startx-60,starty+39,Integer.toString(obj.nextInt(snake.getLengthofsnake()-1)+1));
            text.setFill(Color.WHITE);
            text.setFont(Font.font(null, FontWeight.BOLD,32));
            texts.add(text);
            pane.getChildren().add(blocks.getRectangles().get(i));
            pane.getChildren().add(text);
        }


        coin = new Coin(120,275,1);
        shield = new Shield(35,360);
        magnet = new Magnet(350,300);
        destroy = new Destroy(440,450);

        tokens.add(coin);
        tokens.add(shield);
        tokens.add(magnet);
        tokens.add(destroy);

        pane.getChildren().add(coin.getImageView());
        pane.getChildren().add(magnet.getImageView());
        pane.getChildren().add(destroy.getImageView());
        pane.getChildren().add(shield.getImageView());


        pane.getChildren().add(score.getScore_text());
        pane.getChildren().add(score.getText());

//        spawn_line();
//        spawn_line();


        for(int i = 0; i < snake.getLengthofsnake(); i++){
            Circle ball = snake.getBalls().get(i);
            ball.relocate(snake.getposx().get(i),snake.getPosy().get(i));
            pane.getChildren().add(snake.getBalls().get(i));
        }

//        for(Line line : lines){
//            pane.getChildren().add(line);
//        }

        scene.setOnKeyPressed(this);

        primarystage.setTitle("Snakey");
        primarystage.setScene(scene);
        primarystage.show();

        spawn = new Spawn(snake);
        MyTimer animate = new MyTimer();

        animate.setPane(pane);
        animate.setSnake(snake);
        animate.setBlocks(blocks);
        animate.setTexts(texts);
        animate.setSpawn(spawn);
        animate.setScore(score);
        animate.setLines(lines);
        animate.setTokens(tokens);
        animate.setDatabase(database);


        animate.stop();
        animate.start();

        scene.setOnKeyReleased(event -> {
            database.writesnakel(snake);
            if(atimer != null) atimer.stop();

//            for(Token token : tokens){
//                //remove this
//                token.getImageView().setLayoutX(token.getPosX());
//            }

            atimer =new AnimationTimer() {
                @Override
                public void handle(long now) {
                    for(int i = snake.getLengthofsnake()-1; i>0;i--){
                        snake.getposx().set(i,snake.getposx().get(i-1));
                    }
                    Circle ball = snake.getBalls().get(0);
                    for(Line line: lines) {
                        if (ball.getBoundsInParent().intersects(line.getBoundsInParent())){
                            if(line.getLayoutX() > ball.getLayoutX()){
                                snake.getposx().set(0,snake.getposx().get(0) - 5);
                            }
                            else{
                                snake.getposx().set(0,snake.getposx().get(0) - 5);
                            }
                        }
                    }

                    for(int i = 0; i < snake.getLengthofsnake(); i++){
                        ball = snake.getBalls().get(i);
                        ball.setLayoutX(snake.getposx().get(i));
                    }
                }
            };
            atimer.start();
        });

        final Timer timer=new Timer();

        class RemindTask extends TimerTask {
            public void run() {
//                System.out.println("Time's up!");
                if(snake.getLengthofsnake()<=0){
                    if(atimer!=null) atimer.stop();
                    if(animate != null){
                        animate.stop();
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // if you change the UI, do it here !
                            window.close();
                            try {
                                game_over(primarystage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

//                    try {
//                        game_over(primarystage);
//                    }catch(Exception ioe){
//                        ioe.printStackTrace();
//                    }
                    timer.cancel();
                }
//            timer.cancel(); //Terminate the timer thread
            }
        }

        timer.schedule(new RemindTask(),1000,50);
    }

    private void scene2(Stage primarystage)throws IOException {
        /** The actual game page*/

        database.setissavetrue();
        tokens = new ArrayList<Token>();

        snake = new Snake();
        cond=false;

        window = primarystage;
        score = new Score();

        pane = new Pane();
        blocks = new Blocks();

        pane.setStyle("-fx-background-color: #000000");
        texts = new ArrayList<>();
        lines = new ArrayList<Line>();
//
//        try {
//            scene3(window);
//        }catch(IOException i){
//            i.printStackTrace();
//        }
        scene = new Scene(pane,500,600, Color.BLACK);

        snake.setPane(pane);
        snake.setScene(scene);

        int startx=0,starty=-169;
        for(int i=0;i<blocks.getRectangles().size();i++){
            startx+=100;
            Random obj = new Random();
            Text text = new Text(startx-60,starty+39,Integer.toString(obj.nextInt(snake.getLengthofsnake()-1)+1));
            text.setFill(Color.WHITE);
            text.setFont(Font.font(null, FontWeight.BOLD,32));
            texts.add(text);
            pane.getChildren().add(blocks.getRectangles().get(i));
            pane.getChildren().add(text);
        }


        coin = new Coin(120,275,1);
        shield = new Shield(35,360);
        magnet = new Magnet(350,300);
        destroy = new Destroy(440,450);

        tokens.add(coin);
        tokens.add(shield);
        tokens.add(magnet);
        tokens.add(destroy);

        pane.getChildren().add(coin.getImageView());
        pane.getChildren().add(magnet.getImageView());
        pane.getChildren().add(destroy.getImageView());
        pane.getChildren().add(shield.getImageView());


        score.increase_score(5);

        pane.getChildren().add(score.getScore_text());
        pane.getChildren().add(score.getText());

//        spawn_line();
//        spawn_line();


        for(int i = 0; i < snake.getLengthofsnake(); i++){
            Circle ball = snake.getBalls().get(i);
            ball.relocate(snake.getposx().get(i),snake.getPosy().get(i));
            pane.getChildren().add(snake.getBalls().get(i));
        }

//        for(Line line : lines){
//            pane.getChildren().add(line);
//        }

        scene.setOnKeyPressed(this);

        primarystage.setTitle("Snakey");
        primarystage.setScene(scene);
        primarystage.show();

        spawn = new Spawn(snake);
        MyTimer animate = new MyTimer();

        animate.setPane(pane);
        animate.setSnake(snake);
        animate.setBlocks(blocks);
        animate.setTexts(texts);
        animate.setSpawn(spawn);
        animate.setScore(score);
        animate.setLines(lines);
        animate.setTokens(tokens);
        animate.setDatabase(database);


        animate.stop();
        animate.start();

        scene.setOnKeyReleased(event -> {
            database.writesnakel(snake);
            if(atimer != null) atimer.stop();

//            for(Token token : tokens){
//                //remove this
//                token.getImageView().setLayoutX(token.getPosX());
//            }



            atimer =new AnimationTimer() {
                @Override
                public void handle(long now) {

                    if(snake.getLengthofsnake()>0) {
                        for (int i = snake.getLengthofsnake() - 1; i > 0; i--) {
                            snake.getposx().set(i, snake.getposx().get(i - 1));
                        }
                        Circle ball = snake.getBalls().get(0);
                        for (Line line : lines) {
                            if (ball.getBoundsInParent().intersects(line.getBoundsInParent())) {
                                if (line.getLayoutX() > ball.getLayoutX()) {
                                    snake.getposx().set(0, snake.getposx().get(0) - 5);
                                } else {
                                    snake.getposx().set(0, snake.getposx().get(0) - 5);
                                }
                            }
                        }

                        for (int i = 0; i < snake.getLengthofsnake(); i++) {
                            ball = snake.getBalls().get(i);
                            ball.setLayoutX(snake.getposx().get(i));
                        }
                    }
                }
            };
            atimer.start();
        });

        final Timer timer=new Timer();

        class RemindTask extends TimerTask {
            public void run() {
//                System.out.println("Time's up!");
                if(snake.getLengthofsnake()<=0){
                    if(atimer!=null) atimer.stop();
                    if(animate != null){
                        animate.stop();
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // if you change the UI, do it here !
                            window.close();
                            try {
                                game_over(primarystage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

//                    try {
//                        game_over(primarystage);
//                    }catch(Exception ioe){
//                        ioe.printStackTrace();
//                    }
                    timer.cancel();
                }
//            timer.cancel(); //Terminate the timer thread
            }
        }

        timer.schedule(new RemindTask(),1000,50);
    }

    private void update_scores(){
        leaderboard.addvalue(Integer.parseInt(score.text.getText()));
        database.writeleaderboard(leaderboard);
        leaderboard.displayscores();
    }

    private void game_over(Stage gstage)throws IOException{


        database.setissavefalse();
        update_scores();

        Pane p1 = new Pane();
        Button b1 = new Button("Exit");
        b1.relocate(120,150);
        Button b2 = new Button("Restart");
        b2.relocate(180,150);
//        Gstage.setTitle("abc");
        p1.setStyle("-fx-background-color: #000000");
        Scene s1 = new Scene(p1,300,300,Color.BLACK);
        Stage Gstage = gstage;
        Gstage.setTitle("Game_over");
        p1.getChildren().add(b1);
        Gstage.setScene(s1);
        Gstage.show();
        b1.setOnAction(event1 -> {
//            b1.getScene().getWindow().hide();//even in resume this line is ther, change it `Shahid`
//            System.exit(0);
            Gstage.close();
            try {
                mainScene(gstage);
            }catch (IOException ioe){
                ioe.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        b2.setOnAction(event -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // if you change the UI, do it here !
                    Gstage.close();
                    try {
                        scene2(gstage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }



    private void spawn_line() {
        Random obj = new Random();
        int val = obj.nextInt(2)+1;
        if(val==1){
            //single line
            Line line = new Line();
            line.setStartY(-101);
            line.setEndY(-1);
            val = obj.nextInt(500);
            line.setStartX(val);
            line.setEndX(val);
            line.setStrokeWidth(10);
            line.setFill(Color.WHITE);
            line.setStroke(Color.WHITE);
            line.setStrokeLineCap(StrokeLineCap.ROUND);
            this.lines.add(line);
        }
        else{
            //Pairs
            Line line1 = new Line();
            Line line2 = new Line();
            int index1 = obj.nextInt(5);

            line1.setStartX(blocks.getRect_posx().get(index1));
            line1.setEndX(blocks.getRect_posx().get(index1));
            line1.setStartY(blocks.getRectangles().get(index1).getY()+65);
            line1.setEndY(line1.getStartY() + 150);
            line1.setStrokeWidth(10);
            line1.setFill(Color.WHITE);
            line1.setStroke(Color.WHITE);
            line1.setStrokeLineCap(StrokeLineCap.ROUND);
            this.lines.add(line1);

            if(index1 < 4) index1+=1;
            else index1-=1;

            line2.setStartX(blocks.getRect_posx().get(index1));
            line2.setEndX(blocks.getRect_posx().get(index1));
            line2.setStartY(blocks.getRectangles().get(index1).getY()+65);
            line2.setEndY(line1.getStartY() + 150);
            line2.setStrokeWidth(10);
            line2.setFill(Color.WHITE);
            line2.setStroke(Color.WHITE);
            line2.setStrokeLineCap(StrokeLineCap.ROUND);
            this.lines.add(line2);

        }

    }

    public static void main(String[] args)throws FileNotFoundException,IOException,ClassNotFoundException{
        /**
         * @author Shahid Nawaz Khan 2017102
         * @author Agnel Aaron 2017010
         */
        leaderboard = new Leaderboard();
        database = new Database();
//        database.writeleaderboard();
        ObjectInputStream in;
        try{
            in = new ObjectInputStream((new FileInputStream("leaderboard.txt")));

            leaderboard = (Leaderboard)in.readObject();
            System.out.println("happened"+ leaderboard);
            in.close();
        }
        catch(Exception e){

        }
        launch();
    }

    @Override
    public void handle(KeyEvent event) {
        if(atimer != null){
            atimer.stop();
        }
        if(timeline != null){
            timeline.stop();
        }
        if(event.getCode().toString().equals("RIGHT")){
            moveright();
        }
        else if(event.getCode().toString().equals("LEFT")) {
            moveleft();
        }
        else if(event.getCode().toString().equals("ESCAPE")){
            Pane pane2 = new Pane();
            Button button1 = new Button("Exit");
            Button button2 = new Button("Return to Game");

            button1.relocate(150,250);
            button2.relocate(250,250);
            pane2.getChildren().add(button1);
            pane2.getChildren().add(button2);
            pane2.setStyle("-fx-background-color: #000000");

            Scene pause = new Scene(pane2,500,600,Color.BLACK);

            window.setScene(pause);
            button1.setOnAction(event1 -> {
                button1.getScene().getWindow().hide();
            });
            button2.setOnAction(event1 -> {
                window.setScene(scene);
            });
        }
    }

    public void moveright(){

        /**To move the snake right */

       Bounds bounds = pane.getBoundsInLocal();
        atimer =new AnimationTimer() {
            @Override
            public void handle(long now) {
                for(int i = snake.getLengthofsnake()-1; i>0;i--){
                    snake.getposx().set(i,snake.getposx().get(i-1));
                }
                Circle ball = snake.getBalls().get(0);
                if((ball.getLayoutX() < (475))){
                    boolean m = true;
                    for(Line line: lines){
                        if(ball.getBoundsInParent().intersects(line.getBoundsInParent())) m = false;
                    }
                    if(m==true) snake.getposx().set(0,snake.getposx().get(0) + 5);
                }
//                else{
//                    int err = (int)bounds.getMaxX() - 25;
//                    snake.getposx().set(0,err);
//                }
                for(int i = 0; i < snake.getLengthofsnake(); i++){
                    ball = snake.getBalls().get(i);
                    ball.setLayoutX(snake.getposx().get(i));
                }
            }
        };
        atimer.start();
    }

    public void moveleft(){
        /**To move the snake left */

        Bounds bounds = pane.getBoundsInLocal();

        atimer =new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (snake.getLengthofsnake() > 0) {
                    for (int i = snake.getLengthofsnake() - 1; i > 0; i--) {
                        snake.getposx().set(i, snake.getposx().get(i - 1));
                    }
                    Circle ball = snake.getBalls().get(0);
                    if ((ball.getLayoutX() > (bounds.getMinX() + 25))) {
                        boolean m = true;
                        for(Line line: lines){
                            if(ball.getBoundsInParent().intersects(line.getBoundsInParent())) m = false;
                        }
                        if(m==true) snake.getposx().set(0, snake.getposx().get(0) - 5);
                    }
                    for (int i = 0; i < snake.getLengthofsnake(); i++) {
                        ball = snake.getBalls().get(i);
                        ball.setLayoutX(snake.getposx().get(i));
                    }
                }
            }
        };
        atimer.start();
    }
}
