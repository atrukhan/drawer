package sample.com;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import sample.com.core.PlugFunctions;
import sample.com.core.PlugService;
//import sample.plugins.PLug1;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;


public class Controller {

    private static HashMap<String, ShapeHandler> map;
    private static HashMap<String, String> mapName;
    private static int selectedID = -1;
    private static double lineWidth = 1;
    private static Color color = Color.rgb(0,0,0);
    private static Color tempColor = Color.rgb(0,0,0);
    public static int type = Shape.SHAPE_TYPE_SEGMENT;
    private static ArrayList<Shape> shapes = new ArrayList<>();
    ObservableList<String> langs = FXCollections.observableArrayList();


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Canvas canvas;

    @FXML
    private Canvas canvas_Color;

    @FXML
    private Button btn_Circle;

    @FXML
    private Button btn_Ellipse;

    @FXML
    private Button btn_Segment;

    @FXML
    private Button btn_Rectangle;

    @FXML
    private Button btn_Square;

    @FXML
    private Button btn_Triangle;

    @FXML
    private Button btn_Clear;

    @FXML
    private Button btn_Draw;

    @FXML
    private Button btn_Delete;

    @FXML
    private Button btn_Draw_From_File;

    @FXML
    private Button btn_Write_To_File;

    @FXML
    private ListView listView;

    @FXML
    private Slider slider_Width;

    @FXML
    private Slider slider_Width_Change;

    @FXML
    private Slider slider_Color_Red;

    @FXML
    private Slider slider_Color_Green;

    @FXML
    private Slider slider_Color_Blue;

    @FXML
    private AnchorPane anchorPaneLeft;

    @FXML
    private AnchorPane anchorPaneRight;

    @FXML
    private javafx.scene.shape.Rectangle rectangle_Color;

    MultipleSelectionModel msm;

    @FXML
    void mousePressed(MouseEvent event) {
        shapes.add(map.get(String.valueOf(type)).getObject());
        shapes.get(shapes.size()-1).setX1(event.getX());
        shapes.get(shapes.size()-1).setY1(event.getY());
        shapes.get(shapes.size()-1).setType(type);
        shapes.get(shapes.size()-1).setLineWidth(lineWidth);
        shapes.get(shapes.size()-1).setColor(color);

//        switch (type){
//            case Shape.SHAPE_TYPE_SEGMENT:
//                shapes.add(new Segment(event.getX(), event.getY(), type, lineWidth, color));
//                break;
//            case Shape.SHAPE_TYPE_CIRCLE:
//                shapes.add(new Circle(event.getX(), event.getY(), type, lineWidth, color));
//                break;
//            case Shape.SHAPE_TYPE_ELLIPSE:
//                shapes.add(new Ellipse(event.getX(), event.getY(), type, lineWidth, color));
//                break;
//            case Shape.SHAPE_TYPE_RECTANGLE:
//                shapes.add(new Rectangle(event.getX(), event.getY(), type, lineWidth, color));
//                break;
//            case Shape.SHAPE_TYPE_SQUARE:
//                shapes.add(new Square(event.getX(), event.getY(), type, lineWidth, color));
//                break;
//            case Shape.SHAPE_TYPE_TRIANGLE:
//                shapes.add(new Triangle(event.getX(), event.getY(), type, lineWidth, color));
//                break;
//        }
    }

    @FXML
    void mouseReleased(MouseEvent event) {

        shapes.get(shapes.size()-1).setX2(event.getX());
        shapes.get(shapes.size()-1).setY2(event.getY());
        shapes.get(shapes.size()-1).draw(canvas.getGraphicsContext2D());
        langs.add(mapName.get(String.valueOf(shapes.get(shapes.size()-1).getType())));
    }

    @FXML
    void list_mousePressed(MouseEvent event) {
        msm = listView.getSelectionModel();
        ObservableList list = msm.getSelectedItems();
        if(list.size() > 0) {
            selectedID = msm.getSelectedIndex();
            tempColor = shapes.get(selectedID).getColor();

        }else{
            selectedID = -1;
        }
    }

    @FXML
    void initialize() throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        listView.setItems(langs);
        //MultipleSelectionModel msm = listView.getSelectionModel();

        mapName = new HashMap<String, String >();
        mapName.put(String.valueOf(Shape.SHAPE_TYPE_SEGMENT), "segment");
        mapName.put(String.valueOf(Shape.SHAPE_TYPE_CIRCLE), "circle");
        mapName.put(String.valueOf(Shape.SHAPE_TYPE_ELLIPSE), "ellipse");
        mapName.put(String.valueOf(Shape.SHAPE_TYPE_RECTANGLE), "rectangle");
        mapName.put(String.valueOf(Shape.SHAPE_TYPE_SQUARE), "square");
        mapName.put(String.valueOf(Shape.SHAPE_TYPE_TRIANGLE), "triangle");

        map = new HashMap<String, ShapeHandler>();
        map.put(String.valueOf(Shape.SHAPE_TYPE_SEGMENT), new SegmentHandler());
        map.put(String.valueOf(Shape.SHAPE_TYPE_CIRCLE), new CircleHandler());
        map.put(String.valueOf(Shape.SHAPE_TYPE_ELLIPSE), new EllipseHandler());
        map.put(String.valueOf(Shape.SHAPE_TYPE_RECTANGLE), new RectangleHandler());
        map.put(String.valueOf(Shape.SHAPE_TYPE_SQUARE), new SquareHandler());
        map.put(String.valueOf(Shape.SHAPE_TYPE_TRIANGLE), new TriangleHandler());

        File pluginDir = new File("C:\\Users\\sasha\\OneDrive\\Рабочий стол\\BSUIR\\2 курс\\ООП\\lab_01\\src\\sample\\com\\plugins");
        File[] jars = pluginDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().endsWith(".java");
            }
        });
        Class[] pluginClasses = new Class[jars.length];
        for(int i = 0; i < jars.length; i++){
            URL jarURL = jars[i].toURI().toURL();
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});
            String[] str = jars[i].toString().split("\\\\");
            pluginClasses[i] = classLoader.loadClass("sample.com.plugins." + str[str.length-1].substring(0, str[str.length-1].length()-5));
        }

        Button[] btns = new Button[pluginClasses.length];
        PlugService[] plugServices = new PlugService[jars.length];
        for(int i = 0; i < pluginClasses.length; i++){
            plugServices[i] = (PlugService) pluginClasses[i].newInstance();
            map.put(String.valueOf(plugServices[i].getType()), plugServices[i].getHandler());
            mapName.put(String.valueOf(plugServices[i].getType()), plugServices[i].getName());
            btns[i] = plugServices[i].getButton();

            anchorPaneLeft.getChildren().add(btns[i]);
        }



        File pluginFuncDir = new File("C:\\Users\\sasha\\OneDrive\\Рабочий стол\\BSUIR\\2 курс\\ООП\\lab_01\\src\\sample\\com\\pluginFunctions");
        File[] funcFiles = pluginFuncDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().endsWith(".java");
            }
        });
        pluginClasses = new Class[funcFiles.length];
        for(int i = 0; i < funcFiles.length; i++){
            URL jarURL = funcFiles[i].toURI().toURL();
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});
            String[] str = funcFiles[i].toString().split("\\\\");
            pluginClasses[i] = classLoader.loadClass("sample.com.pluginFunctions." + str[str.length-1].substring(0, str[str.length-1].length()-5));
        }
        Button[] btnsFunc = new Button[pluginClasses.length];
        PlugFunctions[] plugFunctions = new PlugFunctions[funcFiles.length];
        for(int i = 0; i < pluginClasses.length; i++){
            plugFunctions[i] = (PlugFunctions) pluginClasses[i].newInstance();
            btnsFunc[i] = plugFunctions[i].getButton();
            anchorPaneRight.getChildren().add(btnsFunc[i]);
        }


//        File pluginFrFuncDir = new File("C:\\ООП\\lab_01\\src\\sample\\friendPlugin");
//        File[] funcFrFiles = pluginFrFuncDir.listFiles(new FileFilter() {
//            @Override
//            public boolean accept(File pathname) {
//                return pathname.isFile() && pathname.getName().endsWith(".java");
//            }
//        });
//        pluginClasses = new Class[funcFrFiles.length];
//        for(int i = 0; i < funcFrFiles.length; i++){
//            URL jarURL = funcFrFiles[i].toURI().toURL();
//            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});
//            String[] str = funcFrFiles[i].toString().split("\\\\");
//            pluginClasses[i] = classLoader.loadClass("sample.friendPlugin." + str[str.length-1].substring(0, str[str.length-1].length()-5));
//        }
//        Button[] btnsFrFunc = new Button[pluginClasses.length*2];
//        PlugFunctionAdapter[] plugFunctionAdapters = new PlugFunctionAdapter[funcFrFiles.length];
//        for(int i = 0; i < pluginClasses.length; i++){
//            plugFunctionAdapters[i] = (PlugFunctionAdapter) pluginClasses[i].newInstance();
//            btnsFrFunc[i] = plugFunctionAdapters[i].getButtonToJson(shapes, "frJson.json");
//            btnsFrFunc[i+1] = plugFunctionAdapters[i].getButtonReadJson("frJson.json");
//            anchorPaneRight.getChildren().add(btnsFrFunc[i]);
//        }


        File imageFile = new File("images/segment.png");
        Image image = new Image(imageFile.toURI().toString());
        ArrayList<ImageView> images = new ArrayList<>();
        images.add(new ImageView(image));

        imageFile = new File("images/circle.png");
        image = new Image(imageFile.toURI().toString());
        images.add(new ImageView(image));


        imageFile = new File("images/ellipse.png");
        image = new Image(imageFile.toURI().toString());
        images.add(new ImageView(image));

        imageFile = new File("images/rectangle.png");
        image = new Image(imageFile.toURI().toString());
        images.add(new ImageView(image));

        imageFile = new File("images/square.png");
        image = new Image(imageFile.toURI().toString());
        images.add(new ImageView(image));

        imageFile = new File("images/triangle.png");
        image = new Image(imageFile.toURI().toString());
        images.add(new ImageView(image));


        for (byte i = 0; i < images.size(); i++){
            images.get(i).setFitHeight(30);
            images.get(i).setFitWidth(30);
        }

        images.get(2).setFitWidth(40);

        btn_Segment.setGraphic(images.get(0));
        btn_Circle.setGraphic(images.get(1));
        btn_Ellipse.setGraphic(images.get(2));
        btn_Rectangle.setGraphic(images.get(3));
        btn_Square.setGraphic(images.get(4));
        btn_Triangle.setGraphic(images.get(5));

        btn_Write_To_File.setOnAction(actionEvent -> {
            try {
                FileOutputStream fos = new FileOutputStream("savedObjects.xml");
                XMLEncoder encoder = new XMLEncoder(fos);
                encoder.writeObject(shapes.size());
                for (int i = 0; i < shapes.size(); i++){
                    encoder.writeObject(shapes.get(i));
                }
                encoder.close();
                fos.close();

            } catch (FileNotFoundException exception) {
                exception.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btn_Draw_From_File.setOnAction(actionEvent -> {
            try {
                FileInputStream fis = new FileInputStream("savedObjects.xml");
                XMLDecoder decoder = new XMLDecoder(fis);
                Shape sh;
                int num = (int) decoder.readObject();
                for (int i = 0; i < num; i++){
                    sh = (Shape) decoder.readObject();
                    sh.setColor(Color.BLACK);
                    sh.draw(canvas.getGraphicsContext2D());
                    shapes.add(sh);
                    langs.add(mapName.get(String.valueOf(shapes.get(shapes.size()-1).getType())));
                }
                decoder.close();
                fis.close();
            } catch (FileNotFoundException exception) {
                exception.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        btn_Delete.setOnAction(actionEvent -> {
            try{
                ObservableList list = msm.getSelectedItems();
                if(list.size() > 0) {
                    if(msm.getSelectedIndex() > -1) {
                        shapes.remove(msm.getSelectedIndex());
                        langs.remove(list.get(0));
    //                langs.remove(msm.getSelectedIndex());

                        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        for (int i = 0; i < shapes.size(); i++) {
                            shapes.get(i).draw(canvas.getGraphicsContext2D());
                        }
                    }
                }
            }catch (NullPointerException e){

            }
        });


        btn_Circle.setOnAction(actionEvent -> {
            type = Shape.SHAPE_TYPE_CIRCLE;
        });
        btn_Ellipse.setOnAction(actionEvent -> {
            type = Shape.SHAPE_TYPE_ELLIPSE;
        });
        btn_Segment.setOnAction(actionEvent -> {
            type = Shape.SHAPE_TYPE_SEGMENT;
        });
        btn_Rectangle.setOnAction(actionEvent -> {
            type = Shape.SHAPE_TYPE_RECTANGLE;
        });
        btn_Square.setOnAction(actionEvent -> {
            type = Shape.SHAPE_TYPE_SQUARE;
        });

        btn_Triangle.setOnAction(actionEvent -> {
            type = Shape.SHAPE_TYPE_TRIANGLE;
        });

        btn_Clear.setOnAction(actionEvent -> {
            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            shapes.clear();
            langs.clear();
        });

        btn_Draw.setOnAction(actionEvent -> {
            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            shapes.clear();
            langs.clear();

//            try {
//                shapes.add(new Shape.Builder(new Segment())
//                        .withX1(50)
//                        .withY1(50)
//                        .withX2(100)
//                        .withY2(100)
//                        .withType(Shape.SHAPE_TYPE_SEGMENT)
//                        .withLineWidth(1)
//                        .withColor(Color.BLACK)
//                        .build()
//                );
//                shapes.get(shapes.size()-1).draw(canvas.getGraphicsContext2D());
//                langs.add(Shape.getShapeName(shapes.get(shapes.size()-1).getType()));
//                shapes.add(shapes.get(shapes.size()-1).clone());
//                shapes.get(shapes.size()-1).draw(canvas.getGraphicsContext2D());
//                langs.add(Shape.getShapeName(shapes.get(shapes.size()-1).getType()));
            shapes.add(new Segment(50, 50, 300, 50, Shape.SHAPE_TYPE_SEGMENT, lineWidth, color));
            shapes.get(shapes.size()-1).draw(canvas.getGraphicsContext2D());
            langs.add(Shape.getShapeName(shapes.get(shapes.size()-1).getType()));
            shapes.add(new Rectangle(50, 100, 300, 150, Shape.SHAPE_TYPE_RECTANGLE, lineWidth, color));
            shapes.get(shapes.size()-1).draw(canvas.getGraphicsContext2D());
            langs.add(Shape.getShapeName(shapes.get(shapes.size()-1).getType()));
            shapes.add(new Square(500, 50, 700, 250, Shape.SHAPE_TYPE_SQUARE, lineWidth, color));
            shapes.get(shapes.size()-1).draw(canvas.getGraphicsContext2D());
            langs.add(Shape.getShapeName(shapes.get(shapes.size()-1).getType()));
            shapes.add(new Ellipse(50, 200, 300, 250, Shape.SHAPE_TYPE_ELLIPSE, lineWidth, color));
            shapes.get(shapes.size()-1).draw(canvas.getGraphicsContext2D());
            langs.add(Shape.getShapeName(shapes.get(shapes.size()-1).getType()));
            shapes.add(new Circle(50, 300, 150, 450, Shape.SHAPE_TYPE_CIRCLE, lineWidth, color));
            shapes.get(shapes.size()-1).draw(canvas.getGraphicsContext2D());
            langs.add(Shape.getShapeName(shapes.get(shapes.size()-1).getType()));
            shapes.add(new Triangle(50, 500, 200, 650, Shape.SHAPE_TYPE_TRIANGLE, lineWidth, color));
            shapes.get(shapes.size()-1).draw(canvas.getGraphicsContext2D());
            langs.add(Shape.getShapeName(shapes.get(shapes.size()-1).getType()));
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            }

//            shapes.add(new Segment(50, 50, 300, 50, Shape.SHAPE_TYPE_SEGMENT, lineWidth, color));
//            shapes.get(shapes.size()-1).draw(canvas.getGraphicsContext2D());
//            langs.add(Shape.getShapeName(shapes.get(shapes.size()-1).getType()));
//            shapes.add(new Rectangle(50, 100, 300, 150, Shape.SHAPE_TYPE_RECTANGLE, lineWidth, color));
//            shapes.get(shapes.size()-1).draw(canvas.getGraphicsContext2D());
//            langs.add(Shape.getShapeName(shapes.get(shapes.size()-1).getType()));
//            shapes.add(new Square(500, 50, 700, 250, Shape.SHAPE_TYPE_SQUARE, lineWidth, color));
//            shapes.get(shapes.size()-1).draw(canvas.getGraphicsContext2D());
//            langs.add(Shape.getShapeName(shapes.get(shapes.size()-1).getType()));
//            shapes.add(new Ellipse(50, 200, 300, 250, Shape.SHAPE_TYPE_ELLIPSE, lineWidth, color));
//            shapes.get(shapes.size()-1).draw(canvas.getGraphicsContext2D());
//            langs.add(Shape.getShapeName(shapes.get(shapes.size()-1).getType()));
//            shapes.add(new Circle(50, 300, 150, 450, Shape.SHAPE_TYPE_CIRCLE, lineWidth, color));
//            shapes.get(shapes.size()-1).draw(canvas.getGraphicsContext2D());
//            langs.add(Shape.getShapeName(shapes.get(shapes.size()-1).getType()));
//            shapes.add(new Triangle(50, 500, 200, 650, Shape.SHAPE_TYPE_TRIANGLE, lineWidth, color));
//            shapes.get(shapes.size()-1).draw(canvas.getGraphicsContext2D());
//            langs.add(Shape.getShapeName(shapes.get(shapes.size()-1).getType()));
        });

        slider_Width.valueProperty().addListener(new ChangeListener<Number>(){

            public void changed(ObservableValue<? extends Number> changed, Number oldValue, Number newValue){
                lineWidth = newValue.doubleValue();
            }
        });
        slider_Width_Change.valueProperty().addListener(new ChangeListener<Number>(){

            public void changed(ObservableValue<? extends Number> changed, Number oldValue, Number newValue){

                ObservableList list = msm.getSelectedItems();
                if(list.size() > 0) {

                    shapes.get(msm.getSelectedIndex()).setLineWidth(newValue.doubleValue());

                    canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    for (int i = 0; i < shapes.size(); i++) {
                        shapes.get(i).draw(canvas.getGraphicsContext2D());
                    }
                }
            }
        });

        slider_Color_Red.valueProperty().addListener(new ChangeListener<Number>(){

            public void changed(ObservableValue<? extends Number> changed, Number oldValue, Number newValue){
                color = Color.rgb(newValue.intValue(), (int) slider_Color_Green.getValue(), (int) slider_Color_Blue.getValue());
                rectangle_Color.setFill(color);
            }
        });
        slider_Color_Green.valueProperty().addListener(new ChangeListener<Number>(){

            public void changed(ObservableValue<? extends Number> changed, Number oldValue, Number newValue){
                color = Color.rgb((int) slider_Color_Red.getValue(), newValue.intValue(), (int) slider_Color_Blue.getValue());
                rectangle_Color.setFill(color);
            }
        });
        slider_Color_Blue.valueProperty().addListener(new ChangeListener<Number>(){

            public void changed(ObservableValue<? extends Number> changed, Number oldValue, Number newValue){
                color = Color.rgb((int) slider_Color_Red.getValue(), (int) slider_Color_Green.getValue(), newValue.intValue());
                rectangle_Color.setFill(color);
            }
        });
    }

}