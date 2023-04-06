package sample.com.plugins;

import sample.com.Controller;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import sample.com.Shape;
import sample.com.ShapeHandler;
import sample.com.core.PlugService;

import java.io.File;
class RhombHandler extends ShapeHandler{

    public RhombHandler(){

    }

    public PLugRhomb getObject() {
        return new PLugRhomb();
    }
}
public class PLugRhomb extends Shape implements PlugService {

    private static String id = "btn_Rhomb";
    private static double loyoutX = 21.d;
    private static double loyoutY = 188.d;
    private static double maxHeight = 60.d;
    private static double maxWidth = 60.d;
    private static double prefWidth = 60.d;
    private static double prefHeight = 60.d;
    private static String imagePath = "images/rhomb.png";
    private static int type = 6;

    public PLugRhomb(PLugRhomb target){
        super(target);
    }

    public PLugRhomb(double x1, double y1, int type, double lineWidth, Color color) {
        super(x1, y1, type, lineWidth, color);
    }

    public PLugRhomb(){
        super();
    }


    @Override
    public Object createObject() {
        return new PLugRhomb();
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public Shape clone() {
        return new PLugRhomb(this);
    }


    @Override
    public String getName() {
        return "rhomb";
    }

    @Override
    public ShapeHandler getHandler() {
        return new RhombHandler();
    }

    @Override
    public javafx.scene.control.Button getButton() {
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


    public Shape run(double x1, double y1, int type, double lineWidth, Color color){
        return new PLugRhomb(x1, y1, type, lineWidth, color);
    }

    private double[] generateXCoordArray(double coord_1, double coord_2){
        double[] coordArr = new double[4];

        coordArr[0] = coord_1 + (coord_2 - coord_1) / 2.d;
        coordArr[1] = coord_2;
        coordArr[2] = coord_1 + (coord_2 - coord_1) / 2.d;
        coordArr[3] = coord_1;

        return coordArr;
    }

    private double[] generateYCoordArray(double coord_1, double coord_2){
        double[] coordArr = new double[4];

        coordArr[0] = coord_1;
        coordArr[1] = coord_1 + (coord_2 - coord_1) / 2.d;
        coordArr[2] = coord_2;
        coordArr[3] = coord_1 + (coord_2 - coord_1) / 2.d;

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

        gc.strokePolygon(generateXCoordArray(getX1(), getX2()), generateYCoordArray(getY1(), getY2()), 4);
    }

}
