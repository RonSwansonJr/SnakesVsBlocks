import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

public class Destroy extends Token implements Serializable {

    Destroy(double posx,double posy){
        super(posx,posy,"images/flame.png");
        String loc = "images/flame.png";
    }

    @Override
    public void setvalues() {
        this.setPosx(400);
//        this.setPosy(450);
    }
}
