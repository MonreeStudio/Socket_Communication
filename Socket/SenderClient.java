package Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
 *   client线程主要是负责：
 *   1.发送信息
 *   2.一直接收信息，并解析
 * */
public class SenderClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 9999);
             //开启一个线程接收信息，并解析
            ClientThread thread=new ClientThread(socket);
            thread.setName("ReceiverClient");
            thread.start();
            //主线程用来发送信息
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out=new PrintWriter(socket.getOutputStream());
            while(true)
            {
                System.out.println("SenderClient，请输入要发送的消息：");
                String s=br.readLine();
                //out.println(s);
                out.write("SenderClient,1908," + s + "\n");
                out.flush();
            }
        }catch(Exception e){
            System.out.println("服务器异常");
        }
    }
}
