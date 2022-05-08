import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class Controller1 implements EventHandler<KeyEvent> {

    private static AnimationTimer atimer;
    public void setAtimer(AnimationTimer atime){
        atimer = atime;
    }

    @Override
    public void handle(KeyEvent event) {
        atimer.stop();
    }
}
