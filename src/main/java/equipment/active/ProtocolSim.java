package equipment.active;

/**
 * @ClassName: ProtocolSim
 * @ProjectName WorkStation
 * @Description: 填入表格中的数据
 * @Author 15791
 * @Aate 2021/2/316:18
 */
public class ProtocolSim {
    private String labelID;      //4个字节标签ID
    private String address;      //设备地址
    private String labelStatus;  //标签状态，高电量+不按按键：00，高电量+按下按键：01，低电量：08
    private String RSSI;         //RSSI值
    private int count;           //标签的数量

    public String getLabelID() {
        return labelID;
    }

    public void setLabelID(String labelID) {
        this.labelID = labelID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLabelStatus() {
        return labelStatus;
    }

    public void setLabelStatus(String labelStatus) {
        this.labelStatus = labelStatus;
    }

    public String getRSSI() {
        return RSSI;
    }

    public void setRSSI(String RSSI) {
        this.RSSI = RSSI;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

//    @Override
//    public String toString() {
//        return  "labelID='" + labelID + ' ' +
//                "address='" + address + ' ' +
//                "labelStatus='" + labelStatus + ' ' +
//                "RSSI='" + RSSI + ' ' +
//                "count=" + count +'\'';
//    }


    /**
     * 重写equals方法
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(this == obj){
            return true;
        }
        if(obj instanceof ProtocolSim){
            ProtocolSim protocolSim=(ProtocolSim)obj;
            if(protocolSim.getLabelID().equals(this.labelID)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
}
