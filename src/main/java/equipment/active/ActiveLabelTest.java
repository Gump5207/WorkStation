package equipment.active;

import gnu.io.SerialPortEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

/**
 * @ClassName: ActiveLabelTest
 * @ProjectName WorkStation
 * @Description: TODO
 * @Author 15791
 * @Aate 2021/2/217:46
 */
public class ActiveLabelTest extends Application {
    ActiveLabelController controller;



    @Override
    public void start(Stage primaryStage) throws Exception {

//        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/active_label.fxml"));
//        primaryStage.initStyle(StageStyle.UNDECORATED);

        URL location = getClass().getClassLoader().getResource("views/active_label.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        //如果使用 Parent root = FXMLLoader.load(...) 静态读取方法，无法获取到Controller的实例对象
        Scene scene = new Scene(root);
        //加载css样式
        //scene.getStylesheets().add(getClass().getResource("style1.css").toExternalForm());
        primaryStage.setScene(scene);
        controller = fxmlLoader.getController();   //获取Controller的实例对象
        //Controller中写的初始化方法
        controller.init();
        primaryStage.show();
    }

    @Override
    public void init() {

    }

    @Override
    public void stop() {
        controller.disConnect();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
