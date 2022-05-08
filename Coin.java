import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Coin extends Token{

    int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    Coin(double posx, double posy, int value){
        super(posx,posy,"images/coin.jpg");
        String loc = "images/coin.jpg";
        this.setValue(value);
    }


    @Override
    public void setvalues() {
        this.setPosx(120);
//        this.setPosy(275);
    }
}
