package sample.com;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class Square extends Rectangle implements Serializable {

    private static String name = "square";

    public Square(double x1, double y1, int type, double lineWidth, Color color) {
        super(x1, y1, type, lineWidth, color);
    }

    public Square(double x1, double y1, double x2, double y2, int type, double lineWidth, Color color) {
        super(x1, y1, x2, y2, type, lineWidth, color);
    }

    public Square() {
        super();
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
        if (getX2() - getX1() < 0) {
            side = getX1();
            setX1(getX2());
            setX2(side);
        }
        if (getY2() - getY1() < 0) {
            side = getY1();
            setY1(getY2());
            setY2(side);
        }
        side = equateSides();
        gc.strokeRect(getX1(), getY1(),side, side);
    }

//    @Override
//    public Shape crObject() {
//        return new Square();
//    }
}
