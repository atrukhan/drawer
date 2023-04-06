package sample.com;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class Rectangle extends Shape implements Serializable {
    public Rectangle(double x1, double y1, int type, double lineWidth, Color color) {
        super(x1, y1, type, lineWidth, color);
    }

    public Rectangle(double x1, double y1, double x2, double y2, int type, double lineWidth, Color color) {
        super(x1, y1, x2, y2, type, lineWidth, color);
    }

    public Rectangle(Rectangle target) {
        super(target);
    }

    @Override
    public Shape clone() {
        return new Rectangle(this);
    }

    public Rectangle() {
        super();
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

        gc.strokeRect(getX1(), getY1(), getX2() - getX1(), getY2() - getY1());
    }
}
