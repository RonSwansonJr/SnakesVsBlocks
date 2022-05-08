import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

public class Magnet extends Token implements Serializable {


    Magnet(double posx,double posy){
        super(posx,posy,"images/magnet.jpg");
        String loc = "images/magnet.jpg";
    }

    public void setvalues(){
        this.setPosx(210);
//        this.setPosy(300);
    }
}
