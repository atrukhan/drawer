package sample.com;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class Triangle extends Shape implements Serializable {

    private static String name = "triangle";

    public Triangle(Triangle target){
        super(target);
    }

    public Triangle(double x1, double y1, int type, double lineWidth, Color color) {
        super(x1, y1, type, lineWidth, color);

    }

    public Triangle(double x1, double y1, double x2, double y2, int type, double lineWidth, Color color) {
        super(x1, y1, x2, y2, type, lineWidth, color);
    }

    @Override
    public Shape clone() {
        return new Triangle(this);
    }

    public Triangle() {
        super();
    }

    private double[] generateXCoordArray(double coord_1, double coord_2){
        double[] coordArr = new double[3];

        coordArr[0] = coord_1;
        coordArr[1] = coord_2;
        coordArr[2] = coord_1 + (coord_2 - coord_1) / 2.d;

        return coordArr;
    }

    private double[] generateYCoordArray(double coord_1, double coord_2){
        double[] coordArr = new double[3];

        coordArr[0] = coord_2;
        coordArr[1] = coord_2;
        coordArr[2] = coord_1;

        return coordArr;
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

        gc.strokePolygon(generateXCoordArray(getX1(), getX2()), generateYCoordArray(getY1(), getY2()), 3);
    }

//    @Override
//    public Shape crObject() {
//        return new Triangle();
//    }
}
