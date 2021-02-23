package equipment.weigh;

/**
 * @ClassName: Message
 * @ProjectName WorkStation
 * @Description: 报文的组成部分：设备地址、寄存器地址、寄存器值、检验值
 * @Author 15791
 * @Aate 2020/9/214:34
 */
public final class Message {
    public final String LOCAL_ADDRESS = "1F";
    public final String READ_WEIGH = LOCAL_ADDRESS + "03002A0001A67C";
    private String deviceAddress;
    private String registerAddress;
    private String registerValue;
    private String CRC16;


    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public String getRegisterValue() {
        return registerValue;
    }

    public void setRegisterValue(String registerValue) {
        this.registerValue = registerValue;
    }

    public String getCRC16() {
        return CRC16;
    }

    public void setCRC16(String CRC16) {
        this.CRC16 = CRC16;
    }
}
