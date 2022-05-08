import javafx.scene.Scene;
import javafx.scene.shape.Line;

import javafx.scene.text.Text;
import java.io.*;
import java.util.ArrayList;

public class Database {

//    private Leaderboard leaderboard;
//    private Snake snake;
//    private Blocks blocks;
//    private ArrayList<Token> tokens;
//    private Score score;
//    ArrayList<Line> lines;
    private Boolean issaved;



//    public void writelines(ArrayList<Line> l){
//        ObjectOutputStream out;
//        try{
//            out = new ObjectOutputStream(new FileOutputStream("lines.txt"));
//            out.writeObject(l);
//            out.close();
//        }catch(IOException io){
//
//        }
//    }

//
//    public void writetexts(ArrayList<Text> l){
//        ObjectOutputStream out;
//        try{
//            out = new ObjectOutputStream(new FileOutputStream("texts.txt"));
//            out.writeObject(l);
//            out.close();
//        }catch(IOException io){
//
//        }
//    }


    public void writepscore(Score l){
        ObjectOutputStream out;
        try{
            out = new ObjectOutputStream(new FileOutputStream("score.txt"));
            long sc = l.getPlayer_score();
            out.writeObject(sc);
            out.close();
        }catch(IOException io){

        }
    }
//
//
    public void writesnakel(Snake l){
        ObjectOutputStream out;
        try{
            out = new ObjectOutputStream(new FileOutputStream("snake.txt"));
            int len = l.getLengthofsnake();
            out.writeObject(len);
            out.close();
        }catch(IOException io){

        }
    }
//
//    public void writeblocks(Blocks l){
//        ObjectOutputStream out;
//        try{
//            out = new ObjectOutputStream(new FileOutputStream("blocks.txt"));
//            out.writeObject(l);
//            out.close();
//        }catch(IOException io){
//
//        }
//    }
//    public void writetokens(ArrayList<Token> l){
//        ObjectOutputStream out;
//        try{
//            out = new ObjectOutputStream(new FileOutputStream("tokens.txt"));
//            out.writeObject(l);
//            out.close();
//        }catch(IOException io){
//
//        }
//    }
    public void writeleaderboard(Leaderboard l){
        ObjectOutputStream out;
        try{
            out = new ObjectOutputStream(new FileOutputStream("leaderboard.txt"));
            out.writeObject(l);
            out.close();
        }catch(IOException io){

        }
    }

    public void setissavefalse()throws IOException {
        BufferedWriter out;
        try{
            out = new BufferedWriter(new FileWriter("issave.txt"));
            out.write("noser");
            out.close();
        }catch(IOException ioe){

        }
    }

    public void setissavetrue()throws IOException {
        BufferedWriter out;
        try{
            out = new BufferedWriter(new FileWriter("issave.txt"));
            out.write("ser");
            out.close();
        }catch(IOException ioe){

        }
    }

    public Boolean getIssaved() {
        return issaved;
    }

    public void setIssaved(Boolean issaved) {
        this.issaved = issaved;
    }

}
