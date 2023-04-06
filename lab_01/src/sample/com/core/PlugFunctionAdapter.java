package sample.com.core;

//import com.zakharenko.serialize.dal.exception.DalException;
import sample.com.Shape;

import java.util.List;

public interface PlugFunctionAdapter {

    javafx.scene.control.Button getButtonToJson(List<Shape> objects, String path);
    javafx.scene.control.Button getButtonReadJson(String path);
}
