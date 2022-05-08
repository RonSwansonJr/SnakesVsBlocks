import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.Serializable;

public class Score implements Serializable {

    Text score_text;
    Text text;

    long player_score;

    Score(){

        score_text = new Text(340,25,"Score: ");
        this.setPlayer_score(0);
        text = new Text(410,25,Long.toString(this.getPlayer_score()));
        score_text.setFill(Color.BISQUE);
        text.setFill(Color.WHITE);
        text.setFont(Font.font(null, FontWeight.MEDIUM,22));
        score_text.setFont(Font.font(null, FontWeight.MEDIUM,22));
    }

    public long getPlayer_score() {
        return player_score;
    }

    public void setPlayer_score(long player_score) {
        this.player_score = player_score;
    }

    public void increase_score(long value){
        this.setPlayer_score(this.getPlayer_score() + value);
        this.setText();
    }

    public void setText(){
        this.text.setText(Long.toString(this.getPlayer_score()));
    }

    public Text getScore_text() {
        return score_text;
    }

    public Text getText() {
        return text;
    }
}
