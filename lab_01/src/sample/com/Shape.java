package sample.com;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public abstract class Shape implements Serializable {

    public static final int SHAPE_TYPE_SEGMENT = 0;
    public static final int SHAPE_TYPE_CIRCLE = 1;
    public static final int SHAPE_TYPE_ELLIPSE = 2;
    public static final int SHAPE_TYPE_RECTANGLE = 3;
    public static final int SHAPE_TYPE_SQUARE = 4;
    public static final int SHAPE_TYPE_TRIANGLE = 5;

    private static final long serialVersionUID = 1L;

//    private String name;
    private double x1, y1, x2, y2;
    private int type;
    private double lineWidth;
    transient private Color color;

    public Shape(double x1, double y1, int type, double lineWidth, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.type = type;
        this.lineWidth = lineWidth;
        this.color = color;
    }

    public Shape() {
        this.x1 = 0;
        this.y1 = 0;
        this.type = 0;
        this.lineWidth = 0;
        this.color = Color.BLACK;
    }

//    public static String getName() {
//        return name;
//    }
//
//    public static void setName(String name) {
//        Shape.name = name;
//    }

    public Shape(double x1, double y1, double x2, double y2, int type, double lineWidth, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.type = type;
        this.lineWidth = lineWidth;
        this.color = color;
    }

    public static class Builder{
        private Shape newShape;

        public Builder(Shape sh) throws IllegalAccessException, InstantiationException {
            newShape = (Shape) sh;
        }

        public Builder withX1(double x1){
            newShape.x1 = x1;
            return this;
        }
        public Builder withY1(double y1){
            newShape.y1 = y1;
            return this;
        }
        public Builder withX2(double x2){
            newShape.x2 = x2;
            return this;
        }
        public Builder withY2(double y2){
            newShape.y2 = y2;
            return this;
        }
        public Builder withType(int type){
            newShape.type = type;
            return this;
        }
        public Builder withLineWidth(double lineWidth){
            newShape.lineWidth = lineWidth;
            return this;
        }

        public Builder withColor(Color color){
            newShape.color = color;
            return this;
        }

        public Shape build(){
            return newShape;
        }
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public static String getShapeName(int type){
        switch (type) {
            case Shape.SHAPE_TYPE_SEGMENT:
                return "Segment";
            case Shape.SHAPE_TYPE_CIRCLE:
                return "Circle";
            case Shape.SHAPE_TYPE_ELLIPSE:
                return "Ellipse";
            case Shape.SHAPE_TYPE_RECTANGLE:
                return "Rectangle";
            case Shape.SHAPE_TYPE_SQUARE:
                return "Square";
            case Shape.SHAPE_TYPE_TRIANGLE:
                return "Triangle";
        }
        return "error";
    }

    public Shape(Shape target){
        if(target != null){
            this.x1 = target.x1;
            this.y1 = target.y1;
            this.x2 = target.x2;
            this.y2 = target.y2;
            this.type = target.type;
            this.lineWidth = target.lineWidth;
            this.color = target.color;
        }
    }

    public abstract Shape clone();

    public abstract void draw(GraphicsContext gc);
//    public abstract Shape crObject();
}
