package equipment.jt.test;

import com.rfid.uhf.controller.impl.ReaderR2000;
import com.rfid.uhf.service.ReaderR2000Service;
import com.rfid.uhf.service.impl.ReaderR2000ServiceImpl;
import equipment.jt.CallBackR2000;

public class R2000Demo {
	public static void main(String[] args) throws InterruptedException {
		ReaderR2000Service service = new ReaderR2000ServiceImpl();
		ReaderR2000 reader = service.connect("172.16.38.233", 0, new CallBackR2000());
		//ReaderR2000 reader = service.connect("COM3", 0, new CallBackR2000());
		if(null == reader){
			return;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(service.beginInvV2(reader));
			}
		}).start();
		while (true){
			System.out.println(service.getGPIO(reader));
			Thread.sleep(1000);

		}
//		service.beginInvV2(reader);
////		Thread.sleep(1000 * 10);
//
//		service.stopInvV2(reader);
	}
}


