import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Random;

public class Spawn {

    private Snake snake;

    public Spawn(Snake snake){
        this.snake = snake;
    }

    public void randomize_rect(Rectangle rectangle){
        Random obj = new Random();
        int val = obj.nextInt(5)+1;
        if(val==1)rectangle.setFill(Color.GRAY);
        else if(val==2)rectangle.setFill(Color.TAN);
        else if(val==3)rectangle.setFill(Color.BLUE);
        else if(val==4)rectangle.setFill(Color.RED);
        else rectangle.setFill(Color.GREEN);
    }

    public void randomize_text(Text text){
        Random obj = new Random();
        text.setText(Integer.toString(obj.nextInt(Math.max((snake.getLengthofsnake()-1),1)+1) + 1));
    }

    public void randomize_coin_value(){
        Random obj = new Random();
    }

    public int spawn_token(){
        Random obj = new Random();
        int number = obj.nextInt(10);
        return number;
    }

    public ArrayList<Integer> randomx(){
        int[] arr = {35,120,250,350,420};
        ArrayList<Integer> rx = new ArrayList<Integer>();
        ArrayList<Integer> rex = new ArrayList<Integer>();
        Random obj = new Random();
        int number = obj.nextInt(5);
        rx.add(number);
        while(rx.contains(number)){
            number = obj.nextInt(5);
        }
        rx.add(number);
        while(rx.contains(number)){
            number = obj.nextInt(5);
        }
        rx.add(number);
        for(int i =0;i<3;i++){
            rex.add(arr[rx.get(i)]);
        }
        System.out.println("randomx done");
        return rex;
    }

    public ArrayList<Integer> randomy(){
        int[] arr = {0,-60,-120,-180};
        ArrayList<Integer> ry = new ArrayList<Integer>();
        ArrayList<Integer> rey = new ArrayList<Integer>();
        Random obj = new Random();
        int number = obj.nextInt(4);
        ry.add(number);
        while(ry.contains(number)){
            number = obj.nextInt(4);
        }
        ry.add(number);
        while(ry.contains(number)){
            number = obj.nextInt(4);
        }
        ry.add(number);
        for(int i =0;i<3;i++){
            rey.add(arr[ry.get(i)]);
        }
        System.out.println("randomy done");
        return rey;
    }

}
