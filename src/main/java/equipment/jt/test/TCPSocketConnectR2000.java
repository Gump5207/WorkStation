package equipment.jt.test;

import com.rfid.uhf.controller.impl.ReaderR2000;
import com.rfid.uhf.service.ReaderR2000Service;
import com.rfid.uhf.service.impl.ReaderR2000ServiceImpl;
import equipment.jt.CallBackR2000;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TCPSocketConnectR2000 {

    private ServerSocket serverSocket = null;
    private boolean isStartServer = false;
    private Thread sockets;
    private static String device = null;
    public static List<ReaderR2000> list = new ArrayList<ReaderR2000>();
    public ReaderR2000Service service = new ReaderR2000ServiceImpl();

    public static void main(String[] args) throws InterruptedException {
        TCPSocketConnectR2000 connect = new TCPSocketConnectR2000();
        ReaderR2000Service service = new ReaderR2000ServiceImpl();
        int port = 20058;
        connect.startServer(port);
        do {
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    service.stopInv(list.get(i));
                    service.beginInvV2(list.get(i));
                    Thread.sleep(10000 * 2);
                    service.stopInv(list.get(i));
                    service.disconnect(list.get(i));
                }
                break;
            }
//            System.out.println(list);
            Thread.sleep(500);
        } while (true);
        connect.stopServer();
    }

    public boolean startServer(int port, int backlog, String bindAddrIP) {
        try {
            serverSocket = new ServerSocket(port, backlog, InetAddress.getByName(bindAddrIP));
            isStartServer = true;
            if (isStartServer) {
                sockets = new Thread(new sockets());
                sockets.start();
            }
            return true;
        } catch (IOException e1) {
            e1.printStackTrace();
            isStartServer = false;
            return false;
        }
    }

    public boolean startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            isStartServer = true;
            if (isStartServer) {
                sockets = new Thread(new sockets());
                sockets.start();
            }
            return true;
        } catch (IOException e1) {
            e1.printStackTrace();
            isStartServer = false;
        }
        return false;
    }


    public class sockets implements Runnable {
        ReaderR2000 reader;
        int ports = 0;
        String socketName = null;

        public void run() {
            while (isStartServer) {
                try {
                    Socket client = serverSocket.accept();
                    if (null != client) {
                        System.out.println(client.getInetAddress().getHostName());
                        reader = service.tcpServerConnect(client, new CallBackR2000());
                        service.setWorkMode(reader,3);
//                        service.setTriggerModeAlertTime(reader,(byte)10);
//                        service.setGPIO(reader,(byte) 1,(byte) 0);
                        service.beginInvV2(reader);
                        //service.stopInv(reader);
                        list.add(reader);
                        sockets = new Thread(new SocketConnectStatus(client, device));
                        sockets.start();
                    }
                    while (true){
                        System.out.println(service.getGPIO(reader));
                        Thread.sleep(1000);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class SocketConnectStatus implements Runnable {
        private Socket socket;

        public SocketConnectStatus(Socket socket, String device) {
            this.socket = socket;
        }

        public void run() {
            boolean flag = true;
            try {
                while (flag) {
                    socket.setOOBInline(true);
                    socket.sendUrgentData(0xFF);
                    Thread.sleep(1 * 5000);
                }
            } catch (Exception e) {
                flag = false;
                e.printStackTrace();
            }
        }
    }

    public boolean stopServer() {
        try {
            if (isStartServer) {
                isStartServer = false;
                Thread.sleep(5);
                serverSocket.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
