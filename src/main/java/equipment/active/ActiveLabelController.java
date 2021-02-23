package equipment.active;

import gnu.io.SerialPortEvent;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.serialport.SerialPort;

import java.util.Collections;


/**
 * @ClassName: ActiveLabelController
 * @ProjectName WorkStation
 * @Description: TODO
 * @Author 15791
 * @Aate 2021/2/217:49
 */

public class ActiveLabelController {

    @FXML
    private Label currentData;                         //显示当前的标签数据
    @FXML
    private Label labelNum;                            //标签数量显示
    @FXML
    private TableView<ProtocolSim> table;              //数据展示表格
    @FXML
    private Button connect;                            //连接按钮
    @FXML
    private Button disConnect;                         //断开连接按钮
    @FXML
    ComboBox comboBox;

    private SerialPort serialPort;
    private ProtocolParsing protocolParsing = new ProtocolParsing();
    //用于存放数据
    private final ObservableList<ProtocolSim> data = FXCollections.observableArrayList();
    //展示
    private ObservableList<TableColumn<ProtocolSim, ?>> observableList;
    //当前读取的数据
    ProtocolSim protocolSim;



    /**
     * 打开串口连接，读取数据
     */
    public void connect() {
        connect.setVisible(false);
        disConnect.setVisible(true);
        System.out.println("开始读取数据：");
        //串口对象
        serialPort = new SerialPort();

        serialPort.open("COM10", 115200);

        serialPort.setListenerToSerialPort(ev -> {
            if (ev.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                byte[] bytes = serialPort.readData();
                protocolSim = protocolParsing.parsingData(bytes);

                if (protocolSim != null) {
                    //判断在当前把表格中是否有此时读到的数据
                    boolean contains = data.contains(protocolSim);
                    if (contains){
                        for (ProtocolSim p : data) {
                            if (p.equals(protocolSim)){
                                int count = p.getCount();
                                protocolSim.setCount(count + 1);
                                Collections.replaceAll(data, p, protocolSim);
                                break;
                            }
                        }
                    }else {
                        data.add(protocolSim);
                    }

                    //刷新显示
                    Platform.runLater(() -> {
                        table.scrollTo(protocolSim);
                        labelNum.setText(Integer.toString(data.size()));
                        currentData.setText(protocolSim.getLabelID());
                    });
                    table.setItems(data);
                }
            }
        });
    }

    /**
     * 断开串口连接
     */
    public void disConnect() {
        serialPort.close();
        connect.setVisible(true);
        disConnect.setVisible(false);
    }

    public void onShowComboBox() {
        comboBox.getItems().addAll("One", "Two", "Three");
    }

    /**
     * 初始化当前页面的初始函数，可将当前页面需要的初始资源放置在下方函数中
     */
    public void init() {
        observableList = table.getColumns();
        observableList.get(0).setCellValueFactory(new PropertyValueFactory("labelID"));
        observableList.get(1).setCellValueFactory(new PropertyValueFactory("address"));
        observableList.get(2).setCellValueFactory(new PropertyValueFactory("labelStatus"));
        observableList.get(3).setCellValueFactory(new PropertyValueFactory("count"));
        observableList.get(4).setCellValueFactory(new PropertyValueFactory("RSSI"));
    }

    /**
     * 清空当前表格显示
     */
    public void clear(){
        table.getItems().clear();

        //刷新显示
        Platform.runLater(() -> {
            table.scrollTo(protocolSim);
            labelNum.setText(Integer.toString(data.size()));
            currentData.setText("0000000000");
        });
    }
}
