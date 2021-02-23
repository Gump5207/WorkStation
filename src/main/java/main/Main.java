package main;

import gnu.io.SerialPortEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.convert.DataConvert;
import utils.serialport.SerialPort;

import java.util.List;

/**
 * @ClassName: main.Main
 * @ProjectName WorkStation
 * @Description: TODO
 * @Author 15791
 * @Aate 2020/8/2717:38
 */
public class Main extends Application {

    SerialPort serialPort = new SerialPort();
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/weigh.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("测试");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
//        SerialPort serialPort = new SerialPort();
//        serialPort.open("COM10", 115200);


//        byte[] bytes = serialPort.readData();
//        System.out.println(bytes);
//        serialPort.setListenerToSerialPort(ev -> {
//            if (ev.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
//                byte[] bytes = serialPort.readData();
//                for (Byte b :
//                        bytes) {
//                    System.out.print(b.toString() + " ");
//                }
//                System.out.println(DataConvert.bytesToHexString(bytes) + " " + bytes.length);
//            }
//        });
    }
}