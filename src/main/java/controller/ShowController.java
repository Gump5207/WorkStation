package controller;

import com.rfid.uhf.controller.impl.ReaderR2000;
import com.rfid.uhf.service.ReaderR2000Service;
import com.rfid.uhf.service.impl.ReaderR2000ServiceImpl;
import equipment.jt.CallBackR2000;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import org.apache.log4j.Logger;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: ShowController
 * @ProjectName WorkStation
 * @Description: TODO
 * @Author 15791
 * @Aate 2020/9/219:05
 */
public class ShowController {

    private static Logger logger = Logger.getLogger(ShowController.class);


    @FXML
    Circle in1;
    @FXML
    Circle in2;

    @FXML
    Button connect;
    @FXML
    Button disconnect;

    @FXML
    Button out1_Open;
    @FXML
    Button out2_Open;

    @FXML
    Button out1_Close;
    @FXML
    Button out2_Close;

    private ReaderR2000 reader;
    private ReaderR2000Service service = new ReaderR2000ServiceImpl();


    public ShowController(){

    }


    public void connectDevice(){
        try {
            reader = service.connect("172.16.38.232", 20058, new CallBackR2000());
            if (reader == null){
                System.out.println("连接设备失败！");
            }else {
                connect.setVisible(false);
                disconnect.setVisible(true);
            }
        }catch (Exception e){
            logger.warn(e);
            System.out.println("连接超时");
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){

//                    System.out.println(Thread.currentThread().getName()+ ": " + serviceGPIO);
                    try {
                        Map serviceGPIO = service.getGPIO(reader);
                        if ((boolean)serviceGPIO.get("input1")){
                            in1.setFill(Paint.valueOf("#ff0000"));
                        } else {
                            in1.setFill(Paint.valueOf("#00ff00"));
                        }
                        if ((boolean)serviceGPIO.get("input2")){
                            in2.setFill(Paint.valueOf("#ff0000"));
                        }else {
                            in2.setFill(Paint.valueOf("#00ff00"));
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }catch (NullPointerException e){
                        logger.warn(e);
                    }
                }
            }
        }, "方向判断指示灯").start();
    }

    public void disconnectDevice(){

        service.disconnect(reader);
        disconnect.setVisible(false);
        connect.setVisible(true);
        Map<Thread, StackTraceElement[]>  allThread = Thread.getAllStackTraces();
        List<Thread> ss = allThread.keySet().stream().collect(Collectors.toList());
        for(Thread a:ss){
            try {
                if ("方向判断指示灯".equals(a.getName())) {
                    //如果需要找到该线程,并且是在停止该线程之后才能进行其他的操作的话,
                    //需要用interrupt停掉之后,再执行此时的线程,所以还需要将当前的线程join到指定线程停掉之后
                    a.interrupt();
                }
            }catch (Exception e){
                System.out.println("异常");
            }
        }
    }

    public void setOut1_Open(){
        service.setGPIO(reader, (byte) 1, (byte) 1);
        out1_Close.setVisible(true);
        out1_Open.setVisible(false);
    }

    public void setOut2_Open(){
        service.setGPIO(reader, (byte) 2, (byte) 1);
        out2_Close.setVisible(true);
        out2_Open.setVisible(false);
    }

    public void setOut1_Close(){
        service.setGPIO(reader, (byte) 1, (byte) 0);
        out1_Close.setVisible(false);
        out1_Open.setVisible(true);
    }
    public void setOut2_Close(){
        service.setGPIO(reader, (byte) 2, (byte) 0);
        out2_Close.setVisible(false);
        out2_Open.setVisible(true);
    }

}
