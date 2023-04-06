package sample.com.plugins;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.com.Controller;
import sample.com.Shape;
import sample.com.ShapeHandler;
import sample.com.core.PlugService;

import java.io.File;

class PentagonHandler extends ShapeHandler{

    public PentagonHandler(){

    }

    public PlugPentagon getObject() {
        return new PlugPentagon();
    }
}


public class PlugPentagon extends Shape implements PlugService {

    private static String id = "btn_Pentagon";
    private static double loyoutX = 97.d;
    private static double loyoutY = 188.d;
    private static double maxHeight = 60.d;
    private static double maxWidth = 60.d;
    private static double prefWidth = 60.d;
    private static double prefHeight = 60.d;
    private static String imagePath = "images/pentagon.png";
    private static int type = 7;

    public PlugPentagon(PlugPentagon target){
        super(target);
    }
    public PlugPentagon(){
    }

    private double[] generateXCoordArray(double coord_1, double coord_2){
        double[] coordArr = new double[5];

        coordArr[0] = coord_1 + (coord_2 - coord_1) / 2.d;
        coordArr[1] = coord_2;
        coordArr[2] = coord_1 + (coord_2 - coord_1) / 4.d * 3.d;
        coordArr[3] = coord_1 + (coord_2 - coord_1) / 4.d;
        coordArr[4] = coord_1;

        return coordArr;
    }

    private double[] generateYCoordArray(double coord_1, double coord_2){
        double[] coordArr = new double[5];

        coordArr[0] = coord_1;
        coordArr[1] = coord_1 + (coord_2 - coord_1) / 2.d;
        coordArr[2] = coord_2;
        coordArr[3] = coord_2;
        coordArr[4] = coord_1 + (coord_2 - coord_1) / 2.d;

        return coordArr;
    }

    @Override
    public ShapeHandler getHandler() {
        return new PentagonHandler();
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public Shape clone() {
        return new PlugPentagon(this);
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

        gc.strokePolygon(generateXCoordArray(getX1(), getX2()), generateYCoordArray(getY1(), getY2()), 5);
    }

    @Override
    public String getName() {
        return "pentagon";
    }

    @Override
    public Object createObject() {
        return new PlugPentagon();
    }

    @Override
    public Button getButton() {
        javafx.scene.control.Button btn = new javafx.scene.control.Button();
        btn.setId(id);
        btn.setLayoutX(loyoutX);
        btn.setLayoutY(loyoutY);
        btn.setMaxHeight(maxHeight);
        btn.setMaxWidth(maxWidth);
        btn.setMnemonicParsing(false);
        btn.setPrefHeight(prefHeight);
        btn.setPrefWidth(prefWidth);

        btn.setOnAction(actionEvent -> {
            Controller.type = type;
        });

        File imageFile = new File(imagePath);
        Image image = new Image(imageFile.toURI().toString());
        btn.setGraphic(new ImageView(image));

        return btn;
    }
}
