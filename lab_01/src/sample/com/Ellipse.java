package sample.com;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class Ellipse extends Shape implements Serializable {

    private static String name = "ellipse";

    public Ellipse(double x1, double y1, int type, double lineWidth, Color color) {
        super(x1, y1, type, lineWidth, color);
    }

    public Ellipse(Ellipse target){
        super(target);
    }

    public Ellipse() {
        super();
    }

    public Ellipse(double x1, double y1, double x2, double y2, int type, double lineWidth, Color color) {
        super(x1, y1, x2, y2, type, lineWidth, color);
    }

    @Override
    public Shape clone() {
        return new Ellipse(this);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(getLineWidth());
        gc.setStroke(getColor());
        double temp = 0;
        if(getX2() - getX1() < 0){
            temp = getX1();
            setX1(getX2());
            setX2(temp);
        }
        if(getY2() - getY1() < 0){
            temp = getY1();
            setY1(getY2());
            setY2(temp);
        }
        gc.strokeOval(getX1(), getY1(), getX2() - getX1(), getY2() - getY1());
    }

//    @Override
//    public Shape crObject() {
//        return new Ellipse();
//    }
}
