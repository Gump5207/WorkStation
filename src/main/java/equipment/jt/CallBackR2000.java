package equipment.jt;

import com.rfid.callBack.CallBack.R2000;

public class CallBackR2000 implements R2000 {

	public void onceReadTagEnd(Boolean result) {
		
	}

	public void readData(String data, String rssi, String antennaNo, String deviceNo,String direction,String communicationMode){
		System.out.println("EPC " + data + " RSSI " + rssi + " antennaNo " + antennaNo + " deviceNo " + deviceNo+ "dec" + direction+ "communicationMode" + communicationMode);
	}

	public void serialPortException(String name, boolean result) {
		
	}
}