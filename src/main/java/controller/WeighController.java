package controller;

import gnu.io.SerialPortEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import utils.convert.DataConvert;
import utils.serialport.SerialPort;

import java.util.List;

/**
 * @ClassName: WeighController
 * @ProjectName WorkStation
 * @Description: TODO
 * @Author 15791
 * @Aate 2020/9/49:33
 */
public class WeighController {

    private SerialPort serialPort;


    @FXML
    TextArea showResponse;
    @FXML
    Button start;
    @FXML
    Button stop;
    @FXML
    Button peel;
    @FXML
    Button zero;
    @FXML
    ComboBox comboBox;
    @FXML
    TextField showWeigh;

    private boolean isOpen = false;

    public WeighController() {
//        systemPorts = SerialController.getSystemPort();
        serialPort = new SerialPort();
    }

    /**
     * 选择comboBox，刷新里面的数据
     */
    public void onShowComboBox() {
        List<String> systemPort = SerialPort.getSystemPort();
        comboBox.getItems().clear();
        comboBox.getItems().addAll(systemPort);
    }

    /**
     * 开始称重并打开串口
     */
    public void openSerial(){
        if (!isOpen) {
            if (!serialPort.open((String) comboBox.getValue(), 9600)) {
                return;
            }
            isOpen = true;
            start.setVisible(false);
            stop.setVisible(true);
        } else {
            isOpen = false;
            serialPort.close();
        }
    }

    /**
     * 归零处理
     */
    public void toZero(){
        String toZeroComm = "1F0600240000CA7F";
        serialPort.sendData(DataConvert.hexToByteArray(toZeroComm));
        serialPort.setListenerToSerialPort(ev -> {
            if (ev.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                byte[] bytes = serialPort.readData();
                String hexString = DataConvert.bytesToHexString(bytes);
                if (toZeroComm.equals(hexString)){
                    showResponse.appendText("归零操作成功\n");
                }
            }
        });
    }

    /**
     * 去皮操作
     */
    public void toPeel(){
        String toPeelComm = "1F06002500009BBF";
        serialPort.sendData(DataConvert.hexToByteArray(toPeelComm));
        serialPort.setListenerToSerialPort(ev -> {
            if (ev.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                byte[] bytes = serialPort.readData();
                String hexString = DataConvert.bytesToHexString(bytes);
                if (toPeelComm.equals(hexString)){
                    showResponse.appendText("去皮操作成功");
                }
            }
        });
    }

    /**
     * 关闭串口
     */
    public void closeSerial(){
        start.setVisible(true);
        stop.setVisible(false);
        serialPort.close();
    }
}
