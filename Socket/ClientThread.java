package Socket;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
/**
 *   作用：一直接收服务端转发过来的信息
 * */
public class ClientThread extends Thread {

    private Socket socket;
    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            try {
                while (true) {
                    String msg=br.readLine();
                    if(msg == null)
                        break;
                    System.out.println("\n" + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
