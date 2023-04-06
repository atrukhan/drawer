package sample.com.pluginFunctions;

import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import sample.com.core.PlugFunctions;

import java.io.*;

public class XmlToJsonFunc implements PlugFunctions {

    private static String id = "btn_ParseToJson";
    private static double loyoutX = 28.d;
    private static double loyoutY = 471.d;
    private static double prefWidth = 200.d;
    private static double prefHeight = 60.d;
    private static double textSize = 18.d;
    private static String textFill = "#d06a6a";
    private static String fontName = "Roboto Black";
    private static String text = "Parse Xml To Json";


    @Override
    public void parse(String sourceLine) {
        try {
            File file = new File(sourceLine);

            FileReader fr = new FileReader(file);

            BufferedReader reader = new BufferedReader(fr);
            String res = "";
            String line = reader.readLine();
            res += line;
            while (line != null) {
                line = reader.readLine();
                res += line;
            }

            JSONObject jsonObject = XML.toJSONObject(res);

            String jsonStr = jsonObject.toString();

            FileOutputStream fos = new FileOutputStream("savedObjects.json");
            fos.write(jsonStr.getBytes(), 0, jsonStr.length());
            fos.close();
        } catch (JSONException e){
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public javafx.scene.control.Button getButton() {
        javafx.scene.control.Button btn = new javafx.scene.control.Button();

        btn.setId(id);
        btn.setLayoutX(loyoutX);
        btn.setLayoutY(loyoutY);
        btn.setMnemonicParsing(false);
        btn.setPrefHeight(prefHeight);
        btn.setPrefWidth(prefWidth);
        btn.setTextFill(Paint.valueOf(textFill));
        btn.setText(text);
        btn.setFont(new Font(fontName, textSize));
        btn.setOnAction(actionEvent -> {
            parse("savedObjects.xml");
        });

        return btn;
    }


}
