package equipment.active;

/**
 * @ClassName: AntennaConfiguration
 * @ProjectName WorkStation
 * @Description: 天线配置协议类
 * @Author 15791
 * @Aate 2021/2/417:42
 */
public class AntennaConfiguration {
    private byte FH;         //帧头
    private byte address;    //地址
    private byte length;     //长度
    private byte order;      //命令
    private byte RSSI;       //RSSI值
    private byte check;      //校验码

    public byte getFH() {
        return FH;
    }

    public void setFH(byte FH) {
        this.FH = FH;
    }

    public byte getAddress() {
        return address;
    }

    public void setAddress(byte address) {
        this.address = address;
    }

    public byte getLength() {
        return length;
    }

    public void setLength(byte length) {
        this.length = length;
    }

    public byte getOrder() {
        return order;
    }

    public void setOrder(byte order) {
        this.order = order;
    }

    public byte getRSSI() {
        return RSSI;
    }

    public void setRSSI(byte RSSI) {
        this.RSSI = RSSI;
    }

    public byte getCheck() {
        return check;
    }

    public void setCheck(byte check) {
        this.check = check;
    }
}
