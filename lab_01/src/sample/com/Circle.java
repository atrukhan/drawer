package sample.com;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class Circle extends Ellipse implements Serializable {

    public Circle(Circle target){
        super(target);
    }

    public Circle(double x1, double y1, int type, double lineWidth, Color color) {
        super(x1, y1, type, lineWidth, color);
    }

    public Circle(){
        super();
    }


    public Circle(double x1, double y1, double x2, double y2, int type, double lineWidth, Color color) {
        super(x1, y1, x2, y2, type, lineWidth, color);
    }

    @Override
    public Shape clone() {
        return new Circle(this);
    }

    private double equateSides(){
        double side = 0;

        if(getX2() - getX1() > getY2() - getY1()){
            side = getX2() - getX1();
        } else{
            side = getY2() - getY1();
        }

        return side;
    }



    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(getLineWidth());
        gc.setStroke(getColor());
        double side = 0;
        if(getX2() - getX1() < 0){
            side = getX1();
            setX1(getX2());
            setX2(side);
        }
        if(getY2() - getY1() < 0){
            side = getY1();
            setY1(getY2());
            setY2(side);
        }
        side = equateSides();
        gc.strokeOval(getX1(), getY1(), side, side);
    }

   // @Override
//    public static Shape crObject() {
//        return new Circle();
//    }

}
