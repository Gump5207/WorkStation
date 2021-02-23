package equipment.weigh;

import utils.serialport.SerialPort;

import java.util.List;

/**
 * @ClassName: Weigh
 * @ProjectName WorkStation
 * @Description:
 * @Author 15791
 * @Aate 2020/9/211:51
 */
public class Weigh {
    private SerialPort serialController;

    public static void main(String[] args) {
        List<String> systemPort = SerialPort.getSystemPort();
        for (String port :
                systemPort) {
            System.out.println(port);
        }
    }
}
