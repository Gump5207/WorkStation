package utils.serialport;

import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

/**
 * @ClassName: SerialPort
 * @ProjectName SerialPort
 * @Description: TODO
 * @Author 15791
 * @Aate 2020/8/2617:53
 */
public class SerialPort {
    private gnu.io.SerialPort serialPort;

    /**
     * 获得系统可用端口的列表
     *
     * @return 可用的端口名称列表
     */
    @SuppressWarnings("unchecked")
    public static List<String> getSystemPort() {
        List<String> systemPorts = new ArrayList<>();
        //获得系统可用的端口
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            String portName = portList.nextElement().getName();
            systemPorts.add(portName);
        }
//        System.out.println("系统可用端口列表： " + systemPorts);
        return systemPorts;
    }

    /**
     * 开启串口
     * @param name     串口名称
     * @param baudRate 波特率
     * @return 串口对象
     */
    public boolean open(String name, int baudRate) {
        try {
            // 通过端口名称得到端口
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(name);
            // 打开端口  自定义名字，打开超时时间
            CommPort commPort = portIdentifier.open(name, 2222);
            // 判断是不是串口
            if (commPort instanceof gnu.io.SerialPort) {
                serialPort = (gnu.io.SerialPort) commPort;
                //设置串口参数（波特率，数据位8， 停止位1， 校验位无）
                serialPort.setSerialPortParams(baudRate, gnu.io.SerialPort.DATABITS_8, gnu.io.SerialPort.STOPBITS_1, gnu.io.SerialPort.PARITY_NONE);
                return true;
            } else {
                //是其他类型端口
                throw new NoSuchPortException();
            }
        } catch (UnsupportedCommOperationException | NoSuchPortException | PortInUseException e) {
            serialPort.close();
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 关闭串口
     */
    public void close() {
        if (serialPort != null) {
            serialPort.close();
            serialPort = null;
        }
    }

    /**
     * 向串口发送数据
     *
     * @param data 发送的数据
     */
    public void sendData(byte[] data) {
        OutputStream os = null;
        try {
            os = serialPort.getOutputStream(); //获取串口的输出流
            os.write(data);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                    os = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取串口数据
     *
     * @return 返回串口数据
     */
    public byte[] readData() {
        InputStream is = null;
        byte[] bytes = null;
        try {
            is = serialPort.getInputStream(); //获取输入流
            int bufflenth = is.available(); //获取数据长度
            while (bufflenth != 0) {
                bytes = new byte[bufflenth];
                int read = is.read(bytes);
                bufflenth = is.available();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    /**
     * 监听事件
     */
    public void setListenerToSerialPort(SerialPortEventListener listener) {
        try {
            //给串口添加事件监听
            serialPort.addEventListener(listener);
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }
        serialPort.notifyOnDataAvailable(true);//串口有数据监听
        serialPort.notifyOnBreakInterrupt(true);//中断事件监听
    }
}
