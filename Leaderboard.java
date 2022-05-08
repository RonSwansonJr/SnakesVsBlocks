import java.io.Serializable;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Leaderboard implements Serializable {

    private ArrayList<Integer> scores;
    Leaderboard(){
        scores = new ArrayList<Integer>();
        for(int i = 0;i < 10; i++){
            scores.add(0);
        }
    }

    public boolean addvalue(int val){
        boolean isnewscore = false;
        for(int i = 0;i<10;i++){
            if(scores.get(i) < val){
                isnewscore = true;
                scores.add(i,val);
                break;
            }
        }
        if(isnewscore) scores.remove(10);
        return isnewscore;
    }

    public void  displayscores(){
        for(int i:scores) System.out.println(i);
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Integer> scores) {
        this.scores = scores;
    }
}
