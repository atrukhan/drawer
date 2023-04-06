package sample.com.friendPlugin;

//import com.zakharenko.serialize.dal.exception.DalException;
//import com.zakharenko.serialize.dal.impl.JsonDalImpl;
import javafx.scene.control.Button;


import java.util.List;


import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import sample.com.Shape;
import sample.com.core.PlugFunctionAdapter;

public class PlugAdapter implements PlugFunctionAdapter {


    private static String idToJson = "btn_frToJson";
    private static String idReadJson = "btn_frReadJson";

    private static double loyoutXToJson = 28.d;
    private static double loyoutYToJson = 400.d;

    private static double loyoutXReadJson = 28.d;
    private static double loyoutYReadJson = 329.d;

    private static double prefWidth = 200.d;
    private static double prefHeight = 60.d;
    private static double textSize = 18.d;
    private static String textFill = "#d06a6a";
    private static String fontName = "Roboto Black";
    private static String textToJson = "To Json";
    private static String textReadJson = "Read Json";


//    public void toJson(List<Shape> objects, String path) throws DalException {
//        JsonDalImpl<Shape> jdi = new JsonDalImpl<Shape>();
//        jdi.saveToJson(objects, path);
//    }
//
//
//    public List<Shape> readJson(String path) throws DalException {
//        JsonDalImpl<Shape> jdi = new JsonDalImpl<Shape>();
//        List<Shape> shapes = jdi.readFromJson(path);
//        return shapes;
//    }

    @Override
    public Button getButtonToJson(List<Shape> objects, String path) {
        javafx.scene.control.Button btn = new javafx.scene.control.Button();
        btn.setId(idToJson);
        btn.setLayoutX(loyoutXToJson);
        btn.setLayoutY(loyoutYToJson);
        btn.setMnemonicParsing(false);
        btn.setPrefHeight(prefHeight);
        btn.setPrefWidth(prefWidth);
        btn.setTextFill(Paint.valueOf(textFill));
        btn.setText(textToJson);
        btn.setFont(new Font(fontName, textSize));

        btn.setOnAction(actionEvent -> {
//            try {
//                toJson(objects, path);
//            } catch (DalException e) {
//                e.printStackTrace();
//            }
        });


        return btn;
    }

    @Override
    public Button getButtonReadJson(String path) {
        javafx.scene.control.Button btn = new javafx.scene.control.Button();
        btn.setId(idReadJson);
        btn.setLayoutX(loyoutXReadJson);
        btn.setLayoutY(loyoutYReadJson);
        btn.setMnemonicParsing(false);
        btn.setPrefHeight(prefHeight);
        btn.setPrefWidth(prefWidth);
        btn.setTextFill(Paint.valueOf(textFill));
        btn.setText(textReadJson);
        btn.setFont(new Font(fontName, textSize));

        btn.setOnAction(actionEvent -> {
//            try {
//                readJson(path);
//            } catch (DalException e) {
//                e.printStackTrace();
//            }
        });


        return btn;
    }
}
