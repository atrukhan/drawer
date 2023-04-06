package sample.com;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;


public class Segment extends Shape implements Serializable {

    private static String name = "segment";

    public Segment(Segment target){
        super(target);
    }

    public Segment(double x1, double y1, int type, double lineWidth, Color color) {
        super(x1, y1, type, lineWidth, color);
    }

    public Segment(double x1, double y1, double x2, double y2, int type, double lineWidth, Color color) {
        super(x1, y1, x2, y2, type, lineWidth, color);
    }

    @Override
    public Shape clone() {
        return new Segment(this);
    }

    public Segment() {
        super();
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(getLineWidth());
        gc.setStroke(getColor());
        gc.strokeLine(getX1(), getY1(), getX2(), getY2());
    }

//    @Override
//    public Shape crObject() {
//        return new Segment();
//    }
}
