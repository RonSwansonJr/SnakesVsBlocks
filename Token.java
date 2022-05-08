import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

public abstract class Token implements Serializable {

    ImageView imageView;
    Image image;
    double posx;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    double posy;

    Token(double posx,double posy,String loc){

        image = null;
        try {
            image = new Image(new FileInputStream(loc));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView = new ImageView(image);
        imageView.setX(posx);
        imageView.setY(posy);

        imageView.setFitHeight(50);
        imageView.setFitWidth(37);

        imageView.setPreserveRatio(true);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public double getPosx() {
        return posx;
    }

    public void setPosx(double posx) {
        this.posx = posx;
    }

    public double getPosy() {
        return posy;
    }

    public void setPosy(double posy) {
        this.posy = posy;
    }

    public abstract void setvalues();
}
