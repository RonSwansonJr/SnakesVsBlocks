import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

public class Shield extends Token implements Serializable {

    Shield(double posx,double posy){
        super(posx,posy,"images/shield.jpg");
        String loc = "images/shield.jpg";

    }


    @Override
    public void setvalues() {
        this.setPosx(35);
//        this.setPosy(360);
    }
}
