package equipment.jt.test.view;

import com.rfid.uhf.controller.impl.ReaderR2000;
import com.rfid.uhf.service.ReaderR2000Service;
import com.rfid.uhf.service.impl.ReaderR2000ServiceImpl;
import equipment.jt.CallBackR2000;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application {
    ListView<String> list = new ListView<String>();
    ObservableList<String> data = FXCollections.observableArrayList("chocolate", "salmon", "gold", "coral",
            "darkorchid", "darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue", "blueviolet", "brown");

    @Override
    public void init() throws Exception {
        System.out.println("初始化");
        ReaderR2000Service service = new ReaderR2000ServiceImpl();
        ReaderR2000 reader = service.connect("192.168.0.5", 0, new CallBackR2000());
        //ReaderR2000 reader = service.connect("COM3", 0, new CallBackR2000());
        if(null == reader){
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                service.beginInvV2(reader);
                service.getInvOutPutData(reader);
            }
        }).start();
        while (true){
            System.out.println(service.getGPIO(reader));
            Thread.sleep(1000);

        }
    }

    @Override
    public void start(Stage stage) {
        VBox box = new VBox();
        Scene scene = new Scene(box, 200, 200);
        stage.setScene(scene);
        stage.setTitle("ListViewSample");
        box.getChildren().addAll(list);
        VBox.setVgrow(list, Priority.ALWAYS);
        list.setItems(data);
        list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> list) {
                return new ColorRectCell();
            }
        });
        stage.show();
    }





    static class ColorRectCell extends ListCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            Rectangle rect = new Rectangle(100, 20);
            if (item != null) {
                rect.setFill(Color.web(item));
                setGraphic(rect);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}