package equipment.active;

import utils.convert.DataConvert;

/**
 * @ClassName: ProtocolParsing
 * @ProjectName WorkStation
 * @Description: 解析有源标签的各种协议抽象类，采用简单工厂模式
 * @Author 15791
 * @Aate 2021/2/411:07
 */
public class ProtocolParsing {

    byte[] labelID = new byte[4];        //标签数据
    byte[] deviceAddress = new byte[2];  //上传数据的设备地址
    byte[] RSSIValue = new byte[1];      //RSSI值
    /**
     *  解析有源电子标签数据长度为11位或12位的数据
     * @param bytes 需要解析的数据
     * @return 返回解析后的数据
     */
    ProtocolSim parsingData(byte[] bytes) {
        ProtocolSim protocolSim = new ProtocolSim();
        String num;
        int length = bytes.length;           //数据的长度

        if (check(bytes)){
            switch (length){
                case 11:
                    deviceAddress[1] = bytes[1];
                    break;
                case 12:
                    deviceAddress[0] = bytes[1];
                    deviceAddress[1] = bytes[2];
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + length);
            }
            protocolSim.setAddress(DataConvert.bytesToHexString(deviceAddress));
            System.arraycopy(bytes, length - 7, labelID, 0, 4);
            num = Integer.toString(DataConvert.byteArrayToInt(labelID));
            protocolSim.setLabelID("00000" + num);
            protocolSim.setLabelStatus(judgeLabelStatus(bytes[length - 3]));
            RSSIValue[0] = bytes[length - 2];
//            protocolSim.setRSSI(DataConvert.bytesToHexString(RSSIValue));
            protocolSim.setRSSI(((Byte)bytes[length - 2]).toString());
            protocolSim.setCount(1);
            return protocolSim;
        }else {
            return null;
        }
    }


    /**
     * 校验数据是否正确，校验方式，各字节相加，取反加1
     * @param bytes 需要校验的数据
     * @return 返回校验过后的数据状态
     */
    private Boolean check(byte[] bytes) {
        byte tem = (byte) 0x00;
        for (int i = 0; i < bytes.length - 1; i++) {
            tem += bytes[i];
        }
        byte i = (byte) ((byte) ~tem + (byte) 0x01);
        return i == bytes[bytes.length - 1];
    }

    /**
     * 判断标签的状态
     * @param bytes 标签状态字节
     * @return 返回标签状态
     */
    private String judgeLabelStatus(byte bytes){
        switch (bytes){
            case 0: return "脱离";
            case 1: return "正常";
            case 8: return "低电量";
            default:return "状态错误";
        }

    }
}